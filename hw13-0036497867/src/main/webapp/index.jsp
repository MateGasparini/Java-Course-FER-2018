<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>Home</h1>
	<p>
		Change the background color!<br>
		<a href="colors.jsp">Background color chooser</a>
	</p>
	<p>
		Generate a trigonometric table!<br>
		<a href="trigonometric?a=0&b=90">Default table (a, b) = (0, 90)</a>
	</p>
	<form action="trigonometric" method="GET">
		Početni kut:<br>
		<input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br>
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	<p>
		Read a funny story! <br>
		<a href="stories/funny.jsp">Funny story</a>
	</p>
	<p>
		Read the results of the OS usage survey!<br>
		<a href="report.jsp">OS usage survey results</a>
	</p>
	<p>
		Create Microsoft Excel documents!<br>
		<a href="powers?a=1&b=100&n=3">Default table (a, b, n) = (1, 100, 3)</a>
	</p>
	<form action="powers" method="GET">
		Starting number:<br>
		<input type="number" name="a" min="-100" max="100" step="1" value="0"><br>
		Ending number:<br>
		<input type="number" name="b" min="-100" max="100" step="1" value="0"><br>
		Number of pages:<br>
		<input type="number" name="n" min="1" max="5" step="1" value="1"><br>
		<input type="submit" value="Create"><input type="reset" value="Reset">
	</form>
	<p>
		Check the web-application's up time!<br>
		<a href="appinfo.jsp">Time info</a>
	</p>
	<p>
		Vote for your favorite band!<br>
		<a href="glasanje">Glasanje</a>
	</p>
</body>
</html>