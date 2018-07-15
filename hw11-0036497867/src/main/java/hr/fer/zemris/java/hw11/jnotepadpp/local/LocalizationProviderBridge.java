package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Decorator for an {@link ILocalizationProvider} which offers
 * methods for managing a connection status and solves the memory leak
 * problem caused by the {@link LocalizationProvider} holding references
 * to the {@link ILocalizationListener}s.
 * 
 * @author Mate Gasparini
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	/** Connection status. */
	private boolean connected;
	
	/** Provider decorated by this bridge. */
	private ILocalizationProvider parent;
	
	/** Listener which registers to the parent. */
	private ILocalizationListener listener;
	
	/**
	 * Constructor specifying the decorated parent provider.
	 * 
	 * @param parent The specified parent provider.
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.listener = () -> fire();
	}
	
	/**
	 * Disconnects the listener from the parent provider.
	 */
	public void disconnect() {
		if (!connected) return;
		parent.removeLocalizationListener(listener);
		connected = false;
	}
	
	/**
	 * Connects the listener to the parent provider.
	 */
	public void connect() {
		if (connected) return;
		parent.addLocalizationListener(listener);
		connected = true;
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}
}
