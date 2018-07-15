package hr.fer.zemris.java.hw16.jvdraw.adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;

/**
 * Listens for mouse (double) click events and offers edit dialogs for
 * the clicked {@link GeometricalObject} in the {@link JList}.
 * 
 * @author Mate Gasparini
 */
public class ListMouseListener extends MouseAdapter {
	
	/** The specified frame. */
	private JFrame frame;
	
	/** The specified {@link JList} of {@link GeometricalObject}s. */
	private JList<GeometricalObject> list;
	
	/**
	 * Constructor specifying the frame and the {@link JList}.
	 * 
	 * @param frame The specified frame.
	 * @param list The specified {@link JList}.
	 */
	public ListMouseListener(JFrame frame, JList<GeometricalObject> list) {
		this.frame = frame;
		this.list = list;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			GeometricalObject object = list.getSelectedValue();
			if (object == null) return;
			showEditDialog(object.createGeometricalObjectEditor());
		}
	}
	
	/**
	 * Shows the edit dialog containing the given {@link GeometricalObjectEditor}.
	 * 
	 * @param editor The given {@link GeometricalObjectEditor}.
	 */
	private void showEditDialog(GeometricalObjectEditor editor) {
		if (JOptionPane.showConfirmDialog(
				frame,
				editor,
				"Edit",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			try {
				editor.checkEditing();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
					frame,
					ex.getMessage(),
					"Invalid arguments!",
					JOptionPane.WARNING_MESSAGE
				);
				showEditDialog(editor);
			}
			try {
				editor.acceptEditing();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
					frame,
					ex.getMessage(),
					"Edit failed!",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
}
