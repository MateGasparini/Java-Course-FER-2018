package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

/**
 * Testing class for the {@link Util} class.
 * 
 * @author Mate Gasparini
 */
public class UtilTest {
	
	@Test
	public void hextobyteTest() {
		byte[] expected = new byte[] {1, -82, 34};
		byte[] actual = Util.hextobyte("01aE22");
		
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void hextobyteMinTest() {
		byte[] expected = new byte[] {0, 0, 0, 0};
		byte[] actual = Util.hextobyte("00000000");
		
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void hextobyteMaxTest() {
		byte[] expected = new byte[] {-1, -1, -1, -1};
		byte[] actual = Util.hextobyte("FFfFFFFf");
		
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void hextobyteZeroLengthTest() {
		byte[] actual = Util.hextobyte("");
		
		assertEquals(0, actual.length);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hextobyteOddSizedExceptionThrown() {
		Util.hextobyte("1e2af");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hextobyteInvalidCharactersExceptionThrown() {
		Util.hextobyte("1e2affPP");
	}
	
	@Test
	public void bytetohexTest() {
		String expected = "01ae22";
		String actual = Util.bytetohex(new byte[] {1, -82, 34});
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void bytetohexMinTest() {
		String expected = "00000000";
		String actual = Util.bytetohex(new byte[] {0, 0, 0, 0});
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void bytetohexMaxTest() {
		String expected = "ffffffff";
		String actual = Util.bytetohex(new byte[] {-1, -1, -1, -1});
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void bytetohexZeroLengthTest() {
		String actual = Util.bytetohex(new byte[] {});
		
		assertEquals(0, actual.length());
	}
}
