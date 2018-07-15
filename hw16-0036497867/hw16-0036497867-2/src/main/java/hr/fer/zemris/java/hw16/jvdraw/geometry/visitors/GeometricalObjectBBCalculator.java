package hr.fer.zemris.java.hw16.jvdraw.geometry.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * {@link GeometricalObjectVisitor} which calculates the bounding {@link Rectangle}
 * for all visited objects.
 * 
 * @author Mate Gasparini
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	
	/** Minimum x coordinate. */
	private int xMin;
	
	/** Minimum y coordinate. */
	private int yMin;
	
	/** Maximum x coordinate. */
	private int xMax;
	
	/** Maximum y coordinate. */
	private int yMax;
	
	/** Set to true if one object has been visited. */
	private boolean initialized;
	
	@Override
	public void visit(Line line) {
		Point start = line.getStart();
		if (!initialized) {
			init(start.getX(), start.getY());
		} else {
			update(start.getX(), start.getY());
		}
		Point end = line.getEnd();
		update(end.getX(), end.getY());
	}
	
	@Override
	public void visit(Circle circle) {
		visitCircle(circle.getCenter(), circle.getRadius());
	}
	
	@Override
	public void visit(FilledCircle filledCircle) {
		visitCircle(filledCircle.getCenter(), filledCircle.getRadius());
	}
	
	/**
	 * Returns the {@link Rectangle} which can contain all {@link GeometricalObject}s.
	 * 
	 * @return The bounding box for the {@link GeometricalObject}s.
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
	}
	
	/**
	 * Visits the {@link Circle} and {@link FilledCircle} objects in the same way.
	 * 
	 * @param center The given center {@link Point}.
	 * @param radius The given radius length.
	 */
	private void visitCircle(Point center, int radius) {
		int cx = center.getX();
		int cy = center.getY();
		if (!initialized) {
			init(cx - radius, cy - radius);
		} else {
			update(cx - radius, cy - radius);
		}
		update(cx + radius, cy + radius);
	}
	
	/**
	 * Initializes all attributes to the given values.
	 * 
	 * @param x The given x coordinate.
	 * @param y The given y coordinate.
	 */
	private void init(int x, int y) {
		xMin = x;
		xMax = x;
		yMin = y;
		yMax = y;
		initialized = true;
	}
	
	/**
	 * Updates all attributes based on the given values.
	 * 
	 * @param x The given x coordinate.
	 * @param y The given y coordinate.
	 */
	private void update(int x, int y) {
		xMin = x < xMin ? x : xMin;
		xMax = x > xMax ? x : xMax;
		yMin = y < yMin ? y : yMin;
		yMax = y > yMax ? y : yMax;
	}
}
