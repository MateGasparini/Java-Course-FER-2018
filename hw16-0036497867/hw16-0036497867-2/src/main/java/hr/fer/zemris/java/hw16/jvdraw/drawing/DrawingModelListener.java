package hr.fer.zemris.java.hw16.jvdraw.drawing;

/**
 * Listener interface which is used for listening for {@link DrawingModel} changes.
 * 
 * @author Mate Gasparini
 */
public interface DrawingModelListener {
	
	/**
	 * Called when objects has been added to the given {@link DrawingModel}.
	 * 
	 * @param source The given {@link DrawingModel}.
	 * @param index0 The starting index of added objects.
	 * @param index1 The ending index of added objects.
	 */
	void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * Called when objects has been removed from the given {@link DrawingModel}.
	 * 
	 * @param source The given {@link DrawingModel}.
	 * @param index0 The starting index of removed objects.
	 * @param index1 The ending index of removed objects.
	 */
	void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Called when object order has been added in the given {@link DrawingModel}.
	 * 
	 * @param source The given {@link DrawingModel}.
	 * @param index0 The starting index of change.
	 * @param index1 The ending index of change.
	 */
	void objectsChanged(DrawingModel source, int index0, int index1);
}
