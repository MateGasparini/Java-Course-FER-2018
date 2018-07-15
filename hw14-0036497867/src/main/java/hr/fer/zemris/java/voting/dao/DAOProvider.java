package hr.fer.zemris.java.voting.dao;

import hr.fer.zemris.java.voting.dao.sql.SQLDAO;

/**
 * <i>Singleton</i> class which can return some concrete {@link DAO} implementation.
 * 
 * @author Mate Gasparini
 */
public class DAOProvider {
	
	/** Reference to a single implemented {@link DAO} object. */
	private static DAO dao = new SQLDAO();
	
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
	public static DAO getDao() {
		return dao;
	}
}
