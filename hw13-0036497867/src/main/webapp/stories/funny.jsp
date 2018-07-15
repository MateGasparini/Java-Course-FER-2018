<%@ page import="java.util.Random" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Funny story</title>
</head>
<body bgcolor=<%String color = (String) session.getAttribute("pickedBgCol");
				if (color == null) color = "FFFFFF";
				out.write(color);%>>
	<h1>Funny story</h1>
	<p>
		<font color=<%
					Random random = new Random();
					out.write(String.format("\"#%x\"", random.nextInt(0x1000000)));%>>
		In C++ we don't say "Missing asterisk", we say<br>
		"error c2664: 'void std::vector&lt;block,std::allocator&lt;_Ty&gt;&gt;::push_back(
		const block &amp;)': cannot convert argument 1 from 
		'std::_Vector_iterator&lt;std::_Vector_val&lt;std::_Simple_types&lt;block&gt;&gt;&gt;' 
		to 'block &amp;&amp;'"<br>
		and I think that's beautiful.
		</font>
	</p>
	<p>
		<a href="../index.jsp">Back</a>
	</p>
</body>
</html>