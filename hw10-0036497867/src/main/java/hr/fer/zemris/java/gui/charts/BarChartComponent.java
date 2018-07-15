package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * A {@link JComponent} representing the visualization of a {@link BarChart}.
 * 
 * @author Mate Gasparini
 */
public class BarChartComponent extends JComponent {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = 1578958143648920035L;
	
	/* SELF-EXPLANATORY CONSTANTS */
	private static final int SIDE_TO_LABEL = 20;
	private static final int LABEL_TO_NUMBERS = 10;
	private static final int NUMBERS_TO_AXIS = 10;
	private static final int SURPLUS = 5; // Length of the little axis spikes.
	private static final int ARROW_HALF_BASE = 3;
	private static final int SHADOW_OFFSET = 5;
	
	private static final Color BAR_COLOR = new Color(230, 100, 60);
	private static final Color AXIS_COLOR = new Color(100, 100, 100);
	private static final Color BEHIND_COLOR = new Color(230, 100, 60, 100);
	private static final Color SHADOW_COLOR = new Color(150, 150, 150);
	
	private static final Font FONT = new Font("Dialog", Font.BOLD, 12);
	
	/**
	 * Reference to the corresponding {@link BarChart}.
	 */
	private BarChart barChart;
	/**
	 * Reference to the object used for drawing all parts of the chart.
	 */
	private Graphics2D g2d;
	/**
	 * Provides information about the font used (used in some positioning calculations).
	 */
	private FontMetrics fm;
	
	/**
	 * Starting value of the y-axis.
	 */
	private int minY;
	/**
	 * Ending value of the y-axis.
	 */
	private int maxY;
	/**
	 * Step between each two neighboring y-axis values.
	 */
	private int step;
	
	/**
	 * Current width of the component.
	 */
	private int width;
	/**
	 * Current height of the component.
	 */
	private int height;
	
	/**
	 * Length of the longest y value string length.
	 */
	private int yLongestLength;
	
	/**
	 * Currently used font's ascent length.
	 */
	private int ascent;
	/**
	 * Gap from the outer side of the component to the closest axis.
	 */
	private int outerGap;
	/**
	 * Length of the x-axis (without the arrow spike part).
	 */
	private int xAxisLength;
	/**
	 * Length of the y-axis (without the arrow spike part).
	 */
	private int yAxisLength;
	
	/**
	 * Reference to the corresponding list of all values.
	 */
	private List<XYValue> values;
	/**
	 * Number of x values.
	 */
	private int xCount;
	/**
	 * Width of a single bar.
	 */
	private int xStep;
	
	/**
	 * Number of y values.
	 */
	private int yCount;
	/**
	 * Height of one step on the y-axis.
	 */
	private int yStep;
	
	/**
	 * Length from the left/bottom outer side to the y-axis/x-axis numbers.
	 */
	private int sideToNumbers;
	
	/**
	 * Class constructor specifying the corresponding bar chart object.
	 * 
	 * @param barChart Reference to the specified bar chart.
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setUpValues(g);
		
		drawXDescription();
		drawYDescription();
		
		drawShadows();
		
		drawYValues();
		drawXValues();
		
		drawValueRectangles();
		
		drawXAxis();
		drawYAxis();
	}
	
	/**
	 * Calculates and sets all values needed for painting the component.
	 * 
	 * @param g The given {@code Graphics} object reference.
	 */
	private void setUpValues(Graphics g) {
		g2d = (Graphics2D) g;
		
		minY = barChart.getMinY();
		maxY = barChart.getMaxY();
		step = barChart.getStep();
		
		width = getWidth();
		height = getHeight();
		
		fm = g2d.getFontMetrics();
		
		yLongestLength = Math.max(
			fm.stringWidth(String.valueOf(minY)), fm.stringWidth(String.valueOf(maxY))
		);
		
		ascent = fm.getAscent();
		
		outerGap =
			SIDE_TO_LABEL + ascent + LABEL_TO_NUMBERS + yLongestLength + NUMBERS_TO_AXIS;
		
		xAxisLength = width - 2*outerGap;
		yAxisLength = height - 2*outerGap;
		
		values = barChart.getValues();
		
		xCount = values.size();
		xStep = xAxisLength / xCount;
		
		yCount = (maxY - minY) / step;
		yStep = yAxisLength / yCount;
		
		sideToNumbers = SIDE_TO_LABEL + ascent + LABEL_TO_NUMBERS;
		
		g2d.setFont(FONT);
	}
	
