package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing some geometrical objects and providing
 * functionality for {@link GeometricalObjectListener} (de)registration
 * and notification.
 * 
 * @author Mate Gasparini
 */
public abstract class GeometricalObject {
	
	/** Internal {@code List} of {@link GeometricalObjectListener}s. */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * Registers the given {@link GeometricalObjectListener} to the object.
	 * 
	 * @param l The given {@link GeometricalObjectListener}.
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	/**
	 * De-registers the given {@link GeometricalObjectListener} from the object.
	 * 
	 * @param l The given {@link GeometricalObjectListener}.
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Calls the appropriate {@code visit} method on the given visitor.
	 * 
	 * @param v The given visitor.
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Creates the corresponding {@link GeometricalObjectEditor} used for
	 * this {@link GeometricalObject} modification.
	 * 
	 * @return The corresponding {@link GeometricalObjectEditor}.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Notifies the {@link GeometricalObjectListener}s about the object change.
	 */
	protected void fire() {
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
}
