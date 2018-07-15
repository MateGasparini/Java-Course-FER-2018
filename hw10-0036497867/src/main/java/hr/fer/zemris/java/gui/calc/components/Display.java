package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.CalcValueListener;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * A {@link JLabel} which represents a calculator display.<br>
 * It is also a {@link CalcValueListener}, which means each time
 * the corresponding {@link CalcModel}'s value changes, it will update
 * its text accordingly.
 * 
 * @author Mate Gasparini
 */
public class Display extends JLabel implements CalcValueListener {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Text displayed when the calculator first starts.
	 */
	private static final String STARTING_TEXT = "0";
	
	/**
	 * Class constructor which sets the display's appearance.
	 */
	public Display() {
		super(STARTING_TEXT);
		
		setBorder(Calculator.BORDER);
		setBackground(Calculator.YELLOW);
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.TRAILING);
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
		repaint();
	}
}
