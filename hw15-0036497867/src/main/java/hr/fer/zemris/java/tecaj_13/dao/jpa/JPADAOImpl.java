package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * <i>Java persistence API</i> (JPA) implementation of the {@link DAO} interface
 * for accessing the database.
 * 
 * @author Mate Gasparini
 */
public class JPADAOImpl implements DAO {
	
	@Override
	public BlogEntry getBlogEntry(long id) throws DAOException {
		try {
			return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while finding blog entry.", ex);
		}
	}
	
	@Override
	public void registerBlogUser(BlogUser user) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(user);
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while registering blog user.", ex);
		}
	}
	
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		try {
			List<BlogUser> result = JPAEMProvider.getEntityManager()
					.createNamedQuery("BlogUser.nick", BlogUser.class)
					.setParameter("nick", nick).getResultList();
			if (result.isEmpty()) return null;
			return result.get(0);
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while finding blog user.", ex);
		}
	}
	
	@Override
	public BlogUser getBlogUser(long id) throws DAOException {
		try {
			return JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while finding blog user.", ex);
		}
	}
	
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		try {
			return JPAEMProvider.getEntityManager().createNamedQuery(
				"BlogUser.all", BlogUser.class).getResultList();
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while finding blog users.", ex);
		}
	}
	
	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {
		try {
			return JPAEMProvider.getEntityManager()
					.createNamedQuery("BlogEntry.creator", BlogEntry.class)
					.setParameter("creator", user).getResultList();
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while finding blog entries.", ex);
		}
	}
	
	@Override
	public void registerBlogEntry(BlogEntry entry) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(entry);
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while registering blog entry.", ex);
		}
	}
	
	@Override
	public void registerBlogComment(BlogComment comment) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(comment);
		} catch (Exception ex) {
			throw new DAOException("Problem occurred while registering blog comment.", ex);
		}
	}
}