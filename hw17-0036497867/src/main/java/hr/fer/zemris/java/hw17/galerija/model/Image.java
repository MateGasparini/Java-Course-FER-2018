package hr.fer.zemris.java.hw17.galerija.model;

/**
 * Class representing a simple image and containing the name of the image on disk,
 * the title of the image and all tags which describe its content.
 * 
 * @author Mate Gasparini
 */
public class Image {
	
	/** File name of the image on disk. */
	private String name;
	
	/** Title of the image. */
	private String title;
	
	/** Tags which describe the picture's content. */
	private String[] tags;
	
	/**
	 * Constructor specifying the name, the title and the tags.
	 * 
	 * @param name The specified file name.
	 * @param title The specified image title.
	 * @param tags The specified tags.
	 */
	public Image(String name, String title, String[] tags) {
		this.name = name;
		this.title = title;
		this.tags = tags;
	}
	
	/**
	 * Returns the file name of the image.
	 * 
	 * @return The file name of the image.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the title of the image.
	 * 
	 * @return The title of the image.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the tags of the image.
	 * 
	 * @return The tags of the image.
	 */
	public String[] getTags() {
		return tags;
	}
}
