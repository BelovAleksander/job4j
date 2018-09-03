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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        .login{
            position:absolute;
            width:200px;
            height:350px;
            left:75%;
            top:100%;
            margin-left:-390px;
            margin-top:-325px;
        }
        .login input{
            width: 100%;
        }
    </style>
</head>
<body>
    <c:if test="${error != ''}">
        <div style="background-color: red">
            <c:out value="${error}"/>
        </div>
    </c:if>

    <form class="login" action="${pageContext.servletContext.contextPath}/signin" method="POST">
        <p align="center"><label for="login">Login: </label>
        <input type="text" name="login" id="login"/></p>
        <p align="center"><label for="password">Password: </label>
        <input type="password" name="password" id="password"/></p>
        <p align="center"><input type="submit" value="Log in"/></p>
    </form>
</body>
</html>
