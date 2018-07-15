package hr.fer.zemris.java.gui.calc;

/**
 * Listener interface used by a {@link CalcModel}.<br>
 * Each time the calculator value changes, all observers should be notified
 * using the {@link CalcValueListener#valueChanged(CalcModel)} method.
 * 
 * @author Mate Gasparini
 */
public interface CalcValueListener {
	
	/**
	 * Method which is called to notify the observers the calculator value
	 * has changed.
	 * 
	 * @param model Reference to the {@link CalcModel} of the calculator.
	 */
	void valueChanged(CalcModel model);
}