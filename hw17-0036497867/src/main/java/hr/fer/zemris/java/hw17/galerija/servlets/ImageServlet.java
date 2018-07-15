package hr.fer.zemris.java.hw17.galerija.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by reading the given 'name'
 * parameter, reading the corresponding image from the pictures directory
 * and writing it to the {@link HttpServletResponse}'s {@code OutputStream}.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns="/servlets/image")
public class ImageServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Path to the pictures directory. */
	private static final String PICS_PATH = "/WEB-INF/slike";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext servletContext = req.getServletContext();
		String name = req.getParameter("name");
		if (name == null) {
			resp.sendError(500, "Bad request");
			return;
		}
		
		Path picPath = Paths.get(PICS_PATH).resolve(name);
		try (InputStream is = servletContext.getResourceAsStream(picPath.toString())) {
			if (is == null) {
				resp.sendError(404, "Not found");
				return;
			}
			
			BufferedImage image = ImageIO.read(is);
			ImageIO.write(image, "png", resp.getOutputStream());
		}
	}
}
