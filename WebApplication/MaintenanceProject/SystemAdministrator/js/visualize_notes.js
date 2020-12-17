const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_notes.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}


function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/create_note.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/home_sys.html";
}

function renderGui(data) {

    let staticHtml = $("#notes-list-row-template").html();

    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_note);
        row = row.replace(/{Description}/ig, obj.description);
        activity = obj.id_activity
        if(activity=="null")
            activity = ""
        row = row.replace(/{Id_activity}/ig, activity);
        site = obj.id_site
        if(site=="null")
            site = ""
        row = row.replace(/{Id_site}/ig, site);
        let buttons = '<a href="../html/modify_note.html?id='+obj.id_note+'"title="Edit" data-toggle="tooltip"><img src="../imgs/edit.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' + '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteNote(\''+ obj.id_note+'\')" style="width: 20px; height:20px"></a>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#notes-list-rows').append(row);
    });

	$('#notes-list-rows tr').each(function(){
	    $(this).find('td').each(function(){

	    })
	})
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_notes.html";
    $(window).scrollTop(0);
});

function deleteNote(id) {
    
    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/delete_note.jsp",
        data: {id_note: id},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
            } else {
                $(".modal-header").addClass("bg-danger");
                $(".modal-title").html("Error");
            }
            $("#ajax-response").html(result[0].msg);
            $("#modal-ajax-response").modal();
            $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                $(".modal-header").removeClass("bg-danger bg-success");
                $(".modal-title").html("")
                $("#ajax-response").html("");
            });    
        },
        error: function () {
            $(".modal-header").addClass("bg-danger");
            $(".modal-title").html("Error");
            $("#ajax-response").html("Error during execute ajax operation");
        }
    });
}

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});