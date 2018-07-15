package hr.fer.zemris.java.hw16.jvdraw.geometry.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.editors.LineEditor;

/**
 * {@link GeometricalObject} representing a colored line with specified starting
 * and ending {@link Point}s.
 * 
 * @author Mate Gasparini
 */
public class Line extends GeometricalObject {
	
	/** The starting {@link Point}. */
	private Point start;
	
	/** The ending {@link Point}. */
	private Point end;
	
	/** The color of the line. */
	private Color color;
	
	/**
	 * Constructor specifying the {@link Point}s and the color.
	 * 
	 * @param start The specified starting {@link Point}.
	 * @param end The specified ending {@link Point}.
	 * @param color The specified color.
	 */
	public Line(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	
	/**
	 * Updates the attributes with the given values and notifies the observers
	 * that the line has changed.
	 * 
	 * @param start The given starting {@link Point}.
	 * @param end The given ending {@link Point}.
	 * @param color The given color.
	 */
	public void update(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
		fire();
	}
	
	/**
	 * Returns the starting {@link Point}.
	 * 
	 * @return The starting {@link Point}.
	 */
	public Point getStart() {
		return start;
	}
	
	/**
	 * Returns the ending {@link Point}.
	 * 
	 * @return The ending {@link Point}.
	 */
	public Point getEnd() {
		return end;
	}
	
	/**
	 * Returns the line color.
	 * 
	 * @return The line color.
	 */
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return "Line " + start.toString() + "-" + end.toString();
	}
}
