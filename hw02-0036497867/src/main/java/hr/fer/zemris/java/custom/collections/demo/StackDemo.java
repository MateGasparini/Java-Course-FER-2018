package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that demonstrates the functionality of the
 * <code>ObjectStack</code> class by using it to evaluate
 * a postfix notation mathematical expression which is entered
 * as a single command line argument inside quotes.
 * 
 * @author Mate Gasparini
 */
public class StackDemo {
	
	/**
	 * Method which is called when the program starts
	 * 
	 * @param args Command line arguments. It should have a length of 1,
	 * 			and should contain a postfix expression in quotes.
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				System.out.println(
					"Expression evaluates to "
					+ evaluateExpression(args[0].trim().split("\\s+")) + "."
				);
			} catch (IllegalArgumentException ex) {
				System.err.println(ex.getMessage());
				System.exit(1);
			}
		} else {
			System.err.println(
					"Please enter the expression as one argument.\n"
					+ "For example: \"-1 8 2 / +\""
			);
			System.exit(1);
		}
	}
	
	/**
	 * Method that takes an array of strings and, if everything is correct,
	 * returns the evaluated result of the expression.
	 * 
	 * @param elements Array of strings which are either integers,
	 * 			or basic mathematical operators.
	 * @return Result of the expression represented by the parameter.
	 * @throws IllegalArgumentException If the parameter
	 * 			is an invalid expression.
	 */
	private static int evaluateExpression(String[] elements) {
		ObjectStack stack = new ObjectStack();
		
		for (String element : elements) {
			/* If the string is numeric, it will contain
			 * one or none '-' signs and one or more digits. */
			if (element.matches("-?\\d+")) {
				int number = Integer.parseInt(element);
				stack.push(number);
			} else {
				try {
					int secondOperand = Integer.parseInt(stack.pop().toString());
					int firstOperand = Integer.parseInt(stack.pop().toString());
					
					switch (element) {
					case "+":
						stack.push(firstOperand + secondOperand);
						break;
					case "-":
						stack.push(firstOperand - secondOperand);
						break;
					case "*":
						stack.push(firstOperand * secondOperand);
						break;
					case "/":
						stack.push(firstOperand / secondOperand);
						break;
					case "%":
						stack.push(firstOperand % secondOperand);
						break;
					default:
						throw new IllegalArgumentException(
							"Invalid operator '" + element + "'."
						);
					}
				} catch (ArithmeticException e) {
					throw new IllegalArgumentException(
						"Cannot divide by 0."
					);
				} catch (EmptyStackException e) {
					throw new IllegalArgumentException(
						"Invalid postfix expression. "
						+ "Cannot perform '" + element + "'."
					);
				}
			}
		}
		
		if (stack.size() == 1) {
			return Integer.parseInt(stack.pop().toString());
		}
		
		throw new IllegalArgumentException(
			"Invalid postfix expression. "
			+ "One or more operators missing."
		);
	}
}
