package hr.fer.zemris.java.hw17.galerija.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw17.galerija.model.Image;
import hr.fer.zemris.java.hw17.galerija.model.ImageDB;

/**
 * {@link ServletContextListener} used for initialization of the {@link ImageDB}.<br>
 * Information is loaded from a .txt file, parsed to {@link Image} objects and
 * stored into the {@link ImageDB}.
 * 
 * @author Mate Gasparini
 */
@WebListener
public class Setup implements ServletContextListener {
	
	/** Path to the descriptor .txt file. */
	private static final String DESCRIPTOR = "/WEB-INF/opisnik.txt";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Path path = Paths.get(sce.getServletContext().getRealPath(DESCRIPTOR));
			List<String> lines = Files.readAllLines(path);
			
			for (int i = 0, size = lines.size(); i + 2 < size; i += 3) {
				String name = lines.get(i).trim();
				String title = lines.get(i + 1).trim();
				String[] tags = lines.get(i + 2).trim().split("\\s*,\\s*");
				ImageDB.addImage(new Image(name, title, tags));
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error initializing Image DB.", ex);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do nothing.
	}
}
