package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface towards the data persistence layer.
 * 
 * @author Mate Gasparini
 */
public interface DAO {

	/**
	 * Returns the {@link BlogEntry} with the provided ID or {@code null} if there is no entry
	 * with the provided ID.
	 * 
	 * @param id The given ID.
	 * @return The corresponding {@link BlogEntry} or {@code null}.
	 * @throws DAOException If an error occurs.	
	 */
	BlogEntry getBlogEntry(long id) throws DAOException;
	
	/**
	 * Returns the {@link BlogUser} with the provided nickname.
	 * 
	 * @param nick The provided nickname.
	 * @return The corresponding {@link BlogUser}.
	 * @throws DAOException If an error occurs.
	 */
	BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Returns the {@link BlogUser} with the provided ID.
	 * 
	 * @param id The provided ID.
	 * @return The corresponding {@link BlogUser}.
	 * @throws DAOException If an error occurs.
	 */
	BlogUser getBlogUser(long id) throws DAOException;
	
	/**
	 * Returns a {@code List} of all {@link BlogUser}s from the database.
	 * 
	 * @return {@code List} of all registered {@link BlogUser}s.
	 * @throws DAOException If an error occurs.
	 */
	List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * Returns a {@code List} of all {@link BlogEntry} references belonging
	 * to the given {@link BlogUser}.
	 * 
	 * @param user The given {@link BlogUser}.
	 * @return {@code List} of all blog entries belonging to the given {@link BlogUser}.
	 * @throws DAOException If an error occurs.
	 */
	List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException;
	
	/**
	 * Registers the given {@link BlogUser} into the database.
	 * 
	 * @param user Reference to the given {@link BlogUser}.
	 * @throws DAOException If an error occurs.
	 */
	void registerBlogUser(BlogUser user) throws DAOException;
	
	/**
	 * Registers the given {@link BlogEntry} into the database.
	 * 
	 * @param entry Reference to the given {@link BlogEntry}.
	 * @throws DAOException If an error occurs.
	 */
	void registerBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Registers the given {@link BlogComment} into the database.
	 * 
	 * @param comment Reference to the given {@link BlogComment}
	 * @throws DAOException If an error occurs.
	 */
	void registerBlogComment(BlogComment comment) throws DAOException;
}