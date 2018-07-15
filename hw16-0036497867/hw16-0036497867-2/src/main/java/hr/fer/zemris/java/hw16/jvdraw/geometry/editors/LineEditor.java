package hr.fer.zemris.java.hw16.jvdraw.geometry.editors;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * A {@link JPanel} providing functionality of modification
 * of some existing {@link Line} object.
 * 
 * @author Mate Gasparini
 */
public class LineEditor extends GeometricalObjectEditor {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** The specified {@link Line}. */
	private Line line;
	
	/** Text field for starting point's x coordinate. */
	private JTextField startX;
	
	/** Text field for starting point's y coordinate. */
	private JTextField startY;
	
	/** Text field for ending point's x coordinate. */
	private JTextField endX;
	
	/** Text field for ending point's y coordinate. */
	private JTextField endY;
	
	/** Color chooser for the line color. */
	private JColorChooser colorChooser;
	
	/** The new chosen starting {@link Point}. */
	private Point newStart;
	
	/** The new chosen ending {@link Point}. */
	private Point newEnd;
	
	/** The new chosen color. */
	private Color newColor;
	
	/**
	 * Constructor specifying the {@link Line} object.
	 * 
	 * @param line The specified {@link Line}.
	 */
	public LineEditor(Line line) {
		this.line = line;
		initGUI();
	}
	
	/**
	 * Initializes the {@link JPanel} with all required fields.
	 */
	private void initGUI() {
		startX = new JTextField(String.valueOf(line.getStart().getX()), 5);
		startY = new JTextField(String.valueOf(line.getStart().getY()), 5);
		endX = new JTextField(String.valueOf(line.getEnd().getX()), 5);
		endY = new JTextField(String.valueOf(line.getEnd().getY()), 5);
		colorChooser = new JColorChooser(line.getColor());
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel startLabelPanel = new JPanel();
		startLabelPanel.add(new JLabel("Starting point"));
		add(startLabelPanel);
		JPanel startPanel = new JPanel();
		startPanel.add(new JLabel("X: "));
		startPanel.add(startX);
		startPanel.add(new JLabel("Y: "));
		startPanel.add(startY);
		add(startPanel);
		
		JPanel endLabelPanel = new JPanel();
		endLabelPanel.add(new JLabel("Ending point"));
		add(endLabelPanel);
		JPanel endPanel = new JPanel();
		endPanel.add(new JLabel("X: "));
		endPanel.add(endX);
		endPanel.add(new JLabel("Y: "));
		endPanel.add(endY);
		add(endPanel);
		
		JPanel colorLabelPanel = new JPanel();
		colorLabelPanel.add(new JLabel("Line color"));
		add(colorLabelPanel);
		add(colorChooser);
	}
	
	@Override
	public void checkEditing() {
		try {
			newStart = new Point(
				Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
			newEnd = new Point(
				Integer.parseInt(endX.getText()), Integer.parseInt(endY.getText()));
			newColor = colorChooser.getColor();
		} catch (NumberFormatException ex) {
			throw new RuntimeException("Only integer coordinates allowed.");
		}
	}
	
	@Override
	public void acceptEditing() {
		line.update(newStart, newEnd, newColor);
	}
}
