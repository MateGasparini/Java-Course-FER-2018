package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * {@link EntityManagerFactory} provider.<br>
 * Should be set during servlet context initialization.
 * 
 * @author Mate Gasparini
 */
public class JPAEMFProvider {
	
	/** Stored {@link EntityManagerFactory} reference. */
	public static EntityManagerFactory emf;
	
	/**
	 * Returns the stored {@link EntityManagerFactory} reference.
	 * 
	 * @return The stored {@link EntityManagerFactory} reference.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Stores the given {@link EntityManagerFactory} reference.
	 * 
	 * @param emf The given {@link EntityManagerFactory} reference.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}