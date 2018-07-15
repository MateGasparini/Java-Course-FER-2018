package hr.fer.zemris.java.hw17.galerija.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
 * parameter, reading the corresponding image thumbnail (possibly creating it on
 * disk) and writing it to the {@link HttpServletResponse}'s {@code OutputStream}.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns="/servlets/thumbs")
public class ThumbnailServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Path to the thumbnails directory. */
	private static final String THUMBS_PATH = "/WEB-INF/thumbnails";
	
	/** Path to the pictures directory. */
	private static final String PICS_PATH = "/WEB-INF/slike";
	
	/** Size (width and height) of the thumbnail images. */
	private static final int THUMB_SIZE = 150;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext servletContext = req.getServletContext();
		Path thumbs = Paths.get(servletContext.getRealPath(THUMBS_PATH));
		synchronized (this) {
			if (!Files.isDirectory(thumbs)) {
				Files.createDirectories(thumbs);
			}
		}
		
		String name = req.getParameter("name");
		if (name == null) {
			resp.sendError(500, "Bad request");
			return;
		}
		String pngName = name.concat(".png");
		resp.setContentType("image/png");
		
		Path thumb = thumbs.resolve(pngName);
		synchronized (this) {
			if (Files.exists(thumb)) {
				Path thumbPath = Paths.get(THUMBS_PATH).resolve(pngName);
				try (InputStream is = servletContext.getResourceAsStream(thumbPath.toString())) {
					ImageIO.write(ImageIO.read(is), "png", resp.getOutputStream());
				}
			} else {
				Path pic = Paths.get(servletContext.getRealPath(PICS_PATH)).resolve(name);
				if (!Files.exists(pic)) {
					resp.sendError(404, "Not found");
					return;
				}
				
				Path picPath = Paths.get(PICS_PATH).resolve(name);
				try (InputStream is = servletContext.getResourceAsStream(picPath.toString())) {
					BufferedImage original = ImageIO.read(is);
					BufferedImage scaled = scaleImage(original, THUMB_SIZE, THUMB_SIZE);
					ImageIO.write(scaled, "png", Files.newOutputStream(thumb));
					ImageIO.write(scaled, "png", resp.getOutputStream());
				}
			}
		}
	}
	
	/**
	 * Scales the given original image to the given width and height.
	 * 
	 * @param original The given original image.
	 * @param width The given width (in pixels).
	 * @param height The given height (in pixels).
	 * @return The scaled image.
	 */
	private BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaled = new BufferedImage(width, height, original.getType());
		Graphics2D g2d = scaled.createGraphics();
		g2d.drawImage(original, 0, 0, width, height, null);
		g2d.dispose();
		return scaled;
	}
}
