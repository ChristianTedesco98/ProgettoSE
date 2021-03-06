const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);

function initBrowsing() {

	document.getElementById("createButton").onclick=createSite;
	document.getElementById("backButton").onclick=goToVisualizePage;
}

function createSite() {
	let material_string=document.getElementById("inputMaterial").value;

	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/create_material.jsp",
        data: {name_material: material_string},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
                $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_materials.html";
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
	window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_materials.html";
}



let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
