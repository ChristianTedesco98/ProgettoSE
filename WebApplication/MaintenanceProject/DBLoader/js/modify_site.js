const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);
var myParam = params.get('id');

function initBrowsing() {

    $.getJSON(
        "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_site.jsp", {id_site: myParam}, function (data) {
        renderGui(data);
    })

    document.getElementById("modifyButton").onclick=modifySite;
    document.getElementById("backButton").onclick=goToVisualizePage;
}

function modifySite() {

	let id_site_string=myParam;
	let area_string=document.getElementById("inputArea").value;
	let factory_site_string=document.getElementById("inputFactorySite").value;
	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/modify_site.jsp",
        data: {id_site: id_site_string, area: area_string, factory_site: factory_site_string},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
				$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
				window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_sites.html";
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
	window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_sites.html";
}

function renderGui(data) {

    let inputArea = document.getElementById("inputArea");
	let inputFactorySite = document.getElementById("inputFactorySite");
	

	inputArea.value=data[0].area;
	inputFactorySite.value=data[0].factory_site;
}

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});