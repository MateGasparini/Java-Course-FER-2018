package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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
 * {@link HttpServlet} which handles GET requests by loading band voting results
 * from files on disk and, if there are results present, a {@link JFreeChart}
 * is created and written to the {@link HttpServletResponse}'s output stream
 * as an PNG image.
 * 
 * @author Mate Gasparini
 */
public class GlasanjeGrafikaServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Band> bands;
		try {
			bands = GlasanjeUtil.loadSortedResults(
				req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"),
				req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt")
			);
		} catch (IllegalArgumentException | IOException ex) {
			resp.sendError(500, "Internal server error");
			ex.printStackTrace();
			return;
		}
		
		if (bands == null || bands.isEmpty()) {
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeGreska.jsp").forward(req, resp);
			return;
		}
		
		JFreeChart chart = createChart("Rezultati glasanja", bands);
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
	 * Returns a {@link DefaultPieDataset} filled with scores for each band
	 * from the given {@code List} of bands.
	 * 
	 * @param bands The given {@code List} of bands.
	 * @return {@link PieDataset} filled with band scores.
	 */
	private PieDataset getDataset(List<Band> bands) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Band band : bands) {
			dataset.setValue(band.getName(), band.getScore());
		}
		return dataset;
	}
	
	/**
	 * Creates a {@link JFreeChart} with the specified title and filled
	 * with score data for each band from the given {@code List} of bands.
	 * 
	 * @param title The specified chart title.
	 * @param bands The given {@code List} of bands.
	 * @return Reference to the created chart.
	 */
	private JFreeChart createChart(String title, List<Band> bands) {
		JFreeChart chart = ChartFactory.createPieChart3D(
			title, getDataset(bands), true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setBackgroundAlpha(0.f);
		plot.setStartAngle(270);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(1.f);
		return chart;
	}
}
