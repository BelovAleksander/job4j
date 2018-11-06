<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 08.10.2018
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset='utf-8'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <style>
        body {
            margin: 50px 100px 10px 300px;
            font-size: 16px;
            background-color: aliceblue;
            border: dotted 1px royalblue;
            border-radius: 10px;
            width: 777px;
        }
        .form-group {
            font-size: 16px;

            margin: 3px 2px 2px 5px;
        }

    </style>
    <script>

        document.addEventListener('DOMContentLoaded', getAdverts, false);

        function getAdverts() {
            var param = {
                "action" : "getAdverts",
                "value" : ""
            };
            var url = getBaseUrl();

            $.ajax({
                url: "./controller",
                type: "GET",
                data: param,
                async: false,

                complete: function (data) {
                    var cars = JSON.parse(data.responseText);
                    var content = "";
                    for (index in cars) {
                        var car = cars[index];
                        var photoUrl = url + "image/" + car.photo;
                        var route = "${pageContext.servletContext.contextPath}";
                        var row = '<tr><td style="border: solid black">'
                            + '<img src="'+ photoUrl + '" alt="photo" width="300"/></td>'
                            + '<td style="border-top: black 1px solid"><a class="form-group" href="' + route + '/specific?advertId='
                            + car.vin + '"><h2>' + car.title + '</h2></a></td><td style="border-top: black 1px solid"><h2>' + car.price + '</h2></td>'
                            + '<td style="border-top: black 1px solid"><h2 style="margin-left: 5px">' + car.status + '</h2></td></tr>';
                        content += row;
                    }
                    document.getElementById("cars-table-body").innerHTML = content;
                }
            });
        }

        function getBaseUrl() {
            var url;
            $.ajax({
                url: "./controller",
                type: "POST",
                data: {"action" : "getBaseUrl"},
                async: false,
                complete: function (data) {
                    url = data.responseText;
                }
            });
            return url;
        }
    </script>
</head>
<body>
<h1 style="text-align: center">Car Selling</h1>
<c:choose >
    <c:when test="${sessionScope.email == null}">
        <a href="${pageContext.servletContext.contextPath}/signin">Войти</a><br>
        <a href="${pageContext.servletContext.contextPath}/login">Зарегистрироваться</a><br>
    </c:when>
    <c:otherwise>
        ${sessionScope.email}<br>
        <a href="${pageContext.servletContext.contextPath}/new">Добавить объявление</a><br>
        <a href="${pageContext.servletContext.contextPath}/signout">Выйти</a>
    </c:otherwise>
</c:choose>
<table id="cars-table">
    <tbody id="cars-table-body">

    </tbody>
</table>

</body>
</html>
