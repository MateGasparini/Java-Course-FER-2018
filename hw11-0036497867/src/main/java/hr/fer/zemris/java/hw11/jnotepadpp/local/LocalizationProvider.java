package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class extending {@link AbstractLocalizationProvider}
 * functionality with the {@link ILocalizationProvider#getString(String)}
 * method.<br>
 * It holds the current language name abbreviation (e.g. "en", "hr", "de")
 * and a reference to the corresponding {@link ResourceBundle}.
 * 
 * @author Mate Gasparini
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	/** Only instance of the singleton. */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/** Current language used (abbreviation). */
	private String language;
	
	/** Resource bundle for the current language. */
	private ResourceBundle bundle;
	
	/**
	 * Private default constructor. Sets the language to English.
	 */
	private LocalizationProvider() {
		language = "en";
		setBundle();
	}
	
	/**
	 * Returns the singleton's instance.
	 * 
	 * @return The singleton's instance.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Returns the currently used language.
	 * 
	 * @return The current language.
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Sets the current language to the given language
	 * and notifies all {@link ILocalizationListener}s.
	 * 
	 * @param language The given language's abbreviation.
	 */
	public void setLanguage(String language) {
		this.language = language;
		setBundle();
		fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * Sets the bundle for the current language.
	 */
	private void setBundle() {
		bundle = ResourceBundle.getBundle(
			"hr.fer.zemris.java.hw11.jnotepadpp.local.translations",
			Locale.forLanguageTag(language)
		);
	}
}
