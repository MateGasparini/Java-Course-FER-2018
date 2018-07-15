package hr.fer.zemris.java.hw17.galerija.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class representing a simple database of {@link Image}s.
 * 
 * @author Mate Gasparini
 */
public class ImageDB {
	
	/** {@code Map} which maps image names to the corresponding {@link Image} references. */
	private static Map<String, Image> images = new HashMap<>();
	
	/** {@code Map} which maps tags to collections of image names. */
	private static Map<String, Set<String>> imageNames = new HashMap<>();
	
	/** {@code Set} containing all existing image tags. */
	private static Set<String> tags = new HashSet<>();
	
	/**
	 * Private default constructor.
	 */
	private ImageDB() {
	}
	
	/**
	 * Returns the {@link Image} corresponding to the given image name.
	 * 
	 * @param name The given image name.
	 * @return The corresponding {@link Image} from the {@code Map},
	 * 			or {@code null} if no mapping exists.
	 */
	public static Image getImage(String name) {
		return images.get(name);
	}
	
	/**
	 * Returns the {@code Set} of image names corresponding to the given tag.
	 * 
	 * @param tag The given tag.
	 * @return The corresponding {@code Set} of image names,
	 * 			or {@code null} if no mapping exists.
	 */
	public static Set<String> getImageNames(String tag) {
		return imageNames.get(tag);
	}
	
	/**
	 * Returns the {@code Set} of all existing tags.
	 * 
	 * @return The {@code Set} of all existing tags.
	 */
	public static Set<String> getTags() {
		return tags;
	}
	
	/**
	 * Stores the given {@link Image} to the database.<br>
	 * Also, stores information related to the given {@link Image}'s tags etc.
	 * 
	 * @param image The given {@link Image}.
	 */
	public static void addImage(Image image) {
		String name = image.getName();
		images.put(name, image);
		String[] tags = image.getTags();
		for (String tag : tags) {
			ImageDB.tags.add(tag);
			ImageDB.imageNames.putIfAbsent(tag, new HashSet<>());
			ImageDB.imageNames.get(tag).add(name);
		}
	}
}
