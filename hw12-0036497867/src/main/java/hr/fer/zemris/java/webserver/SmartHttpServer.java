package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Simple web server that provides functionality to process HTTP requests
 * and generating corresponding responses.<br>
 * It uses a fixed-size thread pool for processing the requests.<br>
 * Most of the attributes are initialized using the server configuration file.<br>
 * When the program starts with a valid configuration path argument, the server
 * is constructed and the server thread is started.
 * 
 * @author Mate Gasparini
 */
public class SmartHttpServer {
	
	/** Duration (in ms) of the interval between every sessions map cleaning. */
	private static final long SESSION_CLEANER_PERIOD = 300_000;
	
	/** Session ID length. */
	private static final int SID_LENGTH = 20;
	
	/** Server's IP address. */
	@SuppressWarnings("unused")
	private String address;
	
	/** Server's domain name. */
	private String domainName;
	
	/** Port used by the server. */
	private int port;
	
	/** Number of worker threads. */
	private int workerThreads;
	
	/** Cookie session duration. */
	private int sessionTimeout;
	
	/** Map containing all supported mime types. */
	private Map<String, String> mimeTypes;
	
	/** Server thread that delegates accepted client requests to the worker threads. */
	private ServerThread serverThread;
	
	/** Worker threads' thread pool. */
	private ExecutorService threadPool;
	
	/** Server's web root directory */
	private Path documentRoot;
	
	/** Map containing all supported {@link IWebWorker}s. */
	private Map<String, IWebWorker> workersMap;
	
	/** Map containing all currently active sessions (mapped using session IDs). */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	
	/** Random number generator used for generating session IDs. */
	private Random sessionRandom = new Random();
	
	/**
	 * Constructor specifying the file name of the server configuration file name.
	 * 
	 * @param configFileName Name/path to the configuration file.
	 */
	public SmartHttpServer(String configFileName) {
		Properties serverProperties = new Properties();
		try {
			serverProperties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch (IOException e) {
			throw new IllegalArgumentException(
				"Invalid server config file path: " + configFileName);
		}
		
		address = serverProperties.getProperty("server.address");
		domainName = serverProperties.getProperty("server.domainName");
		port = Integer.parseInt(serverProperties.getProperty("server.port"));
		workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
		documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
		
		initMimeTypes(serverProperties.getProperty("server.mimeConfig"));
		initWorkersMap(serverProperties.getProperty("server.workers"));
		
		serverThread = new ServerThread();
		startSessionCleaner();
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments.
	 * 			Expected exactly 1 argument - path to the server configuration file.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected 1 argument (path to server.properties).");
			return;
		}
		
		new SmartHttpServer(args[0]).start();
	}
	
