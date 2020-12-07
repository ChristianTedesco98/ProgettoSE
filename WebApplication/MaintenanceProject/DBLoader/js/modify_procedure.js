const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);
var myParam = params.get('id');

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_procedure.jsp", {id_procedure: myParam}, function (data) {
        renderGui(data);
    })

    document.getElementById("modifyButton").onclick=modifyProcedure;
    document.getElementById("backButton").onclick=goToVisualizePage;
}

function modifyProcedure() {

	let id_procedure_string=myParam;
	let smp_string=document.getElementById("inputSMP").value;

	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/modify_procedure.jsp",
        data: {id_procedure:id_procedure_string, smp: smp_string},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
				$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
				window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_procedures.html";
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
	window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_procedures.html";
}


function renderGui(data) {

    let inputSMP = document.getElementById("inputSMP");
	

	inputSMP.value=data[0].smp;
}



let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});