package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * {@link JLabel} which contains information about the currently
 * selected background and foreground colors.
 * 
 * @author Mate Gasparini
 */
public class StatusBar extends JLabel implements ColorChangeListener {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Foreground color provider. */
	private IColorProvider fgColorProvider;
	
	/** Background color provider. */
	private IColorProvider bgColorProvider;
	
	/**
	 * Constructor specifying the foreground and background color providers.
	 * 
	 * @param fgColorProvider The specified foreground color provider.
	 * @param bgColorProvider The specified background color provider.
	 */
	public StatusBar(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
		
		updateText(fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor());
	}
	
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if (source.equals(fgColorProvider)) {
			updateText(newColor, bgColorProvider.getCurrentColor());
		} else if (source.equals(bgColorProvider)) {
			updateText(fgColorProvider.getCurrentColor(), newColor);
		}
	}
	
	/**
	 * Updates the label text with the given foreground and background colors.
	 * 
	 * @param fgColor The given foreground color.
	 * @param bgColor The given background color.
	 */
	private void updateText(Color fgColor, Color bgColor) {
		setText("Foreground color: ("
			+ fgColor.getRed() + ", " + fgColor.getGreen() + ", " + fgColor.getBlue()
			+ "), background color: ("
			+ bgColor.getRed() + ", " + bgColor.getGreen() + ", " + bgColor.getBlue()
			+ ")."
		);
	}
}
