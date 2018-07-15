package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface used as a <i>Listener</i> when a {@link SingleDocumentModel}
 * changes its {@code modified} flag or its file path.
 * 
 * @author Mate Gasparini
 */
public interface SingleDocumentListener {
	
	/**
	 * Called when the given model's {@code modified} flag has changed.
	 * 
	 * @param model The given model.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Called when the given model's file path has changed.
	 * 
	 * @param model The given model.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
