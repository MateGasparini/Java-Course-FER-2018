package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * {@link SingleDocumentModel} implementation which represents a single document.<br>
 * It contains the file path, the text component, the modified flag,
 * as well as an internal list of {@link SingleDocumentListener} references.
 * 
 * @author Mate Gasparini
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	/** Document's file path. */
	private Path filePath;
	
	/** Document's text component. */
	private JTextArea textComponent;
	
	/** Flag which is set to true when the document is modified. */
	private boolean modified;
	
	/** Internal list of listeners. */
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Default constructor.<br>
	 * The file path is set to {@code null}.
	 */
	public DefaultSingleDocumentModel() {
		this(null, null);
	}
	
	/**
	 * Constructor specifying the file path and the text content.
	 * 
	 * @param filePath The specified file path.
	 * @param textContent The specified text content.
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		textComponent = new JTextArea(textContent);
		
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateModifiedFlag();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateModifiedFlag();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateModifiedFlag();
			}
			
			private void updateModifiedFlag() {
				if (!modified) {
					modified = true;
					listeners.forEach(
						l -> l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this)
					);
				}
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}
	
	@Override
	public Path getFilePath() {
		return filePath;
	}
	
	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "Path must not be null.");
		if (!path.equals(this.filePath)) {
			this.filePath = path;
			listeners.forEach(l -> l.documentFilePathUpdated(this));
		}
	}
	
	@Override
	public boolean isModified() {
		return modified;
	}
	
	@Override
	public void setModified(boolean modified) {
		if (this.modified != modified) {
			this.modified = modified;
			listeners.forEach(l -> l.documentModifyStatusUpdated(this));
		}
	}
	
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	@Override
	public String toString() {
		return filePath == null ? "Untitled" : filePath.getFileName().toString();
	}
}
