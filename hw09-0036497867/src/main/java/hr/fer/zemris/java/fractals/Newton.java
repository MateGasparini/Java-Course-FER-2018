package hr.fer.zemris.java.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program which draws a fractal image (using {@link FractalViewer})
 * derived from Newton-Raphson iteration.<br>
 * Firstly, the user is prompted for input. After two or more complex numbers
 * are entered, a complex polynomial is generated using these numbers as
 * polynomial roots.<br>
 * From this polynomial, a derived polynomial is also generated, and
 * the program draws the corresponding fractal.
 * 
 * @author Mate Gasparini
 */
public class Newton {
	
	/**
	 * The specified polynomial.
	 */
	private static ComplexPolynomial polynomial;
	/**
	 * First derivation of the specified polynomial.
	 */
	private static ComplexPolynomial derived;
	/**
	 * The specified polynomial in root form.
	 */
	private static ComplexRootedPolynomial rooted;
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println(
			"Please enter at least two roots, one root per line. Enter '" + DONE + "' when done."
		);
		
		List<Complex> rootsList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			int currentIndex = 1;
			while (true) {
				System.out.print(ROOT + currentIndex + PROMPT);
				
				String line = reader.readLine();
				if (line.equals(DONE)) {
					if (currentIndex <= 2) {
						System.out.println("At least two roots expected.");
						continue;
					}
					break;
				}
				
				try {
					Complex inputNumber = parseComplex(line);
					rootsList.add(inputNumber);
					
					currentIndex ++;
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (IOException ex) {
			System.out.println("An error occured while reading from stdin.");
			return;
		}
		
		rooted = new ComplexRootedPolynomial(rootsList.toArray(new Complex[0]));
		polynomial = rooted.toComplexPolynom();
		derived = polynomial.derive();
		
		FractalViewer.show(new FractalProducer());
	}
	
	private static Complex parseComplex(String s) {
		s = s.replaceAll("\\s+", ""); // Remove all whitespaces.
		
		double real = 0.0;
		double imaginary = 0.0;
		
		Matcher abiMatcher = A_BI.matcher(s);
		if (abiMatcher.matches()) {
			try {
				real = Double.parseDouble(abiMatcher.group(1));
				imaginary = Double.parseDouble(abiMatcher.group(3));
				if (abiMatcher.group(2).equals(MINUS)) {
					imaginary = -imaginary;
				}
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException(
					"Given number is of type a+ib, but not parseable."
				);
			}
		} else {
			Matcher aMatcher = A.matcher(s);
			if (aMatcher.matches()) {
				try {
					real = Double.parseDouble(s);
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(
						"Given number is of type a, but not parseable."
					);
				}
			} else {
				Matcher biMatcher = BI.matcher(s);
				if (biMatcher.matches()) {
					try {
						if (biMatcher.groupCount() == 3) {
							imaginary = Double.parseDouble(biMatcher.group(2));
						} else {
							imaginary = 1.0;
						}
						if (s.startsWith(MINUS)) {
							imaginary = -imaginary;
						}
					} catch (NumberFormatException ex) {
						throw new IllegalArgumentException(
							"Given number is of type ib, but not parseable."
						);
					}
				} else {
					throw new IllegalArgumentException("Given number not of format a+ib.");
				}
			}
		}
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * A {@code Callable} which, when called, fills the data array
	 * with color information depending on the Newton-Raphson
	 * convergence of the corresponding complex number/coordinate
	 * (it fills it only partially from {@code ymin} to {@code ymax}).
	 * 
	 * @author Mate Gasparini
	 */
	private static class CalculateTask implements Callable<Void> {
		
		/**
		 * The smallest real part.
		 */
		private double reMin;
		/**
		 * The largest real part.
		 */
		private double reMax;
		/**
		 * The smallest imaginary part.
		 */
		private double imMin;
		/**
		 * The largest imaginary part.
		 */
		private double imMax;
		/**
		 * The specified width in pixels.
		 */
		private int width;
		/**
		 * The specified height in pixels.
		 */
		private int height;
		/**
		 * Maximum number of iterations.
		 */
		private int m;
		/**
		 * The starting y position.
		 */
		private int ymin;
		/**
		 * The ending y position.
		 */
		private int ymax;
		/**
		 * Data array filled with color information.
		 */
		private short[] data;
		
		/**
		 * If |z1-zn| is smaller than this constant, zn converges.
		 */
		private static final double CONVERGENCE_TRESHOLD = 1E-3;
		/**
		 * Threshold for finding the index of the closest polynomial root.
		 */
		private static final double ROOT_CLOSENESS_ThRESHOLD = 1E-3;
		
		/**
		 * Constructor specifying all needed information.
		 * 
		 * @param reMin Smallest real part.
		 * @param reMax Largest real part.
		 * @param imMin Smallest imaginary part.
		 * @param imMax Largest imaginary part.
		 * @param width Specified width in pixels.
		 * @param height Specified height in pixels.
		 * @param m Maximum number of iterations.
		 * @param ymin Starting y position.
		 * @param ymax Ending y position.
		 * @param data Data array containing color information.
		 */
		public CalculateTask(double reMin, double reMax, double imMin, double imMax,
				int width, int height, int m, int ymin, int ymax, short[] data) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.m = m;
			this.ymin = ymin;
			this.ymax = ymax;
			this.data = data;
		}
		
		@Override
		public Void call() throws Exception {
			int offset = ymin*width;
			
			for (int y = ymin; y <= ymax; y ++) {
				for (int x = 0; x < width; x ++) {
					Complex zn = mapToComplex(x, y);
					int iter = 0;
					double module;
					
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						Complex zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						
						iter ++;
					} while (module > CONVERGENCE_TRESHOLD && iter < m);
					
					int index = rooted.indexOfClosestRootFor(zn, ROOT_CLOSENESS_ThRESHOLD);
					if (index == -1) {
						data[offset ++] = 0;
					} else {
						data[offset ++] = (short) (index+1);
					}
				}
			}
			
			return null;
		}
		
		/**
		 * Maps the given coordinates to the corresponding complex number.
		 * 
		 * @param x The given x coordinate.
		 * @param y The given y coordinate.
		 * @return The corresponding complex number.
		 */
		private Complex mapToComplex(double x, double y) {
			return new Complex(
				x / (width - 1.0) * (reMax - reMin) + reMin,
				(height - 1.0 - y) / (height - 1.0) * (imMax - imMin) + imMin
			);
		}
	}
	
