const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_activities.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });
}


function renderGui(data) {
    let weekStatic= $("#week").html();
    let staticHtml = $("#activities-list-row-template").html();

    weekStatic.replace(/{Week}/ig, myParam);
    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_activity);
        row = row.replace(/{Area}/ig, obj.area);
        row = row.replace(/{Type}/ig, obj.type);
        row = row.replace(/{EIT}/ig, obj.eit);
        let buttons = '<a href="Planner/visualize_selected.html"><button type="button" class="btn btn-dark" style="height:40px; width: 150px; font-size: 20px; text-align: center;"><b>Select</b></button></a>'
        row = row.replace(/{Select}/ig, buttons);
        $('#activities-list-rows').append(row);
    });

    $('#activities-list-rows tr').each(function(){
        $(this).find('td').each(function(){

        })
    })
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/Planner/html/visualize_activities.html";
    $(window).scrollTop(0);
});


/*function deleteUser(id) {

    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/delete_user.jsp",
        data: {id_user: id},
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
}*/

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});

