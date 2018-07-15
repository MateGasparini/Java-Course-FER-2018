package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program which displays the {@link RayTracerViewer}'s predefined scene
 * with each pixel's color calculated using the light sources and
 * intersections with graphical objects (in this case, multiple spheres).<br>
 * Also, it uses the Fork-join framework and {@code RecursiveAction}
 * for parallelization.
 * 
 * @author Mate Gasparini
 */
public class RayCasterParallel {
	
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
	 * {@code IRayTracerResultObserver} provided in the {@code produce} method.<br>
	 * It, however, does this by using fork-join parallelization across pixel
	 * groups of 16 lines.
	 * 
	 * @return Implemented {@code IRayTracerProducer} which calculates
	 * 			color values for each pixel.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			/* SHARED ATTRIBUTES */
			
			/**
			 * Width of the screen (in pixels).
			 */
			private int width;
			/**
			 * Height of the screen (in pixels).
			 */
			private int height;
			/**
			 * Horizontal length of the view.
			 */
			private double horizontal;
			/**
			 * Vertical length of the view.
			 */
			private double vertical;
			/**
			 * The specified scene.
			 */
			private Scene scene;
			/**
			 * The specified eye position.
			 */
			private Point3D eye;
			/**
			 * The left-upper corner (0,0).
			 */
			private Point3D screenCorner;
			/**
			 * The x axis.
			 */
			private Point3D xAxis;
			/**
			 * The y axis.
			 */
			private Point3D yAxis;
			/**
			 * The array of red pixel levels.
			 */
			private short[] red;
			/**
			 * The array of green pixel levels.
			 */
			private short[] green;
			/**
			 * The array of blue pixel levels.
			 */
			private short[] blue;
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				
				this.width = width;
				this.height = height;
				this.eye = eye;
				this.horizontal = horizontal;
				this.vertical = vertical;
				
				red = new short[width*height];
				green = new short[width*height];
				blue = new short[width*height];
				
				Point3D og = view.copy().modifySub(eye).modifyNormalize();
//				Point3D zAxis = og.negate(); // Not used.
				Point3D vuv = viewUp.copy().modifyNormalize();
				yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv)));
				xAxis = og.vectorProduct(yAxis);
				
				screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
										.modifyAdd(yAxis.scalarMultiply(vertical / 2.0)
				);
				
				scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculateTask(0, height-1));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
			
			/**
			 * A {@code RecursiveAction} which, when {@code compute} is called,
			 * either divides into two actions, or (if threshold is reached)
			 * computes the pixels color data.
			 * 
			 * @author Mate Gasparini
			 */
			class CalculateTask extends RecursiveAction {
				
				/**
				 * Default serial version ID.
				 */
				private static final long serialVersionUID = 1L;
				
				/**
				 * The specified starting y position.
				 */
				private int yMin;
				/**
				 * The specified ending y position.
				 */
				private int yMax;
				
				/**
				 * When yMax-yMin+1 falls below (<=) this threshold,
				 * the data is computed directly
				 */
				private static final int THRESHOLD = 16;
				
				/**
				 * Constructor specifying the starting and the ending
				 * y positions.
				 * 
				 * @param yMin The starting y position.
				 * @param yMax The ending y position.
				 */
				public CalculateTask(int yMin, int yMax) {
					this.yMin = yMin;
					this.yMax = yMax;
				}
				
				@Override
				protected void compute() {
					if (yMax-yMin+1 <= THRESHOLD) {
						computeDirect();
					} else {
						invokeAll(
							new CalculateTask(yMin, yMin+(yMax-yMin)/2),
							new CalculateTask(yMin+(yMax-yMin)/2+1, yMax)
						);
					}
				}
				
				/**
				 * Computes the color data and fills the red, green and blue
				 * arrays accordingly.
				 */
				private void computeDirect() {
					short[] rgb = new short[3];
					int offset = yMin*width;
					for (int y = yMin; y <= yMax; y ++) {
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
				}
			}
		};
	}
}
