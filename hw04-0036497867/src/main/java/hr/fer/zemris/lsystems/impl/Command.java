package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface which represents some command.
 * All commands must implement the <code>execute()</code> method,
 * in which some action is defined.
 * 
 * @author Mate Gasparini
 */
public interface Command {
	
	/**
	 * Executes the action specified by the specific implementation
	 * for some command.
	 * 
	 * @param ctx Context of the current LSystem.
	 * @param painter Painter that can draw a line on the screen.
	 */
	void execute(Context ctx, Painter painter);
}
