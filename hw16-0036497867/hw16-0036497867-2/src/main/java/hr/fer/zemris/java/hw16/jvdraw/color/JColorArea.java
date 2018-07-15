package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * {@link JComponent} which contains some currently selected color.<br>
 * When the color changes, all stored {@link ColorChangeListener}s are notified.
 * 
 * @author Mate Gasparini
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** The currently selected color. */
	private Color selectedColor;
	
	/** The internal {@code List} of listeners. */
	private List<ColorChangeListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor specifying the currently selected color.
	 * 
	 * @param selectedColor The currently selected color.
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color chosenColor = JColorChooser.showDialog(
					JColorArea.this,
					"Choose a color...",
					JColorArea.this.selectedColor
				);
				if (chosenColor == null || JColorArea.this.selectedColor.equals(chosenColor)) {
					return;
				}
				fire(chosenColor);
				repaint();
			}
		});
	}
	
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}
	
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Notifies all listeners about the color change and updates
	 * the currently selected with the given color.
	 * 
	 * @param newColor The given color.
	 */
	private void fire(Color newColor) {
		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, selectedColor, newColor);
		}
		selectedColor = newColor;
	}
}
