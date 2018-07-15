<%@page import="hr.fer.zemris.java.hw13.servlets.glasanje.Band"%>
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
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<p>
		<a href="glasanje">Glasaj ponovno</a><br>
		<a href="index.jsp">Povratak</a>
	</p>
	<h1>Rezultati glasanja</h1>
	<p>
		Ovo su rezultati glasanja.
	</p>
	<table border="1" cellspacing="0" class="rez">
		<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
		<tbody>
			<c:forEach var="band" items="${bands}">
				<tr><th>${band.name}</th><th>${band.score}</th></tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="500" height="300" />
	
	<h2>Rezultati u XLS formatu</h2>
	<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>.</p>
	
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul><%
		@SuppressWarnings("unchecked")
		List<Band> bands = (List<Band>) request.getAttribute("bands");
		int highestScore = bands.get(0).getScore(); // Because it is sorted.
		for (int i = 0, size = bands.size(); i < size; i ++) {
			Band band = bands.get(i);
			int score = band.getScore();
			if (score != highestScore) {
				break;
			}
			String name = band.getName();
			String link = band.getLink();
			out.write("<li><a href=\""+link+"\" target=\"_blank\">"+name+"</a></li>");
		}
	%>
	</ul>
</body>
</html>