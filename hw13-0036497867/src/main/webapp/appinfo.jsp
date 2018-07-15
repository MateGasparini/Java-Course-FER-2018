<%@page import="java.time.Instant"%>
<%@page import="java.time.Duration"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!
private String getUpTime() {
	ServletContext ctx = getServletContext();
	Instant start = Instant.ofEpochMilli((Long) ctx.getAttribute("startTimestamp"));
	Instant now = Instant.now();
	Duration duration = Duration.between(start, now);
	return duration.toDaysPart()+" days "+duration.toHoursPart()+" hours "+
			duration.toMinutesPart()+" minutes "+duration.toSecondsPart()+" seconds "+
			duration.toMillisPart()+" miliseconds";
}
%>
<!DOCTYPE html>
<html>
<head>
	<title>Time info</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>Time info</h1>
	<p>
	The web application has been running for:<br>
	<%=getUpTime()%>
	</p>
	<p>
		<a href="index.jsp">Back</a>
	</p>
</body>
</html>