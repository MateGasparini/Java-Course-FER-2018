package hr.fer.zemris.math;

import static java.util.Objects.requireNonNull;

/**
 * Class that models a polynomial whose variable and coefficients are
 * complex numbers.<br>
 * Coefficients are stored as an array of {@code Complex} references
 * (array index corresponds to the degree of coefficient).
 * 
 * @author Mate Gasparini
 */
public class ComplexPolynomial {
	
	/**
	 * Array of coefficients.
	 */
	private Complex[] factors;
	/**
	 * Order of the polynomial.
	 */
	private short order;
	
	/**
	 * Constructor specifying the polynomial coefficients.<br>
	 * If there are any leading factors (at greater indexes) that are
	 * equal to zero, they will be removed from the array. Bear in mind
	 * that this means a new array will be constructed and filled with
	 * values (up to the point of first leading zero).<br>
	 * If the array contains one element which is also a zero,
	 * the array won't be replaced.
	 * 
	 * @param factors The specified coefficients.
	 * @throws NullPointerException If null is given as one of the arguments.
	 * @throws IllegalArgumentException If no factors are provided.
	 */
	public ComplexPolynomial(Complex ...factors) {
		requireNonNull(factors, "Factors must not be null.");
		
		int length = factors.length;
		if (length == 0) {
			throw new IllegalArgumentException("At least one factor required.");
		}
		
		for (int i = 0; i < length; i ++) {
			requireNonNull(factors[i], "Factor at index " + i + " is null.");
		}
		
		if (factors[length - 1] == Complex.ZERO && length != 1) {
			factors = removeLeadingZeroFactors(factors);
		}
		
		this.factors = factors;
		setOrderShort(); // Maybe a bit overkill, I know. :)
	}
	
	/**
	 * Returns the order of this polynomial (index of the last factor minus 1).<br>
	 * Actually, the minimum of the order and {@link Short#MAX_VALUE}
	 * is returned.
	 * 
	 * @return The polynomial's order.
	 */
	public short order() {
		return order;
	}
	
	/**
	 * Returns a reference to a new {@code ComplexPolynomial} calculated as
	 * a product of this and the given polynomial.
	 * 
	 * @param p The given polynomial.
	 * @return Reference to the product polynomial.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int order1 = this.order();
		int order2 = p.order();
		
		if (order1 == 0 || order2 == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		
		Complex[] resultFactors = new Complex[order1 + order2 + 1];
		
		for (int i = 0; i < resultFactors.length; i ++) {
			resultFactors[i] = Complex.ZERO;
		}
		
		for (int i = 0; i < this.factors.length; i ++) {
			Complex factor1 = this.factors[i];
			
			for (int j = 0; j < p.factors.length; j ++) {
				Complex factor2 = p.factors[j];
				
				int index = i+j;
				resultFactors[index] = resultFactors[index].add(factor1.multiply(factor2));
			}
		}
		
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Returns a reference to a new {@code ComplexPolynomial} calculated as
	 * a derivation of this polynomial.
	 * 
	 * @return Reference to the derived polynomial.
	 */
	public ComplexPolynomial derive() {
		if (factors.length <= 1) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		
		Complex[] resultFactors = new Complex[factors.length - 1];
		
		for (int i = 0; i < resultFactors.length; i ++) {
			resultFactors[i] = factors[i+1];
		}
		
		for (int i = 0; i < factors.length - 1; i ++) {
			resultFactors[i] = resultFactors[i].multiply(new Complex(i+1, 0));
		}
		
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Returns a reference to a {@code Complex} calculated by
	 * applying the given complex number to the polynomial.
	 * 
	 * @param z The given complex number.
	 * @return Reference to the result complex number.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for (int i = 0; i < factors.length; i ++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		if (factors.length == 0) {
			return ZERO;
		} else if (factors.length == 1) {
			return factors[0].toString();
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = factors.length - 1; i >= 0; i --) {
			builder.append(OPENED).append(factors[i]).append(CLOSED);
			if (i != 0) {
				builder.append(Z);
				
				if (i != 1) {
					builder.append(POWER).append(i);
				}
				
				builder.append(PLUS);
			}
		}
		
		builder.setLength(builder.length() - 1);
		
		return builder.toString();
	}
	
//	public static void main(String[] args) {
//		ComplexPolynomial p1 = new ComplexPolynomial(
//			new Complex(1, 2), new Complex(-1, 0), new Complex(1, 0)
//		);
//		ComplexPolynomial p2 = new ComplexPolynomial(
//			new Complex(0, -2), new Complex(0, -1), new Complex(0, 1)
//		);
//		
//		System.out.println(p1);
//		System.out.println(p2);
//		
//		ComplexPolynomial p3 = p1.multiply(p2);
//		System.out.println(p3);
//		
//		ComplexPolynomial p4 = new ComplexPolynomial(
//			new Complex(1, 0)
//		);
//		System.out.println(p4);
//		System.out.println(p4.derive());
//	}
	
	/* HELPER METHODS */
	
	/**
	 * Performs a {@code short} check for the polynomial order.<br>
	 * If the number is too big, {@link Short#MAX_VALUE} is used instead.
	 */
	private void setOrderShort() {
		order = Short.MAX_VALUE;
		
		int orderInt = factors.length - 1;
		if (orderInt < Short.MAX_VALUE) {
			order = (short) orderInt;
		}
	}
	
	/**
	 * Removes leading zero factors from the given array of factors.
	 * 
	 * @param factors The given array of factors.
	 * @return A new array filled without leading zero factors.
	 */
	private Complex[] removeLeadingZeroFactors(Complex ...factors) {
		Complex[] trimmed = null;
		
		boolean nonZeroFound = false;
		for (int i = factors.length - 2; i >= 0; i --) {
			if (nonZeroFound) {
				trimmed[i] = factors[i];
			} else if (factors[i] != Complex.ZERO) {
				trimmed = new Complex[i+1];
				trimmed[i] = factors[i];
				nonZeroFound = true;
			}
		}
		
		if (!nonZeroFound) {
			trimmed = new Complex[1];
			trimmed[0] = Complex.ZERO;
		}
		
		factors = trimmed;
		
		return factors;
	}
	
	/* STRING CONSTANTS */
	private static final String OPENED = "(";
	private static final String CLOSED = ")*";
	private static final String Z = "z";
	private static final String POWER = "^";
	private static final String PLUS = "+";
	private static final String ZERO = "0";
}
