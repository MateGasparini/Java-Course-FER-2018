package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.objects.Point;

/**
 * {@link Tool} used for drawing {@link FilledCircle} objects.
 * 
 * @author Mate Gasparini
 */
public class FilledCircleTool implements Tool {
	
	/** The specified model. */
	private DrawingObjectListModel model;
	
	/** The specified foreground color provider. */
	private IColorProvider fgColorProvider;
	
	/** The specified background color provider. */
	private IColorProvider bgColorProvider;
	
	/** The specified drawing canvas. */
	private JDrawingCanvas canvas;
	
	/** Set to true if started drawing. */
	private boolean drawing;
	
	/** The center {@link Point}. */
	private Point center;
	
	/** The radius length. */
	private int radius;
	
	/**
	 * Constructor specifying the model, the color providers and the drawing canvas.
	 * 
	 * @param model The specified model.
	 * @param fgColorProvider The specified foreground color provider.
	 * @param bgColorProvider The specified background color provider.
	 * @param canvas The specified drawing canvas.
	 */
	public FilledCircleTool(DrawingObjectListModel model, IColorProvider fgColorProvider,
			IColorProvider bgColorProvider, JDrawingCanvas canvas) {
		this.model = model;
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
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
			model.add(new FilledCircle(
				center,
				radius,
				bgColorProvider.getCurrentColor(),
				fgColorProvider.getCurrentColor())
			);
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
		int cornerX = center.getX()-radius;
		int cornerY = center.getY()-radius;
		int diameter = 2*radius;
		
		g2d.setColor(bgColorProvider.getCurrentColor());
		g2d.fillOval(cornerX, cornerY, diameter, diameter);
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawOval(cornerX, cornerY, diameter, diameter);
	}
}
