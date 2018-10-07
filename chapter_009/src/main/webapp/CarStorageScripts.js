function loadValues() {
    getCars();
    getTransmissions();
    getCarcases();
    getEngines();
}

function addCarcase() {
    var carcase = $('#new-carcase').val();
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "addCarcaseXML";
    } else {
        action = "addCarcase@"
    }

    var param = {
        "action" : action,
        "value" : carcase
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getCarcases();
}

function addEngine() {
    var engine = $('#new-engine').val();
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "addEngineXML";
    } else {
        action = "addEngine@";
    }

    var param = {
        "action" : action,
        "value" : engine
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getEngines();
}

function addTransmission() {
    var transmission = $('#new-transmission').val();
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "addTransmissionXML";
    } else {
        action = "addTransmission@";
    }

    var param = {
        "action" : action,
        "value" : transmission
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getTransmissions();
}

function addCar() {
    var name = $('#new-car').val();
    var carcase_id = $('#add-carcase').val();
    var engine_id = $('#add-engine').val();
    var transmission_id = $('#add-transmission').val();
    var car = {
        "name" : name,
        "carcase" : {"id" : carcase_id},
        "engine" : {"id" : engine_id},
        "transmission" : {"id" : transmission_id}
    };
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "addCarXML";
    } else {
        action = "addCar@";
    }
    var param = {
        "action" : action,
        "value" : JSON.stringify(car)
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });

    getCars();
}

function updateCarcase() {
    var id = $('#select-carcase').val();
    var name = $('#update-carcase').val();
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "updateCarcaseXML";
    } else {
        action = "updateCarcase@";
    }

    var param = {
        "action" : action,
        "value" : JSON.stringify({"id" : id, "name" : name})
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getCarcases();
}

function updateEngine() {
    var id = $('#select-engine').val();
    var name = $('#update-engine').val();
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "updateEngineXML";
    } else {
        action = "updateEngine@";
    }

    var param = {
        "action" : action,
        "value" : JSON.stringify({"id" : id, "name" : name})
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getEngines();
}

function updateTransmission() {
    var id = $('#select-transmission').val();
    var name = $('#update-transmission').val();
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "updateTransmissionXML";
    } else {
        action = "updateTransmission@";
    }

    var param = {
        "action" : action,
        "value" : JSON.stringify({"id" : id, "name" : name})
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getTransmissions();
}

function updateCar() {
    var id = $('#select-car').val();
    var name = $('#new-car-name').val();
    var carcase_id = $('#select-car-carcase').val();
    var engine_id = $('#select-car-engine').val();
    var transmission_id = $('#select-car-transmission').val();
    var car = {
        "id" : id,
        "name" : name,
        "carcase" : {"id" : carcase_id},
        "engine" : {"id" : engine_id},
        "transmission" : {"id" : transmission_id}
    };
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "updateCarXML";
    } else {
        action = "updateCar@";
    }

    var param = {
        "action" : action,
        "value" : JSON.stringify(car)
    };
    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getCars();
}

function getCarcases() {
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "getCarcasesXML";
    } else {
        action = "getCarcases@";
    }

    var param = {
        "action" : action,
        "value" : ""
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false,
        complete: function (data) {
            var tableData = "";
            var selectData = "<option disabled selected value=\"\">Select carcase</option>";
            var carcases = JSON.parse(data.responseText);
            for (index in carcases) {
                tableData += '<tr><td>' + carcases[index].id + '</td><td>' + carcases[index].name
                    + '</td><td><button type="button" onclick="deleteCarcase(' + carcases[index].id
                    + ')">delete</button></td></tr>';
                selectData +=
                    '<option value="' + carcases[index].id + '">' + carcases[index].name + '</option>';
            }
            document.getElementById("carcase-table-body").innerHTML = tableData;
            document.getElementById("select-carcase").innerHTML = selectData;
            document.getElementById("add-carcase").innerHTML = selectData;
            document.getElementById("select-car-carcase").innerHTML = selectData;
        }
    });
    return false;
}

