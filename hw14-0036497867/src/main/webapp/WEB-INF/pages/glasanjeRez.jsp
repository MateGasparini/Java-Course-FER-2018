<%@page import="hr.fer.zemris.java.voting.model.PollOption"%>
<%@page import="java.util.List"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
		table.rez td {text-align: center;}
	</style>
	<title>Rezultati glasanja</title>
</head>
<body>
	<p>
		<a href="index.html">Povratak</a>
	</p>
	<h1>Rezultati glasanja</h1>
	<p>
		Ovo su rezultati glasanja.
	</p>
	<table border="1" class="rez">
		<thead><tr><th>Naziv</th><th>Broj glasova</th></tr></thead>
		<tbody>
			<c:forEach var="option" items="${options}">
				<tr><th>${option.title}</th><th>${option.votesCount}</th></tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart"
			src="glasanje-grafika?pollID=<%= request.getParameter("pollID") %>"
			width="500" height="300" />
	
	<h2>Rezultati u XLS formatu</h2>
	<p>Rezultati u XLS formatu dostupni su <a
		href="glasanje-xls?pollID=<%= request.getParameter("pollID") %>">ovdje</a>.
	</p>
	
	<h2>Razno</h2>
	<p>Linkovi na primjere pobjednika:</p>
	<ul><%
		@SuppressWarnings("unchecked")
		List<PollOption> options = (List<PollOption>) request.getAttribute("options");
		long highestScore = options.get(0).getVotesCount();
		for (int i = 0, size = options.size(); i < size; i ++) {
			PollOption option = options.get(i);
			long score = option.getVotesCount();
			if (score != highestScore) {
				break;
			}
			String title = option.getTitle();
			String link = option.getLink();
			out.write("<li><a href=\""+link+"\" target=\"_blank\">"+title+"</a></li>");
		}
	%>
	</ul>
</body>
</html>