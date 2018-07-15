package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * {@link Tool} used for drawing {@link Line} objects.
 * 
 * @author Mate Gasparini
 */
public class LineTool implements Tool {
	
	/** The specified model. */
	private DrawingObjectListModel model;
	
	/** The specified color provider. */
	private IColorProvider colorProvider;
	
	/** The specified drawing canvas. */
	private JDrawingCanvas canvas;
	
	/** Set to true if started drawing. */
	private boolean drawing;
	
	/** The starting {@link Point}. */
	private Point start;
	
	/** The x coordinate of the ending {@link Point}. */
	private int xEnd;
	
	/** The y coordinate of the ending {@link Point}. */
	private int yEnd;
	
	/**
	 * Constructor specifying the model, the color provider and the drawing canvas.
	 * 
	 * @param model The specified model.
	 * @param colorProvider The specified color provider.
	 * @param canvas The specified drawing canvas.
	 */
	public LineTool(DrawingObjectListModel model, IColorProvider colorProvider,
			JDrawingCanvas canvas) {
		this.model = model;
		this.colorProvider = colorProvider;
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// Do nothing.
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing.
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!drawing) {
			start = new Point(e.getX(), e.getY());
			drawing = true;
		} else {
			Point end = new Point(e.getX(), e.getY());
			model.add(new Line(start, end, colorProvider.getCurrentColor()));
			drawing = false;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (drawing) {
			xEnd = e.getX();
			yEnd = e.getY();
			canvas.repaint();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// Do nothing.
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		if (!drawing) return;
		g2d.setColor(colorProvider.getCurrentColor());
		g2d.drawLine(start.getX(), start.getY(), xEnd, yEnd);
	}
}
