package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Program which takes one command line argument which represents a path
 * to some file containing information for constructing a {@link BarChart}.<br>
 * If the file is valid, the corresponding {@link BarChartComponent}
 * is drawn on the screen.
 * 
 * @author Mate Gasparini
 */
public class BarChartDemo extends JFrame {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the constructed corresponding bar chart.
	 */
	private static BarChart barChart;
	/**
	 * The specified path to the file.
	 */
	private static Path argPath;
	
	/**
	 * Default frame constructor.
	 */
	public BarChartDemo() {
		setSize(800, 600);
		setLocation(200, 200);
		setTitle("BarChartDemo");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments. Should be one argument representing
	 * 			the path to the {@code BarChart} description file.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected one argument (path to the chart description file).");
			return;
		}
		
		readChartFromFile(args[0]);
		if (barChart == null) return;
		
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo().setVisible(true);
		});
	}
	
	/**
	 * Parses the specified file and constructs the corresponding {@code BarChart}.
	 * 
	 * @param path Path to the specified file.
	 * @throws IOException If the file cannot be opened.
	 * @throws NumberFormatException If the file contains invalid non-integer characters.
	 * @throws RuntimeException If the values list is of invalid format.
	 */
	private static void readChartFromFile(String path) {
		argPath = Paths.get(path);
		try (BufferedReader reader = Files.newBufferedReader(argPath)) {
			String[] lines = new String[6];
			for (int i = 0; i < 6; i ++) {
				String line = reader.readLine();
				if (line == null) {
					System.out.println(
						"Invalid file content. Expected at least 6 rows, but was: " + i + "."
					);
					return;
				}
				
				lines[i] = line;
			}
			
			String descriptionX = lines[0];
			String descriptionY = lines[1];
			List<XYValue> values = parseValues(lines[2]);
			int minY = Integer.parseInt(lines[3]);
			int maxY = Integer.parseInt(lines[4]);
			int step = Integer.parseInt(lines[5]);
			
			barChart = new BarChart(values, descriptionX, descriptionY, minY, maxY, step);
		} catch (IOException ex) {
			System.out.println("The file does not exist or cannot be opened.");
		} catch (NumberFormatException ex) {
			System.out.println("The description can contain integers only.");
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Parses the given line of text and returns a {@code List} of
	 * corresponding {@link XYValue} references.
	 * 
	 * @param line The given line of text.
	 * @return The corresponding values list.
	 */
	private static List<XYValue> parseValues(String line) {
		String[] pairs = line.trim().split("\\s+");
		
		XYValue[] values = new XYValue[pairs.length];
		for (int i = 0; i < values.length; i ++) {
			String[] pair = pairs[i].split(",");
			if (pair.length != 2) {
				throw new RuntimeException(
					"Invalid values list. Values must be separated with a comma "
					+ "and pairs of values must be separated with whitespace."
				);
			}
			
			values[i] = new XYValue(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
		}
		
		return Arrays.asList(values);
	}
	
	/**
	 * Initializes and adds to the content pane the {@link BarChartComponent}
	 * and the path to the specified file.
	 */
	private void initGUI() {
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		pane.setBackground(Color.WHITE);
		
		pane.add(new BarChartComponent(barChart), BorderLayout.CENTER);
		JLabel pathLabel = new JLabel(argPath.toAbsolutePath().toString());
		pathLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pane.add(pathLabel, BorderLayout.NORTH);
	}
}
