package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * {@link MultipleDocumentModel} implementation which is also a {@link JTabbedPane}.<br>
 * It contains the list of all single document models,
 * a reference to the currently active document model
 * and an internal list of {@link MultipleDocumentListener} references.
 * 
 * @author Mate Gasparini
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Icon used as an icon of the tab with a modified document. */
	private static final ImageIcon SAVE_RED = IconLoader.getInstance()
												.loadIcon("icons/SaveRed.png");
	
	/** Icon used as an icon of the tab with an unmodified document. */
	private static final ImageIcon SAVE_GREEN = IconLoader.getInstance()
												.loadIcon("icons/SaveGreen.png");
	
	/** Reference to the currently active document model. */
	private SingleDocumentModel currentDocument;
	
	/** List of all currently opened documents. */
	private List<SingleDocumentModel> documents = new ArrayList<>();
	
	/** Internal list of listeners. */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	
	/** Modification listener used for icon and title updating. */
	private SingleDocumentListener modificationListener = new ModificationListener();
	
	/**
	 * Default constructor.
	 */
	public DefaultMultipleDocumentModel() {
		addChangeListener(e -> {
			SingleDocumentModel previous = currentDocument;
			previous.removeSingleDocumentListener(modificationListener);
			
			int index = getSelectedIndex();
			if (index != -1) {
				currentDocument = documents.get(index);
				currentDocument.addSingleDocumentListener(modificationListener);
			} else {
				currentDocument = null;
			}
			listeners.forEach(l -> l.currentDocumentChanged(previous, currentDocument));
		});
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}
	
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel();
		
		if (currentDocument != null) {
			currentDocument.removeSingleDocumentListener(modificationListener);
		}
		documents.add(newDocument);
		currentDocument = newDocument;
		currentDocument.addSingleDocumentListener(modificationListener);
		
		int index = this.getTabCount();
		JTextArea textArea = newDocument.getTextComponent();
		addTab("Untitled", SAVE_GREEN, new JScrollPane(textArea), "Untitled");
		setSelectedIndex(index);
		
		return newDocument;
	}
	
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}
	
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path must not be null.");
		
		for (int i = 0, size = documents.size(); i < size; i ++) {
			SingleDocumentModel document = documents.get(i);
			Path filePath = document.getFilePath();
			if (filePath != null && filePath.equals(path)) {
				setSelectedIndex(i);
				return document;
			}
		}
		
		byte[] octets;
		try {
			octets = Files.readAllBytes(path);
		} catch (IOException | OutOfMemoryError e) {
			return null;
		}
		
		String content = new String(octets, StandardCharsets.UTF_8);
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, content);
		
		if (currentDocument != null) {
			currentDocument.removeSingleDocumentListener(modificationListener);
		}
		documents.add(newDocument);
		currentDocument = newDocument;
		currentDocument.addSingleDocumentListener(modificationListener);
		
		int index = getTabCount();
		JTextArea textArea = newDocument.getTextComponent();
		addTab(
			path.getFileName().toString(),
			SAVE_GREEN,
			new JScrollPane(textArea),
			path.toString()
		);
		setSelectedIndex(index);
		
		return newDocument;
	}
	
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			newPath = showSavePathDialog();
			if (newPath == null) {
				showInformation(LocalizationProvider.getInstance().getString("file_not_saved"));
				return;
			}
		}
		if (Files.exists(newPath)) {
			int option = JOptionPane.showConfirmDialog(
				this,
				String.format(
					LocalizationProvider.getInstance().getString("overwrite"),
					newPath.getFileName()),
				LocalizationProvider.getInstance().getString("overwrite_title"),
				JOptionPane.YES_NO_OPTION
			);
			if (option != JOptionPane.YES_OPTION) {
				return;
			}
		}
		byte[] octets = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, octets);
			model.setModified(false);
			model.setFilePath(newPath);
			showInformation(LocalizationProvider.getInstance().getString("saved_successfully"));
		} catch (IOException ex) {
			showError(LocalizationProvider.getInstance().getString("save_failed"));
		}
	}
	
	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = getSelectedIndex();
		if (index != -1) {
			currentDocument.removeSingleDocumentListener(modificationListener);
			documents.remove(model);
			remove(index);
			
			index = getSelectedIndex();
			if (index != -1) {
				currentDocument = documents.get(index);
				currentDocument.addSingleDocumentListener(modificationListener);
			} else {
				currentDocument = null;
			}
		}
	}
	
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}
	
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}
	
	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	/**
	 * Opens a save dialog and if a file is selected, its path is returned.<br>
	 * Otherwise, {@code null} is returned.
	 * 
	 * @return The chosen file path.
	 */
	private Path showSavePathDialog() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().toPath();
		}
		return null;
	}
	
	/**
	 * Opens an error message dialog with the given specified message.
	 * 
	 * @param message The specified message.
	 */
	private void showError(String message) {
		JOptionPane.showMessageDialog(
			this,
			message,
			LocalizationProvider.getInstance().getString("error"),
			JOptionPane.ERROR_MESSAGE
		);
	}
	
	/**
	 * Opens an information message dialog with the given specified message.
	 * 
	 * @param message The specified message.
	 */
	private void showInformation(String message) {
		JOptionPane.showMessageDialog(
			this,
			message,
			LocalizationProvider.getInstance().getString("info"),
			JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	/**
	 * A {@link SingleDocumentListener} which sets the corresponding tab
	 * icon when a document has been modified and updates the tab title
	 * when a document's path has been changed.
	 * 
	 * @author Mate Gasparini
	 */
	private class ModificationListener implements SingleDocumentListener {
		
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			setIconAt(getSelectedIndex(), model.isModified() ? SAVE_RED : SAVE_GREEN);
		}
		
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
		}
	}
}
