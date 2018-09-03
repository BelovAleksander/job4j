<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $( document ).ready(function () {
        $.ajax({
            type: "GET",
            url: "/create",
            dataType: "json",
            data: JSON.stringify("ready"),
            success: JSON.parse(data, function (key, value) {
                $('#countries').replaceWith(value);
                return false;
            })
        })
    })
        $(".country").change(function () {
            $.ajax({
                type: "GET",
                url: "/create",
                dataType: "json",
                data: JSON.stringify(document.getElementById("country").value),
                success: JSON.parse(data, function (key, value) {
                    $('#cities').replaceWith(value);
                    return false;
                })
            })
        })
    </script>
    <script>
        function validate() {
            var name = $('#name').val();
            var email = $('#email').val();
            var login = $('#login').val();
            var pass1 = $('#password').val();
            var pass2 = $('#passwordR').val();

            if (name == "") {
                alert("Empty name field!")
            } else if (email == "") {
                alert("Empty email field!")
            } else if (login == "") {
                alert("Empty login field!")
            } else if (pass1 != pass2) {
                alert("Passwords doesn't equals!")
            }
        }
    </script>
    <style type="text/css">
        .create{
            position:absolute;
            width:200px;
            height:350px;
            left:75%;
            top:100%;
            margin-left:-390px;
            margin-top:-325px;
        }
        .create input{
            width: 100%;
        }
    </style>
</head>
<body>
    <form class="create" action='${pageContext.servletContext.contextPath}/' method='POST'>
        name: <input type='text' name='name' value='' id="name"/>
        email: <input type='text' name='email' value='' id="email"/>
        login: <input type='text' name='login' value='' id="login"/>
        password: <input type="password" name="password1" value="" id="password"/>
        repeat password: <input type="password" name="password2" value="" id="passwordR"/>
        city: <input type="text" list="cities" name = "city" id="city"/>
        <datalist id="cities">

        </datalist>
        country: <input type="text" list="countries" name = "country" id="country" class="country"/>
        <datalist id="countries" class="country">

        </datalist>

        <c:if test="${sessionScope.user.role.equals('admin')}">
            role: <select name="role">
                <option value="admin">admin</option>
            <option value="user">user</option>
            </select>
        </c:if>
        <input type='hidden' name='action' value='add'/>
        <input type='submit' value='create' onclick="return validate()"/>
        <c:if test="${error != ''}">
            <div style="background-color: red">
                <c:out value="${error}"></c:out>
            </div>
        </c:if>
    </form>
</body>
</html>
