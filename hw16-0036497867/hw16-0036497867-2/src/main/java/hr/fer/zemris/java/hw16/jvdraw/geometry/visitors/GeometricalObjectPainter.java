package hr.fer.zemris.java.hw16.jvdraw.geometry.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * {@link GeometricalObjectVisitor} which paints all {@link GeometricalObject}s
 * on the specified {@link Graphics2D} object.
 * 
 * @author Mate Gasparini
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	
	/** The specified {@link Graphics2D} object. */
	private Graphics2D g2d;
	
	/**
	 * Constructor specifying the {@link Graphics2D} object.
	 * 
	 * @param g2d The specified {@link Graphics2D} object.
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void visit(Line line) {
		Point start = line.getStart();
		Point end = line.getEnd();
		g2d.setColor(line.getColor());
		g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();
		g2d.setColor(circle.getColor());
		g2d.drawOval(center.getX()-radius, center.getY()-radius, radius*2, radius*2);
	}
	
	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		int radius = filledCircle.getRadius();
		int cornerX = center.getX()-radius;
		int cornerY = center.getY()-radius;
		int diameter = 2 * radius;
		
		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval(cornerX, cornerY, diameter, diameter);
		g2d.setColor(filledCircle.getFgColor());
		g2d.drawOval(cornerX, cornerY, diameter, diameter);
	}
}
