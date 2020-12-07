const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_typologies.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}

function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/create_typology.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/DBLoader/html/home_db.html";
}

function renderGui(data) {

    let staticHtml = $("#typologies-list-row-template").html();


    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_typology);
        row = row.replace(/{NameTypology}/ig, obj.name_typology);
        let buttons = '<a href="../html/modify_typology.html?id='+obj.id_typology+'"	 title="Edit" data-toggle="tooltip"><img src="../imgs/edit.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' + '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteTypology(\'' + obj.id_typology + '\')" style="width: 20px; height:20px"></button>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#typologies-list-rows').append(row);
    });

	$('#typologies-list-rows tr').each(function(){
	    $(this).find('td').each(function(){

	    })
	})
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_typologies.html";
    $(window).scrollTop(0);
});

function deleteTypology(id) {  
    
    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/delete_typology.jsp",
        data: {id_typology: id},
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