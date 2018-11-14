<%--
  Created by IntelliJ IDEA.
  User: whiterabbit.nsk
  Date: 30.10.2018
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Advert</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
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

            margin: 3px 2px 2px 5px;
        }

    </style>
    <script>
        document.addEventListener("DOMContentLoaded", fillContent, false);
        var fileSelected = false;

        function fillContent() {
            var brandsList = getList("getBrands", "");
            var colorsList = getList("getColors", "");

            document.getElementById("select-brand").innerHTML = buildContent(brandsList);
            document.getElementById("select-color").innerHTML = buildContent(colorsList);
            document.getElementById("select-model").innerHTML = '<option selected disabled>select brand</option>';

            document.getElementById("select-brand").addEventListener("change", rebootModels, false);
        }

        function rebootModels() {
            var models = getList("getModels", $("#select-brand").val());
            document.getElementById("select-model").innerHTML = buildContent(models);
        }

        function buildContent(list) {
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

        function addAdvert(imgPath) {
            var title = $("#title").val();
            var vin = $("#vin-number").val();
            var brand = $("#select-brand").val();
            var model = $("#select-model").val();
            var price = $("#price").val();
            var mileage = $("#mileage").val();
            var color = $("#select-color").val();
            var desc = $("#desc").val();

            var advert = {
                "title" : title,
                "vin" : vin,
                "price" : price,
                "mileage" : mileage,
                "description" : desc,
                "color" : {"id" :  color},
                "brand" : {"id" : brand},
                "model" : {"id" : model},
                "photo" : imgPath,
                "owner" : {"id": "${sessionScope.userId}"}
            };
            var param = {
                "action" : "addAdvert",
                "value" : JSON.stringify(advert)
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

        function fileUpload() {
            if (!fileSelected) {
                addAdvert();
                return;
            }
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
                    addAdvert(data.responseText);
                }
            });
        }

        function onFileSelected(event) {
            fileSelected = true;
            var selectedFile = event.target.files[0];
            var reader = new FileReader();

            var imgtag = document.getElementById("result");
            imgtag.title = selectedFile.name;

            reader.onload = function(event) {
                imgtag.src = event.target.result;
            };

            reader.readAsDataURL(selectedFile);
        }
    </script>
</head>
<body>
<h2 style="text-align: center">New Advert</h2>

<table>
    <tbody>
        <tr>
            <td>
                <div class="form-group">
                    <label for="input-file">Upload Photo</label>
                    <input style="margin-left: 40px" type="file" name="photo" id="input-file" onchange="onFileSelected(event)" /><br>
                </div>
                <div class="form-group">
                    <label for="vin-number">VIN number</label>
                    <input style="margin-left: 50px" type="number" id="vin-number" name="vin-number"><br>
                </div>
                <div class="form-group">
                    <label for="title">Title</label>
                    <input style="margin-left: 130px" type="text" id="title"><br>
                </div>
                <div class="form-group">
                    <label for="select-brand">Brand</label>
                    <select style="margin-left: 116px" class="form-control" id="select-brand">

                    </select><br>
                </div>

                <div class="form-group">
                    <label for="select-model">Model</label>
                    <select style="margin-left: 110px" class="form-control" id="select-model">
                    </select><br>
                </div>

                <div class="form-group">
                    <label for="price">Price</label>
                    <input style="margin-left: 125px" type="number" id="price"/><br>
                </div>

                <div class="form-group">
                    <label for="mileage">Mileage</label>
                    <input style="margin-left: 95px" type="number" id="mileage"/><br>
                </div>

                <div class="form-group">
                    <label for="select-color">Color</label>
                    <select style="margin-left: 120px" id="select-color">
                    </select>
                </div>


            </td>
            <td>
                <img id="result" src="#" height="240" width="320"  /><br>
            </td>
        </tr>
    </tbody>
</table>
<div style="margin-top: 5px" class="form-group">
    <label for="desc">Description</label><br>
    <input style="width: 767px; height: 100px" type="text" id="desc"/><br>
    <button style="width: 150px; height:30px" type="button" onclick="fileUpload()">Add Item</button>
</div>



</body>
</html>
