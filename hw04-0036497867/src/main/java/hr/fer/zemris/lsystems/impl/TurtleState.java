package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents some state of the turtle,
 * including its position, direction, color and move length.
 * 
 * @author Mate Gasparini
 */
public class TurtleState {
	
	/**
	 * Current turtle's position.
	 */
	private Vector2D position;
	/**
	 * Unit vector of the current turtle's direction.
	 */
	private Vector2D direction;
	/**
	 * Color of the turtle's drawn line.
	 */
	private Color color;
	/**
	 * Length of a turtle's step.
	 */
	private double moveLength;
	
	/**
	 * Constructor specifying the position, direction, color
	 * and move length of the turtle.
	 * 
	 * @param position Turtle's position.
	 * @param direction Unit vector of the turtle's direction.
	 * @param color Color of the turtle's drawn line.
	 * @param moveLength Length of a turtle's step.
	 */
	public TurtleState(Vector2D position, Vector2D direction,
			Color color, double moveLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.moveLength = moveLength;
	}
	
	/**
	 * Returns a reference to a new <code>TurtleState</code> instance
	 * with the copied attribute values as this turtle state.
	 * 
	 * @return A new turtle state with the same specifications as this.
	 */
	public TurtleState copy() {
		return new TurtleState(
			position.copy(), direction.copy(), color, moveLength
		);
	}
	
	/**
	 * Returns the current turtle's position.
	 * 
	 * @return The position of the turtle.
	 */
	public Vector2D getPosition() {
		return position;
	}
	
	/**
	 * Sets the turtle's position to the given position.
	 * 
	 * @param position The given position.
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	/**
	 * Returns the current turtle's direction.
	 * 
	 * @return The direction of the turtle.
	 */
	public Vector2D getDirection() {
		return direction;
	}
	
	/**
	 * Returns the current turtle's color.
	 * 
	 * @return The color of the turtle's drawn line.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets the turtle's color to the given color.
	 * 
	 * @param color The given color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Returns the current turtle's move length.
	 * 
	 * @return The turtle's move length.
	 */
	public double getMoveLength() {
		return moveLength;
	}
	
	/**
	 * Sets the turtle's move length to the given move length.
	 * 
	 * @param moveLength The given move length.
	 */
	public void setMoveLength(double moveLength) {
		this.moveLength = moveLength;
	}
}
