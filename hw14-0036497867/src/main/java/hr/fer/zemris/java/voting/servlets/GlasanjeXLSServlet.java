package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.voting.model.PollOption;

/**
 * {@link HttpServlet} which handles GET requests by loading poll results (specified
 * by the given <i>pollID</i> URL parameter) from the DB.<br>
 * If there are results present, an XLS file is created, filled with the results
 * and its binary content is written to the {@link HttpServletResponse} output stream.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<PollOption> options = GlasanjeUtil.getPollResults(req);
		if (options == null) {
			req.getRequestDispatcher(GlasanjeUtil.ERROR_JSP).forward(req, resp);
			return;
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati");
		
		HSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("Naziv");
		rowHead.createCell(1).setCellValue("Broj glasova");
		
		for (int i = 1, size = options.size(); i <= size; i ++) {
			HSSFRow row = sheet.createRow(i);
			PollOption option = options.get(i-1);
			row.createCell(0).setCellValue(option.getTitle());
			row.createCell(1).setCellValue(option.getVotesCount());
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"results.xls\"");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
