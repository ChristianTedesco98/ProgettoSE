const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var Sites = [], Activities = [];

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_sites.jsp", function (data) {
        $.each(data, function (index, obj) {
            Sites.push(obj.id_site);
        });

        personalizeSelect(Sites, "inputSite");
        
    }).fail(function () {
    });

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_activities.jsp", function (data) {
        $.each(data, function (index, obj) {
            Activities.push(obj.id_activity);
        });

        personalizeSelect(Activities, "inputActivity");
        
    }).fail(function () {
    });

    

    document.getElementById("backButton").onclick=goToVisualizePage;
    document.getElementById("createButton").onclick=createNote;
}


function personalizeSelect(array, idSelect, name){
    var str = ""
    for (var item of array) {
            str += "<option>" + item + "</option>"
    }
    
    document.getElementById(idSelect).innerHTML = str;
}

$(document).ready(function() {
    $('input[type="radio"]').click(function() {
        if($(this).attr('id') == 'Site') {
            document.getElementById("inputActivity").disabled = true;    
            document.getElementById("inputSite").disabled = false;  
        }
        else if ($(this).attr('id') == 'Activity'){
            document.getElementById("inputSite").disabled = true; 
            document.getElementById("inputActivity").disabled = false;   
        }
    });
 });

function createNote() {
    var description_string = document.getElementById("inputDescription").value;
    if($('#Site').is(":checked")){
        var site_string = document.getElementById("inputSite").value;
        $.ajax({
            type: "POST",
            url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/create_note_site.jsp",
            data: {description: description_string, id_site: site_string},
            dataType: "html",
            success: function (msg) {
                let result = JSON.parse(msg);
                if (result[0].err_code == "0") {
                    $(".modal-header").addClass("bg-success");
                    $(".modal-title").html("Success");
                    $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                        window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_notes.html";
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
    else if ($('#Activity').is(":checked")){
        var activity_string = document.getElementById("inputActivity").value;
        $.ajax({
            type: "POST",
            url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/create_note_activity.jsp",
            data: {description: description_string, id_activity: activity_string},
            dataType: "html",
            success: function (msg) {
                let result = JSON.parse(msg);
                if (result[0].err_code == "0") {
                    $(".modal-header").addClass("bg-success");
                    $(".modal-title").html("Success");
                    $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                        window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_notes.html";
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
    else{ 
        $(".modal-header").addClass("bg-danger");
        $(".modal-title").html("Error");
        $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
            $(".modal-header").removeClass("bg-danger bg-success");
            $(".modal-title").html("")
            $("#ajax-response").html("");
        });    
        $("#ajax-response").html("You must select an activity or a site!");
        $("#modal-ajax-response").modal();
    }

}

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_notes.html";
}



let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
