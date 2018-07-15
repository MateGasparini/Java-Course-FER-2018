<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Color chooser</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>Color chooser</h1>
	<p>Choose a background color:</p>
		<ul>
			<li><a href="setcolor?color=FFFFFF">WHITE</a></li>
			<li><a href="setcolor?color=FF4444">RED</a></li>
			<li><a href="setcolor?color=44FF44">GREEN</a></li>
			<li><a href="setcolor?color=66FFFF">CYAN</a></li>
		</ul>
	<p>
		<a href="index.jsp">Back</a>
	</p>
</body>
</html>