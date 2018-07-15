package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Special kind of {@link JMenu} which accordingly translates its text
 * when the specified provider's localization changes.
 * 
 * @author Mate Gasparini
 */
public class LJMenu extends JMenu {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Specified key. */
	private String key;
	
	/** Specified localization provider. */
	private ILocalizationProvider provider;
	
	/**
	 * Constructor specifying the key and the localization provider.
	 * 
	 * @param key The specified key.
	 * @param provider The specified localization provider.
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		super();
		this.key = key;
		this.provider = provider;
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				translate();
			}
		});
		translate();
	}
	
	/**
	 * Translates the menu's text.
	 */
	private void translate() {
		setText(provider.getString(key));
	}
}
