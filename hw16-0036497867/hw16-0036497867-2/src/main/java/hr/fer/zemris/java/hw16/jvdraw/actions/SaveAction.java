package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Action used for saving into JVD file format, with file path asked for
 * only when first saving.
 * 
 * @author Mate Gasparini
 */
public class SaveAction extends AbstractAction {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Reference to the {@link JVDraw}. */
	private JVDraw frame;
	
	/** Save file chooser. */
	private JFileChooser fileChooser;
	
	/**
	 * Constructor specifying the {@link JVDraw} reference.
	 * 
	 * @param frame The specified {@link JVDraw}.
	 */
	public SaveAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Save");
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files", "jvd");
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Util.save(frame, fileChooser);
	}
}
