package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * {@code NameBuilder} which, when executed, appends
 * the given String literal to the builder info's string builder.
 * 
 * @author Mate Gasparini
 */
public class LiteralNameBuilder implements NameBuilder {
	
	/**
	 * The specified String literal.
	 */
	private String literal;
	
	/**
	 * Constructor specifying the String literal.
	 * 
	 * @param literal The specified String literal.
	 */
	public LiteralNameBuilder(String literal) {
		this.literal = literal;
	}
	
	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(literal);
	}
}
