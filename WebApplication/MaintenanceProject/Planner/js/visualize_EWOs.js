const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var params = new URLSearchParams(window.location.search);
var myParam = params.get('week');

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_EWOs.jsp", {week: myParam}, function (data) {
        renderGui(data);
    }).fail(function () {
        let weekStatic= $("#week").html();
        weekStatic=weekStatic.replace(/{Week}/ig, myParam);
        document.getElementById('week').innerHTML=weekStatic;
    });
}


function renderGui(data) {
    let weekStatic= $("#week").html();
    let staticHtml = $("#activities-list-row-template").html();
    let dayRan=Math.floor(Math.random() * 6)+1;
    weekStatic=weekStatic.replace(/{Week}/ig, myParam);
    document.getElementById('week').innerHTML=weekStatic;
    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{EWOId}/ig, obj.id_activity);
        row = row.replace(/{Area}/ig, obj.area);
        row = row.replace(/{Type}/ig, obj.type);
        row = row.replace(/{EIT}/ig, obj.eit);
        let buttons = '<a href="compile_EWOs.html?day='+dayRan+'&id='+obj.id_activity+'&week='+myParam+'&area='+obj.area+'&type='+obj.type+'&eit='+obj.eit+'"><button type="button" class="btn btn-dark" style="height:40px; width: 150px; font-size: 20px; text-align: center;"><b>Select</b></button></a>'
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
