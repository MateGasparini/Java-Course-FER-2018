<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>OS Usage</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>OS Usage</h1>
	<p>
		Here are the results of OS usage in survey that we completed.<br><br>
	</p>
	<img src="reportImage">
	<p>
		<a href="index.jsp">Back</a>
	</p>
</body>
</html>