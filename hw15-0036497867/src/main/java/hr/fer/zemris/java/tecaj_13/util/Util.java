package hr.fer.zemris.java.tecaj_13.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Utility class used for reduction of code redundancy.
 * 
 * @author Mate Gasparini
 */
public class Util {
	
	/** Base of hex numbers. Declared for readability enhancement. */
	private static final int BASE = 16;
	
	/** Biggest value in a byte. */
	private static final int MASK = 0xFF;
	
	/** {@code Pattern} which checks if an e-mail address is valid. */
	private static final Pattern EMAIL_VALID =
		Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Returns {@code true} if the given e-mail address is valid.
	 * 
	 * @param email The given e-mail address.
	 * @return {@code true} if the given e-mail address is valid, or {@code false} otherwise.
	 */
	public static boolean isValidEmail(String email) {
		return EMAIL_VALID.matcher(email).matches();
	}
	
	/**
	 * Encodes the given password using the <i>SHA-1</i> hashing function.
	 * 
	 * @param password The given password.
	 * @return The corresponding password hash.
	 */
	public static String hashEncode(String password) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException("Hash encoding not possible.", ex);
		}
		sha.update(password.getBytes());
		byte[] hash = sha.digest();
		return bytetohex(hash);
	}
	
	/**
	 * Trims the given {@code String} or returns an empty {@code String}
	 * (if given {@code null}) which is more suitable for usage on the web.
	 * 
	 * @param s The given {@code String}.
	 * @return The trimmed given {@code String}, or an empty {@code String}.
	 */
	public static String prepareString(String s) {
		return s == null ? "" : s.trim();
	}
	
	/**
	 * Returns {@code true} if the given nickname is already taken by a {@link BlogUser}.
	 * 
	 * @param nick The given nickname.
	 * @return {@code true} if the given nickname is taken, or {@code false} otherwise.
	 */
	public static boolean existsUser(String nick) {
		return DAOProvider.getDAO().getBlogUser(nick) != null;
	}
	
	/**
	 * Checks if the given password is the password used by the {@link BlogUser}
	 * with the given nickname.
	 * 
	 * @param nick The given nickname.
	 * @param password The given password.
	 * @return {@code true} if the given credentials are valid, or {@code false} otherwise.
	 */
	public static boolean checkCredentials(String nick, String password) {
		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if (user == null) return false;
		return user.getPasswordHash().equals(hashEncode(password));
	}
	
	/**
	 * Returns the {@code List} of all entries of the {@link BlogUser}
	 * with the given nickname.
	 * 
	 * @param nick The given nickname.
	 * @return {@code List} of all corresponding {@link BlogUser}'s entries.
	 */
	public static List<BlogEntry> getBlogEntries(String nick) {
		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if (user == null) return null;
		return DAOProvider.getDAO().getBlogEntries(user);
	}
	
	/**
	 * Returns {@code true} if the {@link BlogEntry} with the given entry ID
	 * belongs to the {@link BlogUser} with the given nickname.
	 * 
	 * @param nick The given nickname.
	 * @param entryID The given entry ID.
	 * @return {@code true} if the given entry ID belongs to the given nickname,
	 * 			or {@code false} otherwise.
	 */
	public static boolean entryBelongsToUser(String nick, long entryID) {
		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if (user == null) return false;
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
		if (entry == null) return false;
		return entry.getCreator().equals(user);
	}
	
	/**
	 * Converts the given byte array to the corresponding hex-String.
	 * 
	 * @param byteArray The given byte array.
	 * @return The corresponding hex-String.
	 */
	private static String bytetohex(byte[] byteArray) {
		StringBuilder builder = new StringBuilder();
		
		for (byte b : byteArray) {
			int higher = (b & MASK) >>> 4; // Dividing by 16.
			int lower = (b & MASK) % BASE;
			
			builder.append(toHexDigit(higher)).append(toHexDigit(lower));
		}
		
		return builder.toString();
	}
	
	/**
	 * Converts the given byte to the corresponding hex-digit character.
	 * 
	 * @param b The given byte.
	 * @return The corresponding hex-digit character.
	 */
	private static char toHexDigit(int b) {
		if (b < 10) {
			return (char) ('0' + b);
		} else if (b < BASE) {
			return (char) ('a' + b - 10);
		} else {
			throw new IllegalArgumentException(b + ": not a valid byte.");
		}
	}
}
