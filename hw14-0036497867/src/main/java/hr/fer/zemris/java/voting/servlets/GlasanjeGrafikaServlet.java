package hr.fer.zemris.java.voting.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.voting.model.PollOption;

/**
 * {@link HttpServlet} which handles GET requests by loading poll results (specified
 * by the given <i>pollId</i> URL parameter) from the DB.<br>
 * If there are results present, a {@link JFreeChart} is created and written
 * to the {@link HttpServletResponse}'s output stream as a PNG image.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<PollOption> options = GlasanjeUtil.getPollResults(req);
		if (options == null) {
			req.getRequestDispatcher(GlasanjeUtil.ERROR_JSP).forward(req, resp);
			return;
		}
		
		JFreeChart chart = createChart("Rezultati glasanja", options);
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
	 * Returns a {@link DefaultPieDataset} filled with votes for each poll option
	 * from the given {@code List} of poll options.
	 * 
	 * @param options The given {@code List} of poll options.
	 * @return {@link PieDataset} filled with poll option votes.
	 */
	private PieDataset getDataset(List<PollOption> options) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (PollOption option : options) {
			dataset.setValue(option.getTitle(), option.getVotesCount());
		}
		return dataset;
	}
	
	/**
	 * Creates a {@link JFreeChart} with the specified title and filled
	 * with votes count for each poll option from the given {@code List} of poll options.
	 * 
	 * @param title The specified chart title.
	 * @param options The given {@code List} of poll options.
	 * @return Reference to the created chart.
	 */
	private JFreeChart createChart(String title, List<PollOption> options) {
		JFreeChart chart = ChartFactory.createPieChart3D(
			title, getDataset(options), true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setBackgroundAlpha(0.f);
		plot.setStartAngle(270);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(1.f);
		return chart;
	}
}
