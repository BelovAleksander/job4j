<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 30.10.2018
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>Sign in</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
        body {
                margin: 50px 100px 10px 300px;
                font-size: 16px;
                background-color: aliceblue;
                width: 777px;
        }
        .form-group {
                font-size: 16px;
                text-align: center;
                margin: 3px 2px 2px 5px;
        }
</style>
<script>
    function checkData() {
        var email = $("#email").val();
        var user = validate(email);
        if (user == null) {
            return;
        }
        var password = user.password;
        if (password == null) {
            alert("no such user!");
        }
        else if (password != $("#password").val()) {
            alert("wrong password!");
        } else {
            signIn(user);
        }
    }

    function validate(email) {
        var param = {
            "action" : "getUser",
            "value" : email
        };
        var user;

        $.ajax({
            url: "./controller",
            type: "GET",
            async: false,
            data: param,
            complete: function (data) {
                if (data.responseText != "null") {
                    user = JSON.parse(data.responseText);
                } else {
                    alert("Such user doesn't exist!");
                }
            }
        });
        return user;
    }

    function signIn(user) {

        var param = {
            "action" : "signIn",
            "value" : JSON.stringify(user)
        };
        $.ajax({
            url: "./controller",
            type: "POST",
            data: param,
            async: false,
            complete: function (data) {
                location = "${pageContext.servletContext.contextPath}/home";
            }
        });
    }
</script>
</head>
<body>
<div style="margin-top: 120px" class="form-group">
        <h2>Sign in</h2>
        <a style="margin: 50px 0px 50px 0px;" href="${pageContext.servletContext.contextPath}/login">Registration</a><br>
        <label  for="email">email</label>
        <input style="margin: 0px 0px 0px 62px" type="email" id="email" name="email"/><br>
        <label style="margin: 0px 30px 0px 0px" for="password">password</label>
        <input type="password" id="password" name="password"/><br>
        <button type="button" onclick="checkData()">sign in</button>
    
</div>
</body>
</html>