	/**
	 * Creates a daemon thread pool, divides the data (the picture)
	 * into 8*n horizontal strips, where n is equal to 8 times the
	 * number of available processors.<br>
	 * When the data array is filled with correct color values,
	 * it is submitted to the specified fractal result observer.
	 * 
	 * @author Mate Gasparini
	 */
	private static class FractalProducer implements IFractalProducer {
		
		/**
		 * Thread pool of daemon worker threads.
		 */
		private ExecutorService pool = Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread worker = new Thread(r);
					worker.setDaemon(true);
					return worker;
				}
			}
		);
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer) {
			short[] data = new short[width*height];
			final int m = 16*16*16;
			final int numberOfStrips = 8*Runtime.getRuntime().availableProcessors();
			int linesPerStrip = height / numberOfStrips;
			
			List<Future<Void>> results = new ArrayList<>();
			
			for (int i = 0; i < numberOfStrips; i ++) {
				int yMin = i * linesPerStrip;
				int yMax = (i+1) * linesPerStrip - 1;
				
				if (i == numberOfStrips - 1) {
					yMax = height - 1;
				}
				
				CalculateTask task = new CalculateTask(
					reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data
				);
				results.add(pool.submit(task));
			}
			
			for (Future<Void> task : results) {
				try {
					task.get();
				} catch (InterruptedException | ExecutionException ex) {
					// Continue.
				}
			}
			
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
	
	/* STRING CONSTANTS */
	private static final String DONE = "done";
	private static final String ROOT = "Root ";
	private static final String PROMPT = "> ";
	private static final String MINUS = "-";
	
	/* COMPLEX NUMBER PARSER PATTERNS */
	private static final Pattern A_BI = Pattern.compile(
		"(-?[0-9]+\\.?[0-9]*)(\\+|-)i(-?[0-9]+\\.?[0-9]*)"
	);
	private static final Pattern A = Pattern.compile(
		"-?[0-9]+\\.?[0-9]*"
	);
	private static final Pattern BI = Pattern.compile(
		"(-?)i([0-9]+\\.?[0-9]*)?"
	);
}
