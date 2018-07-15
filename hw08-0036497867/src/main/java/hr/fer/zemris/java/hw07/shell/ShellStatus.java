package hr.fer.zemris.java.hw07.shell;

/**
 * Enumeration containing possible statuses returned after a shell command
 * execution.
 * 
 * @author Mate Gasparini
 */
public enum ShellStatus {
	
	/**
	 * Indicates that the shell execution can be continued.
	 */
	CONTINUE,
	
	/**
	 * Indicates that the shell execution must be stopped.
	 */
	TERMINATE
}
