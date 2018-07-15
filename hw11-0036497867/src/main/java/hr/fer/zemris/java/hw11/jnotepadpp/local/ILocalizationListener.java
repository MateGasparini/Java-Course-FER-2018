package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface used as a <i>Listener</i> when localization (language used
 * by the application) changes.
 * 
 * @author Mate Gasparini
 */
public interface ILocalizationListener {
	
	/**
	 * Called when the language of the application changes.
	 */
	void localizationChanged();
}
