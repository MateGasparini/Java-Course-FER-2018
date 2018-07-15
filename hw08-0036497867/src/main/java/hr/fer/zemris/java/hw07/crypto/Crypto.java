package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A program which allows the user to encrypt/decrypt the given file using
 * the AES crypto-algorithm and the 128-bit encryption key or calculate and
 * check the SHA-256 file digest.<br>
 * The wanted operation (checksha/encrypt/decrypt), as well the files are
 * specified through command line arguments
 * 
 * @author Mate Gasparini
 */
public class Crypto {
	
	/**
	 * Operation name for checking the SHA-256 file digest.
	 */
	private static final String CHECK_SHA = "checksha";
	/**
	 * Operation name for encrypting the file.
	 */
	private static final String ENCRYPT = "encrypt";
	/**
	 * Operation name for decrypting the file.
	 */
	private static final String DECRYPT = "decrypt";
	
	/**
	 * Reader from stdin.
	 */
	private static BufferedReader stdin =
			new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments specifying the operation and the
	 * 			file paths.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Expected arguments, but none given.");
			return;
		}
		
		String command = args[0];
		try {
			if (command.equals(CHECK_SHA)) {
				if (args.length == 2) {
					checkSum(args[1]);
				} else {
					System.out.format(WRONG_ARGS_LENGTH, CHECK_SHA, 1, "");
					return;
				}
			} else if (command.equals(ENCRYPT)) {
				if (args.length == 3) {
					encryptDecrypt(args[1], args[2], true);
				} else {
					System.out.format(WRONG_ARGS_LENGTH, ENCRYPT, 2, "s");
					return;
				}
			} else if (command.equals(DECRYPT)) {
				if (args.length == 3) {
					encryptDecrypt(args[1], args[2], false);
				} else {
					System.out.format(WRONG_ARGS_LENGTH, DECRYPT, 2, "s");
					return;
				}
			} else {
				System.out.println(command + ": invalid command.");
				return;
			}
		} catch (IOException | IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println("An unexpected error occurred.");
		} finally {
			try {
				stdin.close();
			} catch (IOException ignorable) {}
		}
	}
	
	/**
	 * Calculates the SHA-256 file digest, compares it to the given
	 * (expected) digest and prints the comparison result.
	 * 
	 * @param filePath Path to the file.
	 * @throws IOException If the file path is invalid.
	 * @throws NoSuchAlgorithmException If no {@code Provider} supports a
     * 			{@code MessageDigestSpi} implementation for the
     * 			specified algorithm.
	 */
	private static void checkSum(String filePath)
			throws IOException, NoSuchAlgorithmException {
		
		try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
			String expected = promptUserAndGetInput(String.format(
				PROMPT_SHA, filePath)
			);
			
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			
			byte[] buffer = new byte[4096];
			while (true) {
				int numberOfRead = is.read(buffer);
				
				if (numberOfRead < 1) {
					break;
				}
				
				sha.update(buffer, 0, numberOfRead);
			}
			byte[] hash = sha.digest();
			byte[] expectedHash = Util.hextobyte(expected);
			
			System.out.format("Digesting completed. Digest of %s ", filePath);
			if (Arrays.equals(hash, expectedHash)) {
				System.out.println("matches expected digest.");
			} else {
				System.out.format(
					"does not match the expected digest. Digest was: %s%n",
					Util.bytetohex(hash)
				);
			}
		} catch (IOException ex) {
			throw new IOException(
				filePath + ": invalid file path."
			);
		}
	}
	
	/**
	 * Gets the password and the initializing vector from the user, and
	 * encrypts or decrypts the given source file
	 * to the given destination file.
	 * 
	 * @param filePath1 The given source file.
	 * @param filePath2 The given destination file.
	 * @param encrypt True if encrypting, false if decrypting.
	 * @throws IOException If there was an error reading from stdin or file.
	 */
	private static void encryptDecrypt(String filePath1, String filePath2, boolean encrypt)
			throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		try (InputStream is = Files.newInputStream(Paths.get(filePath1));
				OutputStream os = Files.newOutputStream(Paths.get(filePath2))) {
			
			String keyText = promptUserAndGetInput(PROMPT_PASSWORD);
			String ivText = promptUserAndGetInput(PROMPT_VECTOR);
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ?
				Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec
			);
			
			byte[] buffer = new byte[4096];
			while (true) {
				int numberOfRead = is.read(buffer);
				
				if (numberOfRead < 0) {
					break;
				}
				
				os.write(cipher.update(buffer, 0, numberOfRead));
			}
			os.write(cipher.doFinal());
			
			System.out.format(encrypt ?
				ENCRYPT_COMPLETE : DECRYPT_COMPLETE, filePath2, filePath1
			);
		} catch (NoSuchFileException ex) {
			throw new IOException(
				filePath1 + ": invalid file path."
			);
		}
	}
	
	/**
	 * Prompts the user with the specified message and returns the user's input
	 * from the stdin.
	 * 
	 * @param message The specified message.
	 * @return The user's input.
	 * @throws IOException
	 */
	private static String promptUserAndGetInput(String message) throws IOException {
		System.out.println(message);
		System.out.print(INPUT_PROMPT);
		
		try {
			return stdin.readLine();
		} catch (IOException ex) {
			throw new IOException(
				"An error occured while trying to read from stdin."
			);
		}
	}
	
	/* STDOUT CONSTANTS */
	
	private static final String INPUT_PROMPT = "> ";
	private static final String WRONG_ARGS_LENGTH =
			"%s: expected %d argument%s.";
	private static final String PROMPT_SHA =
			"Please provide expected sha-256 digest for %s:";
	private static final String PROMPT_PASSWORD =
			"Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):";
	private static final String PROMPT_VECTOR =
			"Please provide initialization vector as hex-encoded text (32 hex-digits):";
	private static final String COMPLETE =
			"completed. Generated file %s based on file %s.";
	private static final String ENCRYPT_COMPLETE =
			"Encryption ".concat(COMPLETE);
	private static final String DECRYPT_COMPLETE =
			"Decryption ".concat(COMPLETE);
}
