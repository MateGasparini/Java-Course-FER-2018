package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Interface representing a file renaming builder.<br>
 * Used in the {@code MassrenameShellCommand}.
 * 
 * @author Mate Gasparini
 */
public interface NameBuilder {
	
	/**
	 * When called, updates the given builder info
	 * with some new information/content.
	 * 
	 * @param info The given builder info.
	 */
	void execute(NameBuilderInfo info);
}
