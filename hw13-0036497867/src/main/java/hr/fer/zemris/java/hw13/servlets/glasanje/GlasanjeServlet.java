package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by loading the {@code List} of
 * band definitions from disk, setting a request attribute to its reference and
 * forwarding the {@link HttpServletRequest} to the corresponding JSP file
 * (glasanjeIndex.jsp).
 * 
 * @author Mate Gasparini
 */
public class GlasanjeServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath(
			"/WEB-INF/glasanje-definicija.txt");
		try {
			List<Band> bands = GlasanjeUtil.loadBands(Paths.get(fileName));
			req.setAttribute("bands", bands);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		} catch (IllegalArgumentException | IOException ex) {
			resp.sendError(500, "Internal server error");
			ex.printStackTrace();
		}
	}
}
