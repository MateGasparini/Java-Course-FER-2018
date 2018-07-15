package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Action used for saving into JVD file format, with file path always asked for.
 * 
 * @author Mate Gasparini
 */
public class SaveAsAction extends AbstractAction {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Reference to the {@link JVDraw}. */
	private JVDraw frame;
	
	/** Save as file chooser. */
	private JFileChooser fileChooser;
	
	/**
	 * Constructor specifying the {@link JVDraw} reference.
	 * 
	 * @param frame The specified {@link JVDraw}.
	 */
	public SaveAsAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Save As");
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save As");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files", "jvd");
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			Path chosenPath = fileChooser.getSelectedFile().toPath();
			if (!chosenPath.getFileName().toString().endsWith(".jvd")) {
				Util.showError(frame, "JVDraw files (*.jvd) allowed only.");
				return;
			}
			if (Files.isRegularFile(chosenPath)) {
				String message = "Are you sure you want to overwrite "
									+ chosenPath.getFileName() + "?";
				if (JOptionPane.showConfirmDialog(frame, message) != JOptionPane.OK_OPTION) {
					Util.showInfo(frame, "File not saved.");
					return;
				}
			}
			List<String> lines = Util.getObjectsAsText(frame.getDocumentModel());
			try {
				Util.writeToFile(chosenPath, lines);
				Util.showInfo(frame, "File saved successfully.");
			} catch (IOException ex) {
				Util.showError(frame, "An error occurred while saving JVD file.");
			}
		}
	}
}
