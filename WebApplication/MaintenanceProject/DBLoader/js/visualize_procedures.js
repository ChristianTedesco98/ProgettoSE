const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_procedures.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}

function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/create_procedure.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/DBLoader/html/home_db.html";
}


$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_procedures.html";
    $(window).scrollTop(0);
});

function renderGui(data) {
    let staticHtml = $("#procedures-list-row-template").html();

    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_procedure);
        row = row.replace(/{SMP}/ig, obj.smp);
        let buttons = '<a href="../html/assign_competencies.html?id='+obj.id_procedure+'"    title="Assign Competencies" data-toggle="tooltip"><img src="../imgs/add.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' + '<a href="../html/modify_procedure.html?id='+obj.id_procedure+'"     title="Edit" data-toggle="tooltip"><img src="../imgs/edit.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' +  '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteProcedure(\'' + obj.id_procedure + '\')" style="width: 20px; height:20px"></button>';        row = row.replace(/{Actions}/ig, buttons);
        $('#procedures-list-rows').append(row);
    });

	$('#procedures-list-rows tr').each(function(){
	    $(this).find('td').each(function(){

	    })
	})
}

function deleteProcedure(id) {
    
    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/delete_procedure.jsp",
        data: {id_procedure: id},
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