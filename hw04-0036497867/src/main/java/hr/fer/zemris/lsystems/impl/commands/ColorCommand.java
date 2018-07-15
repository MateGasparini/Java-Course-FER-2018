package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command which, on execution, sets the current state's
 * color to the specified color.
 * 
 * @author Mate Gasparini
 */
public class ColorCommand implements Command {
	
	/**
	 * The specified color.
	 */
	private Color color;
	
	/**
	 * Constructor specifying the color.
	 * 
	 * @param color The specified color.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets the current state's color to the specified color.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}
}
