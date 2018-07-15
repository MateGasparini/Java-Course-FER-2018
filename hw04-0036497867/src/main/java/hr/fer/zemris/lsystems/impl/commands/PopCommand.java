package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command which, on execution, pops the currently active
 * state from the stack.
 * 
 * @author Mate Gasparini
 */
public class PopCommand implements Command{
	
	/**
	 * Pops the currently active state from the stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
