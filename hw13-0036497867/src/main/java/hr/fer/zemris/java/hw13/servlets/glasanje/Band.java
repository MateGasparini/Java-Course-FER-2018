package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.util.Comparator;

/**
 * Class containing information about a band (and its voting score).
 * 
 * @author Mate Gasparini
 */
public class Band implements Comparable<Band> {
	
	/** Band's ID. */
	private String id;
	
	/** Band's name. */
	private String name;
	
	/** Web address of some band's song. */
	private String link;
	
	/** Band's voting score. */
	private int score;
	
	/**
	 * Constructor specifying the band's id, name and link to some band's song.
	 * 
	 * @param id The specified band's ID.
	 * @param name The specified band's name.
	 * @param link The specified link to some band's song.
	 */
	public Band(String id, String name, String link) {
		this.id = id;
		this.name = name;
		this.link = link;
	}
	
	/**
	 * Returns the band's ID.
	 * 
	 * @return The band's ID.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Returns the band's name.
	 * 
	 * @return The band's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the link to some band's song.
	 * 
	 * @return The link to some band's song.
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Returns the currently stored band's score.
	 * 
	 * @return The band's score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets the band's score to the given score value.
	 * 
	 * @param score The given score value.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(Band o) {
		return Comparator.comparing(Band::getScore).reversed()
					.thenComparing(Band::getName).compare(this, o);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Band other = (Band) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
