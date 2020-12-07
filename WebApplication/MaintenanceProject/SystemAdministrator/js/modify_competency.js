const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);
var myParam = params.get('id');

function initBrowsing() {


    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_competency.jsp", {id_competency: myParam}, function (data) {
        renderGui(data);
    })


	document.getElementById("modifyButton").onclick=modifyCompetence;
	document.getElementById("backButton").onclick=goToVisualizePage;

}

function modifyCompetence() {
	let id_competency_string=myParam;
	let name_competency_string=document.getElementById("inputNameCompetency").value;
	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/modify_competency.jsp",
        data: {id_competency: id_competency_string, 
			name_competency: name_competency_string},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");    
                $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_competencies.html";
                    $(window).scrollTop(0);
                });
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

function renderGui(data) {

    let inputNameCompetency = document.getElementById("inputNameCompetency");
	inputNameCompetency.value=data[0].name;
}

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_competencies.html";
}


let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});