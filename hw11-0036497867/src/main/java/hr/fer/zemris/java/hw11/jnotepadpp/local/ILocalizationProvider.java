package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface which is able to translate given keys to corresponding
 * translations, as well as register and de-register given
 * {@link ILocalizationListener}s.
 * 
 * @author Mate Gasparini
 */
public interface ILocalizationProvider {
	
	/**
	 * Registers the given listener.
	 * 
	 * @param listener The given listener.
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * De-registers the given listener.
	 * 
	 * @param listener The given listener.
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Returns a translation for the given key.
	 * 
	 * @param key The given key.
	 * @return The corresponding translation.
	 */
	String getString(String key);
}
