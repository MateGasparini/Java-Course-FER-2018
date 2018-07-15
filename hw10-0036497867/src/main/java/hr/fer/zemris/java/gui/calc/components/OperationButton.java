package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * A {@link JButton} used for unary/binary operations input in a calculator.
 * 
 * @author Mate Gasparini
 */
public class OperationButton extends JButton {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Changed when the inverse CheckBox changes.<br>
	 * If set to true, the button produces inverse operations.
	 */
	private boolean reversed;
	
	/**
	 * Class constructor specifying the text shown on the button
	 * and used for operation retrieval.
	 * 
	 * @param text The specified text.
	 */
	public OperationButton(String text) {
		super(text);
		
		setBackground(Calculator.BLUE);
		setBorder(Calculator.BORDER);
	}
	
	/**
	 * Returns true if operations are currently being reversed.
	 * 
	 * @return {@code true} if operations are reversed, {@code false} otherwise.
	 */
	public boolean isReversed() {
		return reversed;
	}
	
	/**
	 * Sets the reversed value to the given value.
	 * 
	 * @param reversed The given value.
	 */
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}
}
