package hr.fer.zemris.java.gui.calc;

import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Helper class which provides unary and binary (and possibly inverse) operations
 * definitions.
 * 
 * @author Mate Gasparini
 */
public class OperatorProvider {
	
	/**
	 * Map containing all available unary operations.
	 */
	private static final Map<String, DoubleUnaryOperator> UNARY = Map.ofEntries(
		Map.entry("1/x", x -> 1.0/x),
		Map.entry("log", x -> log10(x)),
		Map.entry("ln", x -> log(x)),
		Map.entry("sin", x -> sin(x)),
		Map.entry("cos", x -> cos(x)),
		Map.entry("tan", x -> tan(x)),
		Map.entry("ctg", x -> 1.0/tan(x))
	);
	
	/**
	 * Map containing all available inverse unary operations.
	 */
	private static final Map<String, DoubleUnaryOperator> UNARY_INVERSED = Map.ofEntries(
		Map.entry("1/x", x -> 1.0/x),
		Map.entry("log", x -> pow(10, x)),
		Map.entry("ln", x -> exp(x)),
		Map.entry("sin", x -> asin(x)),
		Map.entry("cos", x -> acos(x)),
		Map.entry("tan", x -> atan(x)),
		Map.entry("ctg", x -> atan(1.0/x))
	);
	
	/**
	 * Map containing all available binary operations.
	 */
	private static final Map<String, DoubleBinaryOperator> BINARY = Map.ofEntries(
		Map.entry("/", (x,y) -> x/y),
		Map.entry("*", (x,y) -> x*y),
		Map.entry("-", (x,y) -> x-y),
		Map.entry("+", (x,y) -> x+y),
		Map.entry("x^n", (x,n) -> pow(x, n))
	);
	
	/**
	 * Map containing all available inverse binary operations.
	 */
	private static final Map<String, DoubleBinaryOperator> BINARY_INVERSED = Map.ofEntries(
		Map.entry("x^n", (n,x) -> pow(x, 1.0/n))
		// Could also add (if following lines are uncommented):
//		,
//		Map.entry("/", (x,y) -> y/x),
//		Map.entry("-", (x,y) -> y-x)
	);
	
	/**
	 * Private constructor which guarantees this class won't be instantiated.
	 */
	private OperatorProvider() {
	}
	
	/**
	 * Returns the (possibly inverse) unary operation specified by the given
	 * name.
	 * 
	 * @param name The given mathematical function name.
	 * @param inverse If true, the inverse operation will be returned.
	 * @return The corresponding unary operation.
	 */
	public static DoubleUnaryOperator getUnary(String name, boolean inverse) {
		return inverse ? UNARY_INVERSED.get(name) : UNARY.get(name);
	}
	
	/**
	 * Returns the (possibly inverse) binary operation specified by the given
	 * name.
	 * 
	 * @param name The given mathematical function name.
	 * @param inverse If true, the inverse operation will be returned.
	 * @return The corresponding binary operation.
	 */
	public static DoubleBinaryOperator getBinary(String name, boolean inverse) {
		return inverse ? BINARY_INVERSED.get(name) : BINARY.get(name);
	}
}
