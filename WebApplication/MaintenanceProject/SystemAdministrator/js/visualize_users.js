const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_users.jsp", function (data) {
        renderGui(data);
    }).fail(function () {
    });

    document.getElementById("createButton").onclick=goToCreatePage;
    document.getElementById("homeButton").onclick=goToHomePage;
}

function goToCreatePage() {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/create_user.html";
}

function goToHomePage(){
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/home_sys.html";
}


function renderGui(data) {

    let staticHtml = $("#users-list-row-template").html();


    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_user);
        row = row.replace(/{Username}/ig, obj.username);
        row = row.replace(/{Name}/ig, obj.first_name);
        row = row.replace(/{Surname}/ig, obj.surname);
        row = row.replace(/{BirthDate}/ig, obj.birth_date);
        row = row.replace(/{Password}/ig, obj.password);
        row = row.replace(/{CellNum}/ig, obj.cell_num);
        row = row.replace(/{Email}/ig, obj.email);
        row = row.replace(/{Role}/ig, obj.role);
        let buttons = '<a title="Edit" data-toggle="tooltip" href="../html/modify_user.html?id='+obj.id_user+'"><img src="../imgs/edit.png" style="margin-left: 5px; margin-right: 5px; width: 20px; height:20px"></a>' + '<button type="button" style="border:none; background:none; width:20px; outline:none; margin-right: 5px;"><img src="../imgs/delete.png" onclick="deleteUser(\'' + obj.id_user + '\')" style="width: 20px; height:20px"></button>'
        row = row.replace(/{Actions}/ig, buttons);
        $('#users-list-rows').append(row);
    });

    $('#users-list-rows tr').each(function(){
        $(this).find('td').each(function(){

        })
    })
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_users.html";
    $(window).scrollTop(0);
});


function deleteUser(id) {  

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
}

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});

