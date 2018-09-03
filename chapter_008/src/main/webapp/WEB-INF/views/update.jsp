<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Update</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function validate() {
            var name = $('#name').val();
            var email = $('#email').val();
            var login = $('#login').val();
            var pass1 = $('#password').val();
            var pass2 = $('#passwordR').val();
            var city = $('#city').val();
            var country = $('#country').val();

            if (name == "") {
                alert("Empty name field!")
            } else if (email == "") {
                alert("Empty email field!")
            } else if (login == "") {
                alert("Empty login field!")
            } else if (pass1 != pass2) {
                alert("Passwords doesn't equals!")
            } else if (city == "") {
                alert("Empty city field!")
            } else if (country == "") {
                alert("Empty country field!")
            }
        }
    </script>
    <style type="text/css">
        .update{
            position:absolute;
            width:200px;
            height:350px;
            left:75%;
            top:100%;
            margin-left:-390px;
            margin-top:-325px;
        }
        .update input{
            width: 100%;
        }
    </style>
</head>
<body>
    <form class="update" action='${pageContext.servletContext.contextPath}/' method=POST>
        name: <input type='text' name='name' value='${param.name}' id="name"/>
        login: <input type='text' name='login' value='${param.login}' id="login"/>
        email: <input type='email' name='email' value='${param.email}' id="email"/>
        new password: <input type='password' name='password1' value='' id="password"/>
        repeat password: <input type="password" name="password2" value="" id="passwordR"/>
        city: <input type="text" list="cities" name = "city" id="city" value="${param.city}"/>
        <datalist id="cities">
            <c:forEach items="${cities}" var="city">
                <option><c:out value="${city}"/></option>
            </c:forEach>
        </datalist>
        country: <input type="text" list="countries" name = "country" id="country" value="${param.country}"/>
        <datalist id="countries">
            <c:forEach items="${countries}" var="country">
                <option><c:out value="${country}"/></option>
            </c:forEach>
        </datalist>
        <c:choose>
            <c:when test="${sessionScope.user.role.equals('admin')}">
                role: <select name="role">
                <option value="admin">admin</option>
                <option value="user">user</option>
            </select>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="role" value="user"/>
            </c:otherwise>
        </c:choose>
        <input type='hidden' name='id' value='${param.id}'/>
        <input type='hidden' name='action' value='update'/>
        <input type='submit' value='edit' onclick="return validate()"/>
        <c:if test="${error != ''}">
            <div style="background-color: red">
                <c:out value="${error}"></c:out>
            </div>
        </c:if>
    </form>
</body>
</html>
