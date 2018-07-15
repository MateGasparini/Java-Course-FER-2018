<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Powers Error</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>Error!</h1>
	<p>
		<%= session.getAttribute("errorMessage")%>
	</p>
	<p>
		<a href="index.jsp">Back</a>
	</p>
</body>
</html>