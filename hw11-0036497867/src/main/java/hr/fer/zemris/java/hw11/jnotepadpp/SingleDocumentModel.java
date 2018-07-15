package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface which represents a model of a single document.<br>
 * It contains information about file path from which the document
 * was loaded, document modification status and a reference
 * to the corresponding Swing component used for editing.
 * 
 * @author Mate Gasparini
 */
public interface SingleDocumentModel {
	
	/**
	 * Returns the corresponding editor component.
	 * 
	 * @return The corresponding editor component.
	 */
	JTextArea getTextComponent();
	
	/**
	 * Returns the document's file path,
	 * or null if it is a new document.
	 * 
	 * @return The document's file path.
	 */
	Path getFilePath();
	
	/**
	 * Sets the document's file path to the given path.
	 * 
	 * @param path The given path.
	 */
	void setFilePath(Path path);
	
	/**
	 * Returns true if the document has been modified.
	 * 
	 * @return {@code true} if the document is modified,
	 * 			and {@code false} if it is not.
	 */
	boolean isModified();
	
	/**
	 * Sets the modified flag to the value of the given flag.
	 * 
	 * @param modified The given flag.
	 */
	void setModified(boolean modified);
	
	/**
	 * Registers the given listener to the document.
	 * 
	 * @param l The given listener.
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * De-registers the given listener from the document.
	 * 
	 * @param l The given listener.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
