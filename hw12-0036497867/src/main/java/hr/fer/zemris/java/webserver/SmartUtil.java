package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link SmartHttpServer} utility class used for reading requests, extracting
 * cookies from requests, generating timestamps used for cookie managment etc.
 * 
 * @author Mate Gasparini
 */
public class SmartUtil {
	
	/**
	 * Default private constructor.
	 */
	private SmartUtil() {
	}
	
	/**
	 * Returns a {@code List} of all lines of the request acquired from the given
	 * input stream.
	 * 
	 * @param istream The given input stream.
	 * @return Lines of request content.
	 * @throws IOException If the given input stream has been closed,
	 * 			or if an I/O error occurred.
	 */
	public static List<String> readRequest(PushbackInputStream istream) throws IOException {
		List<String> lines = new ArrayList<>();
		
		byte[] bytes = readRequestAsByteArray(istream);
		if (bytes == null) {
			return lines;
		}
		
		String current = null;
		for (String s : new String(bytes, StandardCharsets.US_ASCII).split("\n")) {
			if (s.isEmpty()) break;
			char c = s.charAt(0);
			if (c == 9 || c == 32) {
				current += s;
			} else {
				if (current != null) {
					lines.add(current);
				}
				current = s;
			}
		}
		if (!current.isEmpty()) {
			lines.add(current);
		}
		
		return lines;
	}
	
	/**
	 * Reads the request from the given input stream byte-by-byte and, when
	 * the ending sequence CR-LF-CR-LF is read, returns all read bytes as an array.<br>
	 * If the end of the request is reached before reading the ending sequence,
	 * {@code null} is returned instead.
	 * 
	 * @param istream The given input stream.
	 * @return Byte array generated from the read request.
	 * @throws IOException If the given input stream has been closed,
	 * 			or if an I/O error occurred.
	 */
	private static byte[] readRequestAsByteArray(PushbackInputStream istream)
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
		
		while (true) {
			int b = istream.read();
			if (b == -1) return null;
			if (b != 13) bos.write(b);
			
			if (state == 0) {
				if (b == 13) state = 1;
				else if (b == 10) state = 0;
			} else if (state == 1) {
				if (b == 10) state = 2;
				else state = 0;
			} else if (state == 2) {
				if (b == 13) state = 3;
				else state = 0;
			} else if (state == 3) {
				if (b == 10) break;
				else state = 0;
			}
		}
		
		return bos.toByteArray();
	}
	
	/**
	 * Reads the given lines of request and returns all parts of the cookie header
	 * as an array of Strings.<br>
	 * If none found, {@code null} is returned instead.
	 * 
	 * @param request The given lines of request.
	 * @return A String array containing the cookies (or {@code null}).
	 */
	public static String[] extractCookies(List<String> request) {
		for (int i = 1, size = request.size(); i < size; i ++) {
			String line = request.get(i);
			if (line.toLowerCase().startsWith("cookie:")) {
				return line.split(":", 2)[1].trim().split(";");
			}
		}
		return null;
	}
	
	/**
	 * Parses the given String array of cookies and (if not session ID)
	 * stores them in the given {@code Map} of permanent parameters.<br>
	 * If session ID, it's value is returned as the SID candidate.
	 * 
	 * @param cookies The given String array of cookies.
	 * @param permParams The given {@code Map} of permanent parameters.
	 * @return The parsed SID candidate's identification value.
	 */
	public static String getSIDCandidate(String[] cookies, Map<String, String> permParams) {
		String sidCandidate = null;
		for (String cookie : cookies) {
			String[] cookieParts = cookie.split("=");
			String cookieName = cookieParts[0].trim();
			String cookieValue = cookieParts[1].trim();
			cookieValue = cookieValue.substring(1, cookieValue.length() - 1);
			
			if (cookieName.equals("sid")) {
				sidCandidate = cookieValue;
			} else {
				permParams.put(cookieName, cookieValue);
			}
		}
		return sidCandidate;
	}
	
	/**
	 * Returns {@code true} if the given timestamp is still valid,
	 * or {@code false} if it is older than the current moment timestamp.
	 * 
	 * @param validUntil The given timestamp.
	 * @return {@code true} if the given timestamp is still valid.
	 */
	public static boolean timestampStillValid(long validUntil) {
		return (System.currentTimeMillis() / 1000L) < validUntil;
	}
	
	/**
	 * Returns the next valid timestamp, calculated as the current moment
	 * plus the given timeout value.
	 * 
	 * @param timeout The given timeout value.
	 * @return The next <i>valid until</i> timestamp.
	 */
	public static long generateUntilValidTimestamp(int timeout) {
		return (System.currentTimeMillis() / 1000L) + timeout;
	}
}
