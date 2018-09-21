function loadValues() {
    var roleList = getValues("getRoles");
    var addressList = getValues("getAddresses");
    var musicList = getValues("getMusic");
    var userList = getValues("getUsers");


    for (el in roleList) {
        var roleName = new String(roleList[el].name);
        var roleId = new String(roleList[el].id);
        $('#updateRole option:last')
            .after('<option value="' + roleList[el].name + '">' + roleName + '</option>');
        $('#MyRoles option:last')
            .after('<option value="' + roleList[el].name + '">' + roleName + '</option>');
        $('#RoleTable tbody:first')
            .after('<tr><td>' + roleName + '</td><td>' + roleId + '</td><td>' +
                '<button onclick="updateRole(' + roleId + ')">update</button>' +
                '<button onclick="deleteRole(' + roleId + ')">delete</button> ' + '</td></tr>');
    }
    for (el in addressList) {
        $('#updateAddress option:last')
            .after('<option value="' + addressList[el].name + '">' + addressList[el].name + '</option>');
        $('#MyAddresses option:last')
            .after('<option value="' + addressList[el].name + '">' + addressList[el].name + '</option>');
        $('#AddressTable tbody:first')
            .after('<tr><td>' + addressList[el].name + '</td><td>' + addressList[el].id + '</td><td>' +
                '<button onclick="updateAddress(' + addressList[el].id +')">update</button>' +
                '<button onclick="deleteAddress(' + addressList[el].id + ')">delete</button> ' + '</td></tr>');
    }
    for (el in musicList) {
        $('#updateMusic option:last').after(
            '<option value="' + musicList[el].name + '">' + musicList[el].name + '</option>');
        $('#MyMusic option:last').after(
            '<option value="' + musicList[el].name + '">' + musicList[el].name + '</option>');
        $('#MusicTable tbody:first')
            .after('<tr><td>' + musicList[el].name + '</td><td>' + musicList[el].id + '</td><td>'+
                '<button onclick="updateMusic(' + musicList[el].id + ')">update</button>' +
                '<button onclick="deleteMusic(' + musicList[el].id + ')">delete</button></td></tr>');
    }

    for (el in userList) {
        var musicString = "";
        var musicL = userList[el].music
        for (index in musicL) {
            musicString += '<p>' + musicL[index].name + '</p>';
        }
        $('#UpdateUser option:last')
            .after('<option value="' + userList[el].login + '">' + userList[el].login + '</option>');
        $('#UserTable tbody:first')
            .after('<tr><td>' + userList[el].id + '</td><td>' + userList[el].login + '</td><td>' +
                userList[el].password + '</td><td>' + userList[el].address.name + '</td><td>' +
                userList[el].role.name + '</td><td>' + musicString + '</td><td>' +
                '<button onclick="deleteUser(' + userList[el].id + ')">delete</button></td></tr>');
    }
}

function getValues(action) {
    var list;

    $.ajax({
        type: "GET",
        async: false,
        url: "./provider",
        data: {"action" : action},
        complete: function (data) {
            list = JSON.parse(data.responseText);
        }
    });
    return list;
}


function searchUsers() {
    var param = $("#searchParam").val();
    var value = $("#searchValue").val();
    var validation = validate(param, "Parameter") && validate(value, "Value");
    if (!validation) {
        return;
    }
    var action;

    if (param === "address") {
        action = "getUsersByAddress";
    } else if (param === "role") {
        action = "getUsersByRole";
    } else if (param === "music") {
        action = "getUsersByMusicType";
    }

    var list = {
        "action" : action,
        "value" : value
    };

    $.ajax({
        type: "GET",
        url: "./provider",
        data: list,
        async: false,
        complete: function (data) {
            var users = JSON.parse(data.responseText);
            $("#searchResults").empty();
            for (el in users) {
                $("#searchResults").append('<p>' + users[el].login + '</p>');
            }
        }
    });
    return false;
}

