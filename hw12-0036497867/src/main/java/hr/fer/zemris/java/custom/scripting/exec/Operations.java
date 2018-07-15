package hr.fer.zemris.java.custom.scripting.exec;

import java.util.Map;
import java.util.function.BiFunction;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Utility class which provides functionality of calculating simple arithmetic
 * operations and updating the specified {@link ObjectStack} at the same time.<br>
 * Each operation is stored in a {@code Map} with the symbol of the operation
 * (e.g. "+", "-", "*", "/") as its key.
 * 
 * @author Mate Gasparini
 */
public final class Operations {
	
	/** Map containing all currently supported operations. */
	private static final Map<String, BiFunction<ValueWrapper, Object, Object>> MAP =
		Map.ofEntries(
			Map.entry("+", (x,y) -> {
				x.add(y);
				return x.getValue();
			}),
			Map.entry("-", (x,y) -> {
				x.subtract(y);
				return x.getValue();
			}),
			Map.entry("*", (x,y) -> {
				x.multiply(y);
				return x.getValue();
			}),
			Map.entry("/", (x,y) -> {
				x.divide(y);
				return x.getValue();
			})
	);
	
	/**
	 * Private default constructor.
	 */
	private Operations() {
	}
	
	/**
	 * Pops two values from the given stack, calculates the result
	 * of the operation specified by the given operator, and pushes
	 * it on the given stack.
	 * 
	 * @param operator The given operator.
	 * @param stack The given stack.
	 * @throws IllegalArgumentException If the given operator is not valid/supported.
	 */
	public static void calculate(String operator, ObjectStack stack) {
		if (!MAP.containsKey(operator)) {
			// Won't happen in our homework, but provides easier future debugging.
			throw new IllegalArgumentException("Invalid operator: " + operator);
		}
		stack.push(
			MAP.get(operator).apply(
				new ValueWrapper(stack.pop()),
				stack.pop()
			)
		);
	}
}
