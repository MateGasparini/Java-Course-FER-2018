package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;
import hr.fer.zemris.java.hw16.jvdraw.geometry.visitors.GeometricalObjectSaver;

/**
 * Utility class used by the action classes from the same subpackage.
 * 
 * @author Mate Gasparini
 */
public class Util {
	
	/**
	 * Private default constructor.
	 */
	private Util() {
	}
	
	/**
	 * Shows an error message dialog with the specified message
	 * on the specified parent component.<br>
	 * Used for reducing the amount of same code.
	 * 
	 * @param parentComponent The specified parent component.
	 * @param message The specified error message.
	 */
	public static void showError(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(
			parentComponent, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Shows an information message dialog with the specified message
	 * on the specified parent component.<br>
	 * Used for reducing the amount of same code.
	 * 
	 * @param parentComponent The specified parent component.
	 * @param message The specified information message.
	 */
	public static void showInfo(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(
			parentComponent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Reads the specified JVD file, parses its content and returns
	 * the constructed {@code List} of {@link GeometricalObject}s.
	 * 
	 * @param filePath Path of the specified JVD file.
	 * @return {@code List} of parsed {@link GeometricalObject}s.
	 * @throws IOException If an I/O error occurs.
	 */
	public static List<GeometricalObject> readObjects(Path filePath) throws IOException {
		List<GeometricalObject> objects = new ArrayList<>();
		
		List<String> lines = Files.readAllLines(filePath);
		for (String line : lines) {
			String[] parts = line.trim().split("\\s+");
			try {
				if (parts.length == 8 && parts[0].equals("LINE")) {
					Line object = parseLine(parts);
					if (object != null) objects.add(object);
				} else if (parts.length == 7 && parts[0].equals("CIRCLE")) {
					Circle object = parseCircle(parts);
					if (object != null) objects.add(object);
				} else if (parts.length == 10 && parts[0].equals("FCIRCLE")) {
					FilledCircle object = parseFilledCircle(parts);
					if (object != null) objects.add(object);
				}
			} catch (NumberFormatException ignorable) {}
		}
		
		return objects;
	}
	
	/**
	 * Converts the {@link GeometricalObject}s from the given {@link DrawingModel}
	 * to a {@code List} of Strings (by using {@link GeometricalObjectSaver})
	 * for the JVD file format.
	 * 
	 * @param documentModel The given {@link DrawingModel}.
	 * @return {@code List} of Strings used for saving in JVD file format.
	 */
	public static List<String> getObjectsAsText(DrawingModel documentModel) {
		GeometricalObjectSaver visitor = new GeometricalObjectSaver();
		for (int i = 0, size = documentModel.getSize(); i < size; i ++) {
			documentModel.getObject(i).accept(visitor);
		}
		return visitor.getLines();
	}
	
	/**
	 * Writes the given {@code List} of Strings (each representing a line of text)
	 * into the specified file.
	 * 
	 * @param filePath Path of the specified file.
	 * @param lines The lines of textual content.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void writeToFile(Path filePath, List<String> lines) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(Files.newOutputStream(filePath)))) {
			for (String line : lines) {
				bw.write(line + "\n");
			}
		}
	}
	
	/**
	 * Used for saving the {@link GeometricalObject}s from the given frame's
	 * document model.<br>
	 * Shows all necessary dialogs and opens the save file chooser only
	 * if the given frame's save path has not yet been set.
	 * 
	 * @param frame The given frame.
	 * @param fileChooser The given save {@link JFileChooser}.
	 */
	public static void save(JVDraw frame, JFileChooser fileChooser) {
		if (frame.getSavePath() == null) {
			if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				Path chosenPath = fileChooser.getSelectedFile().toPath();
				if (!chosenPath.getFileName().toString().endsWith(".jvd")) {
					showError(frame, "JVDraw files (*.jvd) allowed only.");
					return;
				}
				if (Files.isRegularFile(chosenPath)) {
					String message = "Are you sure you want to overwrite "
										+ chosenPath.getFileName() + "?";
					if (JOptionPane.showConfirmDialog(frame, message) != JOptionPane.OK_OPTION) {
						showInfo(frame, "File not saved.");
						return;
					}
				}
				List<String> lines = Util.getObjectsAsText(frame.getDocumentModel());
				try {
					writeToFile(chosenPath, lines);
					frame.setSavePath(chosenPath);
					showInfo(frame, "File saved successfully.");
				} catch (IOException ex) {
					showError(frame, "An error occurred while saving JVD file.");
				}
			}
		} else {
			try {
				writeToFile(
					frame.getSavePath(), Util.getObjectsAsText(frame.getDocumentModel()));
				showInfo(frame, "File saved successfully.");
			} catch (IOException ex) {
				showError(frame, "An error occurred while saving JVD file.");
			}
		}
	}
	
	/**
	 * Returns {@code true} if the given value is between 0 and 255,
	 * so it can be used for {@link Color} components.
	 * 
	 * @param value The given value.
	 * @return {@code true} if the given value is from [0, 255], or false otherwise.
	 */
	private static boolean isValidRGB(int value) {
		return value >= 0 && value <= 255;
	}
	
	/**
	 * Parses a line from a JVD file (split into whitespace-separated parts)
	 * into a corresponding {@link Line}.
	 * 
	 * @param parts The given split line.
	 * @return The corresponding {@link Line}, or {@code null} if line not valid.
	 */
	private static Line parseLine(String[] parts) {
		int x0 = Integer.parseInt(parts[1]);
		int y0 = Integer.parseInt(parts[2]);
		int x1 = Integer.parseInt(parts[3]);
		int y1 = Integer.parseInt(parts[4]);
		int r = Integer.parseInt(parts[5]);
		if (!isValidRGB(r)) return null;
		int g = Integer.parseInt(parts[6]);
		if (!isValidRGB(g)) return null;
		int b = Integer.parseInt(parts[7]);
		if (!isValidRGB(b)) return null;
		return new Line(new Point(x0, y0), new Point(x1, y1), new Color(r, g, b));
	}
	
	/**
	 * Parses a line from a JVD file (split into whitespace-separated parts)
	 * into a corresponding {@link Circle}.
	 * 
	 * @param parts The given split line.
	 * @return The corresponding {@link Circle}, or {@code null} if line not valid.
	 */
	private static Circle parseCircle(String[] parts) {
		int cx = Integer.parseInt(parts[1]);
		int cy = Integer.parseInt(parts[2]);
		int radius = Integer.parseInt(parts[3]);
		int r = Integer.parseInt(parts[4]);
		if (!isValidRGB(r)) return null;
		int g = Integer.parseInt(parts[5]);
		if (!isValidRGB(g)) return null;
		int b = Integer.parseInt(parts[6]);
		if (!isValidRGB(b)) return null;
		return new Circle(new Point(cx, cy), radius, new Color(r, g, b));
	}
	
	/**
	 * Parses a line from a JVD file (split into whitespace-separated parts)
	 * into a corresponding {@link FilledCircle}.
	 * 
	 * @param parts The given split line.
	 * @return The corresponding {@link FilledCircle}, or {@code null} if line not valid.
	 */
	private static FilledCircle parseFilledCircle(String[] parts) {
		int cx = Integer.parseInt(parts[1]);
		int cy = Integer.parseInt(parts[2]);
		int radius = Integer.parseInt(parts[3]);
		int fr = Integer.parseInt(parts[4]);
		if (!isValidRGB(fr)) return null;
		int fg = Integer.parseInt(parts[5]);
		if (!isValidRGB(fg)) return null;
		int fb = Integer.parseInt(parts[6]);
		if (!isValidRGB(fb)) return null;
		int br = Integer.parseInt(parts[7]);
		if (!isValidRGB(br)) return null;
		int bg = Integer.parseInt(parts[8]);
		if (!isValidRGB(bg)) return null;
		int bb = Integer.parseInt(parts[9]);
		if (!isValidRGB(bb)) return null;
		Color bgColor = new Color(br, bg, bb);
		Color fgColor = new Color(fr, fg, fb);
		return new FilledCircle(new Point(cx, cy), radius, bgColor, fgColor);
	}
}
