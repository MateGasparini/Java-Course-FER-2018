package hr.fer.zemris.java.hw16.jvdraw.geometry.visitors;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * {@link GeometricalObjectVisitor} which converts all visited {@link GeometricalObject}s
 * to a {@code List} of Strings used for saving to a JVD file.
 * 
 * @author Mate Gasparini
 */
public class GeometricalObjectSaver implements GeometricalObjectVisitor {
	
	/** The generated {@code List} of JVD file lines. */
	private List<String> lines = new ArrayList<>();
	
	/**
	 * Returns the generated {@code List} of JVD file lines.
	 * 
	 * @return The generated {@code List} of JVD file lines.
	 */
	public List<String> getLines() {
		return lines;
	}
	
	@Override
	public void visit(Line line) {
		Point start = line.getStart();
		Point end = line.getEnd();
		Color color = line.getColor();
		lines.add(
			String.format("LINE %d %d %d %d %d %d %d",
			start.getX(), start.getY(), end.getX(), end.getY(),
			color.getRed(), color.getGreen(), color.getBlue())
		);
	}
	
	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		Color color = circle.getColor();
		lines.add(
			String.format("CIRCLE %d %d %d %d %d %d",
			center.getX(), center.getY(), circle.getRadius(),
			color.getRed(), color.getGreen(), color.getBlue())
		);
	}
	
	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		Color fgColor = filledCircle.getFgColor();
		Color bgColor = filledCircle.getBgColor();
		lines.add(
			String.format("FCIRCLE %d %d %d %d %d %d %d %d %d",
			center.getX(), center.getY(), filledCircle.getRadius(),
			fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue(),
			bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue())
		);
	}
}
