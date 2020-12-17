const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var Planners = [], weeks = [], sites = [], typologies = [], procedures=[];
function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_planners.jsp", function (data) {
        $.each(data, function (index, obj) {
            Planners.push(obj.id_planner);
        });

        personalizeSelect(Planners, "inputPlanner");
        
    }).fail(function () {
    });

    var i;
    for(i=1; i<54; i++){
        weeks.push(i);
    }
    personalizeSelect(weeks, "inputWeek");

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_sites.jsp", function (data) {
        var str = "";
        $.each(data, function (index, obj) {
            var site = obj.area +" - "+obj.factory_site;
            str += "<option>" + site + "</option>";
            sites[site] = obj.id_site;
        });
        document.getElementById("inputSite").innerHTML = str;
    }).fail(function () {
    });

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_typologies.jsp", function (data) {
        var str = "";
        $.each(data, function (index, obj) {
            var typology = obj.name_typology;
            str += "<option>" + typology + "</option>";
            typologies[typology] = obj.id_typology;
        });
        document.getElementById("inputTypology").innerHTML = str;
    }).fail(function () {
    });

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_procedures.jsp", function (data) {
        var str = "";
        $.each(data, function (index, obj) {
            var name_procedure = obj.name;
            str += "<option>" + name_procedure + "</option>";
            procedures[name_procedure] = obj.id_procedure;
        });
        document.getElementById("inputProcedure").innerHTML = str;
    }).fail(function () {
    });

    document.getElementById("backButton").onclick=goToVisualizePage;
    document.getElementById("createButton").onclick=createActivity;
    document.getElementById("inputTime").oninput = function() {
        var max = parseInt(this.max);
        var min = parseInt(this.min);
        if (parseInt(this.value) > max) {
            this.value = max; 
        }
        if (parseInt(this.value) < min) {
            this.value = min; 
        }
    }
}


function personalizeSelect(array, idSelect, name){
    var str = ""
    for (var item of array) {
            str += "<option>" + item + "</option>"
    }
    
    document.getElementById(idSelect).innerHTML = str;
}

function createActivity() {
    var description_string = document.getElementById("inputDescription").value;
    var week_string = document.getElementById("inputWeek").value;
    var interruptible_string;
    if($('#inputInterruptible').is(":checked")){
        interruptible_string = "true";
    }
    else{
        interruptible_string = "false";
    }

    var eit_string = document.getElementById("inputTime").value;
    var id_typology_string = typologies[document.getElementById("inputTypology").value];
    var id_site_string = sites[document.getElementById("inputSite").value];
    var id_procedure_string = procedures[document.getElementById("inputProcedure").value];
    var id_user_string = document.getElementById("inputPlanner").value;

    var ewo_string;
    if($('#inputEWO').is(":checked")){
        ewo_string = "true";
    }
    else{
        ewo_string = "false";
    }

    
    if(eit_string == ""){
        $(".modal-header").addClass("bg-danger");
        $(".modal-title").html("Error");
        $("#ajax-response").html("Insert a valid time for the activity!");
        $("#modal-ajax-response").modal();
        $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    $(".modal-header").removeClass("bg-danger bg-success");
                    $(".modal-title").html("")
                    $("#ajax-response").html("");
                });
        return;
            
    }

    
	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/create_activity.jsp",
        data: {description: description_string, week: week_string, interruptible: interruptible_string, eit: eit_string, 
            id_typology: id_typology_string, id_site: id_site_string, id_procedure: id_procedure_string, id_user: id_user_string, ewo: ewo_string},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
				$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
				    window.location.pathname = "../MaintenanceProject/Planner/html/visualize_maintenance_activities.html";
				    $(window).scrollTop(0);
				});
            } else {
                $(".modal-header").addClass("bg-danger");
                $(".modal-title").html("Error");
				$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
				    $(".modal-header").removeClass("bg-danger bg-success");
				    $(".modal-title").html("")
				    $("#ajax-response").html("");
				});    
            }
            $("#ajax-response").html(result[0].msg);
            $("#modal-ajax-response").modal();
        },
        error: function () {
            $(".modal-header").addClass("bg-danger");
            $(".modal-title").html("Error");
            $("#ajax-response").html("Error during execute ajax operation");
        }
    });

	
}

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/Planner/html/visualize_maintenance_activities.html";
}



let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
