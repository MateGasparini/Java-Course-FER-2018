package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * <i>Singleton</i> class which can return some concrete {@link DAO} implementation.<br>
 * Currently set to return {@link JPADAOImpl}.
 * 
 * @author Mate Gasparini
 */
public class DAOProvider {
	
	/** Reference to a single implemented {@link DAO} object. */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Private default constructor.
	 */
	private DAOProvider() {
	}
	
	/**
	 * Returns the reference to the implemented {@link DAO} object.
	 * 
	 * @return Single {@link DAO} stored in this <i>Singleton</i>.
	 */
	public static DAO getDAO() {
		return dao;
	}
}