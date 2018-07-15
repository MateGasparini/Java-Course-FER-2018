package hr.fer.zemris.java.hw16.jvdraw.adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * Listens for mouse events and delegates them to the {@link JVDraw}'s
 * currently active {@link Tool}.
 * 
 * @author Mate Gasparini
 */
public class CanvasMouseListener extends MouseAdapter {
	
	/** Reference to the {@link JVDraw}. */
	private JVDraw frame;
	
	/**
	 * Constructor specifying the {@link JVDraw} reference.
	 * 
	 * @param frame The specified {@link JVDraw}.
	 */
	public CanvasMouseListener(JVDraw frame) {
		this.frame = frame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Tool tool = frame.getCurrentTool();
		if (tool == null) return;
		tool.mouseClicked(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Tool tool = frame.getCurrentTool();
		if (tool == null) return;
		tool.mousePressed(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		Tool tool = frame.getCurrentTool();
		if (tool == null) return;
		tool.mouseReleased(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Tool tool = frame.getCurrentTool();
		if (tool == null) return;
		tool.mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		Tool tool = frame.getCurrentTool();
		if (tool == null) return;
		tool.mouseMoved(e);
	}
}
