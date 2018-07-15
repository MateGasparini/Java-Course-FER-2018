package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Special kind of {@link JToolBar} which accordingly translates its name
 * when the specified provider's localization changes.
 * 
 * @author Mate Gasparini
 */
public class LJToolBar extends JToolBar {
	
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
	public LJToolBar(String key, ILocalizationProvider provider) {
		super(provider.getString(key));
		this.key = key;
		this.provider = provider;
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				translate();
			}
		});
	}
	
	/**
	 * Translates the tool bar's name.
	 */
	private void translate() {
		/* Does not really work as expected when tested.
		 * It translates the title only once, and only if it
		 * was docked when the localization change occurred.
		 * I kept this class, but it is not really needed
		 * unless fix found. If you find it, please inform me.
		 * 
		 * - Mate Gašparini */
		setName(provider.getString(key));
	}
}
