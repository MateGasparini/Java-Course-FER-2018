<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Main page</title>
		
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
           font-weight: bold;
		   text-align: right;
           padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
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
		
		<h1>Blog</h1>
		<c:if test="${sessionScope['current.user.id'] == null}">
		<form action="main" method="post">

		<div>
		 <div>
		  <span class="formLabel">Nickname</span>
		  <input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="10">
		 </div>
		 <c:if test="${form.errorPresent('nick')}">
		 <div class="greska"><c:out value="${form.getError('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Password</span>
		  <input type="password" name="password" value="" size="10">
		 </div>
		 <c:if test="${form.errorPresent('password')}">
		 <div class="greska"><c:out value="${form.getError('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Login">
		</div>
		
		</form>
		
		<p>
			Don't have an account yet? <a href="register">Register here!</a>
		</p>
		</c:if>
		
		<h2>Currently registered users:</h2>
		<c:choose>
		  <c:when test="${users.isEmpty()}">
		    <p>No users registered yet.</p>
		  </c:when>
		  <c:otherwise>
		  <ol>
		    <c:forEach var="user" items="${users}">
		    <li><a href="author/<c:out value="${user.nick}"/>">${user.nick}</a></li>
		    </c:forEach>
		  </ol>
		  </c:otherwise>
		</c:choose>

	</body>
</html>
