<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Greška</title>
</head>
<body>
	<h1>Greška!</h1>
	<p>
		${error}
	</p>
	<p>
		<a href="index.html">Povratak</a>
	</p>
</body>
</html>