package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface used as a <i>Listener</i> when a {@link MultipleDocumentModel}
 * changes its current document, adds a new document or removes a document.
 * 
 * @author Mate Gasparini
 */
public interface MultipleDocumentListener {
	
	/**
	 * Called when the current document of a {@link MultipleDocumentModel}
	 * changes from the document with the given previous model
	 * to the document with the given current model.
	 * 
	 * @param previousModel The given previous model.
	 * @param currentModel The given current model.
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel);
	
	/**
	 * Called when a document has been added to a {@link MultipleDocumentModel}.
	 * 
	 * @param model The added document's model.
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Called when a document has been removed from a {@link MultipleDocumentModel}.
	 * 
	 * @param model The removed document's model.
	 */
	void documentRemoved(SingleDocumentModel model);
}
