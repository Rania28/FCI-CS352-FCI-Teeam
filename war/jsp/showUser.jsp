<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
   <%@ taglib 
    uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title> Users </title>
</head>
<body>

<c:forEach items="${it.Userslist}" var="User">

<p> User Name <c:out value="${User.name}"></c:out> </p>
 <p> User Email <c:out value="${User.email}"></c:out> </p>
  
 <br>
 </c:forEach>

</body>
</html>