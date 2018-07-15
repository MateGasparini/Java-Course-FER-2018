package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Program which displays two JLists with the same {@link PrimListModel}
 * (initially containing only the number 1) and a {@link JButton}
 * used for generating the next prime number.
 * 
 * @author Mate Gasparini
 */
public class PrimDemo extends JFrame {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default frame constructor.
	 */
	public PrimDemo() {
		setSize(500, 500);
		setLocation(200, 200);
		setTitle("PrimDemo");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
	
	/**
	 * Initializes all components and adds the lists to the center position
	 * {@code GridLayout}, and the next button to the south position.
	 */
	private void initGUI() {
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		JPanel center = new JPanel(new GridLayout(1, 2));
		
		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		center.add(new JScrollPane(list1), 0);
		JList<Integer> list2 = new JList<>(model);
		center.add(new JScrollPane(list2), 1);
		
		pane.add(center);
		
		JButton next = new JButton("Sljedeći");
		pane.add(next, BorderLayout.SOUTH);
		
		next.addActionListener(e -> model.next());
	}
}
