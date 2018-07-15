package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command which, on execution, makes a copy of the currently
 * active state, and pushes it on the stack.
 * 
 * @author Mate Gasparini
 */
public class PushCommand implements Command {
	
	/**
	 * Pushes a copy of the currently active state on the stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}
}
