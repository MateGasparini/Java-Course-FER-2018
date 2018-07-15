package hr.fer.zemris.math;

/**
 * Class representing an immutable 3-dimensional vector.<br>
 * It can be thought of as a vector that starts at the origin point
 * and ends at the point with coordinates represented by the
 * three components of the {@code Vector3}.
 * 
 * @author Mate Gasparini
 */
public class Vector3 {
	
	/**
	 * Vector's first component, x coordinate.
	 */
	private double x;
	/**
	 * Vector's second component, y coordinate.
	 */
	private double y;
	/**
	 * Vector's third component, z coordinate.
	 */
	private double z;
	
	/**
	 * Constructor specifying the three components.
	 * 
	 * @param x The x component.
	 * @param y The y component.
	 * @param z The z component.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the norm (length) of the vector.
	 * 
	 * @return The vector's norm.
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Returns a reference to a new {@code Vector3} with the same direction,
	 * but with a norm of unit length.<br>
	 * If the vector's norm is equal to 0, the vector's copy is returned.
	 * 
	 * @return Reference to the normalized version of the vector.
	 */
	public Vector3 normalized() {
		double norm = this.norm();
		
		if (norm == 0.0) {
			return new Vector3(0.0, 0.0, 0.0);
		} else {
			return this.scale(1.0 / norm);
		}
	}
	
	/**
	 * Returns a reference to a new {@code Vector3} with each component equal
	 * to the sum of this and the given vector's corresponding components.
	 * 
	 * @param other The given vector.
	 * @return Reference to the sum vector.
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	
	/**
	 * Returns a reference to a new {@code Vector3} with each component equal
	 * to the difference of this and the given vector's corresponding components.
	 * 
	 * @param other The given vector.
	 * @return Reference to the difference vector.
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}
	
	/**
	 * Returns the value of the dot product of this and the given vector.
	 * 
	 * @param other The given vector.
	 * @return The dot product value.
	 */
	public double dot(Vector3 other) {
		return this.x*other.x + this.y*other.y + this.z*other.z;
	}
	
	/**
	 * Returns a reference to a new {@code Vector3} calculated as
	 * a cross product between this and the given vector.
	 * 
	 * @param other The given vector.
	 * @return Reference to the cross product vector.
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(
			this.y*other.z - this.z*other.y,
			this.z*other.x - this.x*other.z,
			this.x*other.y - this.y*other.x
		);
	}
	
	/**
	 * Returns a reference to a new {@code Vector3} with each component equal
	 * to the product of the given scale value and the corresponding component.
	 * 
	 * @param s The given scale value.
	 * @return Reference to the scaled vector.
	 */
	public Vector3 scale(double s) {
		return new Vector3(s*x, s*y, s*z);
	}
	
	/**
	 * Returns the cosine of the angle between this and the given vector.
	 * 
	 * @param other The given vector.
	 * @return The cosine of the angle between the two vectors.
	 * @throws IllegalArgumentException If this or other vector's norm is zero.
	 */
	public double cosAngle(Vector3 other) {
		double thisNorm = this.norm();
		if (thisNorm == 0.0) {
			throw new IllegalArgumentException("Angle undefined for zero-length vectors.");
		}
		
		double otherNorm = other.norm();
		if (otherNorm == 0.0) {
			throw new IllegalArgumentException("Angle undefined for zero-length vectors.");
		}
		
		return this.dot(other) / (this.norm() * other.norm());
	}
	
	/**
	 * Returns the x component.
	 * 
	 * @return The x component.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the y component.
	 * 
	 * @return The y component.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the z component.
	 * 
	 * @return The z component.
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns a reference to an array of double values {@code [x,y,z]},
	 * each of which corresponds to a vector component.
	 * 
	 * @return An array of the three components.
	 */
	public double[] toArray() {
		return new double[] {
			x, y, z
		};
	}
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}
	
//	public static void main(String[] args) {
//		Vector3 i = new Vector3(1, 0, 0);
//		Vector3 j = new Vector3(0, 1, 0);
//		Vector3 k = i.cross(j);
//		
//		Vector3 l = k.add(j).scale(5);
//		
//		Vector3 m = l.normalized();
//		
//		System.out.println(i);
//		System.out.println(j);
//		System.out.println(k);
//		System.out.println(l);
//		System.out.println(l.norm());
//		System.out.println(m);
//		System.out.println(l.dot(j));
//		System.out.println(i.add(new Vector3(0, 1, 0)).cosAngle(l));
//	}
}
