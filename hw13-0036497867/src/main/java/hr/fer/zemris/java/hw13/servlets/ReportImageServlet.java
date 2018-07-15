package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * {@link HttpServlet} which handles GET requests by generating a {@link JFreeChart}
 * PNG image and writing it's binary content directly to the {@link HttpServletResponse}'s
 * output stream.<br>
 * The chart is generated using hard-coded data from a survey about OS usage.
 * 
 * @author Mate Gasparini
 */
public class ReportImageServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JFreeChart chart = createChart("Which operating system are you using?");
		BufferedImage bim = chart.createBufferedImage(500, 270);
		resp.setContentType("image/png");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			resp.getOutputStream().write(bos.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns a {@link DefaultPieDataset} with some values representing OS usage
	 * statistics.
	 * 
	 * @return {@link PieDataset} filled with OS survey information.
	 */
	private PieDataset getDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 29);
		dataset.setValue("Mac", 20);
		dataset.setValue("Windows", 51);
		return dataset;
	}
	
	/**
	 * Creates a {@link JFreeChart} with the specified title, fills it with data
	 * and returns its reference.
	 * 
	 * @param title The specified chart title.
	 * @return Reference to the created chart.
	 */
	private JFreeChart createChart(String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(
			title, getDataset(), true, true, false);
		
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		
		return chart;
	}
}
