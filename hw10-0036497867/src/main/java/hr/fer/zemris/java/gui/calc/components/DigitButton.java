package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * A {@link JButton} used for digit input in a calculator.
 * 
 * @author Mate Gasparini
 */
public class DigitButton extends JButton {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor specifying the corresponding digit
	 * and the calculator's {@link CalcModel}.
	 * 
	 * @param digit The specified digit.
	 * @param model The calculator's model.
	 */
	public DigitButton(int digit, CalcModel model) {
		super(String.valueOf(digit));
		
		setBackground(Calculator.BLUE);
		setBorder(Calculator.BORDER);
		addActionListener(e -> model.insertDigit(digit));
	}
}
