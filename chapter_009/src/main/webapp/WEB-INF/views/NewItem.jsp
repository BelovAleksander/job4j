<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 08.10.2018
  Time: 0:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Item</title>
</head>
<body>
<h2>Подать объявление о продаже автомобиля</h2>
<form action="${pageContext.servletContext.contextPath}/upload" enctype="multipart/form-data" method="POST">
    <label for="vin-number">VIN number</label>
    <input type="number" id="vin-number"><br>
    <label for="select-firm">Firm</label>
    <select id="select-firm">
        <option selected disabled value="">Select firm</option>
    </select><br>
    <label for="select-model">Model</label>
    <select id="select-model">
        <option selected disabled value="">Select model</option>
    </select><br>
    <label for="mileage">Mileage</label>
    <input type="number" id="mileage"><br>
    <label for="color">Color</label>
    <input type="color" id="color"><br>
    <label for="desc">Description</label>
    <input type="text" id="desc"><br>
    <label for="image">Photo</label>
    <input type="file" name="file" id="image"/><br>
    <input type="submit" value="add">
</form>
</body>
</html>
