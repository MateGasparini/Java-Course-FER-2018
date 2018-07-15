package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A program that generates a curve from a text file specifying
 * the parameters and draws it on the screen.
 * 
 * @author Mate Gasparini
 */
public class Glavni3 {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
