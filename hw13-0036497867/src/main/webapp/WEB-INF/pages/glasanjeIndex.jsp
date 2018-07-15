<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Glasanje</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>
		Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!
	</p>
	<ol>
		<c:forEach var="band" items="${bands}">
			<li><a href="glasanje-glasaj?id=${band.id}">${band.name}</a></li>
		</c:forEach>
	</ol>
	<p>
		<a href="index.jsp">Povratak</a>
	</p>
</body>
</html>