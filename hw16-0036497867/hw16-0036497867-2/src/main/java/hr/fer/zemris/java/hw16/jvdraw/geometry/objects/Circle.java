package hr.fer.zemris.java.hw16.jvdraw.geometry.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.editors.CircleEditor;

/**
 * {@link GeometricalObject} representing a colored circle outline
 * with specified center {@link Point} and radius length.
 * 
 * @author Mate Gasparini
 */
public class Circle extends GeometricalObject {
	
	/** The center {@link Point}. */
	private Point center;
	
	/** Radius length (in pixels). */
	private int radius;
	
	/** The color of the circle. */
	private Color color;
	
	/**
	 * Constructor specifying the center {@link Point}, the radius length
	 * and the color.
	 * 
	 * @param center The specified center {@link Point}.
	 * @param radius The specified radius length (in pixels).
	 * @param color The specified color.
	 */
	public Circle(Point center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
	
	/**
	 * Updates the attributes with the given values and notifies the observers
	 * that the circle has changed.
	 * 
	 * @param center The given center {@link Point}.
	 * @param radius The given radius length.
	 * @param color The given color.
	 */
	public void update(Point center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		fire();
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
	 * Returns the radius length (in pixels).
	 * 
	 * @return The radius length.
	 */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * Returns the circle color.
	 * 
	 * @return The circle color.
	 */
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return "Circle " + center.toString() + ", " + radius;
	}
}
