package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A program that generates a Koch curve (starting with a line)
 * and draws it on the screen.
 * 
 * @author Mate Gasparini
 */
public class Glavni2 {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
	
	/**
	 * Builds an <code>LSystem</code> from Strings of data and returns it.
	 * 
	 * @param provider Parameter which provides functionality for
	 * 			<code>LSystem</code> building.
	 * @return The specified <code>LSystem</code>.
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
					"origin					0.05 0.4",
					"angle					0",
					"unitLength				0.9",
					"unitLengthDegreeScaler	1.0 / 3.0",
					"",
					"command F draw 1",
					"command + rotate 60",
					"command - rotate -60",
					"",
					"axiom F",
					"",
					"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
