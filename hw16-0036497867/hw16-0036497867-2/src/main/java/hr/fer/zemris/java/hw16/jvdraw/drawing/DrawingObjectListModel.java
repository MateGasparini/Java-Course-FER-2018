package hr.fer.zemris.java.hw16.jvdraw.drawing;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * A {@link GeometricalObject} list model containing a reference to some
 * {@link DrawingModel} (and acting as its <i>adapter</i>).
 * 
 * @author Mate Gasparini
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
	implements DrawingModelListener {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Reference to some drawing model. */
	private DrawingModel drawingModel;
	
	/**
	 * Constructor specifying the {@link DrawingModel} reference.
	 * 
	 * @param drawingModel The specified {@link DrawingModel}.
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}
	
	@Override
	public int getSize() {
		return drawingModel.getSize();
	}
	
	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}
	
	/**
	 * Stores the given {@link GeometricalObject} in the model.
	 * 
	 * @param object The given {@link GeometricalObject}.
	 */
	public void add(GeometricalObject object) {
		drawingModel.add(object);
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		super.fireIntervalAdded(source, index0, index1);
	}
	
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		super.fireIntervalRemoved(source, index0, index1);
	}
	
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}
}
