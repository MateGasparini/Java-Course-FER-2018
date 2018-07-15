package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command which, on execution, updates the turtle's position
 * to the next calculated position (which is specified by the
 * turtle's current direction and the step value).
 * 
 * @author Mate Gasparini
 */
public class SkipCommand implements Command {
	
	/**
	 * Number of unit steps the turtle will take.
	 */
	private double step;
	
	/**
	 * Constructor specifying the step value
	 * 
	 * @param step Number of unit steps.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Updates the turtle's position based on the turtle's direction
	 * and the step value.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		state.getPosition().translate(state.getDirection()
							.scaled(step * state.getMoveLength())
		);
	}
}
