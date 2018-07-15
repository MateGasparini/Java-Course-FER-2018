package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that wraps an {@code Object} read-write property.
 * It provides methods used for basic arithmetic operations
 * upon this value, as well as a numerical comparison method.
 * 
 * @author Mate Gasparini
 */
public class ValueWrapper {
	
	/**
	 * The wrapped value. Can be null.
	 */
	private Object value;
	
	/**
	 * Constructor specifying the value.
	 * 
	 * @param value The specified value.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Returns the wrapped value.
	 * 
	 * @return The wrapped value.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Sets the wrapped value to the given value.
	 * 
	 * @param value The given value.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * If possible, sets the wrapped value to the calculated
	 * sum of the wrapped value and the given value.
	 * 
	 * @param incValue The given value.
	 * @throws IllegalArgumentException If the operation is not possible,
	 * 			i.e. if the wrapped value or the given value
	 * 			cannot be represented as a valid number.
	 */
	public void add(Object incValue) {
		checkValueTypes(incValue);
		
		Number first = convertToValidOperand(value);
		Number second = convertToValidOperand(incValue);
		
		if (first instanceof Integer && second instanceof Integer) {
			value = first.intValue() + second.intValue();
		} else {
			value = first.doubleValue() + second.doubleValue();
		}
	}
	
	/**
	 * If possible, sets the wrapped value to the calculated
	 * difference of the wrapped value and the given value.
	 * 
	 * @param decValue The given value.
	 * @throws IllegalArgumentException If the operation is not possible,
	 * 			i.e. if the wrapped value or the given value
	 * 			cannot be represented as a valid number.
	 */
	public void subtract(Object decValue) {
		checkValueTypes(decValue);
		
		Number first = convertToValidOperand(value);
		Number second = convertToValidOperand(decValue);
		
		if (first instanceof Integer && second instanceof Integer) {
			value = first.intValue() - second.intValue();
		} else {
			value = first.doubleValue() - second.doubleValue();
		}
	}
	
	/**
	 * If possible, sets the wrapped value to the calculated
	 * product of the wrapped value and the given value.
	 * 
	 * @param mulValue The given value.
	 * @throws IllegalArgumentException If the operation is not possible,
	 * 			i.e. if the wrapped value or the given value
	 * 			cannot be represented as a valid number.
	 */
	public void multiply(Object mulValue) {
		checkValueTypes(mulValue);
		
		Number first = convertToValidOperand(value);
		Number second = convertToValidOperand(mulValue);
		
		if (first instanceof Integer && second instanceof Integer) {
			value = first.intValue() * second.intValue();
		} else {
			value = first.doubleValue() * second.doubleValue();
		}
	}
	
	// TODO: can throw when dividing by 0
	/**
	 * If possible, sets the wrapped value to the calculated
	 * quotient of the wrapped value and the given value.
	 * 
	 * @param divValue The given value.
	 * @throws IllegalArgumentException If the operation is not possible,
	 * 			i.e. if the wrapped value or the given value
	 * 			cannot be represented as a valid number.
	 * @throws ArithmeticException If the given value is zero.
	 */
	public void divide(Object divValue) {
		checkValueTypes(divValue);
		
		Number first = convertToValidOperand(value);
		Number second = convertToValidOperand(divValue);
		
		if (first instanceof Integer && second instanceof Integer) {
			value = first.intValue() / second.intValue();
		} else {
			value = first.doubleValue() / second.doubleValue();
		}
	}
	
	/**
	 * If possible, returns the result of the comparison
	 * between the wrapped value and the given value.<p>
	 * If the wrapped value is greater, a positive integer is returned.<br>
	 * If the given value is greater, a negative integer is returned.<br>
	 * If the values are the same, zero is returned.</p>
	 * 
	 * @param withValue The given value.
	 * @return Result of the comparison.
	 * @throws IllegalArgumentException If the comparison is not possible,
	 * 			i.e. if the wrapped value or the given value
	 * 			cannot be represented as a valid number.
	 */
	public int numCompare(Object withValue) {
		checkValueTypes(withValue);
		
		Number first = convertToValidOperand(value);
		Number second = convertToValidOperand(withValue);
		
		if (first instanceof Integer && second instanceof Integer) {
			return ((Integer) first).compareTo((Integer) second);
		} else {
			return ((Double) first.doubleValue()).compareTo(second.doubleValue());
		}
	}
	
	private void checkValueTypes(Object givenValue) {
		checkOneValueType(value);
		checkOneValueType(givenValue);
	}
	
	private void checkOneValueType(Object givenValue) {
		if (givenValue == null
				|| givenValue instanceof Integer
				|| givenValue instanceof Double
				|| givenValue instanceof String) {
			return;
		} else {
			throw new IllegalArgumentException(INVALID_OPERANDS);
		}
	}
	
	private Number convertToValidOperand(Object value) {
		Object validOperand = value;
		
		if (value == null) {
			validOperand = Integer.valueOf(0);
		} else if (value instanceof String) {
			String valueAsString = (String) value;
			
			if (valueAsString.contains(DOT) || valueAsString.contains(LETTER_E)) {
				try {
					validOperand = Double.parseDouble(valueAsString);
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(INVALID_OPERANDS);
				}
			} else {
				try {
					validOperand = Integer.parseInt(valueAsString);
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(INVALID_OPERANDS);
				}
			}
		}
		
		return (Number) validOperand;
	}
	
	/* STRING CONSTANTS */
	
	private static final String INVALID_OPERANDS =
		"Requested operation not possible for specified values.";
	private static final String DOT = ".";
	private static final String LETTER_E = "E";
}
