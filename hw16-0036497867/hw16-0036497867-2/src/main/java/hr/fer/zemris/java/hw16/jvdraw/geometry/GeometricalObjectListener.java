package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * Listener interface which is used for listening for changes
 * on some {@link GeometricalObject}.
 * 
 * @author Mate Gasparini
 */
public interface GeometricalObjectListener {
	
	/**
	 * Called when the given {@link GeometricalObject} changes some of its properties.
	 * 
	 * @param o The given {@link GeometricalObject}.
	 */
	void geometricalObjectChanged(GeometricalObject o);
}
