package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Class that represents a two-dimensional vector,
 * ie. a point in a two-dimensional plane.
 * 
 * @author Mate Gasparini
 */
public class Vector2D {
	
	/**
	 * The x-coordinate of this vector.
	 */
	private double x;
	/**
	 * The y-coordinate of this vector.
	 */
	private double y;
	
	/**
	 * Constructor specifying the vector coordinates.
	 * 
	 * @param x The x-coordinate of the vector.
	 * @param y The y-coordinate of the vector.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the value of the x-coordinate of this vector.
	 * 
	 * @return The x-coordinate of this vector.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the value of the x-coordinate of this vector.
	 * 
	 * @return The y-coordinate of this vector.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates this vector by the given offset vector.
	 * 
	 * @param offset The given offset vector.
	 */
	public void translate(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Returns this vector translated by the given offset vector,
	 * but does not modify this vector.
	 * 
	 * @param offset The given offset vector.
	 * @return The resulting translated vector.
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(
			this.x + offset.x,
			this.y + offset.y
		);
	}
	
	/**
	 * Rotates this vector by the given angle.
	 * 
	 * @param angle The given angle.
	 */
	public void rotate(double angle) {
		double sine = sin(toRadians(angle));
		double cosine = cos(toRadians(angle));
		
		double xNew = x*cosine - y*sine;
		y = x*sine + y*cosine;
		x = xNew;
	}
	
	/**
	 * Returns this vector rotated by the given angle,
	 * but does not modify this vector.
	 * 
	 * @param angle The given angle.
	 * @return The resulting rotated vector.
	 */
	public Vector2D rotated(double angle) {
		double sine = sin(toRadians(angle));
		double cosine = cos(toRadians(angle));
		
		return new Vector2D(
			x*cosine - y*sine,
			x*sine + y*cosine
		);
	}
	
	/**
	 * Scales this vector by the given scaling value.
	 * 
	 * @param scaler The given scaling value.
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns this vector scaled by the given scaling value,
	 * but does not modify this vector.
	 * 
	 * @param scaler The given scaling value.
	 * @return The resulting scaled vector.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(
			x * scaler,
			y * scaler
		);
	}
	
	/**
	 * Returns a reference to a new <code>Vector2D</code> instance
	 * with the same coordinates as this vector.
	 * 
	 * @return A new vector with the same coordinates as this vector.
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
}