	/**
	 * Starts the server thread and initializes the worker threads.
	 */
	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}
	
	/**
	 * Interrupts the server thread and shuts down the worker thread pool.
	 */
	protected synchronized void stop() {
		if (serverThread.isAlive()) {
			serverThread.interrupt();
		}
		threadPool.shutdown();
	}
	
	/**
	 * Initializes the {@code Map} of mime types.
	 * 
	 * @param configFileName Path to the mime configuration file.
	 */
	private void initMimeTypes(String configFileName) {
		mimeTypes = new HashMap<>();
		Properties mimeProperties = new Properties();
		try {
			mimeProperties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch (IOException ex) {
			throw new IllegalArgumentException(
				"Invalid mime config file path: " + configFileName);
		}
		for (String propertyName : mimeProperties.stringPropertyNames()) {
			String propertyValue = mimeProperties.getProperty(propertyName);
			mimeTypes.put(propertyName, propertyValue);
		}
	}
	
	/**
	 * Initializes the {@code Map} of supported workers.
	 * 
	 * @param configFileName Path to the workers configuration file.
	 */
	private void initWorkersMap(String configFileName) {
		workersMap = new HashMap<>();
		Properties workersProperties = new Properties();
		try {
			workersProperties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch (IOException ex) {
			throw new IllegalArgumentException(
				"Invalid workers config file path: " + configFileName);
		}
		for (String propertyName : workersProperties.stringPropertyNames()) {
			String path = propertyName.substring(1);
			if (workersMap.containsKey(path)) {
				throw new IllegalArgumentException(
					"Worker path '" + path + "' declared more than once.");
			}
			String fqcn = workersProperties.getProperty(propertyName);
			try {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.getConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(path, iww);
			} catch (ReflectiveOperationException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Starts the daemonic session cleaner thread.
	 */
	private void startSessionCleaner() {
		Thread sessionCleaner = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(SESSION_CLEANER_PERIOD);
				} catch (InterruptedException ignorable) {}
				synchronized (sessions) {
					sessions.entrySet().removeIf(
						session -> !SmartUtil.timestampStillValid(
							session.getValue().validUntil
						)
					);
				}
			}
		});
		sessionCleaner.setDaemon(true);
		sessionCleaner.start();
	}
	
	/**
	 * Server thread used for accepting client requests and delegating them
	 * to the worker thread pool.
	 * 
	 * @author Mate Gasparini
	 */
	protected class ServerThread extends Thread {
		
		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * {@code Runnable} class used for processing a request from the specified
	 * client socket.
	 * 
	 * @author Mate Gasparini
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/** URL path used for any web worker instantiation and request processing. */
		private static final String EXT = "ext/";
		
		/** Any web worker's package (without the class name). */
		private static final String WORKERS_PACKAGE = "hr.fer.zemris.java.webserver.workers.";
		
		/** URL path to the private directory. */
		private static final String PRIVATE = "private";
		
		/** Specified client socket. */
		private Socket csocket;
		
		/** Wrapped client socket's input stream. */
		private PushbackInputStream istream;
		
		/** Client socket's output stream. */
		private OutputStream ostream;
		
		/** Request's HTTP version. */
		private String version;
		
		/** Request's HTTP method. */
		private String method;
		
		/** Exact requested host. */
		private String host;
		
		/** Map of request's parameters. */
		private Map<String, String> params = new HashMap<>();
		
		/** Map of request's temporary parameters. */
		private Map<String, String> tempParams = new HashMap<>();
		
		/** Map of request's permanent parameters. */
		private Map<String, String> permParams = new HashMap<>();
		
		/** List of output cookies. */
		private List<RCCookie> outputCookies = new ArrayList<>();
		
		/** Request's session ID. */
		private String SID;
		
		/** Request context reference. */
		private RequestContext context;
		
		/**
		 * Constructor specifying the client socket.
		 * 
		 * @param csocket The specified client socket.
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
				List<String> request = SmartUtil.readRequest(istream);
				if (request.isEmpty()) {
					sendError(400, "Bad request");
					return;
				}
				
				String firstLine = request.get(0);
				String[] firstLineParts = firstLine.split("\\s+");
				if (firstLineParts.length != 3) {
					sendError(400, "Bad request");
					return;
				}
				
				method = firstLineParts[0].toUpperCase();
				String requestedPath = firstLineParts[1];
				version = firstLineParts[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")
						|| !method.equals("GET")) {
					sendError(400, "Bad request");
					return;
				}
				
				setHost(request);
				checkSession(request);
				
				String[] requestedPathParts = requestedPath.split("\\?", 2);
				String path = requestedPathParts[0];
				if (requestedPathParts.length != 1) {
					parseParameters(requestedPathParts[1]);
				}
				
				internalDispatchRequest(path, true);
			} catch (Exception ex) {
				try {
					csocket.close();
				} catch (IOException ignorable) {}
				ex.printStackTrace();
			}
		}
		
		/**
		 * Parses all parameters from the given String
		 * and stores them in the map of parameters.
		 * 
		 * @param paramString The given String of parameters.
		 */
		private void parseParameters(String paramString) {
			String[] parameters = paramString.split("&");
			for (String parameter : parameters) {
				String parts[] = parameter.split("=", 2);
				params.put(parts[0], parts.length > 1 ? parts[1] : null);
			}
		}
		
		/**
		 * Sets the host to the request's host header value,
		 * or if none present, sets it to the server's domain name.
		 * 
		 * @param request
		 */
		private void setHost(List<String> request) {
			host = domainName;
			for (int i = 1, size = request.size(); i < size; i ++) {
				String line = request.get(i);
				if (line.toLowerCase().startsWith("host:")) {
					host = line.split(":")[1].trim();
					return;
				}
			}
		}
		
		/**
		 * Checks if the given lines of request contain a cookie header and,
		 * if it does, it's session ID is checked for validity.<br>
		 * The corresponding session entry in the {@code Map} of sessions
		 * is updated or removed and re-generated.<br>
		 * Also, an {@link RCCookie} is added to the output cookies.
		 * 
		 * @param request The given lines of request.
		 */
		private void checkSession(List<String> request) {
			String[] cookies = SmartUtil.extractCookies(request);
			permParams = new ConcurrentHashMap<>();
			
			synchronized (sessions) {
				if (cookies != null) {
					String sidCandidate = SmartUtil.getSIDCandidate(cookies, permParams);
					
					if (sidCandidate != null && sessions.containsKey(sidCandidate)) {
						SessionMapEntry sessionEntry = sessions.get(sidCandidate);
						if (sessionEntry.host.equals(host)) {
							if (SmartUtil.timestampStillValid(sessionEntry.validUntil)) {
								sessionEntry.updateValidUntil(sessionTimeout);
								permParams = sessionEntry.map;
								return;
							} else {
								sessions.remove(sidCandidate);
							}
						}
					}
				}
				
				generateNewSID();
				sessions.put(
					SID,
					new SessionMapEntry(SID, host, sessionTimeout, permParams)
				);
			}
			outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
		}
		
		/**
		 * Generates a new session ID and stores it in the corresponding attribute.<br>
		 * It is generated randomly and contains {@code SID_LENGHT} uppercase English
		 * letters.<br>
		 * Should be synchronized using the generator, but called only inside an
		 * existing synchronized block.
		 */
		private void generateNewSID() {
			final int alphabetLength = 26;
			
			char[] sequence = new char[SID_LENGTH];
			for (int i = 0; i < SID_LENGTH; i ++) {
				sequence[i] = (char) ('A' + sessionRandom.nextInt(alphabetLength));
			}
			
			SID = new String(sequence);
		}
		
		/**
		 * Writes the error response to the client using the given status
		 * code and the given status text.
		 * 
		 * @param statusCode The given status code.
		 * @param statusText The given status text.
		 * @throws IOException If an I/O error occurs.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(
				("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
				"Server: Smart Http Server\r\n"+
				"Content-Type: text/plain;charset=UTF-8\r\n"+
				"Content-Length: 0\r\n"+
				"Connection: close\r\n"+
				"\r\n").getBytes(StandardCharsets.UTF_8)
			);
			csocket.close();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Processes the request specified by the given URL path.<br>
		 * If the given boolean flag is set to {@code true}, access to
		 * the private directory is granted (otherwise it is forbidden).
		 * 
		 * @param urlPath The given URL path.
		 * @param directCall The given boolean flag which marks the directness of the call.
		 * @throws Exception If some type of error (e.g. I/O) occurs.
		 */
		public void internalDispatchRequest(String urlPath,
				boolean directCall) throws Exception {
			urlPath = urlPath.substring(1);
			
			if (directCall &&
					(urlPath.equals(PRIVATE) || urlPath.startsWith(PRIVATE.concat("/")))) {
				sendError(404, "File not found");
				return;
			}
			
			if (context == null) {
				context = new RequestContext(
					ostream, params, permParams, outputCookies, tempParams, this
				);
			}
			context.setStatusCode(200);
			context.setStatusText("OK");
			
			if (workersMap.containsKey(urlPath)) {
				workersMap.get(urlPath).processRequest(context);
				csocket.close();
				return;
			}
			
			if (urlPath.startsWith(EXT)) {
				processExtRequest(urlPath);
				csocket.close();
				return;
			}
			
			Path resolvedPath = documentRoot.resolve(urlPath);
			if (!resolvedPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}
			if (!Files.isRegularFile(resolvedPath) || !Files.isReadable(resolvedPath)) {
				sendError(404, "File not found");
				return;
			}
			
			String fileName = resolvedPath.getFileName().toString();
			if (fileName.startsWith(".")) {
				// Remove leading dot from hidden files' file names.
				fileName = fileName.substring(1);
			}
			
			String mimeType = "application/octet-stream";
			String[] fileNameParts = fileName.split("\\.", 2);
			String fileExtension = null;
			if (fileNameParts.length == 2) {
				fileExtension = fileNameParts[1];
				if (mimeTypes.containsKey(fileExtension)) {
					mimeType = mimeTypes.get(fileExtension);
				}
			}
			context.setMimeType(mimeType);
			
			if ("smscr".equals(fileExtension)) {
				processSmartScript(resolvedPath);
			} else {
				processStaticFile(resolvedPath);
			}
			csocket.close();
		}
		
		/**
		 * Instantiates the {@link IWebWorker} using the given URL path
		 * and processes its request using the specified request context.
		 * 
		 * @param urlPath The given URL path.
		 * @throws Exception If some type of error (e.g. I/O) occurs.
		 */
		private void processExtRequest(String urlPath) throws Exception {
			String className = urlPath.substring(EXT.length());
			String fqcn = WORKERS_PACKAGE.concat(className);
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.getConstructor().newInstance();
			IWebWorker iww = (IWebWorker) newObject;
			iww.processRequest(context);
		}
		
		/**
		 * Executes the smart script located at the given path.
		 * 
		 * @param resolvedPath The given path.
		 * @throws IOException If an I/O error occurs.
		 */
		private void processSmartScript(Path resolvedPath) throws IOException {
			String script = new String(
				Files.readAllBytes(resolvedPath),
				StandardCharsets.UTF_8
			);
			new SmartScriptEngine(
				new SmartScriptParser(script).getDocumentNode(),
				context
			).execute();
		}
		
		/**
		 * Writes the content of the static file (e.g. text, image)
		 * given by the specified path.<br>
		 * The content length is also provided to the request context
		 * because it is always known.
		 * 
		 * @param resolvedPath The specified path.
		 * @throws IOException If an I/O error occurs.
		 */
		private void processStaticFile(Path resolvedPath) throws IOException {
			context.setContentLength(Files.size(resolvedPath));
			try (InputStream fis = Files.newInputStream(resolvedPath)) {
				byte[] buffer = new byte[1024];
				while (true) {
					int readCount = fis.read(buffer);
					if (readCount < 1) break;
					context.write(buffer, 0, readCount);
				}
			}
		}
	}
	
	/**
	 * Map entry representing a session with its session ID used as the key.
	 * 
	 * @author Mate Gasparini
	 */
	private static class SessionMapEntry {
		
		/** Session ID. */
		@SuppressWarnings("unused")
		String sid;
		
		/** Exact session host. */
		String host;
		
		/** Timestamp representing the time until the session is valid. */
		long validUntil;
		
		/** Map of all session's cookies. */
		Map<String, String> map;
		
		/**
		 * Constructor specifying the session ID, the host, the cookie map and
		 * the session timeout used for calculating the <i>valid until</i> timestamp.
		 * 
		 * @param sid The specified session ID.
		 * @param host The exact specified session host.
		 * @param timeout Session timeout value.
		 * @param map The specified cookie map.
		 */
		public SessionMapEntry(String sid, String host,
				int timeout, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			updateValidUntil(timeout);
			this.map = map;
		}
		
		/**
		 * Updates the <i>valid until</i> timestamp using the given timeout value.
		 * 
		 * @param timeout The given timeout value.
		 */
		public void updateValidUntil(int timeout) {
			validUntil = SmartUtil.generateUntilValidTimestamp(timeout);
		}
	}
}