	/**
	 * Draws the horizontal description below the x-axis.
	 */
	private void drawXDescription() {
		g2d.setColor(AXIS_COLOR);
		
		String descriptionX = barChart.getDescriptionX();
		g2d.drawString(
			descriptionX,
			(width-fm.stringWidth(descriptionX)) / 2,
			height - SIDE_TO_LABEL + ascent
		);
	}
	
	/**
	 * Draws the vertical description next to the y-axis.
	 */
	private void drawYDescription() {
		// Rotate for vertical yDescription.
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		
		String descriptionY = barChart.getDescriptionY();
		g2d.drawString(
			descriptionY,
			(-height-fm.stringWidth(descriptionY)) / 2,
			SIDE_TO_LABEL
		);
		
		// Restore rotation.
		g2d.setTransform(defaultAt);
	}
	
	/**
	 * Draws the bar shadows.
	 */
	private void drawShadows() {
		g2d.setColor(SHADOW_COLOR);
		
		for (int i = 0; i < xCount; i ++) {
			int x = outerGap + i*xStep + SHADOW_OFFSET;
			int y = (int) (height - outerGap
				- (double) (values.get(i).getY()-minY) / step * yStep) + SHADOW_OFFSET;
			int barHeight = height - outerGap - y;
			
			g2d.fillRect(x, y, xStep, barHeight);
		}
	}
	
	/**
	 * Draws the numbers on the y-axis, as well as all horizontal lines in the back.
	 */
	private void drawYValues() {
		for (int i = 0; i <= yCount; i ++) {
			String number = String.valueOf(minY + step*i);
			int xPosition = sideToNumbers + yLongestLength - fm.stringWidth(number);
			int yPosition = height - outerGap + ascent/2 - i*yStep;
			
			g2d.setColor(Color.BLACK);
			g2d.drawString(number, xPosition, yPosition - ascent/10);
			
			yPosition -= ascent/2;
			g2d.setColor(AXIS_COLOR);
			g2d.drawLine(outerGap - SURPLUS, yPosition, outerGap, yPosition);
			g2d.setColor(BEHIND_COLOR);
			g2d.drawLine(outerGap, yPosition, width - outerGap + SURPLUS, yPosition);
		}
	}
	
	/**
	 * Draws the numbers on the x-axis, as well as all vertical lines in the back.
	 */
	private void drawXValues() {
		for (int i = 0; i < xCount; i ++) {
			String number = String.valueOf(values.get(i).getX());
			int xPosition = outerGap + i*xStep + xStep/2;
			int yPosition = height - sideToNumbers;	
			
			g2d.setColor(Color.BLACK);
			g2d.drawString(number, xPosition, yPosition);
			
			xPosition += xStep/2;
			g2d.setColor(AXIS_COLOR);
			g2d.drawLine(xPosition, height - outerGap + SURPLUS, xPosition, height - outerGap);
			g2d.setColor(BEHIND_COLOR);
			g2d.drawLine(xPosition, height - outerGap, xPosition, outerGap);
		}
	}
	
	/**
	 * Draws the value bars.
	 */
	private void drawValueRectangles() {
		for (int i = 0; i < xCount; i ++) {
			int x = outerGap + i*xStep;
			int y = (int) (height - outerGap
				- (double) (values.get(i).getY()-minY) / step * yStep);
			int barHeight = height - outerGap - y;
			
			g2d.setColor(BAR_COLOR);
			g2d.fillRect(x, y, xStep, barHeight);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(x, y, xStep, barHeight);
		}
	}
	
	/**
	 * Draws the x-axis with its arrow.
	 */
	private void drawXAxis() {
		g2d.setColor(AXIS_COLOR);
		
		g2d.drawLine(
			outerGap,
			height - outerGap,
			width - outerGap + SURPLUS,
			height - outerGap
		);
		g2d.fillPolygon(
			new int[] {
				width - outerGap + 2*SURPLUS,
				width - outerGap + SURPLUS/2,
				width - outerGap + SURPLUS/2
			},
			new int[] {
				height - outerGap,
				height - outerGap - ARROW_HALF_BASE,
				height - outerGap + ARROW_HALF_BASE
			}, 3
		);
	}
	
	/**
	 * Draws the y-axis with its arrow.
	 */
	private void drawYAxis() {
		g2d.drawLine(
			outerGap,
			outerGap - SURPLUS,
			outerGap,
			height - outerGap + SURPLUS
		);
		g2d.fillPolygon(
			new int[] {
				outerGap,
				outerGap - ARROW_HALF_BASE,
				outerGap + ARROW_HALF_BASE
			},
			new int[] {
				outerGap - 2*SURPLUS,
				outerGap - SURPLUS/2,
				outerGap - SURPLUS/2
			}, 3
		);
	}
}