function getEngines() {
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "getEnginesXML";
    } else {
        action = "getEngines@";
    }

    var param = {
        "action" : action,
        "value" : ""
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false,
        complete: function (data) {
            var tableData = "";
            var selectData = "<option disabled selected value=\"\">Select engine</option>";
            var engines = JSON.parse(data.responseText);
            for (index in engines) {
                tableData += '<tr><td>' + engines[index].id + '</td><td>' + engines[index].name
                    + '</td><td><button type="button" onclick="deleteEngine(' + engines[index].id
                    + ')">delete</button></td></tr>';
                selectData +=
                    '<option value="' + engines[index].id + '">' + engines[index].name + '</option>';
            }
            document.getElementById("engine-table-body").innerHTML = tableData;
            document.getElementById("select-engine").innerHTML = selectData;
            document.getElementById("add-engine").innerHTML = selectData;
            document.getElementById("select-car-engine").innerHTML = selectData;
        }
    });
    return false;
}

function getTransmissions() {
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "getTransmissionsXML";
    } else {
        action = "getTransmissions@";
    }

    var param = {
        "action" : action,
        "value" : ""
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false,
        complete: function (data) {
            var tableData = "";
            var selectData = "<option disabled selected value=\"\">Select transmission</option>";
            var transmissions = JSON.parse(data.responseText);
            for (index in transmissions) {
                tableData += '<tr><td>' + transmissions[index].id + '</td><td>' + transmissions[index].name
                    + '</td><td><button type="button" onclick="deleteTransmission('
                    + transmissions[index].id + ')">delete</button></td></tr>';
                selectData +=
                    '<option value="' + transmissions[index].id + '">'
                    + transmissions[index].name + '</option>';
            }
            document.getElementById("transmission-table-body").innerHTML = tableData;
            document.getElementById("select-transmission").innerHTML = selectData;
            document.getElementById("add-transmission").innerHTML = selectData;
            document.getElementById("select-car-transmission").innerHTML = selectData;
        }
    });
    return false;
}

function getCars() {
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "getCarsXML";
    } else {
        action = "getCars@";
    }

    var param = {
        "action" : action,
        "value" : ""
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false,
        complete: function (data) {
            var cars = JSON.parse(data.responseText);
            var tableData = "";
            var selectData = "";
            for (index in cars) {
                tableData += '<tr><td>' + cars[index].id + '</td><td>' + cars[index].name + '</td><td>'
                    + cars[index].carcase.name + '</td><td>' + cars[index].engine.name + '</td><td>'
                    + cars[index].transmission.name + '</td><td><button type="button" onclick="deleteCar('
                    + cars[index].id + ')">delete</button></td></tr>';
                selectData += '<option value="' + cars[index].id + '">' + cars[index].name + '</option>';
            }
            document.getElementById("car-table-body").innerHTML = tableData;
            document.getElementById("select-car").innerHTML = selectData;
        }
    });
    return false;
}

function deleteCarcase(id) {
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "deleteCarcaseXML";
    } else {
        action = "deleteCarcase@";
    }

    var param = {
        "action" : action,
        "value" : id
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getCarcases();
}

function deleteEngine(id) {
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "deleteEngineXML";
    } else {
        action = "deleteEngine@";
    }

    var param = {
        "action" : action,
        "value" : id
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getEngines();
}

function deleteTransmission(id) {
    var param = {
        "action" : "deleteTransmissionXML",
        "value" : id
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getTransmissions();
}

function deleteCar(id) {
    var type = $('#class-type').val();
    var action;

    if (type === "XML") {
        action = "deleteCarXML";
    } else {
        action = "deleteCar@";
    }

    var param = {
        "action" : action,
        "value" : id
    };

    $.ajax({
        url: "./cars",
        type: "POST",
        data: param,
        async: false
    });
    getCars();
}