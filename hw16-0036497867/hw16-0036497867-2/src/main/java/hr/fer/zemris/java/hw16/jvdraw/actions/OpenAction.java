package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Action used for opening JVD files.
 * 
 * @author Mate Gasparini
 */
public class OpenAction extends AbstractAction {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Reference to the {@link JVDraw}. */
	private JVDraw frame;
	
	/** Open file chooser. */
	private JFileChooser fileChooser;
	
	/**
	 * Constructor specifying the {@link JVDraw} reference.
	 * 
	 * @param frame The specified {@link JVDraw}.
	 */
	public OpenAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Open");
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files", "jvd");
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			Path chosenPath = fileChooser.getSelectedFile().toPath();
			if (!chosenPath.getFileName().toString().endsWith(".jvd")) {
				Util.showError(frame, "JVDraw files (*.jvd) allowed only.");
				return;
			}
			if (!Files.isRegularFile(chosenPath) || !Files.isReadable(chosenPath)) {
				Util.showError(frame, "File does not exist or is not readable.");
				return;
			}
			List<GeometricalObject> objects;
			try {
				objects = Util.readObjects(chosenPath);
			} catch (IOException ex) {
				Util.showError(frame, "Error occurred while loading JVD file.");
				return;
			}
			frame.setSavePath(chosenPath);
			DrawingModel model = frame.getDocumentModel();
			clearDrawingModel(model);
			objects.forEach(object -> model.add(object));
			Util.showInfo(frame, "Loaded " + objects.size() + " geometrical objects.");
		}
	}
	
	/**
	 * Removes all {@link GeometricalObject}s from the given model.
	 * 
	 * @param model The given model.
	 */
	private void clearDrawingModel(DrawingModel model) {
		while (model.getSize() > 0) {
			model.remove(model.getObject(0));
		}
	}
}
