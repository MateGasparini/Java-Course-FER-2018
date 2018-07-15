package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * Special kind of {@link AbstractAction} which adds the ability
 * of translating Action's text given the specified key and
 * specified {@link ILocalizationProvider}.
 * 
 * @author Mate Gasparini
 */
public abstract class LocalizableAction extends AbstractAction {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Properties file description suffix. */
	private static final String DESCRIPTION = "_desc";
	
	/** Properties file mnemonic suffix. */
	private static final String MNEMONIC = "_mnem";
	
	/** Specified key. */
	private String key;
	
	/** Specified key appended with the description suffix. */
	private String keyDescription;
	
	/** Specified key appended with the mnemonic suffix. */
	private String keyMnemonic;
	
	/** Specified localization provider. */
	private ILocalizationProvider provider;
	
	/** Listener which, when the provider's localization changes, translates the action. */
	private ILocalizationListener listener;
	
	/**
	 * Constructor specifying the key and the localization provider.
	 * 
	 * @param key The specified key.
	 * @param provider The specified localization provider.
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		this.keyDescription = key.concat(DESCRIPTION);
		this.keyMnemonic = key.concat(MNEMONIC);
		this.provider = provider;
		this.listener = () -> translate();
		provider.addLocalizationListener(listener);
		translate();
	}
	
	/**
	 * Translates action's text.
	 */
	private void translate() {
		putValue(NAME, provider.getString(key));
		putValue(SHORT_DESCRIPTION, provider.getString(keyDescription));
		putValue(MNEMONIC_KEY, (int) provider.getString(keyMnemonic).charAt(0));
	}
}
