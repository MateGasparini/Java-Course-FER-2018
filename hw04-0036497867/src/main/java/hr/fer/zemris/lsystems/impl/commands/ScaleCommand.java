package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command which, on execution, scales the currently active
 * state's move length by the specified factor.
 * 
 * @author Mate Gasparini
 */
public class ScaleCommand implements Command {
	
	/**
	 * The specified scaling factor.
	 */
	private double factor;
	
	/**
	 * Constructor specifying the scaling factor.
	 * 
	 * @param factor The specified scaling factor.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Scales the currently active state's move length by the specified factor.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setMoveLength(factor * state.getMoveLength());
	}
}
