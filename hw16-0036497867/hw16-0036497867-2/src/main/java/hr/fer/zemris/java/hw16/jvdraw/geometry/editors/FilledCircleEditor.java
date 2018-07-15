package hr.fer.zemris.java.hw16.jvdraw.geometry.editors;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * A {@link JPanel} providing functionality of modification
 * of some existing {@link FilledCircle} object.
 * 
 * @author Mate Gasparini
 */
public class FilledCircleEditor extends GeometricalObjectEditor {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** The specified {@link FilledCircle}. */
	private FilledCircle filledCircle;
	
	/** Text field for center point's x coordinate. */
	private JTextField centerX;
	
	/** Text field for center point's y coordinate. */
	private JTextField centerY;
	
	/** Text field for radius length. */
	private JTextField radius;
	
	/** Color chooser for the outline color. */
	private JColorChooser fgColorChooser;
	
	/** Color chooser for the fill color. */
	private JColorChooser bgColorChooser;
	
	/** The new chosen center {@link Point}. */
	private Point newCenter;
	
	/** The new chosen radius length. */
	private int newRadius;
	
	/** The new chosen outline color. */
	private Color newFgColor;
	
	/** The new chosen fill color. */
	private Color newBgColor;
	
	/**
	 * Constructor specifying the {@link FilledCircle} object.
	 * 
	 * @param filledCircle The specified {@link FilledCircle}.
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		initGUI();
	}
	
	/**
	 * Initializes the {@link JPanel} with all required fields.
	 */
	private void initGUI() {
		centerX = new JTextField(String.valueOf(filledCircle.getCenter().getX()), 5);
		centerY = new JTextField(String.valueOf(filledCircle.getCenter().getY()), 5);
		radius = new JTextField(String.valueOf(filledCircle.getRadius()), 5);
		fgColorChooser = new JColorChooser(filledCircle.getFgColor());
		bgColorChooser = new JColorChooser(filledCircle.getBgColor());
		
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
		
		JPanel fgColorLabelPanel = new JPanel();
		fgColorLabelPanel.add(new JLabel("Outline color"));
		add(fgColorLabelPanel);
		add(fgColorChooser);
		
		JPanel bgColorLabelPanel = new JPanel();
		bgColorLabelPanel.add(new JLabel("Area color"));
		add(bgColorLabelPanel);
		add(bgColorChooser);
	}
	
	@Override
	public void checkEditing() {
		try {
			newCenter = new Point(
				Integer.parseInt(centerX.getText()), Integer.parseInt(centerY.getText()));
			newRadius = Integer.parseInt(radius.getText());
			newFgColor = fgColorChooser.getColor();
			newBgColor = bgColorChooser.getColor();
		} catch (NumberFormatException ex) {
			throw new RuntimeException("Only integer coordinates allowed.");
		}
	}
	
	@Override
	public void acceptEditing() {
		filledCircle.update(newCenter, newRadius, newBgColor, newFgColor);
	}
}
