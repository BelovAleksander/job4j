<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 30.10.2018
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Log in</title>
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
        function validation() {
            var result = true;
            if ($("#email").val() == "") {
                result = false;
                alert("email is empty!");
            } else if ($("#password-first").val() == "") {
                result = false;
                alert("password is empty!");
            } else if ($("#password-first").val() != $("#password-second").val()) {
                result = false;
                alert("password don't match!");
            }
            return result;
        }

        function login() {
            if (!validation()) {
                return;
            }
            var email = $("#email").val();
            var password =  $("#password-first").val();
            var role = {
                "id" : 2,
                "name" : "user"
            };
            var user = {
                "email" : email,
                "password" : password,
                "role" : role
            };
            var param = {
                "action" : "addUser",
                "value" : JSON.stringify(user)
            };

            $.ajax({
                url: "./controller",
                type: "POST",
                data: param,
                async: false,
                success: function (data) {
                    location = "${pageContext.servletContext.contextPath}/home";
                }
            });

        }
    </script>
</head>
<body>
<div style="margin-top: 200px" class="form-group">
    <h2>Registration</h2>
    <div style="margin: 5px 0 0 5px">
        <label style="margin-right: 85px;" for="email">email</label>
        <input type="email" id="email" value=""/><br>
    </div>
    <div style="margin: 5px 0 0 5px">
        <label style="margin-right: 52px;" for="password-first">password</label>
        <input type="password" id="password-first" value=""/><br>
    </div>
    <div style="margin: 5px 0 0 5px">
        <label for="password-second">repeat password</label>
        <input type="password" id="password-second" value=""/><br>
        <button style="width: 170px; margin-top: 5px" type="button" onclick="login()">login</button>
    </div>

</div>
</body>
</html>