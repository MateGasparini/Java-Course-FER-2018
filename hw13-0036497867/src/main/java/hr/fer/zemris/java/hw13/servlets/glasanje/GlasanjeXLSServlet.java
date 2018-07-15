package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * {@link HttpServlet} which handles GET requests by loading band voting results
 * from disk, creates a XLS file filled with it and writing its binary content
 * to the {@link HttpServletResponse} output stream.
 * 
 * @author Mate Gasparini
 */
public class GlasanjeXLSServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Band> bands;
		try {
			bands = GlasanjeUtil.loadSortedResults(
				req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"),
				req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt")
			);
		} catch (IllegalArgumentException | IOException ex) {
			resp.sendError(500, "Internal server error");
			ex.printStackTrace();
			return;
		}
		
		if (bands == null || bands.isEmpty()) {
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeGreska.jsp").forward(req, resp);
			return;
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati");
		
		HSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("Name");
		rowHead.createCell(1).setCellValue("ID");
		rowHead.createCell(2).setCellValue("Score");
		
		for (int i = 1, size = bands.size(); i <= size; i ++) {
			HSSFRow row = sheet.createRow(i);
			Band band = bands.get(i-1);
			row.createCell(0).setCellValue(band.getName());
			row.createCell(1).setCellValue(band.getId());
			row.createCell(2).setCellValue(band.getScore());
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"band_results.xls\"");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
