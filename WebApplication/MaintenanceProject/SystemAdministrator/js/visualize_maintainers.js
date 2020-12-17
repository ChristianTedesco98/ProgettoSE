const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_maintainers.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("homeButton").onclick=goToHomePage;
}


function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/home_sys.html";
}


function renderGui(data) {

    let staticHtml = $("#maintainers-list-row-template").html();


    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Username}/ig, obj.username);
        row = row.replace(/{Name}/ig, obj.first_name);
        row = row.replace(/{Surname}/ig, obj.surname);
        row = row.replace(/{CellNum}/ig, obj.cell_num);
        row = row.replace(/{Email}/ig, obj.email);
        let buttons = '<a href="../html/assign_competencies.html?id='+obj.id_user+'"    title="Assign Competencies" data-toggle="tooltip"><img src="../imgs/add.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#maintainers-list-rows').append(row);
    });

    $('#maintainers-list-rows tr').each(function(){
        $(this).find('td').each(function(){

        })
    })
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_maintainers.html";
    $(window).scrollTop(0);
});




let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});

