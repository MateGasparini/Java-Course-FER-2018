package hr.fer.zemris.java.raytracer.model;

/**
 * {@code GraphicalObject} representing a sphere specified by its center
 * and its radius.<br>
 * Also, all light diffusion and reflection coefficients for the sphere
 * material are specified.
 * 
 * @author Mate Gasparini
 */
public class Sphere extends GraphicalObject {
	
	/**
	 * Center point of the sphere.
	 */
	private Point3D center;
	/**
	 * Length of the sphere's radius.
	 */
	private double radius;
	/**
	 * Red color diffusion coefficient.
	 */
	private double kdr;
	/**
	 * Green color diffusion coefficient.
	 */
	private double kdg;
	/**
	 * Blue color diffusion coefficient.
	 */
	private double kdb;
	/**
	 * Red color reflection coefficient.
	 */
	private double krr;
	/**
	 * Green color reflection coefficient.
	 */
	private double krg;
	/**
	 * Blue color reflection coefficient.
	 */
	private double krb;
	/**
	 * The n factor of reflection.
	 */
	private double krn;
	
	/**
	 * Constructor specifying all needed information.
	 * 
	 * @param center Center point of the sphere.
	 * @param radius Length of the sphere's radius.
	 * @param kdr Red color diffusion coefficient.
	 * @param kdg Green color diffusion coefficient.
	 * @param kdb Blue color diffusion coefficient.
	 * @param krr Red color reflection coefficient.
	 * @param krg Green color reflection coefficient.
	 * @param krb Blue color reflection coefficient.
	 * @param krn The n factor of reflection.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb,
			double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D oc = ray.start.sub(center);
		
		double loc = ray.direction.scalarProduct(oc);
		double ocNorm = oc.norm();
		
		double radicand = loc*loc - ocNorm*ocNorm + radius*radius;
		
		if (radicand < 0.0) {
			return null;
		}
		
		double distance = - loc - Math.sqrt(radicand);
		Point3D intersection = ray.start.add(ray.direction.scalarMultiply(distance));
		
		return new RayIntersection(intersection, distance, true) {
			
			@Override
			public Point3D getNormal() {
				return intersection.sub(center).modifyNormalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
}
