<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Entries - ${author}</title>
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
		
		<h1>${author}'s blog entries</h1>
		<c:choose>
		  <c:when test="${entries.isEmpty()}">
		    <p>No entries created yet.</p>
		  </c:when>
		  <c:otherwise>
		    <ol>
		      <c:forEach var="entry" items="${entries}">
		      <li><a href="<c:out value="${author}"/>/${entry.id}">${entry.title}</a></li>
		    </c:forEach>
		    </ol>
		  </c:otherwise>
		</c:choose>
		<c:if test="${sessionScope['current.user.nick'].equals(author)}">
		  <a href="<c:out value="${author}"/>/new">Create new blog entry.</a>
		</c:if>
	</body>
</html>
