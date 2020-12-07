const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);
var myParam = params.get('id');

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_typology.jsp", {id_typology: myParam}, function (data) {
        renderGui(data);
    })

    document.getElementById("modifyButton").onclick=modifyTypology;
    document.getElementById("backButton").onclick=goToVisualizePage;
}

function modifyTypology() {

	let id_typology_string=myParam;
	let name_typology_string=document.getElementById("inputTypology").value;
	
	
	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/modify_typology.jsp",
        data: {id_typology: id_typology_string, name_typology: name_typology_string},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
				$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
				window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_typologies.html";
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

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_typologies.html";
}

function renderGui(data) {

    let inputTypology = document.getElementById("inputTypology");
	

	inputTypology.value=data[0].name_typology;
}


let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});