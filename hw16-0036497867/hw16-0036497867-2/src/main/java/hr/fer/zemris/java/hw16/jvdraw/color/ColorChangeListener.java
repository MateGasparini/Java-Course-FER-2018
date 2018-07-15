package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Listener interface which is used for listening for color changes.
 * 
 * @author Mate Gasparini
 */
public interface ColorChangeListener {
	
	/**
	 * Called when the color changes from the given old color to the given new color.
	 * 
	 * @param source The source of the color change.
	 * @param oldColor The given old color.
	 * @param newColor The given new color.
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
