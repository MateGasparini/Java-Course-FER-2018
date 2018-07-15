package hr.fer.zemris.java.hw16.jvdraw.geometry.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.editors.FilledCircleEditor;

/**
 * {@link GeometricalObject} representing a circle with specified outline and fill
 * color, center {@link Point} and radius length.
 * 
 * @author Mate Gasparini
 */
public class FilledCircle extends GeometricalObject {
	
	/** The center {@link Point}. */
	private Point center;
	
	/** The radius length (in pixels). */
	private int radius;
	
	/** The fill color. */
	private Color bgColor;
	
	/** The outline color. */
	private Color fgColor;
	
	/**
	 * Constructor specifying the center {@link Point}, radius length,
	 * fill and outline color.
	 * 
	 * @param center The specified center {@link Point}.
	 * @param radius The specified radius length (in pixels).
	 * @param bgColor The specified fill color.
	 * @param fgColor The specified outline color.
	 */
	public FilledCircle(Point center, int radius, Color bgColor, Color fgColor) {
		this.center = center;
		this.radius = radius;
		this.bgColor = bgColor;
		this.fgColor = fgColor;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
	
	/**
	 * Returns the center {@link Point}.
	 * 
	 * @return The center {@link Point}.
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	 * Returns the radius length.
	 * 
	 * @return The radius length (in pixels).
	 */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * Returns the fill color.
	 * 
	 * @return The fill color.
	 */
	public Color getBgColor() {
		return bgColor;
	}
	
	/**
	 * Returns the outline color.
	 * 
	 * @return The outline color.
	 */
	public Color getFgColor() {
		return fgColor;
	}
	
	/**
	 * Updates the attributes with the given values and notifies the observers
	 * that the filled circle has changed.
	 * 
	 * @param center The given center {@link Point}.
	 * @param radius The given radius length (in pixels).
	 * @param bgColor The given fill color.
	 * @param fgColor The given outline color.
	 */
	public void update(Point center, int radius, Color bgColor, Color fgColor) {
		this.center = center;
		this.radius = radius;
		this.bgColor = bgColor;
		this.fgColor = fgColor;
		fire();
	}
	
	@Override
	public String toString() {
		return String.format(
			"Filled circle %s, %d, #%02X%02X%02X",
			center.toString(), radius, bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()
		);
	}
}
