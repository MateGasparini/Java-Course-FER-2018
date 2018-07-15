package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Interface representing some drawing tool which, when in use,
 * draws some concrete {@link GeometricalObject}.
 * 
 * @author Mate Gasparini
 */
public interface Tool {
	
	/**
	 * Called when the mouse is pressed.
	 * 
	 * @param e The event to be processed.
	 */
	void mousePressed(MouseEvent e);
	
	/**
	 * Called when the mouse is released.
	 * 
	 * @param e The event to be processed.
	 */
	void mouseReleased(MouseEvent e);
	
	/**
	 * Called when the mouse is clicked.
	 * 
	 * @param e The event to be processed.
	 */
	void mouseClicked(MouseEvent e);
	
	/**
	 * Called when the mouse is moved.
	 * 
	 * @param e The event to be processed.
	 */
	void mouseMoved(MouseEvent e);
	
	/**
	 * Called when the mouse is dragged.
	 * 
	 * @param e The event to be processed.
	 */
	void mouseDragged(MouseEvent e);
	
	/**
	 * Paints the corresponding {@link GeometricalObject}
	 * on the given {@link Graphics2D} object.
	 * 
	 * @param g2d The given {@link Graphics2D} object.
	 */
	void paint(Graphics2D g2d);
}