function validate(field, fieldName, action, check) {
    var result = true;
    if (check == undefined) {
        check = false;
    }

    if (field === "" || field === null) {
        alert(fieldName + " is empty!");
        return;
    } else if (check) {
        result = checkOverlap(field, fieldName, action)
    }
    return result;
}

function checkOverlap(field, fieldName, action) {
    var result = true;
    var list = getValues(action);
    for (el in list) {
        if (list[el].name === field) {
            alert("Such " + fieldName + " already exists!");
            result = false;
        }
    }
    if (list.indexOf(field) != -1) {
        alert("Such " + fieldName + " already exists!");
        result = false;
    }
    return result;
}

function validateMusicList(musicList) {
    var result = true;
    var test = musicList[0].name;
    if (musicList[0].name === "") {
        alert("Music is empty!");
        result = false;
    }
    return result;
}

function addRole() {
    var name = $('#newRole').val();
    if (!validate(name, "Role", "getRoles", true)) {
        return;
    }
    var list = {
        "action" : "addRole",
        "name" : name
    };

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/chapter_008/mc",
        data: list,
        async: false,
        complete: function (data) {
            var role = JSON.parse(data.responseText);
            $('#updateRole option:last')
                .after('<option name="role" value="' + role.name + '">' + role.name + '</option>');
            $('#MyRoles option:last')
                .after('<option name="role" value="' + role.name + '">' + role.name + '</option>');
            $('#RoleTable tbody:first')
                .after('<tr><td>' + role.name + '</td><td>' + role.id + '</td><td>' +
                    '<button onclick="updateRole(' + role.id + ')">update</button>' +
                    '<button onclick="deleteRole(' + role.id + ')">delete</button> ' + '</td></tr>');
        }
    });
}
function addAddress() {
    var name = $('#newAddress').val();
    if (!validate(name, "Address", "getAddresses", true)) {
        return;
    }
    var list = {
        "action" : "addAddress",
        "name" : name
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        data: list,
        async: false,
        complete: function (data) {
            var address = JSON.parse(data.responseText);
            $('#updateAddress option:last').after('<option value="' + address.name + '">' + address.name + '</option>');
            $('#MyAddresses option:last').after('<option value="' + address.name + '">' + address.name + '</option>');
            $('#AddressTable tbody:first')
                .after('<tr><td>' + address.name + '</td><td>' + address.id + '</td><td>' +
                    '<button onclick="updateRole(' + address.id + ')">update</button>' +
                    '<button onclick="deleteRole(' + address.id + ')">delete</button> ' + '</td></tr>');
        }
    });
}

function addMusic() {
    var name = $('#newMusic').val();
    if (!validate(name, "Music", "getMusic", true)) {
        return;
    }
    var list = {
        "action" : "addMusic",
        "name" : name
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        async: false,
        data: list,
        complete: function (data) {
            var music = JSON.parse(data.responseText);
            $('#updateMusic option:last').after('<option value="' + music.name + '">' + music.name + '</option>');
            $('#MyMusic option:last').after('<option value="' + music.name + '">' + music.name + '</option>');
            $('#MusicTable tbody:first')
                .after('<tr><td>' + music.name + '</td><td>' + music.id + '</td><td>' +
                    '<button onclick="updateRole(' + music.id + ')">update</button>' +
                    '<button onclick="deleteRole(' + music.id + ')">delete</button> ' + '</td></tr>');
        }
    });
    return false;
}

