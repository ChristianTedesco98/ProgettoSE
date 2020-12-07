const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {
    
    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_sites.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}

function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/create_site.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/DBLoader/html/home_db.html";
}


$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_sites.html";
    $(window).scrollTop(0);
});

function renderGui(data) {
    
    let staticHtml = $("#sites-list-row-template").html();

 
    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_site);
        row = row.replace(/{Area}/ig, obj.area);
        row = row.replace(/{FactorySite}/ig, obj.factory_site);
        let buttons = '<a href="../html/modify_site.html?id='+obj.id_site+'"	 title="Edit" data-toggle="tooltip"><img src="../imgs/edit.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' + '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteSite(\'' + obj.id_site + '\')" style="width: 20px; height:20px"></a>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#sites-list-rows').append(row);
    });

	$('#sites-list-rows tr').each(function(){
	    $(this).find('td').each(function(){

	    })
	})
}


function deleteSite(id) {  
    
    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/delete_site.jsp",
        data: {id_site: id},
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