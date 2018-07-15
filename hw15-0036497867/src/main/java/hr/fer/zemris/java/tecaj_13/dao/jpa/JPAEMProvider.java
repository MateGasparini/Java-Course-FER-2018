package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * {@link EntityManager} provider.<br>
 * Should be set before accessing the database
 * and closed to commit the database transaction.
 * 
 * @author Mate Gasparini
 */
public class JPAEMProvider {
	
	/** Thread-local {@link EntityManager}, different for every thread. */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();
	
	/**
	 * Returns the {@link EntityManager} for the current thread (caller).<br>
	 * Possibly creates it first and begins the database transaction.
	 * 
	 * @return {@link EntityManager} reference.
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}
	
	/**
	 * Commits the {@link EntityManager}'s transaction, closes it and removes it
	 * from the {@link ThreadLocal}.
	 * 
	 * @throws DAOException If an error occurs.
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null) throw dex;
	}
}