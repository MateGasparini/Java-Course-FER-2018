package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Command which, on execution, draws a line starting
 * from the current turtle's position to the position
 * specified by the turtle's current direction and
 * the step value, and updates the turtle's position.
 * 
 * @author Mate Gasparini
 */
public class DrawCommand implements Command {
	
	/**
	 * Number of unit steps the turtle will take.
	 */
	private double step;
	
	/**
	 * Constructor specifying the step value
	 * 
	 * @param step Number of unit steps.
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Draws a line from the current turtle's position to the position
	 * specified by the turtle's direction and the step value, and
	 * updates the turtle's position.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		Vector2D start = state.getPosition();
		Vector2D end = start.translated(state.getDirection()
								.scaled(step * state.getMoveLength())
		);
		
		painter.drawLine(
			start.getX(), start.getY(),
			end.getX(), end.getY(),
			state.getColor(), 1.f
		);
		
		state.setPosition(end);
	}
}
