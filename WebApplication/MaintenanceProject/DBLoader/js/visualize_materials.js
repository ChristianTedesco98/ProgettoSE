const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_materials.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}

function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/create_material.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/DBLoader/html/home_db.html";
}

function renderGui(data) {

    let staticHtml = $("#materials-list-row-template").html();


    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_material);
        row = row.replace(/{NameMaterial}/ig, obj.name_material);
        let buttons = '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteMaterial(\'' + obj.id_material + '\')" style="width: 20px; height:20px"></button>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#materials-list-rows').append(row);
    });

	$('#materials-list-rows tr').each(function(){
	    $(this).find('td').each(function(){

	    })
	})
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_materials.html";
    $(window).scrollTop(0);
});

function deleteMaterial(id) {  
    
    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/delete_material.jsp",
        data: {id_material: id},
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