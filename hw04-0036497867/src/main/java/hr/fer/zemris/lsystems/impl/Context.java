package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class which provides a stack specialized for storing
 * turtle states.
 * 
 * @author Mate Gasparini
 */
public class Context {
	
	/**
	 * The stack in which the states are stored.
	 */
	private ObjectStack stack;
	
	/**
	 * Default constructor.
	 */
	public Context() {
		stack = new ObjectStack();
	}
	
	/**
	 * Returns the currently active state,
	 * but does not pop it from the stack.
	 * 
	 * @return The current turtle state.
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}
	
	/**
	 * Pushes the given state to the stack.
	 * 
	 * @param state The given turtle state.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Pops the top of the stack, ie. erases the currently
	 * active state, so that the next state in the stack
	 * starts being active.
	 */
	public void popState() {
		stack.pop();
	}
}
