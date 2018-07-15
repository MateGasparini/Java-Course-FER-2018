<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Error</title>
	</head>
	
	<body>
		<c:choose>
		 <c:when test="${sessionScope['current.user.id'] == null}">
		  Not signed in.
		 </c:when>
		 <c:otherwise>
		  ${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}<br>
		  <a href="${pageContext.servletContext.contextPath}/servleti/logout">Logout</a>
		 </c:otherwise>
		</c:choose>
		
		<h1>Error encountered!</h1>
		<p>${error}</p>
		
		<a href="${pageContext.servletContext.contextPath}/servleti/main">Back to main page</a>
	</body>
</html>
