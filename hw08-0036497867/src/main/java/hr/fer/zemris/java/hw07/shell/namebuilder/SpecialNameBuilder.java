package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * {@code NameBuilder} which, when executed, appends
 * the file name's group contents (as specified by the renaming
 * expression).
 * 
 * @author Mate Gasparini
 */
public class SpecialNameBuilder implements NameBuilder {
	
	/**
	 * Contents of the tag.
	 */
	private String data;
	/**
	 * The specified group index.
	 */
	private int group;
	/**
	 * The specified minimum char width of group content.
	 * By default, zero, but can be given as an additional tag argument.
	 */
	private int minWidth;
	/**
	 * If true, the group content will be filled with zero's instead of spaces.
	 * By default, false, but can be given as part of the additional tag argument.
	 */
	private boolean zeroFilled;
	
	/**
	 * Constructor specifying the contents of the tag.
	 * 
	 * @param data The specified tag contents.
	 */
	public SpecialNameBuilder(String data) {
		this.data = data;
	}
	
	@Override
	public void execute(NameBuilderInfo info) {
		String[] parts = data.split(COMMA);
		
		if (parts.length == 1) {
			group = Integer.parseInt(parts[0]);
		} else if (parts.length == 2) {
			group = Integer.parseInt(parts[0]);
			
			String additional = parts[1];
			
			if (!additional.equals(ZERO) && additional.startsWith(ZERO)) {
				zeroFilled = true;
			}
			
			minWidth = Integer.parseInt(additional);
		} else {
			throw new RuntimeException(
				parts + ": expected 1 or 2 tag arguments, but was: " + parts.length + "."
			);
		}
		
		try {
			String groupContent = info.getGroup(group);
			StringBuilder sb = info.getStringBuilder();
			
			int appendCount = minWidth - groupContent.length();
			if (appendCount > 0) {
				appendMultiple(sb, zeroFilled ? ZERO : SPACE, appendCount);
			}
			
			info.getStringBuilder().append(groupContent);
		} catch (IndexOutOfBoundsException ex) {
			throw new RuntimeException(
				group + ": group out of bounds."
			);
		}
	}
	
	/**
	 * Appends the given string builder
	 * count number of times using the given string.
	 * 
	 * @param sb The given string builder.
	 * @param string The given string.
	 * @param count Number of times to append.
	 */
	private void appendMultiple(StringBuilder sb, String string, int count) {
		for (int i = 0; i < count; i ++) {
			sb.append(string);
		}
	}
	
	/* STRING CONSTANTS */
	private static final String COMMA = ",";
	private static final String ZERO = "0";
	private static final String SPACE = " ";
}
