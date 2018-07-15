package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class which contains context for the simple web server.
 * 
 * @author Mate Gasparini
 */
public class RequestContext {
	
	/** Default encoding name. */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	/** Default status code. */
	private static final int DEFAULT_STATUS_CODE = 200;
	
	/** Default status text. */
	private static final String DEFAULT_STATUS_TEXT = "OK";
	
	/** Default mime type. */
	private static final String DEFAULT_MIME_TYPE = "text/html";
	
	/** Underlying stream where data is written. */
	private OutputStream outputStream;
	
	/** Charset used for writing to the output stream. */
	private Charset charset;
	
	/** Name of the encoding used for writing to the output stream. */
	private String encoding = DEFAULT_ENCODING;
	
	/** Status code of the request.. */
	private int statusCode = DEFAULT_STATUS_CODE;
	
	/** Status text of the request. */
	private String statusText = DEFAULT_STATUS_TEXT;
	
	/** Mime type of the request content. */
	private String mimeType = DEFAULT_MIME_TYPE;
	
	/** Map of parameter names mapped to their values. */
	private Map<String, String> parameters;
	
	/** Map of temporary parameter names mapped to their values. */
	private Map<String, String> temporaryParameters;
	
	/** Map of persistent parameter names mapped to their values. */
	private Map<String, String> persistentParameters;
	
	/** List of request cookies. */
	private List<RCCookie> outputCookies;
	
	/** Once set to true, it marks that the header has been generated. */
	private boolean headerGenerated;
	
	/** Size of the requested content (in bytes). */
	private Long contentLength;
	
	/** Read-only dispatcher. */
	private IDispatcher dispatcher;
	
	/**
	 * Constructor specifying the output stream (and possibly other parameters).<br>
	 * If one or more of the other parameters are {@code null}, they are initialized
	 * to default empty collections ({@link HashMap} and {@link ArrayList}).
	 * 
	 * @param outputStream The specified output stream.
	 * @param parameters The specified {@code Map} of parameters.
	 * @param persistentParameters The specified {@code Map} of persistent parameters.
	 * @param outputCookies The specified {@code List} of request cookies.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null);
	}
	
	/**
	 * Constructor specifying the output stream (and possibly other parameters).<br>
	 * If one or more of the other parameters are {@code null}, they are initialized
	 * to default empty collections ({@link HashMap} and {@link ArrayList}).
	 * 
	 * @param outputStream The specified output stream.
	 * @param parameters The specified {@code Map} of parameters.
	 * @param persistentParameters The specified {@code Map} of persistent parameters.
	 * @param outputCookies The specified {@code List} of request cookies.
	 * @param temporaryParameters The specified {@code Map} of temporary parameters.
	 * @param dispatcher The specified dispatcher.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		Objects.requireNonNull(outputStream, "Output stream must not be null.");
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ?
									new HashMap<>() : persistentParameters;
		this.temporaryParameters = new HashMap<>();
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.temporaryParameters = temporaryParameters == null ?
									new HashMap<>() : temporaryParameters;
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Sets the encoding name to the given encoding name.
	 * 
	 * @param encoding The given encoding name.
	 * @throws RuntimeException If the header has already been generated.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) throw new RuntimeException("Header already generated.");
		this.encoding = encoding;
	}
	
	/**
	 * Sets the status code to the given status code.
	 * 
	 * @param statusCode The given status code.
	 * @throws RuntimeException If the header has already been generated.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) throw new RuntimeException("Header already generated.");
		this.statusCode = statusCode;
	}
	
	/**
	 * Sets the status text to the given status text.
	 * 
	 * @param statusText The given status text.
	 * @throws RuntimeException If the header has already been generated.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) throw new RuntimeException("Header already generated.");
		this.statusText = statusText;
	}
	
	/**
	 * Sets the mime type to the given mime type.
	 * 
	 * @param mimeType The given mime type.
	 * @throws RuntimeException If the header has already been generated.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) throw new RuntimeException("Header already generated.");
		this.mimeType = mimeType;
	}
	
	/**
	 * Sets the content length to the given value (in bytes).
	 * 
	 * @param contentLength The given value.
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated) throw new RuntimeException("Header already generated.");
		this.contentLength = contentLength;
	}
	
	/**
	 * Returns the specified dispatcher.
	 * 
	 * @return The specified dispatcher.
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Returns the parameter value corresponding to the given key.
	 * 
	 * @param name The given key (name of the parameter).
	 * @return The corresponding parameter value,
	 * 			or {@code null} if no association exists.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Returns a read-only {@code Set} of names of all parameters.
	 * 
	 * @return A read-only {@code Set} of parameters' names.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Returns the persistent parameter corresponding to the given key.
	 * 
	 * @param name The given key (name of the persistent parameter).
	 * @return The corresponding persistent parameter value,
	 * 			or {@code null} if no association exists.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Returns a read-only {@code Set} of names of all persistent parameters.
	 * 
	 * @return A read-only {@code Set} of persistent parameters' names.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Stores the given pair to the persistent parameters map.
	 * 
	 * @param name The given persistent parameter name (key).
	 * @param value The given persistent parameter value.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes the persistent parameter value corresponding to the given key.
	 * 
	 * @param name The given key (name of the persistent parameter).
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Returns the temporary parameter corresponding to the given key.
	 * 
	 * @param name The given key (name of the temporary parameter).
	 * @return The corresponding temporary parameter value,
	 * 			or {@code null} if no association exists.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Returns a read-only {@code Set} of names of all temporary parameters.
	 * 
	 * @return A read-only {@code Set} of temporary parameters' names.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Stores the given pair to the temporary parameters map.
	 * 
	 * @param name The given temporary parameter name (key).
	 * @param value The given temporary parameter value.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes the temporary parameter value corresponding to the given key.
	 * 
	 * @param name The given key (name of the temporary parameter).
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Adds the given cookie to the {@code List} of the request cookies.
	 * 
	 * @param cookie The given cookie.
	 * @throws RuntimeException If the header has already been generated.
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated) throw new RuntimeException("Header already generated.");
		outputCookies.add(cookie);
	}
	
	/**
	 * Writes {@code len} bytes from the given byte array starting at the given offset
	 * to the underlying output stream.<br>
	 * Before that, the header may also generated and written to the underlying
	 * output stream (if this has not been done yet).
	 * 
	 * @param data The given byte array.
	 * @param offset The given offset.
	 * @param len The number of bytes to write.
	 * @return Reference to this {@code RequestContext}
	 * @throws IOException If an I/O error occurs.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		
		outputStream.write(data, offset, len);
		
		return this;
	}
	
	/**
	 * Writes {@code data.length} bytes from the given byte array to the
	 * underlying output stream.<br>
	 * Before that, the header may also generated and written to the underlying
	 * output stream (if this has not been done yet).
	 * 
	 * @param data The given byte array.
	 * @return Reference to this {@code RequestContext}.
	 * @throws IOException If an I/O error occurs.
	 */
	public RequestContext write(byte[] data) throws IOException {
		return write(data, 0, data.length);
	}
	
