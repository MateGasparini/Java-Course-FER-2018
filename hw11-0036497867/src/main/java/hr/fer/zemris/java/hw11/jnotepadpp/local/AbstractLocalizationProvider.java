package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link ILocalizationProvider} implementation which has the ability to
 * register, de-register and inform {@link ILocalizationListener}s.<br>
 * It does not implement the {@link ILocalizationProvider#getString(String)}
 * method.
 * 
 * @author Mate Gasparini
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	/** Internal list of listeners. */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		Objects.requireNonNull(listener, "Listener must not be null.");
		listeners.add(listener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Informs all listeners that the localization has changed.<br>
	 * This method is thread-safe.
	 */
	public void fire() {
		List<ILocalizationListener> copy = new ArrayList<>(listeners);
		copy.forEach(l -> l.localizationChanged());
	}
}
