package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by reading a color parameter from
 * the {@link HttpServletRequest} and (if it is a valid HEX color code) sets a
 * session attribute to its value. After that, the request is forwarded to the
 * corresponding JSP file (colors.jsp).<br>
 * If the color value is not found or if it is not valid, the {@code DEFAULT_COLOR_CODE}
 * is used instead.
 * 
 * @author Mate Gasparini
 */
public class SetColorServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Default color code, white HEX value. */
	public static final String DEFAULT_COLOR_CODE = "FFFFFF";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String colorCode = req.getParameter("color");
		req.getSession().setAttribute(
			"pickedBgCol",
			colorCode != null && colorCode.matches("[\\p{XDigit}]{6}") ?
			colorCode : DEFAULT_COLOR_CODE
		);
		req.getRequestDispatcher("/colors.jsp").forward(req, resp);
	}
}
