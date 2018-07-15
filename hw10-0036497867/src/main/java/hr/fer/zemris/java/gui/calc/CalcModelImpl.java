package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * {@code CalcModel} implementation which represents a simple calculator
 * that stores the displayed value (as a {@code String}), the activeOperand
 * (as a {@code double}) and the pending operation (as a
 * {@code DoubleBinaryOperator}).<br>
 * It also contains a {@code List} of {@code CalcValueListener} references
 * used for notifying the listeners each time the value changes.
 * 
 * @author Mate Gasparini
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * The currently displayed value.
	 */
	private String displayed;
	/**
	 * The currently active operand value.
	 */
	private double activeOperand;
	/**
	 * True if the active operand is set.
	 */
	private boolean activeOperandSet;
	/**
	 * The currently pending operation.
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * The internal {@code List} of listeners.
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	@Override
	public String toString() {
		if (displayed == null) {
			return ZERO;
		}
		
		return displayed;
	}
	
	@Override
	public double getValue() {
		if (displayed == null) {
			return 0.0;
		}
		
		return Double.parseDouble(displayed);
	}
	
	@Override
	public void setValue(double value) {
		if (Double.isFinite(value)) {
			displayed = String.valueOf(value);
			notifyObservers();
		}
	}
	
	@Override
	public void clear() {
		displayed = null;
		notifyObservers();
	}
	
	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingOperation = null;
		notifyObservers();
	}
	
	@Override
	public void swapSign() {
		if (displayed != null) {
			if (displayed.startsWith(MINUS)) {
				displayed = displayed.substring(1);
			} else {
				displayed = MINUS.concat(displayed);
			}
			
			notifyObservers();
		}
	}
	
	@Override
	public void insertDecimalPoint() {
		if (displayed == null) {
			displayed = ZERO.concat(DOT);
			notifyObservers();
		} else if (!displayed.contains(DOT)) {
			displayed = displayed.concat(DOT);
			notifyObservers();
		}
	}
	
	@Override
	public void insertDigit(int digit) {
		if (displayed == null) {
			displayed = String.valueOf(digit);
			notifyObservers();
		} else {
			if (displayed.equals(ZERO)) {
				displayed = String.valueOf(digit);
				notifyObservers();
			} else {
				String newDisplayed = displayed.concat(String.valueOf(digit));
				if (Double.parseDouble(newDisplayed) <= Double.MAX_VALUE) {
					displayed = newDisplayed;
					notifyObservers();
				}
			}
		}
	}
	
	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}
	
	@Override
	public double getActiveOperand() {
		if (activeOperandSet) {
			return activeOperand;
		} else {
			throw new IllegalStateException("Active operand not set.");
		}
	}
	
	@Override
	public void setActiveOperand(double activeOperand) {
		activeOperandSet = true;
		this.activeOperand = activeOperand;
	}
	
	@Override
	public void clearActiveOperand() {
		activeOperandSet = false;
	}
	
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}
	
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	private void notifyObservers() {
		listeners.forEach(l -> l.valueChanged(this));
	}
	
	/* STRING CONSTANTS */
	/* Declared as constants to reduce heap allocations. */
	private static final String ZERO = "0";
	private static final String MINUS = "-";
	private static final String DOT = ".";
}
