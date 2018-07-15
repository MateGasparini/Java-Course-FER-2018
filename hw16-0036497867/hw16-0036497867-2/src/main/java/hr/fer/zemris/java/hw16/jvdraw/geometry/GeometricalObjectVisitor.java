package hr.fer.zemris.java.hw16.jvdraw.geometry;

import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;

/**
 * Visitor interface used for visiting all of the different {@link GeometricalObject}s.
 * 
 * @author Mate Gasparini
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Visits the given {@link Line}.
	 * 
	 * @param line The given {@link Line}.
	 */
	void visit(Line line);
	
	/**
	 * Visits the given {@link Circle}.
	 * 
	 * @param circle The given {@link Circle}.
	 */
	void visit(Circle circle);
	
	/**
	 * Visits the given {@link FilledCircle}.
	 * 
	 * @param filledCircle The given {@link FilledCircle}.
	 */
	void visit(FilledCircle filledCircle);
}
