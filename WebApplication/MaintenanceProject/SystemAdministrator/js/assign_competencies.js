const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);
var myParam = params.get('id');
var ids = [];

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_assigned_competencies.jsp", {id_user: myParam}, function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("backButton").onclick=goToBack;
    document.getElementById("assignButton").onclick=assign;
}


function goToBack(){
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_maintainers.html";
}

function assign(){
    let checks = []
    ids.forEach(function (id){
        if($('#competency'+id).is(":checked")){
            checks.push(1);
        }
        else{
            checks.push(0);
        }
    });

    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/assign_competencies.jsp",
        data: {id_user: myParam, num_competencies: checks.length, checks: checks.toString(), ids: ids.toString()},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
                $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_maintainers.html";
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

function renderGui(data) {

    let staticHtml = $("#competencies-list-row-template").html();

    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{competencies}/ig, obj.name_competency);
        let string_checked = "";
        if(obj.assigned == "1")
            string_checked = "checked";
        let checkbox = '<input '+string_checked+' type="checkbox" class="checkbox_competencies" id="competency'+obj.id_competency+'">'
        ids.push(obj.id_competency);
        row = row.replace(/{check}/ig, checkbox);
        $('#competencies-list-rows').append(row);
    });

	$('#competencies-list-rows tr').each(function(){
	    $(this).find('td').each(function(){

	    })
	})
}


let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});