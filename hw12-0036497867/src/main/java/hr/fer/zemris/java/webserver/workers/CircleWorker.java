package hr.fer.zemris.java.webserver.workers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} that creates a {@link BufferedImage} with a simple circle
 * shape, converts it to a byte array and writes it to the given context
 * as a PNG image.
 * 
 * @author Mate Gasparini
 */
public class CircleWorker implements IWebWorker {
	
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = bim.createGraphics();
		g2d.fillOval(0, 0, 200, 200);
		g2d.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.setMimeType("image/png");
			context.write(bos.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
