package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Helper class that contains all needed methods for filling
 * the data array used for scene picture generation in
 * {@code RayCaster} and {@code RayCasterParallel} programs.
 * 
 * @author Mate Gasparini
 */
public class RayTracerHelper {
	
	/**
	 * Small constant used for distinguishing double number equality.
	 */
	private static final double EPSILON = 1E-3;
	/**
	 * Default ambient component for red, green and blue color.
	 */
	private static final short AMBIENT = 15;
	
	/**
	 * Fills the given array with red, green and blue color values
	 * for the pixel corresponding to the given ray in the given scene.
	 * 
	 * @param scene The given scene.
	 * @param ray The given ray.
	 * @param rgb The given array of short values.
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		
		RayIntersection closest = findClosestIntersection(scene, ray);
		
		if (closest == null) {
			return;
		}
		
		determineColorFor(closest, rgb, scene, ray);
	}
	
	/**
	 * Returns the closest {@code RayIntersection} of the given ray
	 * with the objects from the given scene.<br>
	 * If none is found, {@code null} is returned.
	 * 
	 * @param scene The given scene.
	 * @param ray The given ray.
	 * @return Closest ray intersection, or null if the ray does not
	 * 			intersect anything in the given scene.
	 */
	protected static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();
		
		RayIntersection closest = null;
		for (GraphicalObject object : objects) {
			RayIntersection current = object.findClosestRayIntersection(ray);
			
			if (current == null) {
				continue;
			} else if (closest == null || current.getDistance() < closest.getDistance()) {
				closest = current;
			}
		}
		
		return closest;
	}
	
	/**
	 * Calculates the color (by using ambient, diffuse and reflective light
	 * components) of the pixel specified by the given object intersection
	 * and fills the given array with RGB values.
	 * 
	 * @param objectIntersection The given object intersection.
	 * @param rgb The array of short values.
	 * @param scene The given scene containing light sources and objects.
	 * @param eyeRay Ray coming from the "eye".
	 */
	protected static void determineColorFor(RayIntersection objectIntersection,
			short[] rgb, Scene scene, Ray eyeRay) {
		rgb[0] = AMBIENT;
		rgb[1] = AMBIENT;
		rgb[2] = AMBIENT;
		
		List<LightSource> lights = scene.getLights();
		for (LightSource light : lights) {
			Point3D direction = objectIntersection.getPoint().sub(light.getPoint()).modifyNormalize();
			
			RayIntersection lightIntersection = findClosestIntersection(
				scene, new Ray(light.getPoint(), direction)
			);
			
			if (lightIntersection == null) {
				continue;
			}
			
			double lightDistance = lightIntersection.getDistance();
			double objectDistance = objectIntersection.getPoint().sub(light.getPoint()).norm();
			
			if (lightDistance + EPSILON < objectDistance) {
				continue;
			}
			
			Point3D n = lightIntersection.getNormal();
			double lnDot = direction.negate().scalarProduct(n);
			if (lnDot < 0.0) lnDot = 0.0;
			
			Point3D r = direction.sub(n.scalarMultiply(2*direction.scalarProduct(n)));
			double rvDot = r.scalarProduct(eyeRay.direction.negate());
			if (rvDot < 0.0) rvDot = 0.0;
			
			double krn = lightIntersection.getKrn();
			
			rgb[0] += diffuseAndReflectiveComponent(
				light.getR(), lightIntersection.getKdr(), lightIntersection.getKrr(),
				krn, lnDot, rvDot
			);
			rgb[1] += diffuseAndReflectiveComponent(
				light.getG(), lightIntersection.getKdg(), lightIntersection.getKrg(),
				krn, lnDot, rvDot
			);
			rgb[2] += diffuseAndReflectiveComponent(
				light.getB(), lightIntersection.getKdb(), lightIntersection.getKrb(),
				krn, lnDot, rvDot
			);
		}
	}
	
	/**
	 * Returns the short value that represents the sum of the diffuse and
	 * reflective component of some light source for some point.
	 * 
	 * @param intensity Some color intensity from some light source.
	 * @param kd Some color diffuse coefficient for some object.
	 * @param kr Some color reflective coefficient for some object.
	 * @param krn The n factor of reflection for some object.
	 * @param lnDot Dot product of the l and the n vector (see IRG book).
	 * @param rvDot Dot product of the r and the v vector (see IRG book).
	 * @return Sum of the calculated diffuse and reflective component.
	 */
	protected static short diffuseAndReflectiveComponent(double intensity,
			double kd, double kr, double krn,
			double lnDot, double rvDot) {
		// Formula 9.8 from IRG book.
		double result = intensity * (kd * lnDot + kr * Math.pow(rvDot, krn)) + EPSILON;
		return (short) result;
	}
}
