<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 29.10.2018
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Advert</title>
    <meta charset='utf-8'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <style>
        body {
            margin: 50px 100px 10px 300px;
            font-size: 25px;
            background-color: aliceblue;
            border: dotted 1px royalblue;
            border-radius: 10px;
            width: 777px;
        }
        .form-group {
            font-size: 25px;

            margin: 7px 2px 2px 6px;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", init, false);
        var urlString = window.location.href;
        var url = new URL(urlString);
        var advertId = url.searchParams.get("advertId");

        function rebootModels() {
            var models = getList("getModels", $("#select-brand").val());
            document.getElementById("select-models").innerHTML = getContentOfList(models, advert.model.id);
        }

        function init() {
            var advert = getAdvert(advertId);

            var brands = getList("getBrands", "");
            document.getElementById("select-brand").innerHTML = getContentOfList(brands, advert.brand.id);
            var models = getList("getModels", $("#select-brand").val());
            document.getElementById("select-model").innerHTML = getContentOfList(models, advert.model.id);
            var colors = getList("getColors", "");
            document.getElementById("select-color").innerHTML = getContentOfList(colors, advert.color.id);

            document.getElementById("div-title").innerHTML = '<label for="title">Title</label>' +
                '<input style="margin-left: 127px" type="text" id="title" value="' + advert.title + '"><br>';
            document.getElementById("div-price").innerHTML = '<label for="price">Price</label>\n' +
                '<input style="margin-left: 117px" type="number" id="price" value="' + advert.price + '"/><br>';
            document.getElementById("div-mileage").innerHTML = '<label for="mileage">Mileage</label>\n' +
                '<input style="margin-left: 88px" type="number" id="mileage" value="' + advert.mileage + '"/><br>';
            document.getElementById("div-desc").innerHTML = '<label for="desc">Description</label><br>' +
                '<input  type="text" style="width: 755px; height: 100px" id="desc" value="' + advert.description + '"/><br>' +
                '<button type="button" style="width: 150px; height:30px" onclick="fileUpload()">Update Item</button>';

            var url = getBaseUrl();
            var photoUrl = url + "image/" + advert.photo;
            document.getElementById("div-img").innerHTML =
                '<img src="' + photoUrl + '" weight="320" height="240"/>'

            document.getElementById("select-brand").addEventListener("change", rebootModels, false);
        }

        function getContentOfList(list, id) {
            var content = "";
            for (index in list) {
                var el = list[index];
                if (el.id == id) {
                    content += '<option selected value="' + el.id + '">' + el.name + '</option>';
                } else {
                    content += '<option value="' + el.id + '">' + el.name + '</option>';
                }
            }
            return content;
        }


        function getAdvert(id) {
            var advert;
            var param = {
                "action" : "getAdvert",
                "value" : id
            };
            $.ajax({
                url: "./controller",
                type: "GET",
                data: param,
                async: false,
                complete: function (data) {
                    advert = JSON.parse(data.responseText);
                }
            });
            return advert;
        }

        function getList(action, value) {
            var list;
            var param = {
                "action" : action,
                "value" : value
            };
            $.ajax({
                url: "./controller",
                type: "GET",
                async: false,
                data: param,
                complete: function (data) {
                    list = JSON.parse(data.responseText);
                }
            });
            return list;
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

        function onFileSelected(event) {
            document.getElementById("div-img").innerHTML =
                '<img id="result" src="#" height="100" /><br>';
            var selectedFile = event.target.files[0];
            var reader = new FileReader();

            var imgtag = document.getElementById("result");
            imgtag.title = selectedFile.name;

            reader.onload = function(event) {
                imgtag.src = event.target.result;
            };

            reader.readAsDataURL(selectedFile);
        }

        function deletePhoto() {
            $.ajax({
                url: "./controller",
                type: "POST",
                data: {
                    "action": "deletePhoto",
                    "value": advertId
                }
            });
        }

        function fileUpload() {
            deletePhoto();
            var data = new FormData();
            jQuery.each(jQuery('#input-file')[0].files, function(i, file) {
                data.append('file-'+i, file);
            });

            $.ajax('./controller', {
                data: data,
                cache: false,
                contentType: false,
                processData: false,
                async: false,
                type: 'POST',
                complete: function(data){
                    updateAdvert(data.responseText);
                }
            });
        }

        function updateAdvert(imgPath) {
            var title = $("#title").val();
            var vin = advertId;
            var brand = $("#select-brand").val();
            var model = $("#select-model").val();
            var price = $("#price").val();
            var mileage = $("#mileage").val();
            var color = $("#select-color").val();
            var desc = $("#desc").val();

            var advert = {
                "title": title,
                "vin": vin,
                "price": price,
                "mileage": mileage,
                "description": desc,
                "color": {"id": color},
                "brand": {"id": brand},
                "model": {"id": model},
                "photo": imgPath,
                "owner" : {"id": "${sessionScope.userId}"}
            };
            var param = {
                "action": "updateAdvert",
                "value": JSON.stringify(advert)
            }

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
<h2 style="text-align: center; margin-top: 20px">Update Advert</h2>
<table>
    <tbody>
        <tr>
            <td>
                <div class="form-group">
                    <label for="input-file">Upload Photo</label>
                    <input style="margin-left: 30px" type="file" name="photo" id="input-file" onchange="onFileSelected(event)" /><br>
                </div>
                <div class="form-group" id="div-title">
                </div>

                <div class="form-group">
                    <label for="select-brand">Brand</label>
                    <select style="margin-left: 106px" class="form-control" id="select-brand">
                    </select><br>
                </div>

                <div class="form-group">
                    <label for="select-model">Model</label>
                    <select style="margin-left: 102px" class="form-control" id="select-model">
                    </select><br>
                </div>

                <div class="form-group" id="div-price">
                </div>

                <div class="form-group" id="div-mileage">
                </div>

                <div class="form-group">
                    <label for="select-color">Color</label>
                    <select style="margin-left: 112px" id="select-color">
                    </select>
                </div>

            </td>
            <td>
                <div id="div-img">

                </div>
            </td>
        </tr>
    </tbody>
</table>

<div style="margin-top: 2px; margin-left: 10px" class="form-group" id="div-desc">
    <label for="desc">Description</label>
    <input style="" type="text" id="desc"/><br>

</div>


</body>
</html>
