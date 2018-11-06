<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 28.10.2018
  Time: 3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Specifics</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
            font-size: 20px;

            margin: 3px 2px 2px 5px;
        }
        table {
            font-size: 20px
        }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', getAdvert, false);

        var urlString = window.location.href;
        var url = new URL(urlString);
        var advertId = url.searchParams.get("advertId");

        function getAdvert() {

            $.ajax({
                url: "./controller",
                type: "GET",
                data: {
                    "action" : "getAdvert",
                    "value" : advertId
                },
                complete: function (data) {
                    var advert = JSON.parse(data.responseText);
                    var url = getBaseUrl();
                    var photoUrl = url + "image/" + advert.photo;
                    var userRole = "${sessionScope.role}";
                    var content =
                      '<h3>' + advert.title + '</h3>'
                    + '<img src="' + photoUrl + '" alt="photo"/>'
                    + '<table><tbody><tr><td style="width: 120px">vin number:</td><td>' + advert.vin + '</td></tr>'
                    + '<tr><td>model:       </td><td>' + advert.brand.name + " " + advert.model.name + '</td></tr>'
                    + '<tr><td>mileage:     </td><td>' + advert.mileage + '</td></tr>'
                    + '<tr><td>color:       </td><td>' + advert.color.name + '</td></tr>'
                    + '<tr><td>description: </td><td>' + advert.description + '</td></tr>'
                    + '<tr><td>price:       </td><td>' + advert.price + '</td></tr></tbody></table>';

                    var owner = advert.owner.email;
                    var user = "${sessionScope.email}";
                    var path = "${pageContext.servletContext.contextPath}";
                    <c:choose>
                        <c:when test="${sessionScope.role.equals('admin')}">
                            content += '<form style="margin-top: 10px" action="' + path + '/update">' +
                                '<input type="hidden" name="advertId" value="' + advertId +'"/>' +
                                '<input id="submit" type="submit" value="update"/>' +
                                '</form>' +
                                '<button id="delete" type="button" onclick="deleteAdvert()">delete</button>' +
                                '<button id="change" type="button" onclick="changeStatus()">change status</button>';
                        </c:when>
                        <c:otherwise>
                            if (owner == user) {
                                content += '<form style="margin-top: 10px" action="' + path + '/update">' +
                                    '<input type="hidden" name="advertId" value="' + advertId +'"/>' +
                                    '<input  type="submit" value="update"/>' +
                                    '</form>' +
                                    '<button type="button" onclick="deleteAdvert()">delete</button>' +
                                    '<button type="button" onclick="changeStatus()">change status</button>';
                            }
                        </c:otherwise>
                    </c:choose>

                    document.getElementById("advert-content").innerHTML = content;
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

        function deleteAdvert() {
            $.ajax({
                url: "./controller",
                type: "POST",
                data: {
                    "action" : "deleteAdvert",
                    "value" : advertId
                },
                async: false,
                success: function (data) {
                    location = "${pageContext.servletContext.contextPath}/home";
                }
            });
        }

        function changeStatus() {
            $.ajax({
                url: "./controller",
                type: "POST",
                data: {
                    "action" : "changeStatus",
                    "value" : advertId
                },
                async: false,
                complete: function (data) {
                    location = "${pageContext.servletContext.contextPath}/home";
                }
            });
        }

    </script>
</head>
<body>
    <div id="advert-content" class="form-group">

    </div>
</body>
</html>
