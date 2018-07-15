package hr.fer.zemris.java.gui.layouts;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A demo program which demonstrates the functionality
 * of the {@code CalcLayout} class.<br>
 * It displays a frame with some layout positions filled with JButtons.
 * 
 * @author Mate Gasparini
 */
public class CalcLayoutDemo extends JFrame {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor which sets up the frame.
	 */
	public CalcLayoutDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Initializes all used components and arranges them
	 * with the {@code CalcLayout}.
	 */
	private void initGUI() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(1,1));
		p.add(new JButton("y"), new RCPosition(2,3));
		p.add(new JButton("z"), new RCPosition(2,7));
		p.add(new JButton("w"), new RCPosition(4,2));
		p.add(new JButton("a"), new RCPosition(4,5));
		p.add(new JButton("b"), new RCPosition(4,7));
		
		getContentPane().add(p);
		pack();
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new CalcLayoutDemo().setVisible(true);
		});
	}
}
