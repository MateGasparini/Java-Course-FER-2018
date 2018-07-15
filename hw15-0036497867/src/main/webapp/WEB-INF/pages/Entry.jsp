<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>${entry.title}</title>
		
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
		
		<h1 align="center">${entry.title}</h1>
		<p align="center">${entry.text}</p><br>
		<c:if test="${sessionScope['current.user.nick'].equals(entry.creator.nick)}">
		  <a href='edit/<c:out value="${entry.id}"></c:out>'>Edit blog entry</a>
		</c:if>
		
		<h2>Comments</h2>
		<c:choose>
		  <c:when test="${entry.comments.isEmpty()}">
			<p>No comments posted yet.</p>
		  </c:when>
			<c:otherwise>
			<ul>
			<c:forEach var="comment" items="${entry.comments}">
			  <li><div style="font-weight: bold">[User=<c:out value="${comment.usersEMail}"/>] <c:out value="${comment.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${comment.message}"/></div></li>
			</c:forEach>
			</ul>
			</c:otherwise>
		</c:choose>
		
		<h3>Add a new comment</h3>
		<form action="" method="post">
		
		<div>
		 <div>
		  <textarea name="message" rows="10" cols="60"></textarea>
		 </div>
		 <c:if test="${form.errorPresent('message')}">
		 <div class="greska"><c:out value="${form.getError('message')}"/></div>
		 </c:if>
		</div>
		
		<c:if test="${sessionScope['current.user.id'] == null}">
		 <div>
		  <span class="formLabel">E-mail</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="29">
		 </div>
		 <c:if test="${form.errorPresent('email')}">
		 <div class="greska"><c:out value="${form.getError('email')}"/></div>
		 </c:if>
		</c:if>
		
		<div class="formControls">
		  <input type="submit" name="method" value="Comment">
		</div>
		
		</form>
	</body>
</html>
