package hr.fer.zemris.java.hw16.jvdraw.adapters;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JList;

import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Listens for key press events and changes the specified {@link DrawingModel}'s
 * order or deletes objects from the model.
 * 
 * @author Mate Gasparini
 */
public class ListKeyListener extends KeyAdapter {
	
	/** The specified {@link JList} of {@link GeometricalObject}s. */
	private JList<GeometricalObject> list;
	
	/** The specified drawing model. */
	private DrawingModel documentModel;
	
	/**
	 * Constructor specifying the {@link JList} of {@link GeometricalObject}s
	 * and the drawing model.
	 * 
	 * @param list The specified {@link JList}.
	 * @param documentModel The specified {@link DrawingModel}.
	 */
	public ListKeyListener(JList<GeometricalObject> list, DrawingModel documentModel) {
		this.list = list;
		this.documentModel = documentModel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		GeometricalObject object = list.getSelectedValue();
		if (object == null) return;
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS) {
			documentModel.changeOrder(object, -1);
		} else if (keyCode == KeyEvent.VK_SUBTRACT || keyCode == KeyEvent.VK_MINUS) {
			documentModel.changeOrder(object, 1);
		} else if (keyCode == KeyEvent.VK_DELETE) {
			documentModel.remove(object);
		}
	}
}
