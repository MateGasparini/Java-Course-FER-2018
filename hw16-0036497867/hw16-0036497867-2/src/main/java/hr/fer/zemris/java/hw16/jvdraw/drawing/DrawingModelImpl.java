package hr.fer.zemris.java.hw16.jvdraw.drawing;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Concrete implementation of the {@link DrawingModel} containing internal
 * lists of {@link GeometricalObject}s and {@link DrawingModelListener}s.
 * 
 * @author Mate Gasparini
 */
public class DrawingModelImpl implements DrawingModel {
	
	/** Internal {@link GeometricalObject} {@code List}. */
	private List<GeometricalObject> objects = new ArrayList<>();
	
	/** Internal {@link DrawingModelListener} {@code List}. */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o);
		if (index == -1) return;
		listeners.forEach(l -> l.objectsChanged(this, index, index));
	}
	
	@Override
	public int getSize() {
		return objects.size();
	}
	
	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}
	
	@Override
	public void add(GeometricalObject object) {
		int index = objects.size();
		objects.add(object);
		object.addGeometricalObjectListener(this);
		listeners.forEach(l -> l.objectsAdded(this, index, index));
	}
	
	@Override
	public void remove(GeometricalObject object) {
		object.removeGeometricalObjectListener(this);
		objects.remove(object);
		int index = objects.size();
		listeners.forEach(l -> l.objectsRemoved(this, index, index));
	}
	
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		if (offset == 0) return;
		int index = objects.indexOf(object);
		int newIndex = index + offset;
		if (newIndex < 0 || newIndex >= objects.size()) return;
		objects.remove(object);
		objects.add(newIndex, object);
		listeners.forEach(l -> l.objectsChanged(this, index, newIndex));
	}
	
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
}
