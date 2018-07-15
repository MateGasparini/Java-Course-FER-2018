package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an immutable complex number specified by
 * double values of its real and its imaginary part.
 * 
 * @author Mate Gasparini
 */
public class Complex {
	
	/**
	 * Value of the real part.
	 */
	private double re;
	/**
	 * Value of the imaginary part.
	 */
	private double im;
	
	/**
	 * Number 0.
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Number 1.
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Number -1.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Number i.
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Number -i.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Default constructor with both real and imaginary parts equal to 0.<br>
	 * As instances of the {@code Complex} class are immutable,
	 * it is advised to acquire the reference through {@link Complex#ZERO},
	 * rather than through this constructor.
	 */
	public Complex() {
	}
	
	/**
	 * Constructor specifying the values of the real and the imaginary part.
	 * 
	 * @param re The value of the real part.
	 * @param im The value of the imaginary part.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Returns the value of the complex number's module.
	 * 
	 * @return The complex number's module.
	 */
	public double module() {
		return sqrt(re*re + im*im);
	}
	
	/**
	 * Returns a reference to a new {@code Complex} calculated as
	 * a product of this and the given complex number.
	 * 
	 * @param c The given complex number.
	 * @return Reference to the product complex number.
	 */
	public Complex multiply(Complex c) {
		return new Complex(
			this.re*c.re - this.im*c.im,
			this.re*c.im + this.im*c.re
		);
	}
	
	/**
	 * Returns a reference to a new {@code Complex} calculated as
	 * a quotient of this and the given complex number.
	 * 
	 * @param c The given complex number.
	 * @return Reference to the quotient complex number.
	 * @throws IllegalArgumentException If the given complex number is zero.
	 */
	public Complex divide(Complex c) {
		double denominator = c.re*c.re + c.im*c.im;
		
		if (denominator == 0.0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		
		return new Complex(
			(this.re*c.re + this.im*c.im) / denominator,
			(-this.re*c.im + this.im*c.re) / denominator
		);
	}
	
	/**
	 * Returns a reference to a new {@code Complex} calculated as
	 * a sum of this and the given complex number.
	 * 
	 * @param c The given complex number.
	 * @return Reference to the sum complex number.
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * Returns a reference to a new {@code Complex} calculated as
	 * a difference of this and the given complex number.
	 * 
	 * @param c The given complex number.
	 * @return Reference to the difference complex number.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * Returns a reference to a new {@code Complex} with
	 * real and imaginary part values negated.
	 * 
	 * @return Reference to the negated complex number.
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * Returns a reference to a new {@code Complex} calculated as
	 * this complex number raised to the power of n.
	 * 
	 * @param n The exponent of the power operation.
	 * @return Reference to the n-th power {@code Complex}.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
				"Cannot raise complex number to negative power."
			);
		} else if (n == 0) {
			return Complex.ONE;
		} else if (n == 1) {
			return this;
		}
		
		double module = pow(this.module(), n);
		double angle = n * atan2(im, re);
		
		return new Complex(
			module * cos(angle),
			module * sin(angle)
		);
	}
	
	/**
	 * Returns a {@code List} of references to new {@code Complex} objects,
	 * each representing one of the n n-th roots of this comlex number.
	 * 
	 * @param n The degree of root.
	 * @return {@code List} of root {@code Complex} references.
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(
				"Degree of root must be positive."
			);
		}
		
		double module = pow(this.module(), 1.0 / n);
		
		List<Complex> roots = new ArrayList<>(n);
		for (int k = 0; k < n; k ++) {
			double angle = (atan2(im, re) + 2*k*PI) / n;
			Complex root = new Complex(
				module * cos(angle),
				module * sin(angle)
			);
			roots.add(root);
		}
		
		return roots;
	}
	
	@Override
	public String toString() {
		if (re == 0.0) {
			if (im == 0.0) {
				// z = 0
				return String.valueOf(0.0);
			} else {
				// z = bi
				return String.valueOf(im) + I;
			}
		} else if (im == 0.0) {
			// z = a
			return String.valueOf(re);
		}
		
		StringBuilder builder = new StringBuilder();
		
		// z = a + bi
		builder.append(re);
		if (im >= 0.0) {
			builder.append(PLUS);
		}
		builder.append(im).append(I);
		
		return builder.toString();
	}
	
	/* STRING CONSTANTS */
	private static final String PLUS = "+";
	private static final String I = "i";
}
