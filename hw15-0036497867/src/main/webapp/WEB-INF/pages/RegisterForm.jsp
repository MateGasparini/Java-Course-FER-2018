<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Register</title>
		
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
		<h1>User registration</h1>

		<form action="register" method="post">

		<div>
		 <div>
		  <span class="formLabel">First name</span><input type="text" name="firstname" value='<c:out value="${form.firstName}"/>' size="20">
		 </div>
		 <c:if test="${form.errorPresent('firstname')}">
		 <div class="greska"><c:out value="${form.getError('firstname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last name</span><input type="text" name="lastname" value='<c:out value="${form.lastName}"/>' size="20">
		 </div>
		 <c:if test="${form.errorPresent('lastname')}">
		 <div class="greska"><c:out value="${form.getError('lastname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">E-mail</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="20">
		 </div>
		 <c:if test="${form.errorPresent('email')}">
		 <div class="greska"><c:out value="${form.getError('email')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Nickname</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="20">
		 </div>
		 <c:if test="${form.errorPresent('nick')}">
		 <div class="greska"><c:out value="${form.getError('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value="" size="20">
		 </div>
		 <c:if test="${form.errorPresent('password')}">
		 <div class="greska"><c:out value="${form.getError('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Register">
		  <input type="submit" name="method" value="Cancel">
		</div>
		
		</form>

	</body>
</html>
