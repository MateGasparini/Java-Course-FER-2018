package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * {@link Tool} used for drawing {@link Circle} objects.
 * 
 * @author Mate Gasparini
 */
public class CircleTool implements Tool {
	
	/** The specified model. */
	private DrawingObjectListModel model;
	
	/** The specified color provider. */
	private IColorProvider colorProvider;
	
	/** The specified drawing canvas. */
	private JDrawingCanvas canvas;
	
	/** Set to true if starting drawing. */
	private boolean drawing;
	
	/** The center {@link Point}. */
	private Point center;
	
	/** The radius length. */
	private int radius;
	
	/**
	 * Constructor specifying the model, the color provider and the drawing canvas.
	 * 
	 * @param model The specified model.
	 * @param colorProvider The specified color provider.
	 * @param canvas The drawing canvas.
	 */
	public CircleTool(DrawingObjectListModel model, IColorProvider colorProvider,
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
			center = new Point(e.getX(), e.getY());
			drawing = true;
		} else {
			model.add(new Circle(center, radius, colorProvider.getCurrentColor()));
			drawing = false;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (drawing) {
			radius = (int) center.distanceFrom(e.getX(), e.getY());
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
		g2d.drawOval(center.getX()-radius, center.getY()-radius, radius*2, radius*2);
	}
}
