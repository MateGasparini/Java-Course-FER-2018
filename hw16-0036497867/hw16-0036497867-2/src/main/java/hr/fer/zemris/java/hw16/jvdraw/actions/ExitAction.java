package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModelListener;

/**
 * Action for exiting the application.<br>
 * Before exiting, if changes were made, the user will be asked to save them.
 * 
 * @author Mate Gasparini
 */
public class ExitAction extends AbstractAction implements DrawingModelListener {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Reference to the {@link JVDraw}. */
	private JVDraw frame;
	
	/** Set to {@code true} if the model has been modified. */
	private boolean modified;
	
	/**
	 * Constructor specifying the {@link JVDraw} reference and the drawing model.
	 * 
	 * @param frame The specified {@link JVDraw}.
	 * @param model The specified drawing model.
	 */
	public ExitAction(JVDraw frame, DrawingModel model) {
		this.frame = frame;
		model.addDrawingModelListener(this);
		putValue(NAME, "Exit");
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (modified) {
			int option = JOptionPane.showConfirmDialog(
				frame, "Do you want to save the changes?",
				"Save changes?", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				Util.save(frame, new JFileChooser());
			} else if (option != JOptionPane.NO_OPTION) {
				return;
			}
		}
		frame.dispose();
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		modified = true;
	}
	
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		modified = true;
	}
	
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		modified = true;
	}
}
