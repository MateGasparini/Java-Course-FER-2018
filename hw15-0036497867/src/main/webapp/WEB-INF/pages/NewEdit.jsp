<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>${action} entry</title>
		
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
		
		<h1><c:out value="${action}"/> entry</h1>
		
		<form action='
			<c:if test="${action.equals('New')}">
				${action.toLowerCase()}
			</c:if>' method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Title</span>
		  <input type="text" name="title" value="${form.title}" size="15">
		 </div>
		 <c:if test="${form.errorPresent('title')}">
		 <div class="greska"><c:out value="${form.getError('title')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Content</span>
		  <textarea name="text" rows="20" cols="60">${form.text}</textarea>
		 </div>
		 <c:if test="${form.errorPresent('text')}">
		 <div class="greska"><c:out value="${form.getError('text')}"/></div>
		 </c:if>
		</div>
		
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Submit">
		  <input type="submit" name="method" value="Cancel">
		</div>
		
		</form>
	</body>
</html>
