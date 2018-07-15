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
public class Glavni1 {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}
	
	/**
	 * Builds an <code>LSystem</code> and returns it.
	 * 
	 * @param provider Parameter which provides functionality for
	 * 			<code>LSystem</code> building.
	 * @return The specified <code>LSystem</code>.
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
					.registerCommand('F', "draw 1")
					.registerCommand('+', "rotate 60")
					.registerCommand('-', "rotate -60")
					.setOrigin(0.05, 0.4)
					.setAngle(0)
					.setUnitLength(0.9)
					.setUnitLengthDegreeScaler(1.0/3.0)
					.registerProduction('F', "F+F--F+F")
					.setAxiom("F")
					.build();
	}
}
