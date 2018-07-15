package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Interface of some component which has some currently selected color
 * which can be changed. When a change occurs, all {@link ColorChangeListener}
 * objects are notified.
 * 
 * @author Mate Gasparini
 */
public interface IColorProvider {
	
	/**
	 * Returns the currently selected color.
	 * 
	 * @return The currently selected color.
	 */
	Color getCurrentColor();
	
	/**
	 * Registers the given {@link ColorChangeListener} to the color provider.
	 * 
	 * @param l The given {@link ColorChangeListener}.
	 */
	void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * De-registers the given {@link ColorChangeListener} from the color provider.
	 * 
	 * @param l The given {@link ColorChangeListener}.
	 */
	void removeColorChangeListener(ColorChangeListener l);
}
