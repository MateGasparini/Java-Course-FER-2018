package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class and {@link Entity} representing a blog comment in the database.
 * 
 * @author Mate Gasparini
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {
	
	/** The ID of the comment. */
	private Long id;
	
	/** The {@link BlogEntry} this comment belongs to. */
	private BlogEntry blogEntry;
	
	/** The e-mail address of the person which posted this comment. */
	private String usersEMail;
	
	/** The comment's message. */
	private String message;
	
	/** {@code Date} of posting. */
	private Date postedOn;
	
	/**
	 * Returns the comment's ID.
	 * 
	 * @return The comment's ID.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the comment's ID to the given ID.
	 * 
	 * @param id The given ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns the {@link BlogEntry} this comment belongs to.
	 * 
	 * @return The {@link BlogEntry} this comment belongs to.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets the {@link BlogEntry} this comment belongs to to the given {@link BlogEntry}.
	 * 
	 * @param blogEntry The given {@link BlogEntry}.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}
	
	/**
	 * Returns the poster's e-mail address.
	 * 
	 * @return The poster's e-mail address.
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}
	
	/**
	 * Sets the poster's e-mail address to the given e-mail address.
	 * 
	 * @param usersEMail The given e-mail address.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}
	
	/**
	 * Returns the comment's message.
	 * 
	 * @return The comment's message.
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the comment's message to the given message.
	 * 
	 * @param message The given message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Returns the {@code Date} of posting.
	 * 
	 * @return The {@code Date} of posting.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}
	
	/**
	 * Sets the {@code Date} of posting to the given {@code Date}.
	 * 
	 * @param postedOn The given {@code Date}.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}