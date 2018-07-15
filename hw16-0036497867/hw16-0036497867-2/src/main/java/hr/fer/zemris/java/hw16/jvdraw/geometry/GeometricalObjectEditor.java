package hr.fer.zemris.java.hw16.jvdraw.geometry;

import javax.swing.JPanel;

/**
 * Abstract class and {@link JPanel} representing some {@link GeometricalObject}'s
 * editor (used for object modification).
 * 
 * @author Mate Gasparini
 */
public abstract class GeometricalObjectEditor extends JPanel {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Checks if the given {@link GeometricalObject} modification parameters are valid.
	 */
	public abstract void checkEditing();
	
	/**
	 * Updates the corresponding {@link GeometricalObject} with the specified modifications.
	 */
	public abstract void acceptEditing();
}
