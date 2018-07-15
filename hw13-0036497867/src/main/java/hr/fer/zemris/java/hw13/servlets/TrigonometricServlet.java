package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by getting two parameters
 * (starting and ending angle in degrees) and generating a {@code List} of
 * {@link ValueTriplet}s for each angle between the parameters. After that,
 * an attribute is set to the generated {@code List} and the request is
 * forwarded to the corresponding JSP file (trigonometric.jsp).<br>
 * If the values are not found or invalid, default  values ({@code DEFAULT_A}
 * and {@code DEFAULT_B}) are used.
 * 
 * @author Mate Gasparini
 */
public class TrigonometricServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Default starting angle value. */
	public static final int DEFAULT_A = 0;
	
	/** Default ending angle value. */
	public static final int DEFAULT_B = 360;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int a = getValueFromParameter(req.getParameter("a"), DEFAULT_A);
		int b = getValueFromParameter(req.getParameter("b"), DEFAULT_B);
		
		if (a > b) {
			int aCopy = a;
			a = b;
			b = aCopy;
		}
		if (b > a+720) {
			b = a+720;
		}
		
		List<ValueTriplet> triplets = new ArrayList<>(b-a+1);
		for (int angle = a; angle <= b; angle ++) {
			triplets.add(new ValueTriplet(angle));
		}
		req.setAttribute("triplets", triplets);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * Parses the given parameter as an integer, or returns the specified default value.
	 * 
	 * @param parameter The given parameter value.
	 * @param defaultValue The specified default value.
	 * @return A valid parameter value.
	 */
	private int getValueFromParameter(String parameter, int defaultValue) {
		int value = defaultValue;
		if (parameter != null) {
			try {
				value = Integer.parseInt(parameter);
			} catch (NumberFormatException ignorable) {}
		}
		return value;
	}
	
	/**
	 * Class containing an angle in degrees and its sine and cosine values.
	 * 
	 * @author Mate Gasparini
	 */
	public static class ValueTriplet {
		
		/** Integer angle in degrees. Contains the degree symbol. */
		private String angle;
		
		/** Formatted sine value of the angle. */
		private String sin;
		
		/** Formatted cosine value of the angle. */
		private String cos;
		
		/**
		 * Constructor specifying the angle.
		 * 
		 * @param angle The specified angle.
		 */
		public ValueTriplet(int angle) {
			this.angle = String.format("%d\u00b0", angle); // With degree symbol.
			this.sin = String.format("%.4f", Math.sin(Math.toRadians(angle)));
			this.cos = String.format("%.4f", Math.cos(Math.toRadians(angle)));
		}
		
		/**
		 * Returns the angle (with the degree symbol included).
		 * 
		 * @return The angle.
		 */
		public String getAngle() {
			return angle;
		}
		
		/**
		 * Returns the sine value of the specified angle.
		 * 
		 * @return The sine value.
		 */
		public String getSin() {
			return sin;
		}
		
		/**
		 * Returns the cosine value of the specified angle.
		 * 
		 * @return The cosine value.
		 */
		public String getCos() {
			return cos;
		}
	}
}
