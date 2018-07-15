package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.regex.Matcher;

/**
 * Implementation of the {@code NameBuilderInfo} interface
 * which contains a {@code StringBuilder} object reference
 * and a reference to the specified matcher for some file name.
 * 
 * @author Mate Gasparini
 */
public class NameBuilderInformation implements NameBuilderInfo {
	
	/**
	 * The specified matcher.
	 */
	private Matcher matcher;
	/**
	 * The string builder used for building the new file name.
	 */
	private StringBuilder stringBuilder = new StringBuilder();
	
	/**
	 * Constructor specifying the file name matcher.
	 * 
	 * @param matcher The specified matcher.
	 */
	public NameBuilderInformation(Matcher matcher) {
		this.matcher = matcher;
	}
	
	@Override
	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}
	
	/**
	 * @throws IndexOutOfBoundsException If there is no group for the given index.
	 */
	@Override
	public String getGroup(int index) {
		return matcher.group(index);
	}
}
