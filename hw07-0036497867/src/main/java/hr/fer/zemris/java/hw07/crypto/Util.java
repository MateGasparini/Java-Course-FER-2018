package hr.fer.zemris.java.hw07.crypto;

/**
 * Class providing methods for conversion from hex-Strings to byte arrays,
 * and from byte arrays to hex-Strings.
 * 
 * @author Mate Gasparini
 */
public class Util {
	
	/**
	 * Base of hex numbers. Declared for readability enhancement.
	 */
	private static final int BASE = 16;
	/**
	 * Biggest value in a byte.
	 */
	private static final int MASK = 0xFF;
	
	/**
	 * Converts the given hex-String to the corresponding byte array.
	 * 
	 * @param keyText The given hex-String.
	 * @return The corresponding byte array.
	 * @throws IllegalArgumentException If the given hex-String is invalid.
	 */
	public static byte[] hextobyte(String keyText) {
		int keyLength = keyText.length();
		
		if (keyLength % 2 == 1) {
			throw new IllegalArgumentException(
				keyText + ": odd-sized and therefore not a valid hex number."
			);
		}
		
		byte[] byteArray = new byte[keyLength / 2];
		
		char[] chars = keyText.toCharArray();
		for (int i = 0; i < chars.length; i += 2) {
			byteArray[i / 2] = charPairToByte(chars[i], chars[i + 1]);
		}
		
		return byteArray;
	}
	
	/**
	 * Converts the given byte array to the corresponding hex-String.
	 * 
	 * @param byteArray The given byte array.
	 * @return The corresponding hex-String.
	 */
	public static String bytetohex(byte[] byteArray) {
		StringBuilder builder = new StringBuilder();
		
		for (byte b : byteArray) {
			int higher = (b & MASK) >>> 4; // Dividing by 16.
			int lower = (b & MASK) % BASE;
			
			builder.append(toHexDigit(higher)).append(toHexDigit(lower));
		}
		
		return builder.toString();
	}
	
	/**
	 * Converts the given pair of characters to the corresponding byte.
	 * 
	 * @param higher Higher half-byte.
	 * @param lower Lower half-byte.
	 * @return The corresponding byte.
	 */
	private static byte charPairToByte(char higher, char lower) {
		return (byte) (BASE*valueOfHexDigit(higher) + valueOfHexDigit(lower));
	}
	
	/**
	 * Converts the given hex-digit character to the corresponding byte.
	 * 
	 * @param digit The given hex-digit.
	 * @return The corresponding byte.
	 */
	private static byte valueOfHexDigit(char digit) {
		if (digit >= '0' && digit <= '9') {
			return (byte) (digit - '0');
		} else if (digit >= 'A' && digit <= 'F') {
			return (byte) (digit - 'A' + 10);
		} else if (digit >= 'a' && digit <= 'f') {
			return (byte) (digit - 'a' + 10);
		} else {
			throw new IllegalArgumentException(
				digit + ": not a valid hex digit."
			);
		}
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
