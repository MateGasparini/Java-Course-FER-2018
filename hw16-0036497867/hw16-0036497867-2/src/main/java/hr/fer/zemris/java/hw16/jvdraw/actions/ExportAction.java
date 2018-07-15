package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometry.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.geometry.visitors.GeometricalObjectPainter;

/**
 * Action used for exporting the model to an image file.
 * 
 * @author Mate Gasparini
 */
public class ExportAction extends AbstractAction {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Currently supported image types. */
	private static final String[] SUPPORTED_TYPES = {"jpg", "png", "gif"};
	
	/** Reference to the {@link JVDraw}. */
	private JVDraw frame;
	
	/** Export file chooser. */
	private JFileChooser fileChooser;
	
	/**
	 * Constructor specifying the {@link JVDraw} reference.
	 * 
	 * @param frame The specified {@link JVDraw}.
	 */
	public ExportAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Export");
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"JVDraw images", SUPPORTED_TYPES);
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			Path chosenPath = fileChooser.getSelectedFile().toPath();
			String extension = extractExtension(chosenPath.getFileName().toString());
			if (!isSupportedType(extension)) {
				Util.showError(frame, "JPG/PNG/GIF allowed only.");
				return;
			}
			if (Files.isRegularFile(chosenPath)) {
				String message = "Are you sure you want to overwrite "
									+ chosenPath.getFileName() + "?";
				if (JOptionPane.showConfirmDialog(frame, message) != JOptionPane.OK_OPTION) {
					Util.showInfo(frame, "Image not exported.");
					return;
				}
			}
			export(chosenPath, extension);
		}
	}
	
	/**
	 * Exports the {@link JVDraw}'s document model to the image of given type
	 * with given file path.
	 * 
	 * @param path The given file path.
	 * @param type The given image type.
	 */
	private void export(Path path, String type) {
		DrawingModel model = frame.getDocumentModel();
		if (model.getSize() == 0) {
			Util.showError(frame, "Cannot export empty images.");
		}
		GeometricalObjectBBCalculator bbCalculator = new GeometricalObjectBBCalculator();
		for (int i = 0, size = model.getSize(); i < size; i ++) {
			model.getObject(i).accept(bbCalculator);
		}
		Rectangle box = bbCalculator.getBoundingBox();
		BufferedImage image = new BufferedImage(
			box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D g2d = image.createGraphics();
		g2d.translate(-box.x, -box.y);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(box.x, box.y, box.width, box.height);
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		for (int i = 0, size = model.getSize(); i < size; i ++) {
			model.getObject(i).accept(painter);
		}
		g2d.dispose();
		File file = path.toFile();
		try {
			ImageIO.write(image, type, file);
			Util.showInfo(frame, "Image successfully exported.");
		} catch (IOException ex) {
			Util.showError(frame, "An error occurred while exporting the image.");
		}
	}
	
	/**
	 * Extracts the file type extension from the given file name.
	 * 
	 * @param fileName The given file name.
	 * @return The file type.
	 */
	private String extractExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex == -1 || dotIndex == fileName.length() - 1) return null;
		return fileName.substring(dotIndex + 1, fileName.length());
	}
	
	/**
	 * Returns {@code true} if the given file type extension is supported.
	 * 
	 * @param extension The given file type.
	 * @return {@code true} if extension is supported, or {@code false} otherwise.
	 */
	private boolean isSupportedType(String extension) {
		for (String type : SUPPORTED_TYPES) {
			if (type.equals(extension)) {
				return true;
			}
		}
		return false;
	}
}
