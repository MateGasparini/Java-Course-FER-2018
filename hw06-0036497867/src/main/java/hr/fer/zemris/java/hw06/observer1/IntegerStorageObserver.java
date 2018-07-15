package hr.fer.zemris.java.hw06.observer1;

/**
 * <i>Observer</i> interface for the <i>Subject</i>
 * {@link IntegerStorage} class.
 * 
 * @author Mate Gasparini
 */
public interface IntegerStorageObserver {
	
	/**
	 * Called when the given {@code IntegerStorage} changes
	 * its value, so a specified (implementation-dependent)
	 * action is performed.
	 * 
	 * @param istorage The given {@code IntegerStorage}.
	 */
	public void valueChanged(IntegerStorage istorage);
}
