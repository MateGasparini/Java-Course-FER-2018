package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJToolBar;

/**
 * Program which opens a simple text file editor called JNotepad++.<br>
 * It contains a {@link JTabbedPane} containing all currently opened
 * documents and provides multiple functions for text editing
 * and loading from/saving to disk.
 * 
 * @author Mate Gasparini
 */
public class JNotepadPP extends JFrame {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Title/name of the application. */
	private static final String TITLE = "JNotepad++";
	
	/** Tabbed pane containing all opened documents. */
	private DefaultMultipleDocumentModel tabbedPane;
	
	/** Caret listener implementation object reference. */
	private CaretListener caretListener = new CaretListenerImpl();
	
	/** List of tool bar's buttons */
	private List<JButton> toolBarButtons = new ArrayList<>(10);
	
	/** Status bar length label. */
	private JLabel length;
	
	/** Status bar position label. */
	private JLabel position;
	
	/** Status bar clock label. */
	private JLabel clock;
	
	/** Flag used to notify the clock thread when closing the application. */
	private volatile boolean stopRequested;
	
	/** Form localization provider linked to this JFrame. */
	private FormLocalizationProvider flp =
			new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
	
	/**
	 * Default frame constructor which initializes all GUI components.
	 */
	public JNotepadPP() {
		setTitle(TITLE);
		setSize(800, 600);
		setLocation(200, 200);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				exitCheckAllTabs();
			}
		});
		flp.addLocalizationListener(() -> {
			clearToolBarText();
			updateStatusBar();
		});
		
		initGUI();
	}
	
	//-----------------//
	// PRIVATE METHODS //
	//-----------------//
	
	/**
	 * Initializes all GUI components (delegates to other private methods).
	 */
	private void initGUI() {
		Container pane = this.getContentPane();
		pane.setLayout(new BorderLayout());
		
		tabbedPane = new DefaultMultipleDocumentModel();
		tabbedPane.addMultipleDocumentListener(new TabbedPaneListener());
		pane.add(tabbedPane, BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolBar();
		createStatusBar();
	}
	
	/**
	 * Initializes all actions.
	 */
	private void createActions() {
		createAction(newDocumentAction, "control N", "icons/NewFile.png");
		createAction(openDocumentAction, "control O", "icons/OpenFile.png");
		createAction(saveDocumentAction, "control S", "icons/SaveBlue.png");
		createAction(saveAsDocumentAction, "control shift S", "icons/SaveAs.png");
		createAction(exitAction, "control Q", "icons/exit.png");
		createAction(cutAction, "control X", "icons/cut.png");
		createAction(copyAction, "control C", "icons/copy.png");
		createAction(pasteAction, "control V", "icons/paste.png");
		createAction(infoAction, "control I", "icons/info.png");
		createAction(closeDocumentAction, "control shift Q", "icons/close.png");
		createAction(languageEnAction, "control E", "icons/en.png");
		createAction(languageHrAction, "control H", "icons/hr.png");
		createAction(languageDeAction, "control D", "icons/de.png");
		createAction(toUppercaseAction, "control U", "icons/upper.png");
		createAction(toLowercaseAction, "control L", "icons/lower.png");
		createAction(invertCaseAction, "control shift I", null);
		createAction(sortAscendingAction, "control shift A", null);
		createAction(sortDescendingAction, "control shift D", null);
		createAction(uniqueAction, "control shift N", null);
		
		setEnabledSelectActions(false);
	}
	
	/**
	 * Initializes all {@link JMenuBar}'s menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu file = new LJMenu("file", flp);
		menuBar.add(file);
		file.add(new JMenuItem(newDocumentAction));
		file.add(new JMenuItem(openDocumentAction));
		file.addSeparator();
		file.add(new JMenuItem(saveDocumentAction));
		file.add(new JMenuItem(saveAsDocumentAction));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));
		
		JMenu edit = new LJMenu("edit", flp);
		menuBar.add(edit);
		edit.add(new JMenuItem(cutAction));
		edit.add(new JMenuItem(copyAction));
		edit.add(new JMenuItem(pasteAction));
		edit.addSeparator();
		JMenu changeCase = new LJMenu("change_case", flp);
		edit.add(changeCase);
		changeCase.add(new JMenuItem(toUppercaseAction));
		changeCase.add(new JMenuItem(toLowercaseAction));
		changeCase.add(new JMenuItem(invertCaseAction));
		JMenu sort = new LJMenu("sort", flp);
		edit.add(sort);
		sort.add(new JMenuItem(sortAscendingAction));
		sort.add(new JMenuItem(sortDescendingAction));
		edit.add(new JMenuItem(uniqueAction));
		
		JMenu document = new LJMenu("document", flp);
		menuBar.add(document);
		document.add(new JMenuItem(infoAction));
		document.add(new JMenuItem(closeDocumentAction));
		
		JMenu languages = new LJMenu("languages", flp);
		menuBar.add(languages);
		languages.add(new JMenuItem(languageEnAction));
		languages.add(new JMenuItem(languageHrAction));
		languages.add(new JMenuItem(languageDeAction));
	}
	
	/**
	 * Initializes all {@link JToolBar}'s buttons.
	 */
	private void createToolBar() {
		JToolBar toolBar = new LJToolBar("toolbar", flp);
		toolBar.setFloatable(true);
		
		addButtonToToolBar(toolBar, newDocumentAction);
		addButtonToToolBar(toolBar, openDocumentAction);
		addButtonToToolBar(toolBar, saveDocumentAction);
		addButtonToToolBar(toolBar, saveAsDocumentAction);
		toolBar.addSeparator();
		addButtonToToolBar(toolBar, cutAction);
		addButtonToToolBar(toolBar, copyAction);
		addButtonToToolBar(toolBar, pasteAction);
		toolBar.addSeparator();
		addButtonToToolBar(toolBar, infoAction);
		addButtonToToolBar(toolBar, closeDocumentAction);
		toolBar.addSeparator();
		addButtonToToolBar(toolBar, exitAction);
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}
	
	/**
	 * Initializes all status bar's labels (including the clock thread).
	 */
	private void createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(0, 3));
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		Border border = new LineBorder(Color.GRAY);
		final String initial = "";
		
		length = new JLabel(initial, SwingConstants.LEFT);
		length.setBorder(border);
		statusBar.add(length);
		position = new JLabel(initial, SwingConstants.LEFT);
		position.setBorder(border);
		statusBar.add(position);
		clock = new JLabel(initial, SwingConstants.RIGHT);
		clock.setBorder(border);
		statusBar.add(clock);
		
		Thread clockThread = new Thread(() -> {
			final SimpleDateFormat formatter =
					new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (Exception ignorable) {}
				if (stopRequested) break;
				SwingUtilities.invokeLater(() -> {
					clock.setText(formatter.format(new Date()));
				});
			}
		});
		clockThread.setDaemon(true);
		clockThread.start();
	}
	
	//---------//
	// ACTIONS //
	//---------//
	
	/** Action used for creating a new document. */
	private Action newDocumentAction = new LocalizableAction("new", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			tabbedPane.createNewDocument();
		}
	};
	
	/** Action used for opening a document from disk. */
	private Action openDocumentAction = new LocalizableAction("open", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path filePath = chooser.getSelectedFile().toPath();
			if (!Files.isReadable(filePath)) {
				showError(
					String.format(flp.getString("does_not_exist"), filePath.getFileName())
				);
				return;
			}
			tabbedPane.loadDocument(filePath);
		}
	};
	
	/** Action used for saving a document to disk. */
	private Action saveDocumentAction = new LocalizableAction("save", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = tabbedPane.getCurrentDocument();
			if (current == null) {
				return;
			}
			
			Path currentPath = current.getFilePath();
			tabbedPane.saveDocument(current, currentPath);
		}
	};
	
	/** Action used for saving a document to disk (at specified path). */
	private Action saveAsDocumentAction = new LocalizableAction("saveAs", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = tabbedPane.getCurrentDocument();
			if (current == null) {
				return;
			}
			
			tabbedPane.saveDocument(current, null);
		}
	};
	
	/** Action used for exiting the application. */
	private Action exitAction = new LocalizableAction("exit", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			exitCheckAllTabs();
		}
	};
	
	/** Action used for cutting selected text. */
	private Action cutAction = new LocalizableAction("cut", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = tabbedPane.getCurrentDocument();
			if (current == null) return;
			current.getTextComponent().cut();
		}
	};
	
	/** Action used for copying selected text. */
	private Action copyAction = new LocalizableAction("copy", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = tabbedPane.getCurrentDocument();
			if (current == null) return;
			current.getTextComponent().copy();
		}
	};
	
	/** Action used for pasting text from the clipboard. */
	private Action pasteAction = new LocalizableAction("paste", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = tabbedPane.getCurrentDocument();
			if (current == null) return;
			current.getTextComponent().paste();
		}
	};
	
	/** Action used for showing statistical information about the current document. */
	private Action infoAction = new LocalizableAction("statistics", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel model = tabbedPane.getCurrentDocument();
			
			if (model == null) {
				showError(flp.getString("no_document_selected"));
				return;
			}
			
			JTextArea area = model.getTextComponent();
			int characterCount = area.getDocument().getLength();
			int nonBlankCount = 0;
			String text = area.getText();
			for (int i = 0, length = text.length(); i < length; i ++) {
				if (!Character.isWhitespace(text.charAt(i))) {
					nonBlankCount ++;
				}
			}
			int lineCount = area.getLineCount();
			String message = String.format(flp.getString("info_message"),
					model, characterCount, nonBlankCount, lineCount);
			
			JOptionPane.showMessageDialog(
				JNotepadPP.this,
				message,
				flp.getString("statistical_info"),
				JOptionPane.INFORMATION_MESSAGE
			);
		}
	};
	
	/** Action used for closing the current document. */
	private Action closeDocumentAction = new LocalizableAction("close", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = tabbedPane.getCurrentDocument();
			if (document == null) return;
			if (exitCheckTab(document)) {
				tabbedPane.closeDocument(document);
			}
		}
	};
	
	/** Action used for changing the language to English. */
	private Action languageEnAction = new LocalizableAction("english", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	/** Action used for changing the language to Croatian. */
	private Action languageHrAction = new LocalizableAction("croatian", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	/** Action used for changing the language to German. */
	private Action languageDeAction = new LocalizableAction("german", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
	
	/** Action used for transforming the selected text to uppercase characters. */
	private Action toUppercaseAction = new LocalizableAction("uppercase", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeSelectedCase(c -> Character.toUpperCase(c));
		}
	};
	
	/** Action used for transforming the selected text to lowercase characters. */
	private Action toLowercaseAction = new LocalizableAction("lowercase", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeSelectedCase(c -> Character.toLowerCase(c));
		}
	};
	
	/** Action used for inverting the selected text's case. */
	private Action invertCaseAction = new LocalizableAction("invert_case", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeSelectedCase(c -> {
				if (Character.isLowerCase(c)) {
					c = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					c = Character.toLowerCase(c);
				}
				return c;
			});
		}
	};
	
	/** Action used for sorting the selected text (ascending). */
	private Action sortAscendingAction = new LocalizableAction("ascending", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sortSelected(true);
		}
	};
	
	/** Action used for sorting the selected text (descending). */
	private Action sortDescendingAction = new LocalizableAction("descending", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sortSelected(false);
		}
	};
	
	/** Action used for removing duplicate lines of text. */
	private Action uniqueAction = new LocalizableAction("unique", flp) {
		
		/** Default serial version ID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			removeDuplicates();
		}
	};
	
	//-----------//
	// LISTENERS //
	//-----------//
	
	/**
	 * {@link MultipleDocumentListener} implementation used for dynamic
	 * title changing, as well as adding/removing caret listeners
	 * when the current document changes.
	 * 
	 * @author Mate Gasparini
	 */
	private class TabbedPaneListener implements MultipleDocumentListener {
		
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel,
				SingleDocumentModel currentModel) {
			String title = TITLE;
			if (previousModel != null) {
				previousModel.getTextComponent().removeCaretListener(caretListener);
				length.setText(null);
				position.setText(null);
			}
			if (currentModel != null) {
				Path currentPath = currentModel.getFilePath();
				if (currentPath != null) {
					title = currentPath + " - " + TITLE;
				}
				currentModel.getTextComponent().addCaretListener(caretListener);
			}
			JNotepadPP.this.setTitle(title);
		}
		
		@Override
		public void documentAdded(SingleDocumentModel model) {
			// Do nothing. Not needed.
		}
		
		@Override
		public void documentRemoved(SingleDocumentModel model) {
			// Do nothing. Not needed.
		}
	}
	
	/**
	 * {@link CaretListener} implementation which updates the status bar
	 * information everytime the caret updates.
	 * 
	 * @author Mate Gasparini
	 */
	private class CaretListenerImpl implements CaretListener {
		
		@Override
		public void caretUpdate(CaretEvent e) {
			updateStatusBar();
		}
	}
	
	//------------------------//
	// PRIVATE HELPER METHODS //
	//------------------------//
	
	/**
	 * Modifies the given action by registering an accelerator key with the
	 * given key event and by registering an icon from the given icon path.
	 * 
	 * @param action The given action.
	 * @param keyEvent The given key event.
	 * @param iconPath The given icon path. If {@code null}, it is skipped.
	 */
	private void createAction(Action action, String keyEvent, String iconPath) {
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyEvent));
		if (iconPath == null) return;
		action.putValue(Action.SMALL_ICON, IconLoader.getInstance().loadIcon(iconPath));
	}
	
	/**
	 * Adds a button constructed with the given action to the given tool bar.
	 * It resets the button's text (because the buttons have icons).
	 * 
	 * @param toolBar The given tool bar.
	 * @param action The given action.
	 */
	private void addButtonToToolBar(JToolBar toolBar, Action action) {
		JButton button = new JButton(action);
		button.setText(null);
		toolBar.add(button);
		toolBarButtons.add(button);
	}
	
	/**
	 * Check all opened tabs for saving changes before disposing the frame.
	 */
	private void exitCheckAllTabs() {
		for (SingleDocumentModel document : tabbedPane) {
			if (!exitCheckTab(document)) {
				return;
			}
		}
		
		stopRequested = true;
		dispose();
	}
	
	/**
	 * Checks the given document for saving changes before closing it.<br>
	 * Returns true if the document can be closed.
	 * 
	 * @param document The given document.
	 * @return {@code true} if it is allowed to close the given document,
	 * 			or {@code false} otherwise.
	 */
	private boolean exitCheckTab(SingleDocumentModel document) {
		if (!document.isModified()) {
			return true;
		}
		
		int option = JOptionPane.showOptionDialog(
			this,
			String.format(flp.getString("save_before_closing"), document),
			flp.getString("save_before_closing_title"),
			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
			null,
			new String[] {
				flp.getString("save"), flp.getString("dont_save"), flp.getString("cancel")
			},
			null
		);
		
		if (option == JOptionPane.YES_OPTION) {
			tabbedPane.saveDocument(document, document.getFilePath());
			return true;
		} else if (option == JOptionPane.NO_OPTION) {
			return true;
		}
		return false;
	}
	
	/**
	 * Resets all tool bar buttons' text.
	 */
	private void clearToolBarText() {
		for (JButton button : toolBarButtons) {
			button.setText(null);
		}
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
			flp.getString("error"),
			JOptionPane.ERROR_MESSAGE
		);
	}
	
	/**
	 * Changes the case of the selected text (by using the given function).
	 * 
	 * @param changeCase The given function. Specifies the rule of changing case.
	 */
	private void changeSelectedCase(Function<Character, Character> changeCase) {
		SingleDocumentModel current = tabbedPane.getCurrentDocument();
		if (current == null) return;
		
		JTextArea area = current.getTextComponent();
		Document doc = area.getDocument();
		int len = Math.abs(
			area.getCaret().getDot()-area.getCaret().getMark()
		);
		int offset = 0;
		if (len != 0) {
			offset = Math.min(
				area.getCaret().getDot(), area.getCaret().getMark()
			);
		} else {
			len = doc.getLength();
		}
		try {
			String text = doc.getText(offset, len);
			text = changeString(text, changeCase);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Changes the given text's case (by using the given function).
	 * 
	 * @param text The given text.
	 * @param changeCase The given function. Specifies the rule of changing case.
	 * @return Returns the modified text.
	 */
	private String changeString(String text, Function<Character, Character> changeCase) {
		char[] characters = text.toCharArray();
		for (int i = 0; i < characters.length; i ++) {
			characters[i] = changeCase.apply(characters[i]);
		}
		return new String(characters);
	}
	
	/**
	 * Modifies the selected text by using the given function.
	 * 
	 * @param resultFunction The given function which transforms some
	 * 			List of Strings to one String.
	 */
	private void modifySelected(Function<List<String>, String> resultFunction) {
		SingleDocumentModel model = tabbedPane.getCurrentDocument();
		if (model == null) return;
		
		JTextArea area = model.getTextComponent();
		int dot = area.getCaret().getDot();
		int mark = area.getCaret().getMark();
		int startPos = Math.min(dot, mark);
		int endPos = Math.max(dot, mark);
		
		if (startPos == endPos) return;
		try {
			int startLine = area.getLineOfOffset(startPos);
			int endLine = area.getLineOfOffset(endPos);
			
			List<String> lines = getLines(area, startLine, endLine);
			Document document = area.getDocument();
			document.remove(
				area.getLineStartOffset(startLine),
				area.getLineEndOffset(endLine) - area.getLineStartOffset(startLine)
			);
			String result = resultFunction.apply(lines);
			document.insertString(
				area.getLineStartOffset(startLine),
				result,
				null
			);
		} catch (BadLocationException ignorable) {}
	}
	
	/**
	 * Sorts the selected text (ascending or descending, depending on the given
	 * flag).
	 * 
	 * @param ascending The given flag. When {@code true}, sorted ascending;
	 * 			when {@code false}, sorted descending.
	 */
	private void sortSelected(boolean ascending) {
		modifySelected(lines -> joinLines(sortLines(lines, ascending)));
	}
	
	/**
	 * Sorts the given List of Strings (ascending or descending, depending on the given
	 * flag).
	 * 
	 * @param lines The given List of Strings.
	 * @param ascending The given flag. When {@code true}, sorted ascending;
	 * 			when {@code false}, sorted descending.
	 * @return The sorted List of Strings.
	 */
	private List<String> sortLines(List<String> lines, boolean ascending) {
		Collator collator = Collator.getInstance(new Locale(
				LocalizationProvider.getInstance().getLanguage())
		);
		lines.sort(ascending ? collator : collator.reversed());
		return lines;
	}
	
	/**
	 * Joins the given List of Strings and returns it as one String.
	 * 
	 * @param lines The given List of Strings.
	 * @return The joined String.
	 */
	private String joinLines(List<String> lines) {
		return String.join("", lines);
	}
	
	/**
	 * Returns a List of Strings representing all given text area's lines
	 * from the given starting line index to the given ending line index.
	 * 
	 * @param area The given text area.
	 * @param startLine The given starting line index.
	 * @param endLine The given ending line index.
	 * @return The String List of all corresponding lines.
	 * @throws BadLocationException Why isn't this a RuntimeException?
	 */
	private List<String> getLines(JTextArea area, int startLine, int endLine)
			throws BadLocationException {
		List<String> lines = new ArrayList<>(endLine - startLine + 1);
		Document document = area.getDocument();
		final String newLine = "\n";
		for (int i = startLine; i <= endLine; i ++) {
			String line = document.getText(
					area.getLineStartOffset(i),
					area.getLineEndOffset(i) - area.getLineStartOffset(i)
			);
			lines.add(line.endsWith(newLine) ? line : line.concat(newLine));
		}
		return lines;
	}
	
	/**
	 * Removes duplicate lines and keeps first unique line occurences.
	 */
	private void removeDuplicates() {
		modifySelected(lines -> joinLines(uniqueLines(lines)));
	}
	
	/**
	 * Returns a List of unique Strings from the given List of Strings.
	 * 
	 * @param lines The given List of Strings.
	 * @return The List with all duplicate Strings removed.
	 */
	private List<String> uniqueLines(List<String> lines) {
		List<String> unique = new ArrayList<>();
		for (String line : lines) {
			if (!unique.contains(line)) {
				unique.add(line);
			}
		}
		return unique;
	}
	
	/**
	 * Sets all text selection-required action's enabled flag
	 * to the value of the given flag.
	 * 
	 * @param enabled The given flag.
	 */
	private void setEnabledSelectActions(boolean enabled) {
		cutAction.setEnabled(enabled);
		copyAction.setEnabled(enabled);
		toUppercaseAction.setEnabled(enabled);
		toLowercaseAction.setEnabled(enabled);
		invertCaseAction.setEnabled(enabled);
		sortAscendingAction.setEnabled(enabled);
		sortDescendingAction.setEnabled(enabled);
		uniqueAction.setEnabled(enabled);
	}
	
	/**
	 * Updates the status bar contents with all needed (and translated)
	 * information.
	 */
	private void updateStatusBar() {
		SingleDocumentModel current = tabbedPane.getCurrentDocument();
		if (current == null) {
			return;
		}
		
		JTextArea area = current.getTextComponent();
		int characterCount = area.getDocument().getLength();
		
		length.setText(flp.getString("status_left").concat(String.valueOf(characterCount)));
		try {
			int caretPosition = area.getCaretPosition();
			int line = area.getLineOfOffset(caretPosition);
			int column = caretPosition - area.getLineStartOffset(line);
			int selected = Math.abs(area.getCaret().getMark() - area.getCaret().getDot());
			position.setText(
				String.format(flp.getString("status_center"), line, column, selected)
			);
			
			setEnabledSelectActions(selected > 0);
		} catch (BadLocationException ignorable) {}
	}
}
