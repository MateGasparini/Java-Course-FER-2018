package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.gui.calc.components.DigitButton;
import hr.fer.zemris.java.gui.calc.components.Display;
import hr.fer.zemris.java.gui.calc.components.OperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Program which when started opens a simple calculator.<br>
 * It uses the {@link CalcLayout} for arranging the buttons
 * and the display (display being the (1,1) cell).<br>
 * It contains various unary and binary operation buttons,
 * as well as buttons used for inserting the 0-9 digits.
 * 
 * @author Mate Gasparini
 */
public class Calculator extends JFrame {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Length of the gap used for the {@link CalcLayout} constructor.
	 */
	private static final int GAP = 10;
	/**
	 * Line border used for all buttons and the display.
	 */
	public static final Border BORDER = new LineBorder(new Color(90, 130, 170));
	/**
	 * Blue color used for buttons.
	 */
	public static final Color BLUE = new Color(125, 195, 225);
	/**
	 * Yellow color used for display.
	 */
	public static final Color YELLOW = new Color(255, 240, 25);
	
	/**
	 * A {@link CalcModel} corresponding to this calculator.
	 */
	private CalcModel model = new CalcModelImpl();
	/**
	 * Content pane of the frame.
	 */
	private Container pane = getContentPane();
	/**
	 * Stack used for push/pop operations.
	 */
	private Stack<Double> stack = new Stack<>();
	/**
	 * Check box used for inverting various operations.
	 */
	private JCheckBox inverse = new JCheckBox("Inv");
	
	/**
	 * Default frame constructor.
	 */
	public Calculator() {
		setLocation(200, 200);
		setSize(600, 400);
		setTitle("Calculator");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
	
	/**
	 * Initializes all components and arranges them using the {@link CalcLayout}.
	 */
	private void initGUI() {
		pane.setLayout(new CalcLayout(GAP));
		
		Display display = new Display();
		model.addCalcValueListener(display);
		pane.add(display, new RCPosition(1, 1));
		
		inverse.setBorder(BORDER);
		inverse.setBackground(BLUE);
		inverse.setBorderPainted(true);
		pane.add(inverse, new RCPosition(5, 7));
		
		addDigitButtons();
		addUnaryButtons();
		addBinaryButtons();
		addOtherButtons();
	}
	
	/**
	 * Adds all buttons used for digit input.
	 */
	private void addDigitButtons() {
		pane.add(new DigitButton(0, model), new RCPosition(5, 3));
		for (int i = 1; i <= 9; i ++) {
			pane.add(new DigitButton(i, model), new RCPosition(4 - (i-1)/3, 3 + (i-1)%3));
		}
	}
	
	/**
	 * Adds all buttons used for unary operations.
	 */
	private void addUnaryButtons() {
		addUnaryButton("1/x", 2, 1);
		addUnaryButton("log", 3, 1);
		addUnaryButton("ln", 4, 1);
		addUnaryButton("sin", 2, 2);
		addUnaryButton("cos", 3, 2);
		addUnaryButton("tan", 4, 2);
		addUnaryButton("ctg", 5, 2);
	}
	
	/**
	 * Adds all buttons used for binary operations.
	 */
	private void addBinaryButtons() {
		addBinaryButton("/", 2, 6);
		addBinaryButton("*", 3, 6);
		addBinaryButton("-", 4, 6);
		addBinaryButton("+", 5, 6);
		addBinaryButton("x^n", 5, 1);
	}
	
	/**
	 * Adds various other buttons used for special operations and functions.
	 */
	private void addOtherButtons() {
		addOtherButton("+/-", 5, 4, e -> model.swapSign());
		addOtherButton(".", 5, 5, e -> model.insertDecimalPoint());
		addOtherButton("=", 1, 6, e -> calculateResult());
		addOtherButton("clr", 1, 7, e -> model.clear());
		addOtherButton("res", 2, 7, e -> model.clearAll());
		addOtherButton("push", 3, 7, e -> stack.push(model.getValue()));
		addOtherButton("pop", 4, 7, e -> popStack());
	}
	
	/**
	 * Constructs the specified unary button and adds it to the content pane.
	 * 
	 * @param name The specified button name.
	 * @param row The index of the row position.
	 * @param column The index of the column position.
	 */
	private void addUnaryButton(String name, int row, int column) {
		OperationButton button = new OperationButton(name);
		
		button.addActionListener(e -> calculateUnary(button));
		if (OperatorProvider.getUnary(name, true) != null) {
			inverse.addActionListener(e -> button.setReversed(inverse.isSelected()));
		}
		
		pane.add(button, new RCPosition(row, column));
	}
	
	/**
	 * Constructs the specified binary button and adds it to the content pane.
	 * 
	 * @param name The specified button name.
	 * @param row The index of the row position.
	 * @param column The index of the column position.
	 */
	private void addBinaryButton(String name, int row, int column) {
		OperationButton button = new OperationButton(name);
		
		button.addActionListener(e -> calculateBinary(button));
		if (OperatorProvider.getBinary(name, true) != null) {
			inverse.addActionListener(e -> button.setReversed(inverse.isSelected()));
		}
		
		pane.add(button, new RCPosition(row, column));
	}
	
	/**
	 * Constructs the specified JButton and adds it to the content pane.
	 * 
	 * @param name The specified button name.
	 * @param row The index of the row position.
	 * @param column The index of the column position.
	 * @param l The specified listener that should be registered to the button.
	 */
	private void addOtherButton(String name, int row, int column, ActionListener l) {
		JButton button = new JButton(name);
		button.setBorder(BORDER);
		button.setBackground(BLUE);
		button.addActionListener(l);
		pane.add(button, new RCPosition(row, column));
	}
	
	/**
	 * Tries to pop the stack.<br>
	 * If it is empty, an error dialog is shown.
	 */
	private void popStack() {
		if (!stack.isEmpty()) {
			model.setValue(stack.pop());
		} else {
			showError("Stack is empty.");
		}
	}
	
	/**
	 * Tries to calculate the pending operation and store it as the new value.
	 * If it is not a finite value, an error dialog is shown.
	 */
	private void calculateResult() {
		if (model.isActiveOperandSet()) {
			double value = model.getPendingBinaryOperation().applyAsDouble(
				model.getActiveOperand(), model.getValue()
			);
			if (Double.isFinite(value)) {
				model.setValue(value);
				model.clearActiveOperand();
			} else {
				showError("Result of operation undefined.");
			}
		}
	}
	
	/**
	 * Tries to calculate the unary operation and store it as the new value.
	 * If it is not a finite value, an error dialog is shown.
	 */
	private void calculateUnary(OperationButton button) {
		double value = OperatorProvider.getUnary(button.getText(), button.isReversed())
				.applyAsDouble(model.getValue());
		if (Double.isFinite(value)) {
			model.setValue(value);
		} else {
			showError("Result of operation undefined.");
		}
	}
	
	/**
	 * Tries to calculate the binary operation and store it as the new value.
	 * If it is not a finite value, an error dialog is shown.
	 */
	private void calculateBinary(OperationButton button) {
		if (model.isActiveOperandSet()) {
			double value = model.getPendingBinaryOperation().applyAsDouble(
				model.getActiveOperand(), model.getValue()
			);
			if (Double.isFinite(value)) {
				model.setActiveOperand(value);
			} else {
				showError("Result of operation undefined.");
			}
		} else {
			model.setActiveOperand(model.getValue());
		}
		
		model.setPendingBinaryOperation(
			OperatorProvider.getBinary(button.getText(), button.isReversed())
		);
		model.clear();
	}
	
	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}
}
