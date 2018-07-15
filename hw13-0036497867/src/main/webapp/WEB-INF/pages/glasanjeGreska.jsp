<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Greška u glasanju</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>Greška!</h1>
	<p>
		Nisu zabilježeni nikakvi glasovi.<br>
		Glasaj <a href=glasanje>ovdje</a>!
	</p>
	<p>
		<a href="index.jsp">Povratak</a>
	</p>
</body>
</html>