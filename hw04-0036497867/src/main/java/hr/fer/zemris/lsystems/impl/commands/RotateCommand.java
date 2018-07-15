package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command which, on execution, changes the currently active
 * state's direction by the specified angle.
 * 
 * @author Mate Gasparini
 */
public class RotateCommand implements Command {
	
	/**
	 * The specified angle.
	 */
	private double angle;
	
	/**
	 * Constructor specifying the angle of direction change.
	 * 
	 * @param angle The specified angle (in degrees).
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Rotates the current turtle's direction vector by the specified angle.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}
}
