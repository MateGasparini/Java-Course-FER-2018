package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program which displays the {@link RayTracerViewer}'s predefined scene
 * with each pixel's color calculated using the light sources and
 * intersections with graphical objects (in this case, multiple spheres).
 * 
 * @author Mate Gasparini
 */
public class RayCaster {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(
			getIRayTracerProducer(),
			new Point3D(10, 0, 0),
			new Point3D(0, 0, 0),
			new Point3D(0, 0, 10),
			20, 20
		);
	}
	
	/**
	 * Returns an implementation of the {@link IRayTracerProducer} interface
	 * that needs to be passed to the {@link RayTracerViewer#show(IRayTracerProducer)}
	 * method.<br>
	 * It fills three arrays of short color values and passes them to the
	 * {@code IRayTracerResultObserver} provided in the {@code produce} method.
	 * 
	 * @return Implemented {@code IRayTracerProducer} which calculates
	 * 			color values for each pixel.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D og = view.copy().modifySub(eye).modifyNormalize();
//				Point3D zAxis = og.negate(); // Not used.
				Point3D vuv = viewUp.copy().modifyNormalize();
				Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv)));
				Point3D xAxis = og.vectorProduct(yAxis);
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
										.modifyAdd(yAxis.scalarMultiply(vertical / 2.0)
				);
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y ++) {
					for (int x = 0; x < width; x ++) {
						Point3D screenPoint = screenCorner
							.add(xAxis.scalarMultiply(x * horizontal / (width - 1)))
							.modifySub(yAxis.scalarMultiply(y * vertical / (height - 1))
						);
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						RayTracerHelper.tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset ++;
					}
				}
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
}
