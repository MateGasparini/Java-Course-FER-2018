package hr.fer.zemris.java.hw16.jvdraw.geometry.editors;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * A {@link JPanel} providing functionality of modification
 * of some existing {@link Circle} object.
 * 
 * @author Mate Gasparini
 */
public class CircleEditor extends GeometricalObjectEditor {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** The specified {@link Circle}. */
	private Circle circle;
	
	/** Text field for center point's x coordinate. */
	private JTextField centerX;
	
	/** Text field for center point's y coordinate. */
	private JTextField centerY;
	
	/** Text field for radius length. */
	private JTextField radius;
	
	/** Color chooser for the circle color. */
	private JColorChooser colorChooser;
	
	/** The new chosen center {@link Point}. */
	private Point newCenter;
	
	/** The new chosen radius length. */
	private int newRadius;
	
	/** The new chosen color. */
	private Color newColor;
	
	/**
	 * Constructor specifying the {@link Circle} object.
	 * 
	 * @param circle The specified {@link Circle}.
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		initGUI();
	}
	
	/**
	 * Initializes the {@link JPanel} with all required fields.
	 */
	private void initGUI() {
		centerX = new JTextField(String.valueOf(circle.getCenter().getX()), 5);
		centerY = new JTextField(String.valueOf(circle.getCenter().getY()), 5);
		radius = new JTextField(String.valueOf(circle.getRadius()), 5);
		colorChooser = new JColorChooser(circle.getColor());
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel centerLabelPanel = new JPanel();
		centerLabelPanel.add(new JLabel("Center point"));
		add(centerLabelPanel);
		JPanel centerPanel = new JPanel();
		centerPanel.add(new JLabel("X: "));
		centerPanel.add(centerX);
		centerPanel.add(new JLabel("Y: "));
		centerPanel.add(centerY);
		add(centerPanel);
		
		JPanel radiusPanel = new JPanel();
		radiusPanel.add(new JLabel("Radius: "));
		radiusPanel.add(radius);
		add(radiusPanel);
		
		JPanel colorLabelPanel = new JPanel();
		colorLabelPanel.add(new JLabel("Outline color"));
		add(colorLabelPanel);
		add(colorChooser);
	}
	
	@Override
	public void checkEditing() {
		try {
			newCenter = new Point(
				Integer.parseInt(centerX.getText()), Integer.parseInt(centerY.getText()));
			newRadius = Integer.parseInt(radius.getText());
			newColor = colorChooser.getColor();
		} catch (NumberFormatException ex) {
			throw new RuntimeException("Only integer coordinates allowed.");
		}
	}
	
	@Override
	public void acceptEditing() {
		circle.update(newCenter, newRadius, newColor);
	}
}
