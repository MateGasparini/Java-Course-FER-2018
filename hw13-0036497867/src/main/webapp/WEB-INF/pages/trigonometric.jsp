<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Trigonometric table</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<p>
		<a href="index.jsp">Back</a>
	</p>
	<h1>Trigonometric table</h1>
	<table border="1">
		<thead>
			<tr><th>x</th><th>sin(x)</th><th>cos(x)</th></tr>
		</thead>
		<tbody>
			<c:forEach var="triplet" items="${triplets}">
				<tr><td>${triplet.angle}</td><td>${triplet.sin}</td><td>${triplet.cos}</td></tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>