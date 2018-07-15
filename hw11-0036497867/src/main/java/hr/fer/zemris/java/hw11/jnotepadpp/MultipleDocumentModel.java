package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface which represents a model capable of holding multiple documents.<br>
 * It also holds a reference to the document currently being edited
 * and provides the iterator used for iterating through all of its single
 * document models.
 * 
 * @author Mate Gasparini
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Creates a new document and adds it to this model.
	 * 
	 * @return The new document.
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Returns the currently active document.<br>
	 * If none active, {@code null} is returned.
	 * 
	 * @return The document currently being edited.
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads the document specified by the given path and adds it to this model.
	 * 
	 * @param path The given path.
	 * @return The loaded document.
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the contents of the given model to the given path.
	 * 
	 * @param model The given model.
	 * @param newPath The given path.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes the model with the same reference as the given model.
	 * 
	 * @param model The given model.
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Registers the given listener to the document.
	 * 
	 * @param l The given listener.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * De-registers the given listener from the document.
	 * 
	 * @param l The given listener.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Returns the count of currently stored documents.
	 * 
	 * @return Number of single documents stored in this model.
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns the model from the given index.
	 * 
	 * @param index The given index.
	 * @return The corresponding single model reference.
	 */
	SingleDocumentModel getDocument(int index);
}
