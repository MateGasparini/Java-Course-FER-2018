package hr.fer.zemris.math;

import static java.util.Objects.requireNonNull;

/**
 * Class that models a polynomial whose variable and coefficients are
 * complex numbers.<br>
 * It is specified by an array of roots of the polynomial.<br>
 * For a coefficient-based polynomial, see {@link ComplexPolynomial}.
 * 
 * @author Mate Gasparini
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Roots of the polynomial.
	 */
	private Complex[] roots;
	
	/**
	 * Constructor specifying the roots of the polynomial.
	 * 
	 * @param roots The specified roots.
	 * @throws NullPointerException If null is given as one of the arguments.
	 * @throws IllegalArgumentException If no roots are provided.
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		requireNonNull(roots, "Roots must not be null.");
		
		if (roots.length == 0) {
			throw new IllegalArgumentException(
				"Complex polynomial must have at least 1 root."
			);
		}
		
		for (int i = 0; i < roots.length; i ++) {
			requireNonNull(roots[i], "Root at index " + i + " is null.");
		}
		
		this.roots = roots;
	}
	
	/**
	 * Returns a reference to a {@code Complex} calculated by
	 * applying the given complex number to the polynomial.
	 * 
	 * @param z The given complex number.
	 * @return Reference to the result complex number.
	 */
	public Complex apply(Complex z) {
		Complex value = Complex.ONE;
		
		for (Complex root : roots) {
			value = value.multiply(z.sub(root));
		}
		
		return value;
	}
	
	/**
	 * Converts the rooted polynomial to a {@code ComplexPolynomial}
	 * which contains coefficients instead of roots.
	 * 
	 * @return Reference to the converted polynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(roots[0].negate(), Complex.ONE);
		for (int i = 1; i < roots.length; i ++) {
			ComplexPolynomial nextPolynomial = new ComplexPolynomial(roots[i].negate(), Complex.ONE);
			polynomial = polynomial.multiply(nextPolynomial);
		}
		
		return polynomial;
	}
	
	/**
	 * Finds the root closest to the given complex number,
	 * but only if it is closer than given threshold,
	 * and returns its index in the array of roots.<br>
	 * If none is found, -1 is returned.
	 * 
	 * @param z The given 
	 * @param threshold The given threshold.
	 * @return The index of the root closest to the given complex number,
	 * 		or -1 if the smallest distance is larger than given threshold.
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int indexOfClosest = -1;
		double distanceOfClosest = Double.MAX_VALUE;
		
		for (int i = 0; i < roots.length; i ++) {
			double distance = z.sub(roots[i]).module();
			
			if (distance < threshold && distance < distanceOfClosest) {
				distanceOfClosest = distance;
				indexOfClosest = i;
			}
		}
		
		return indexOfClosest;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (Complex root : roots) {
			builder.append(OPENED).append(root).append(CLOSED);
		}
		builder.setLength(builder.length() - 1);
		
		return builder.toString();
	}
	
	/* STRING CONSTANTS */
	private static final String OPENED = "(z-(";
	private static final String CLOSED = "))*";
}