function addUser() {
    var login = $('#login').val();
    var password = $('#password').val();
    var address = $('#MyAddresses').val();
    var role = $('#MyRoles').val();
    var musicList = getMusicList("MyMusic");

    var validation = validate(login, "Login", "getLogins", true) && validate(password, "Password") &&
        validate(address, "Address") && validate(role, "Role") && validateMusicList(musicList);

    if(!validation) {
        return;
    }

    var user = {
        "login" : login,
        "password" : password,
        "address" : {"name" : address},
        "role" : {"name" : role},
        "music" : musicList
    };
    
    var param = {
        "action" : "addUser",
        "user" : JSON.stringify(user)
    }

    $.ajax({
        type: "POST",
        url: "./usersMC",
        data: param,
        async: false,
        complete: function (data) {
            var res = JSON.parse(data.responseText);
            var musicStr = "";
            for (el in res.music) {
                musicStr += '<p>' + res.music[el].name + '</p>';
            }
            $('#UpdateUser option:last').after('<option value="' + res.login + '">' + res.login +'</option>');
            $('#UserTable tbody:first').after('<tr><td>' + res.id + '</td><td>' + res.login + '</td><td>' +
            res.password + '</td><td>' + res.address.name + '</td><td>' + res.role.name + '</td><td>' +
                musicStr + '</td><td>' +
                '<button type="button" onclick="deleteUser(' + res.id + ')">delete</button></td></tr>');
        }
    });
    return false;
}

function updateRole(id) {
    var newName = prompt("Input new name: ", "new");
    if (!validate(newName, "Name", "getRoles", true)) {
        return;
    }
    var list = {
        "action" : "updateRole",
        "id" : id,
        "name" : newName
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        data: list
    });
    window.location.reload();
    return false;
}

function updateAddress(id) {

    var newName = prompt("Input new name: ", "new");
    if (!validate(newName, "Name", "getAddresses", true)) {
        return;
    }
    var list = {
        "action" : "updateAddress",
        "id" : id,
        "name" : newName
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        data: list
    });
    window.location.reload();
    return false;
}

function updateMusic(id) {

    var newName = prompt("Input new name: ", "new");
    if (!validate(newName, "Name", "getMusic", true)) {
        return;
    }
    var list = {
        "action" : "updateMusic",
        "id" : id,
        "name" : newName
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        data: list
    });
    window.location.reload();
    return false;
}

function updateUser() {
    var oldLogin = $('#UpdateUser').val();
    var id = getUserID(oldLogin);
    var login = $('#updateLogin').val();
    var password = $('#updatePassword').val();
    var address = $('#updateAddress').val();
    var role = $('#updateRole').val();
    var music = getMusicList("updateMusic");

    var validation = validate(login, "Login", "getLogins", true) && validate(password, "Password") &&
        validate(address, "Address") && validate(role, "Role") && validateMusicList(music);
    if (!validation) {
        return;
    }

    var user = {
        "id" : id,
        "login" : login,
        "password" : password,
        "address" : address,
        "role" : role,
        "music" : music
    }

    var param = {
        "action" : "updateUser",
        "user" : JSON.stringify(user)
    }

    $.ajax({
        type: "POST",
        url: "./usersMC",
        data: param,
        complete: window.location.reload()
    });
    return false;
}

function getUserID(login) {
    list = {
        "action" : "getUserID",
        "login" : login
    }
    var id;

    $.ajax({
        type: "GET",
        url: "./provider",
        data: list,
        async: false,
        complete: function (data) {
            id = JSON.parse(data.responseText);
        }
    });
    return id;
}

function deleteRole(id) {
    var list = {
        "action" : "deleteRole",
        "id" : id
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        data: list
    });
    window.location.reload();
    return false;
}

function deleteAddress(id) {
    var list = {
        "action" : "deleteAddress",
        "id" : id
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        data: list
    });
    window.location.reload();
    return false;
}

function deleteMusic(id) {
    var list = {
        "action" : "deleteMusic",
        "id" : id
    };

    $.ajax({
        type: "POST",
        url: "./mc",
        data: list
    });
    window.location.reload();
    return false;
}

function deleteUser(id) {
    var list = {
        "action" : "deleteUser",
        "id" : id
    };

    $.ajax({
        type: "POST",
        url: "./usersMC",
        data: list
    });
    window.location.reload();
    return false;
}

function getMusicList(id) {
    var result = [];
    var myMusic = document.getElementById(id);

    for (var i = 0; i < myMusic.options.length; i++) {
        if (myMusic.options[i].selected) {
            result.push({"name" : myMusic.options[i].value});
        }
    }
    return result;
}
