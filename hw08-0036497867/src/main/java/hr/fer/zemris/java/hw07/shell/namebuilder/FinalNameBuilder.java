package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.List;

/**
 * {@code NameBuilder} which, when executed, calls
 * {@link NameBuilder#execute(NameBuilderInfo)} for every
 * internally stored {@code NameBuilder}.
 * 
 * @author Mate Gasparini
 */
public class FinalNameBuilder implements NameBuilder {
	
	/**
	 * Internal List of all name builders.
	 */
	private List<NameBuilder> builders;
	
	/**
	 * Constructor specifying the List of all name builders.
	 * 
	 * @param builders The specified List of name builders.
	 */
	public FinalNameBuilder(List<NameBuilder> builders) {
		this.builders = builders;
	}
	
	@Override
	public void execute(NameBuilderInfo info) {
		for (NameBuilder builder : builders) {
			builder.execute(info);
		}
	}
}
