package hr.fer.zemris.java.tecaj_13.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class and {@link Entity} representing a blog user in the database.
 * 
 * @author Mate Gasparini
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.nick",query="select b from BlogUser as b where b.nick=:nick"),
	@NamedQuery(name="BlogUser.all",query="select b from BlogUser b")
})
@Entity
@Table(name="blog_users")
public class BlogUser {
	
	/** The ID of the user. */
	private Long id;
	
	/** The first name of the user. */
	private String firstName;
	
	/** The last name of the user. */
	private String lastName;
	
	/** The nickname of the user. */
	private String nick;
	
	/** The e-mail address of the user. */
	private String email;
	
	/** The hash of the user's password. */
	private String passwordHash;
	
	/** {@code Set} containing all user's entries. */
	private Collection<BlogEntry> entries = new HashSet<>();
	
	/**
	 * Returns the ID of the user.
	 * 
	 * @return The ID of the user.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the ID of the user to the given ID.
	 * 
	 * @param id The given ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns the first name of the user.
	 * 
	 * @return The first name of the user.
	 */
	@Column(nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name of the user to the given first name.
	 * 
	 * @param firstName The given first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns the last name of the user.
	 * 
	 * @return The last name of the user.
	 */
	@Column(nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name of the user to the given last name.
	 * 
	 * @param lastName The given last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Returns the nickname of the user.
	 * 
	 * @return The nickname of the user.
	 */
	@Column(unique=true, nullable=false)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Sets the nickname of the user to the given nickname.
	 * 
	 * @param nick The given nickname.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Returns the e-mail address of the user.
	 * 
	 * @return The e-mail address of the user.
	 */
	@Column(nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the e-mail address of the user to the given e-mail address.
	 * 
	 * @param email The given e-mail address.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns the hash of the user's password.
	 * 
	 * @return The hash of the user's password.
	 */
	@Column(nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Sets the hash of the user's password to the given hash.
	 * 
	 * @param passwordHash The given hash.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Returns all user's entries.
	 * 
	 * @return All user's entries.
	 */
	@OneToMany(mappedBy="creator")
	public Collection<BlogEntry> getEntries() {
		return entries;
	}
	
	/**
	 * Sets the user's entries to the given {@code Collection} of entries.
	 * 
	 * @param entries The given {@code Collection} of entries.
	 */
	public void setEntries(Collection<BlogEntry> entries) {
		this.entries = entries;
	}
}
