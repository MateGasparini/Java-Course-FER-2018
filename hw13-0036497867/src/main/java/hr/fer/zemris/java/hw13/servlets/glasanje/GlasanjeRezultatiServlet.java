package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by loading the band voting results,
 * setting a request attribute to the band {@code List} reference and forwards the
 * {@link HttpServletRequest} to the corresponding JSP file (glasanjeRez.jsp).
 * 
 * @author Mate Gasparini
 */
public class GlasanjeRezultatiServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath(
			"/WEB-INF/glasanje-rezultati.txt");
		List<Band> bands;
		try {
			bands = GlasanjeUtil.loadSortedResults(
				req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"),
				fileName
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
		req.setAttribute("bands", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
