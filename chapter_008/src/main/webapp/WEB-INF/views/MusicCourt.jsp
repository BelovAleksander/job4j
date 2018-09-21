<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Music Court</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap core CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Material Design Bootstrap -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.10/css/mdb.min.css" rel="stylesheet">
    <style>

        body {
            margin-left:100px;
            margin-top:50px;
        }
/*

        #RoleTable {
            position: absolute;
            right: 700px;
            top: 50px;
        }

        #AddressTable {
            position: absolute;
            right: 450px;
            top: 50px;
        }

        #MusicTable {
            position: absolute;
            right: 100px;
            top: 50px;
        }
*/

        #RoleTable, #AddressTable, #MusicTable {
            display: inline-block;
            width: 27%;
        }

        #tables {
            position: absolute;
            margin-left: 300px;
            right: 10px;
            top: 50px;

        }

        #UserTable {
            width: 50%;
            position: absolute;
            right: 100px;
            top: 700px;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/ScriptsMC.js"></script>
    <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
    <script>
        document.addEventListener('DOMContentLoaded', loadValues, false);
    </script>
</head>
<body>
<div>
    <label for="newAddress">Add Address:</label>
    <p><input type="text" name="newAddress" id="newAddress"/></p>
    <p><input type="submit" value="Add" onclick="addAddress()"/></p>
</div>
<div>
    <label for="newRole">Add Role:</label>
    <p><input type="text" name="newRole" id="newRole"/></p>
    <p><input type="submit" value="Add" onclick="addRole()"/></p>
</div>
<div>
    <label for="newMusic">Add Music:</label>
    <p><input type="text" name="newMusic" id="newMusic"/></p>
    <p><input type="submit" value="Add" onclick="addMusic()"/></p>
</div>
<div>
    <form>
        <p>Add User:</p>
        <input type="text" name="login" id="login" value="login"/><br>
        <input type="text" name="password" id="password" value="password"><br>
        <select id="MyAddresses">
            <option disabled selected>Select address</option>
        </select><br>
        <select id="MyRoles">
            <option disabled selected>Select role</option>
        </select><br>
        <select class="mdb-select md-form colorful-select dropdown-primary" multiple id="MyMusic">
            <option value="" disabled selected>Select your music</option>
        </select><br>
        <button type="button" onclick="addUser()">Add User</button>
    </form>
</div>
<div>
    <form>
        <p>Update User: </p>
        <select id="UpdateUser">
            <option disabled selected>Select user</option>
        </select><br>
        <input type="text" id="updateLogin" value="login"><br>
        <input type="text" value="password" id="updatePassword"><br>
        <select id="updateAddress">
            <option disabled selected>Select address</option>
        </select><br>
        <select id="updateRole">
            <option disabled selected>Select role</option>
        </select><br>
        <select id="updateMusic" multiple>
            <option value="" disabled selected>Select music</option>
        </select><br>
        <button type="button" onclick="updateUser()">Update User</button>
    </form>
</div>
<div id="search">
    <form>
        <p>Search User By Parameter:</p>
        <select id="searchParam" onchange="loadSelectValues(this.options[this.selectedIndex].value)">
            <option disabled selected>Select parameter</option>
            <option value="address">Address</option>
            <option value="role">Role</option>
            <option value="music">Music Type</option>
        </select><br>
        <select id="searchValue">
            <option disabled selected>Select value</option>
        </select><br>
        <button type="button" onclick="searchUsers()">search</button>
    </form>
    <script>
        function loadSelectValues(param) {
            $("#searchValue").empty();
            var list;
            if (param === "address") {
                list = getValues("getAddresses");
            } else if (param === "role") {
                list = getValues("getRoles");
            } else if (param === "music") {
                list = getValues("getMusic");
            }
            $("#searchValue").append('<option disabled selected>Select value</option>');

            for (el in list) {
                $("#searchValue").append('<option value="' + list[el].name + '">' + list[el].name + '</option>');
            }
        }
    </script>
</div>
<p>Results:</p>
<div id="searchResults">

</div>
<div id="tables">
    <table border="2" id="RoleTable" class="table table-bordered">
        <thead>
        <tr>
            <td>name</td>
            <td>id</td>
            <td>action</td>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

    <table border="2" id="AddressTable" class="table table-bordered">
        <thead>
        <tr>
            <td width="200">name</td>
            <td width="100">id</td>
            <td width="100">action</td>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

    <table border="2" id="MusicTable" class="table table-bordered">
        <thead>
        <tr>
            <td width="200">name</td>
            <td width="100">id</td>
            <td width="100">action</td>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<table border="2" id="UserTable" class="table table-bordered">
    <thead>
    <tr>
        <th width="100">id</th>
        <th width="200">login</th>
        <th width="200">password</th>
        <th width="300">address</th>
        <th width="100">role</th>
        <th width="100">music</th>
        <td width="100">action</td>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<!-- JQuery -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Bootstrap tooltips -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.13.0/umd/popper.min.js"></script>
<!-- Bootstrap core JavaScript -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
<!-- MDB core JavaScript -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.10/js/mdb.min.js"></script>
</body>
</html>
