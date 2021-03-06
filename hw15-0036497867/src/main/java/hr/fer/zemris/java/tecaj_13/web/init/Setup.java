package hr.fer.zemris.java.tecaj_13.web.init;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * {@link ServletContextListener} which initializes the {@link JPAEMFProvider}
 * when the web-application starts and closes it when it terminates.
 * 
 * @author Mate Gasparini
 */
@WebListener
public class Setup implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
									"baza.podataka.za.blog");  
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext()
									.getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}