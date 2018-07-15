package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.adapters.CanvasMouseListener;
import hr.fer.zemris.java.hw16.jvdraw.adapters.ListKeyListener;
import hr.fer.zemris.java.hw16.jvdraw.adapters.ListMouseListener;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.StatusBar;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * A simple GUI application used for creating and exporting vector graphics.<br>
 * Currently supports drawing of {@link Line}s, {@link Circle}s and {@link FilledCircle}s.
 * 
 * @author Mate Gasparini
 */
public class JVDraw extends JFrame {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Default foreground color. */
	private static final Color DEFAULT_FOREGROUND = Color.BLACK;
	
	/** Default background color. */
	private static final Color DEFAULT_BACKGROUND = Color.WHITE;
	
	/** The menu bar. */
	private JMenuBar menuBar = new JMenuBar();
	
	/** The tool bar. */
	private JToolBar toolBar = new JToolBar("Tool Bar");
	
	/** The drawing canvas. */
	private JDrawingCanvas canvas;
	
	/** The status bar. */
	private StatusBar statusBar;
	
	/** The currently selected {@link Tool}. */
	private Tool currentTool;
	
	/** The drawing model. */
	private DrawingModel documentModel;
	
	/** The list model for the {@link JList}. */
	private DrawingObjectListModel listModel;
	
	/** The {@link JList}. */
	private JList<GeometricalObject> list;
	
	/** The current save path. Set to {@code null} by default */
	private Path savePath;
	
	/**
	 * Default constructor which initializes the GUI.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		setSize(800, 600);
		setLocation(200, 200);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * Returns the currently selected {@link Tool}.
	 * 
	 * @return The currently selected {@link Tool}.
	 */
	public Tool getCurrentTool() {
		return currentTool;
	}
	
	/**
	 * Returns the drawing model.
	 * 
	 * @return The drawing model.
	 */
	public DrawingModel getDocumentModel() {
		return documentModel;
	}
	
	/**
	 * Returns the current save path. If not set, {@code null} is returned.
	 * 
	 * @return The current save path.
	 */
	public Path getSavePath() {
		return savePath;
	}
	
	/**
	 * Sets the current save path to the given path.
	 * 
	 * @param savePath The given path.
	 */
	public void setSavePath(Path savePath) {
		this.savePath = savePath;
	}
	
	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		initCanvas();
		cp.add(canvas, BorderLayout.CENTER);
		
		initToolBarAndStatusBar();
		cp.add(toolBar, BorderLayout.NORTH);
		cp.add(statusBar, BorderLayout.SOUTH);
		
		initMenuBar();
		setJMenuBar(menuBar);
		
		initList();
		cp.add(new JScrollPane(list), BorderLayout.EAST);
	}
	
	/**
	 * Initializes the menu bar.
	 */
	private void initMenuBar() {
		JMenu file = new JMenu("File");
		
		file.add(new JMenuItem(new OpenAction(this)));
		file.addSeparator();
		file.add(new JMenuItem(new SaveAction(this)));
		file.add(new JMenuItem(new SaveAsAction(this)));
		file.addSeparator();
		file.add(new JMenuItem(new ExportAction(this)));
		file.addSeparator();
		file.add(new JMenuItem(new ExitAction(this, documentModel)));
		
		menuBar.add(file);
	}
	
	/**
	 * Initializes the canvas.
	 */
	private void initCanvas() {
		documentModel = new DrawingModelImpl();
		listModel = new DrawingObjectListModel(documentModel);
		canvas = new JDrawingCanvas(documentModel);
		MouseAdapter mouseAdapter = new CanvasMouseListener(this);
		canvas.addMouseListener(mouseAdapter);
		canvas.addMouseMotionListener(mouseAdapter);
	}
	
	/**
	 * Initializes the tool bar and the status bar.
	 */
	private void initToolBarAndStatusBar() {
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JColorArea fgColorArea = new JColorArea(DEFAULT_FOREGROUND);
		toolBar.add(fgColorArea);
		JColorArea bgColorArea = new JColorArea(DEFAULT_BACKGROUND);
		toolBar.add(bgColorArea);
		statusBar = new StatusBar(fgColorArea, bgColorArea);
		
		Tool lTool = new LineTool(listModel, fgColorArea, canvas);
		Tool cTool = new CircleTool(listModel, fgColorArea, canvas);
		Tool fcTool = new FilledCircleTool(listModel, fgColorArea, bgColorArea, canvas);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		setUpButton("Line", lTool, buttonGroup);
		setUpButton("Circle", cTool, buttonGroup);
		setUpButton("Filled circle", fcTool, buttonGroup);
	}
	
	/**
	 * Creates a {@link JToggleButton} with the given title, sets it up
	 * for changing the currently selected tool to the given tool and adds
	 * it to the given button group.
	 * 
	 * @param title The given title.
	 * @param tool The given tool.
	 * @param buttonGroup The given button group.
	 */
	private void setUpButton(String title, Tool tool, ButtonGroup buttonGroup) {
		JToggleButton button = new JToggleButton(title);
		button.addActionListener(e -> {
			currentTool = tool;
			canvas.setCurrentTool(tool);
		});
		toolBar.add(button);
		buttonGroup.add(button);
	}
	
	/**
	 * Initializes the list.
	 */
	private void initList() {
		list = new JList<>(listModel);
		list.addMouseListener(new ListMouseListener(this, list));
		list.addKeyListener(new ListKeyListener(list, documentModel));
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JVDraw frame = new JVDraw();
			frame.setVisible(true);
		});
	}
}
