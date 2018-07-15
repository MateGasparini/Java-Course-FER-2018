package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Interface that defines the model of a simple calculator
 * which contains some sort of a display and can store
 * an active operand and a pending operation.
 * 
 * @author Mate Gasparini
 */
public interface CalcModel {
	
	/**
	 * Registers the given {@code CalcValueListener} to the model.
	 * 
	 * @param l The given listener.
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * De-registers the given {@code CalcValueListener} from the model.
	 * 
	 * @param l The given listener.
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns the String representation of the stored value.
	 * 
	 * @return The stored value as a String.
	 */
	@Override
	String toString();
	
	/**
	 * Returns the currently stored value.
	 * 
	 * @return The currently stored value.
	 */
	double getValue();
	
	/**
	 * Sets the current value to the given value.
	 * 
	 * @param value The given value.
	 */
	void setValue(double value);
	
	/**
	 * Clears the currently stored value.
	 */
	void clear();
	
	/**
	 * Clears the currently stored value, as well as the currently active
	 * operand and the pending operation.
	 */
	void clearAll();
	
	/**
	 * Swaps the current value's sign.
	 */
	void swapSign();
	
	/**
	 * Appends a decimal point to the current value.<br>
	 * If it already contains one, the value stays unchanged.
	 */
	void insertDecimalPoint();
	
	/**
	 * Appends the given digit to the current value.
	 * 
	 * @param digit The given digit.
	 */
	void insertDigit(int digit);
	
	/**
	 * Returns true if there is an active operand set.
	 * 
	 * @return {@code true} if an operand is active, {@code false} otherwise.
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Returns the currently active operand.
	 * 
	 * @return The currently active operand.
	 */
	double getActiveOperand();
	
	/**
	 * Sets the currently active operand to the given value.
	 * 
	 * @param activeOperand The given value.
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Clears the active operand.<br>
	 * After this method has been called, {@link CalcModel#isActiveOperandSet()}
	 * must return {@code false}.
	 */
	void clearActiveOperand();
	
	/**
	 * Returns the currently pending operation.
	 * 
	 * @return The currently pending operation.
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Sets the currently pending operation to the given operation.
	 * 
	 * @param op The given operation.
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}