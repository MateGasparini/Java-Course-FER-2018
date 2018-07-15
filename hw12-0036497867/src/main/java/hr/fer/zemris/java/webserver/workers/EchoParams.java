package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} that generates HTML content containing a table
 * filled with context's parameter name-value pairs.
 * 
 * @author Mate Gasparini
 */
public class EchoParams implements IWebWorker {
	
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		StringBuilder sb = new StringBuilder(
			"<html><head><title>EchoParams</title></head><body>"
		);
		
		Set<String> parameterNames = context.getParameterNames();
		if (parameterNames.isEmpty()) {
			sb.append("No parameters provided!");
		} else {
			sb.append("<table><thead><ttitle>Parameters Table</ttitle>")
				.append("<tr><th>Name</th><th>Value</th></tr></thead><tbody>");
			for (String parameterName : parameterNames) {
				String parameterValue = context.getParameter(parameterName);
				sb.append("<tr><td>").append(parameterName).append("</td>")
					.append("<td>").append(parameterValue).append("</td></tr>");
			}
			sb.append("</tbody></table>");
		}
		sb.append("</body></html>");
		context.write(sb.toString());
	}
}
