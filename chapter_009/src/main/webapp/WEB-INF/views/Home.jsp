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

        var url = getBaseUrl();

        function getAdverts() {

            /*document.getElementById("select-model").style.visibility = 'hidden';*/

            var value = {};
            if (document.querySelector("input[name=show-with-photo]").checked) {
                value['limitByPhoto'] = null;
            }
            if (document.querySelector("input[name=show-today-adverts]").checked) {
                value['limitByDate'] = null;
            }
            if (document.querySelector("input[name=show-spec-type]").checked && $('#select-brand').val() != undefined) {

                value['limitByBrand'] = {'currentBrandId' : $('#select-brand').val()};
            } else if (document.querySelector("input[name=show-spec-type]").checked && $('#select-brand').val() == undefined) {
                showBrands();
            } else if (!document.querySelector("input[name=show-spec-type]").checked) {
                document.getElementById("select-brand").style.visibility = 'hidden';
            }
            var param = {
                "action" : "getAdverts",
                "value" : JSON.stringify(value)
            };
            $.ajax({
                url: "./controller",
                type: "GET",
                data: param,
                async: false,
                complete: function (data) {
                    var cars = JSON.parse(data.responseText);
                    showCars(cars);
                }
            });
        }

        function contentForCar(car) {
            var photoUrl = url + "image/" + car.photo;
            var route = "${pageContext.servletContext.contextPath}";
            var date = new Date(car.createdDate);
            var content = '<tr><td style="border: solid black">'
                + '<img src="'+ photoUrl + '" alt="photo" width="300"/></td>'
                + '<td style="border-top: black 1px solid"><a class="form-group" href="' + route + '/specific?advertId='
                + car.vin + '"><h2>' + car.title + '</h2></a></td><td style="border-top: black 1px solid"><h2>' + car.price + '</h2></td>'
                + '<td style="border-top: black 1px solid"><h2 style="margin-left: 5px">' + car.status + '</h2><h2>' +
                date.getDate() + '.' + date.getMonth() + '.' + date.getFullYear() + '</h2></td></tr>';
            return content;
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
/*
        function showModels() {
            var brand = $("#select-brand").val();
            var selectModel = document.getElementById("select-model");
            selectModel.addEventListener("change", showCars(), false);
            selectModel.innerHTML = contentForList(getList("getModels", brand));
            selectModel.style.visibility = 'visible';
        }*/

        function contentForList(list) {
            var content = "<option selected disabled>select</option>";
            for (index in list) {
                var element = list[index];
                var option = '<option value="' + element.id + '">' + element.name + '</option>';
                content += option;
            }
            return content;
        }

        function getList(action, value) {
            var list;
            $.ajax({
                url: "./controller",
                type: "GET",
                data: {
                    "action" : action,
                    "value" : value
                },
                async: false,
                complete: function (data) {
                    list = JSON.parse(data.responseText);
                }
            });
            return list;
        }

        function showBrands() {
            var brands = contentForList(getList("getBrands", ""));
            document.getElementById("select-brand").innerHTML = brands;
            document.getElementById("select-brand").style.visibility = "visible";
            document.getElementById("select-brand").addEventListener("change", getAdverts, false);
        }

        function showCars(cars) {
            var content = "";
            for (index in cars) {
                var car = cars[index];
                content += contentForCar(car);
            }
            if (content == "") {
                content += "no such advert :(";
            }
            document.getElementById("cars-table-body").innerHTML = content;
        }

        /*function showCars() {
            var content = "";
            var filteredList = [];
            if ($("#select-brand").val() == null) {
                var brands = contentForList(getList("getBrands", ""));
                document.getElementById("select-brand").innerHTML = brands;
                document.getElementById("select-brand").addEventListener("change", showModels, false);
            }
            for (index in cars) {
                car = cars[index];
                if (filter(car)) {
                    content += contentForCar(car);
                }
            }
            if (content == "") {
                content += "no such advert :(";
            }
            document.getElementById("cars-table-body").innerHTML = content;
        }

        function filter(car) {
            var result = true;
            if (document.querySelector("input[name=show-today-adverts]").checked) {
                var advertDate = new Date(car.createdDate);
                var currentDate = new Date();
                result = (currentDate.getDate() == advertDate.getDate())
                    && (currentDate.getMonth() == advertDate.getMonth())
                    && (currentDate.getFullYear() == advertDate.getFullYear());
            }
            if (result && document.querySelector("input[name=show-with-photo]").checked) {
                result = (car.photo != null);
            }
            if (result && document.querySelector("input[name=show-spec-type]").checked) {
                document.getElementById("select-brand").style.visibility = "visible";

                if ($("#select-model").val() != null) {
                    result = (car.model.id == $("#select-model").val());
                } else if ($("#select-brand").val() != null) {
                    document.getElementById("select-model").style.visibility = "visible";
                    result = car.brand.id == $("#select-brand").val();
                }
            } else {
                document.getElementById("select-brand").style.visibility = "hidden";
                document.getElementById("select-model").style.visibility = "hidden";
            }
            return result;
        }*/
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
<div id="checkboxes">
<input type="checkbox" title="today's adverts" id="show-today-adverts" name="show-today-adverts"/>
    today's adverts<br>
<input type="checkbox" title="with photo"  id="show-with-photo" name="show-with-photo"/>
    with photo<br>
<input type="checkbox" title="specific brand" id="show-spec-type" name="show-spec-type" />
    specific brand<br>
</div>
<div>
    <select id="select-brand" >

    </select>
</div>
<%--<div>
    <select id="select-model">

    </select>
</div>--%>
<script>
    document.querySelector("input[name=show-spec-type]").addEventListener("change", getAdverts, false);
    document.querySelector("input[name=show-with-photo]").addEventListener("change", getAdverts, false);
    document.querySelector("input[name=show-today-adverts]").addEventListener("change", getAdverts, false);
    document.getElementById("select-brand").style.visibility = 'hidden';
</script>
<table id="cars-table">
    <tbody id="cars-table-body">

    </tbody>
</table>

</body>
</html>
