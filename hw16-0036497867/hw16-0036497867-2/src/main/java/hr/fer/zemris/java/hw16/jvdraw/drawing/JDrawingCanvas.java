package hr.fer.zemris.java.hw16.jvdraw.drawing;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * A {@link JComponent} used for visualization of {@link GeometricalObject}
 * from the specified {@link DrawingModel}.
 * 
 * @author Mate Gasparini
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** The specified {@link DrawingModel}. */
	private DrawingModel model;
	
	/** The currently selected drawing tool. */
	private Tool currentTool;
	
	/**
	 * Constructor specifying the {@link DrawingModel}.
	 * 
	 * @param model The specified {@link DrawingModel}.
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	
	/**
	 * Sets the currently selected {@link Tool} to the given tool.
	 * 
	 * @param tool The given tool.
	 */
	public void setCurrentTool(Tool tool) {
		this.currentTool = tool;
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);
		for (int i = 0, size = model.getSize(); i < size; i ++) {
			model.getObject(i).accept(painter);
		}
		if (currentTool == null) return;
		currentTool.paint(g2d);
	}
}