	/**
	 * Writes {@code text.length} bytes from the byte array generated from
	 * the given text (by using the current context charset) to the
	 * underlying output stream.<br>
	 * Before that, the header may also generated and written to the underlying
	 * output stream (if this has not been done yet).
	 * 
	 * @param text The given text.
	 * @return Reference to this {@code RequestContext}.
	 * @throws IOException If an I/O error occurs.
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader(); // Redundant, but charset must be set.
		}
		
		return write(text.getBytes(charset));
	}
	
	/**
	 * Generates the header and writes it to the underlying output stream.
	 * 
	 * @throws IOException If an I/O error occurs.
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		
		StringBuilder builder = new StringBuilder("HTTP/1.1 ");
		builder.append(statusCode).append(" ").append(statusText).append("\r\n");
		
		if (contentLength != null) {
			builder.append("Content-Length: ").append(contentLength).append("\r\n");
		}
		
		builder.append("Content-Type: ").append(mimeType);
		if (mimeType.startsWith("text/")) {
			builder.append("; charset=").append(encoding);
		}
		builder.append("\r\n");
		
		for (RCCookie cookie : outputCookies) {
			builder.append("Set-Cookie: ")
					.append(cookie.name).append("=\"").append(cookie.value).append("\"");
			if (cookie.domain != null) builder.append("; Domain=").append(cookie.domain);
			if (cookie.path != null) builder.append("; Path=").append(cookie.path);
			if (cookie.maxAge != null) builder.append("; Max-Age=").append(cookie.maxAge);
			builder.append("; HttpOnly\r\n");
		}
		builder.append("\r\n");
		
		outputStream.write(builder.toString().getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;
	}
	
	/**
	 * Class representing a cookie which contains multiple read-only properties.
	 * 
	 * @author Mate Gasparini
	 */
	public static class RCCookie {
		
		/** Read-only name. */
		private final String name;
		
		/** Read-only value. */
		private final String value;
		
		/** Read-only domain. */
		private final String domain;
		
		/** Read-only path. */
		private final String path;
		
		/** Read-only maximum age. */
		private final Integer maxAge;
		
		/**
		 * Constructor specifying all read-only properties.
		 * 
		 * @param name The specified name.
		 * @param value The specified value.
		 * @param maxAge The specified maximum age.
		 * @param domain The specified domain.
		 * @param path The specified path.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = Objects.requireNonNull(name, "Name must not be null.");
			this.value = Objects.requireNonNull(value, "Value must not be null.");
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * Returns the name.
		 * 
		 * @return The name.
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Returns the value.
		 * 
		 * @return The value.
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Returns the domain, or {@code null} if not set.
		 * 
		 * @return The domain.
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Returns the path, or {@code null} if not set.
		 * 
		 * @return The path.
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Returns the maximum age, or {@code null} if not set.
		 * 
		 * @return The maximum age.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}
}
