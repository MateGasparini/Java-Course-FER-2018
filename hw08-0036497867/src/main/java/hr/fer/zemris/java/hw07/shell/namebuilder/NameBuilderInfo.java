package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Interface representing some {@code NameBuilder} information.
 * 
 * @author Mate Gasparini
 */
public interface NameBuilderInfo {
	
	/**
	 * Returns the string builder used for building the file name.
	 * 
	 * @return The internal string builder.
	 */
	StringBuilder getStringBuilder();
	
	/**
	 * Returns the specified group from the matched old file name.
	 * 
	 * @param index The index of the specified group.
	 * @return The specified group from the file name.
	 */
	String getGroup(int index);
}
