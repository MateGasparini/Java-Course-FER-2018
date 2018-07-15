package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class and {@link Entity} representing a blog entry in the database.
 * 
 * @author Mate Gasparini
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.creator",query="select b from BlogEntry as b where b.creator=:creator")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {
	
	/** The ID of the entry. */
	private Long id;
	
	/** {@code List} of {@link BlogComment}s posted on the entry. */
	private List<BlogComment> comments = new ArrayList<>();
	
	/** {@code Date} of entry creation. */
	private Date createdAt;
	
	/** {@code Date} of last entry modification. */
	private Date lastModifiedAt;
	
	/** The title of the entry. */
	private String title;
	
	/** The text of the entry. */
	private String text;
	
	/** Reference to the {@link BlogUser} who created the entry. */
	private BlogUser creator;
	
	/**
	 * Returns the ID of the entry.
	 * 
	 * @return The ID of the entry.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the ID of the entry to the given ID.
	 * 
	 * @param id The given ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns the {@code List} of {@link BlogComment}s posted on the entry.
	 * 
	 * @return The {@code List} of {@link BlogComment}s.
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Sets the {@code List} of {@link BlogComment}s to the given {@code List}.
	 * 
	 * @param comments The given {@code List} of {@link BlogComment}s.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Returns the {@code Date} of entry creation.
	 * 
	 * @return The {@code Date} of entry creation.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * Sets the {@code Date} of entry creation to the given {@code Date}.
	 * 
	 * @param createdAt The given {@code Date}.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * Returns the {@code Date} of last entry modification.
	 * 
	 * @return The {@code Date} of last entry modification.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	
	/**
	 * Sets the {@code Date} of last entry modification to the given {@code Date}.
	 * 
	 * @param lastModifiedAt The given {@code Date}.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	/**
	 * Returns the title of the entry.
	 * 
	 * @return The title of the entry.
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of the entry to the given title.
	 * 
	 * @param title The given title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns the text of the entry.
	 * 
	 * @return The text of the entry.
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the text of the entry to the given text.
	 * 
	 * @param text The given text.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Returns the {@link BlogUser} who created the entry.
	 * 
	 * @return The {@link BlogUser} who created the entry.
	 */
	@ManyToOne
	public BlogUser getCreator() {
		return creator;
	}
	
	/**
	 * Sets the {@link BlogUser} who created the entry to the given {@link BlogUser}.
	 * 
	 * @param creator The given {@link BlogUser}.
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}