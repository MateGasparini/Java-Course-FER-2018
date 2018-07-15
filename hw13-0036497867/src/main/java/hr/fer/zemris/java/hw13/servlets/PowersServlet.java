package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * {@link HttpServlet} which handles GET requests by getting three parameters
 * from the {@link HttpServletRequest}, constructs an XLS file and writes it
 * to the {@link HttpServletResponse} output stream.<br>
 * The XLS workbook contains {@code n} sheets. On sheet {@code i} two columns
 * are displayed - the first of which contains values from {@code a} to {@code b}
 * and the second contains {@code i}-th powers of these values.
 * 
 * @author Mate Gasparini
 */
public class PowersServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Minimum value for both a and b. */
	private static final int MIN_A_B = -100;
	
	/** Maximum value for both a and b. */
	private static final int MAX_A_B = 100;
	
	/** Minimum value for n. */
	private static final int MIN_N = 1;
	
	/** Maximum value for n. */
	private static final int MAX_N = 5;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int a;
		int b;
		int n;
		
		try {
			a = getValueFromParameter(req, "a");
			b = getValueFromParameter(req, "b");
			n = getValueFromParameter(req, "n");
		} catch (IllegalArgumentException ex) {
			req.getSession().setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/powerserror.jsp").forward(req, resp);
			return;
		}
		
		if (a > b) {
			int aCopy = a;
			a = b;
			b = aCopy;
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int sheetIndex = 1; sheetIndex <= n; sheetIndex ++) {
			HSSFSheet sheet = hwb.createSheet(String.valueOf(sheetIndex));
			for (int value = a; value <= b; value ++) {
				HSSFRow row = sheet.createRow(value-a);
				row.createCell(0).setCellValue(value);
				row.createCell(1).setCellValue(Math.pow(value, sheetIndex));
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		String filename = "table_"+a+"_"+b+"_"+n+".xls";
		resp.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
	
	/**
	 * Gets the parameter from the given HTTP request specified by the given
	 * parameter name and returns it if it is a valid integer.<br>
	 * Otherwise, a runtime exception is thrown.
	 * 
	 * @param req The given HTTP request.
	 * @param name The given parameter name.
	 * @return The corresponding parameter value (if valid).
	 * @throws IllegalArgumentException If the corresponding value is not a valid integer,
	 * 			or if the corresponding parameter could not be found.
	 */
	private int getValueFromParameter(HttpServletRequest req, String name)
			throws IllegalArgumentException {
		String parameter = req.getParameter(name);
		if (parameter != null) {
			try {
				int value = Integer.parseInt(parameter);
				checkValue(name, value);
				return value;
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException(
					"Value '"+parameter+"' is not a valid integer.");
			}
		}
		throw new IllegalArgumentException("Value for '"+name+"' must be provided.");
	}
	
	/**
	 * Checks the given value for the given parameter name.
	 * 
	 * @param name The given parameter name.
	 * @param value The given parameter value.
	 * @throws IllegalArgumentException If the given value is outside
	 * 			the allowed parameter values.
	 */
	private void checkValue(String name, int value) throws IllegalArgumentException {
		if (name.equals("a") || name.equals("b")) {
			checkValue(name, value, MIN_A_B, MAX_A_B);
		} else if (name.equals("n")) {
			checkValue(name, value, MIN_N, MAX_N);
		}
	}
	
	/**
	 * Checks the given parameter value using the given bounds.
	 * 
	 * @param name The given parameter name (used for exception messages).
	 * @param value The given parameter value.
	 * @param minValue The given lower bound value.
	 * @param maxValue The given higher bound value.
	 * @throws IllegalArgumentException If the given value is outside
	 * 			the specified parameter bounds.
	 */
	private void checkValue(String name, int value, int minValue, int maxValue)
			throws IllegalArgumentException{
		if (value < minValue || value > maxValue) {
			throw new IllegalArgumentException(
				"Value of '"+name+"' is out of bounds. "+
				"Was "+value+", but should be from ["+minValue+","+maxValue+"].");
		}
	}
}
