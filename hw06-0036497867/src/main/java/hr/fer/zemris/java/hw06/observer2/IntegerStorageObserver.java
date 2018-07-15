package hr.fer.zemris.java.hw06.observer2;

/**
 * <i>Observer</i> interface for the <i>Subject</i>
 * {@link IntegerStorage} class.
 * 
 * @author Mate Gasparini
 */
public interface IntegerStorageObserver {
	
	/**
	 * Called when an {@link IntegerStorageChange} is made,
	 * so a specified (implementation-dependent) action
	 * is performed.
	 * 
	 * @param change Reference to the {@code IntegerStorageChange} made.
	 */
	public void valueChanged(IntegerStorageChange change);
}
