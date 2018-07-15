package hr.fer.zemris.java.hw16.jvdraw.drawing;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectListener;

/**
 * Interface representing a model containing all added {@link GeometricalObject}s.<br>
 * The objects have defined ordering which can be changed.
 * 
 * @author Mate Gasparini
 */
public interface DrawingModel extends GeometricalObjectListener {
	
	/**
	 * Returns the number of stored {@link GeometricalObject}s.
	 * 
	 * @return The number of stored {@link GeometricalObject}s.
	 */
	int getSize();
	
	/**
	 * Returns the {@link GeometricalObject} at given index.
	 * 
	 * @param index The given index.
	 * @return The corresponding {@link GeometricalObject}.
	 */
	GeometricalObject getObject(int index);
	
	/**
	 * Stores the given {@link GeometricalObject}.
	 * 
	 * @param object The given {@link GeometricalObject}.
	 */
	void add(GeometricalObject object);
	
	/**
	 * Removes the given {@link GeometricalObject} from the model.
	 * 
	 * @param object The given {@link GeometricalObject}.
	 */
	void remove(GeometricalObject object);
	
	/**
	 * Changes the position of the given {@link GeometricalObject}
	 * by {@code offset} number of places.
	 * 
	 * @param object The given {@link GeometricalObject}.
	 * @param offset The given offset.
	 */
	void changeOrder(GeometricalObject object, int offset);
	
	/**
	 * Registers the given {@link DrawingModelListener} to the model.
	 * 
	 * @param l The given {@link DrawingModelListener}.
	 */
	void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * De-registers the given {@link DrawingModelListener} from the model.
	 * 
	 * @param l The given {@link DrawingModelListener}.
	 */
	void removeDrawingModelListener(DrawingModelListener l);
}
