<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Početna stranica</title>
</head>
<body>
	<h1>Početna stranica</h1>
	<p>
		Odaberite jednu od sljedećih anketa.
	</p>
	<ol>
		<c:forEach var="poll" items="${polls}">
			<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>