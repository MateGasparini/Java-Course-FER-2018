package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Singleton class providing functionality for getting {@link ImageIcon}
 * references from specified file path.
 * 
 * @author Mate Gasparini
 */
public class IconLoader {
	
	/** Only instance of the singleton. */
	private static IconLoader instance = new IconLoader();
	
	/** Cache map holding icons for given file paths. */
	private Map<String, ImageIcon> icons = new HashMap<>();
	
	/**
	 * Private default constructor.
	 */
	private IconLoader() {
	}
	
	/**
	 * Returns the singleton's instance.
	 * 
	 * @return The singleton's instance.
	 */
	public static IconLoader getInstance() {
		return instance;
	}
	
	/**
	 * Returns an {@link ImageIcon} loaded from the given path.<br>
	 * If it was already once loaded, its cached reference is returned.
	 * 
	 * @param path The given path.
	 * @return The corresponding {@link ImageIcon}.
	 */
	public ImageIcon loadIcon(String path) {
		if (icons.containsKey(path)) {
			return icons.get(path);
		} else {
			return loadFromPath(path);
		}
	}
	
	/**
	 * Returns an {@link ImageIcon} loaded from the given path.
	 * 
	 * @param path The given path.
	 * @return The loaded {@link ImageIcon}.
	 */
	private ImageIcon loadFromPath(String path) {
		byte[] imageData;
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) {
				throw new IllegalArgumentException(
					"Invalid icon path: " + path
				);
			}
			imageData = is.readAllBytes();
		} catch (IOException | OutOfMemoryError e) {
			throw new IllegalArgumentException("Error loading icon from path: " + path);
		}
		
		return new ImageIcon(imageData);
	}
}
