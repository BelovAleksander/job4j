<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 23.08.2018
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
    <c:if test="${error != ''}">
        <div style="background-color: red">
            <c:out value="${error}"/>
        </div>
    </c:if>
    <form action="${pageContext.servletContext.contextPath}/signin" method="POST">
        Login: <input type="text" name="login"/>
        Password: <input type="password" name="password"/>
        <input type="submit" value="Log in"/>
    </form>
</body>
</html>
