package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Special kind of {@link LocalizationProviderBridge} which
 * has a bound to some {@link JFrame}, so that when the frame
 * is opened, a connection is established, and when the frame
 * is closed, it is disconnected.
 * 
 * @author Mate Gasparini
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor specifying the parent provider and the {@code JFrame}.<br>
	 * It registers a {@link WindowListener} to the specified frame
	 * which manages the connection in sync with window opening/closing.
	 * 
	 * @param parent The specified parent provider.
	 * @param frame The specified {@code JFrame}.
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
}
