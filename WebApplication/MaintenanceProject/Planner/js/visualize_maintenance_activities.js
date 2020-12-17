const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_maintenance_activities.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}

function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/Planner/html/create_activity.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/Planner/html/home_planner.html";
}


function renderGui(data) {

    let staticHtml = $("#activities-list-row-template").html();


    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_activity);
        row = row.replace(/{Week}/ig, obj.week);
        row = row.replace(/{User}/ig, obj.username);
        if(obj.interruptible=="t")
            interr="Yes"
        else
            interr="No"
        row = row.replace(/{Interruptible}/ig, interr);
        row = row.replace(/{Eit}/ig, obj.eit);
        row = row.replace(/{Area}/ig, obj.area);
        row = row.replace(/{Type}/ig, obj.type);
        row = row.replace(/{Procedure}/ig, obj.name);
        let buttons = '<a title="Edit" data-toggle="tooltip" href="../html/modify_activity.html?id='+obj.id_activity+'"><img src="../imgs/edit.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' + '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteActivity(\'' + obj.id_activity + '\')" style="width: 20px; height:20px"></button>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#activities-list-rows').append(row);
    });

    $('#activities-list-rows tr').each(function(){
        $(this).find('td').each(function(){

        })
    })
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/Planner/html/visualize_maintenance_activities.html";
    $(window).scrollTop(0);
});


function deleteActivity(id) {  

    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/delete_activity.jsp",
        data: {id_activity: id},
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

