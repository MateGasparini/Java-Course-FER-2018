package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by reading the specified id parameter
 * and registering a vote for it.<br>
 * It is registered in a file which contains band IDs and their corresponding score
 * separated by a TAB.<br>
 * IDs are unchecked, which means it is not checked whether a band with the specified ID
 * exists or not.
 * 
 * @author Mate Gasparini
 */
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id == null) {
			resp.sendError(400, "Bad request");
			return;
		}
		
		String fileName = req.getServletContext().getRealPath(
			"/WEB-INF/glasanje-rezultati.txt");
		registerVote(id, fileName);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
	/**
	 * Registers a vote for the band with the given ID to the file specified
	 * by the given file name.
	 * 
	 * @param id The given band ID.
	 * @param fileName The given file name/path.
	 * @throws IOException If an I/O error occurs.
	 */
	private void registerVote(String id, String fileName) throws IOException {
		Path filePath = Paths.get(fileName);
		if (Files.isRegularFile(filePath) && Files.isReadable(filePath)) {
			Map<String, Integer> results = GlasanjeUtil.readResults(filePath);
			results.put(id, results.containsKey(id) ? results.get(id)+1 : 1);
			GlasanjeUtil.writeResults(filePath, results);
		} else {
			GlasanjeUtil.createResults(filePath, id);
		}
	}
}
