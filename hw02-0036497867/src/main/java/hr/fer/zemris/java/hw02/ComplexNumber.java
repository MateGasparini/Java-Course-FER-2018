package hr.fer.zemris.java.hw02;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.PI;

/**
 * Class representing an unmodifiable complex number that is specified
 * by values of it's real and it's imaginary part.
 * 
 * @author Mate Gasparini
 */
public class ComplexNumber {
	
	/**
	 * Value of this complex number's real part.
	 */
	final private double real;
	/**
	 * Value of this complex number's imaginary part.
	 */
	final private double imaginary;
	
	/**
	 * Class constructor specifying the real
	 * and the imaginary part of the number.
	 * 
	 * @param real Value of the real part.
	 * @param imaginary Value of the imaginary part.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Factory method that constructs a <code>ComplexNumber</code>
	 * using only it's real part (imaginary part is 0.0).
	 * 
	 * @param real Value of the complex number's real part.
	 * @return Complex number specified by the parameter.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}
	
	/**
	 * Factory method that constructs a <code>ComplexNumber</code>
	 * using only it's imaginary part (real part is 0.0).
	 * 
	 * @param imaginary Value of the complex number's imaginary part.
	 * @return Complex number specified by the parameter.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}
	
	/**
	 * Factory method that constructs a <code>ComplexNumber</code>
	 * using it's polar coordinates.
	 * 
	 * @param magnitude Value of the complex number's magnitude.
	 * @param angle Value of the complex number's angle.
	 * @return Complex number specified by the parameters.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(
			magnitude * cos(angle),
			magnitude * sin(angle)
		);
	}
	
	/**
	 * Method that accepts a <code>String</code> which represents a complex
	 * number, and returns a new corresponding <code>ComplexNumber</code>.
	 * 
	 * @param s String that is in the form of a complex number a+bi,
	 * 			where a and b are some real numbered values. 
	 * @return Complex number specified by the string parameter.
	 * @throws NullPointerException If <code>s</code> is <code>null</code>.
	 * @throws IllegalArgumentException If <code>s</code> is not parseable. 
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new NullPointerException("Cannot parse null.");
		}
		
		boolean hasImaginary = false;
		if (s.endsWith("i")) {
			hasImaginary = true;
			
			// Replace single 'i' values with value '1'.
			s = s.replaceFirst("\\+i", "+1");
			s = s.replaceFirst("\\-i", "-1");
			
			// Special case where input is just "i".
			if (s.equals("i")) {
				s = "1";
			}
			
			// Remove trailing 'i' values altogether.
			if (s.endsWith("i")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		
		// Split the real from the imaginary part.
		String[] parts = s.split("(?=\\+)|(?=\\-)");
		
		double real = 0.0;
		double imaginary = 0.0;
		
		try {
			if (parts.length == 1) {
				if (hasImaginary) {
					imaginary = Double.parseDouble(parts[0]);
				} else {
					real = Double.parseDouble(parts[0]);
				}
			} else if (parts.length == 2) {
				real = Double.parseDouble(parts[0]);
				imaginary = Double.parseDouble(parts[1]);
			}
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
				"String " + s + " not parseable."
			);
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Returns the value of this complex number's real part.
	 * 
	 * @return Value of the real part.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns the value of this complex number's imaginary part.
	 * 
	 * @return Value of the imaginary part.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Calculates and returns the value of this complex number's magnitude
	 * (distance from the origin point).
	 * 
	 * @return Magnitude value.
	 */
	public double getMagnitude() {
		return sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Calculates and returns the value of this complex number's angle
	 * (angle between the complex number's radius vector and the
	 * positive x-axis), also known as this complex number's argument.
	 * 
	 * @return Angle value.
	 */
	public double getAngle() {
		double angle = atan2(imaginary, real);
		return angle > 0 ? angle : 2*PI + angle;
	}
	
	/**
	 * Adds the other complex number to this complex number,
	 * and returns the result as a new <code>ComplexNumber</code>.
	 * 
	 * @param c The other complex number.
	 * @return Result of the operation.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(
			this.real + c.real,
			this.imaginary + c.imaginary
		);
	}
	
	/**
	 * Subtracts the other complex number from this complex number,
	 * and returns the result as a new <code>ComplexNumber</code>.
	 * 
	 * @param c The other complex number.
	 * @return Result of the operation.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(
			this.real - c.real,
			this.imaginary - c.imaginary
		);
	}
	
	/**
	 * Multiplies this complex number with the other complex number,
	 * and returns the result as a new <code>ComplexNumber</code>.
	 * 
	 * @param c The other complex number.
	 * @return Result of the operation.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(
			this.real*c.real - this.imaginary*c.imaginary,
			this.real*c.imaginary + this.imaginary*c.real
		);
	}
	
	/**
	 * Divides this complex number by the other complex number,
	 * and returns the result as a new <code>ComplexNumber</code>.
	 * 
	 * @param c The other complex number.
	 * @return Result of the operation.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double denominator = c.real*c.real + c.imaginary*c.imaginary;
		
		return new ComplexNumber(
			(this.real*c.real + this.imaginary*c.imaginary) / denominator,
			(- this.real*c.imaginary + this.imaginary*c.real) / denominator
		);
	}
	
	/**
	 * Raises this complex number to the power of <code>n</code>,
	 * and returns the result as a new <code>ComplexNumber</code>.
	 * 
	 * @param n The exponent of the power operation. 
	 * @return Result of the operation.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
				"Exponent must not be negative. Was: " + n + "."
			);
		}
		
		return fromMagnitudeAndAngle(
			pow(getMagnitude(), n),
			n * getAngle()
		);
	}
	
	/**
	 * Calculates the <code>n</code>-th root(s) of this complex number,
	 * and returns the result(s) as a new <code>ComplexNumber</code> array.
	 * 
	 * @param n Degree of the root operation.
	 * @return Result of the operation.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(
				"Root degree must be positive. Was: " + n + "."
			);
		}
		
		double magnitude = pow(getMagnitude(), 1.0 / n);
		
		ComplexNumber[] roots = new ComplexNumber[n];
		
		for (int k = 0; k < n; k ++) {
			roots[k] = fromMagnitudeAndAngle(
				magnitude,
				(getAngle() + 2*k*PI) / n
			);
		}
		
		return roots;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.valueOf(real));
		if (imaginary >= 0) {
			sb.append('+');
		}
		sb.append(String.valueOf(imaginary));
		sb.append('i');
		
		return sb.toString();
	}
}
