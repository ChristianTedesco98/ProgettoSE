const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_competencies.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}


function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/create_competency.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/home_sys.html";
}

function renderGui(data) {

    let staticHtml = $("#competencies-list-row-template").html();

    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_competency);
        row = row.replace(/{Name}/ig, obj.name_competency);
        let buttons = '<a href="../html/modify_competency.html?id='+obj.id_competency+'"title="Edit" data-toggle="tooltip"><img src="../imgs/edit.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' + '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteCompetence(\''+ obj.id_competency+'\')" style="width: 20px; height:20px"></a>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#users-list-rows').append(row);
    });

	$('#users-list-rows tr').each(function(){
	    $(this).find('td').each(function(){

	    })
	})
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_competencies.html";
    $(window).scrollTop(0);
});

function deleteCompetence(id) {
    
    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/delete_competency.jsp",
        data: {id_competency: id},
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