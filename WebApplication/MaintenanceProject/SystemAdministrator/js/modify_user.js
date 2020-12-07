const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);
var myParam = params.get('id');

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/visualize_user.jsp", {id_user: myParam}, function (data) {
        renderGui(data);
    })

	document.getElementById("modifyButton").onclick=modifyUsers;
	document.getElementById("backButton").onclick=goToVisualizePage;
}

function modifyUsers() {
	let id_user_string=myParam;
	let username_string=document.getElementById("inputUsername").value;
	let password_string=document.getElementById("inputPassword").value;
	let first_name_string=document.getElementById("inputName").value;
	let surname_string=document.getElementById("inputSurname").value;
	let email_string=document.getElementById("inputEmail").value;
	let cell_num_string=document.getElementById("inputCell").value;
	let birth_date_string=document.getElementById("inputBirth").value;
	let role_string = "";
	let role_html_string=document.getElementById("inputRole").value;
	if(role_html_string === "Planner")
		role_string = "planner";
	else 
		if(role_html_string === "Maintainer")
			role_string = "maintainer";
		else 
			if(role_html_string === "System Administrator")
				role_string = "sys_adm";
			else 
				role_string = "dbloader";
	
	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/modify_user.jsp",
        data: {id_user: id_user_string, username: username_string, 
				password: password_string, first_name: first_name_string, surname: surname_string, email: email_string,
				cell_num: cell_num_string, birth_date: birth_date_string, role: role_string},
        dataType: "html",
        success: function (msg) {
            let result = JSON.parse(msg);
            if (result[0].err_code == "0") {
                $(".modal-header").addClass("bg-success");
                $(".modal-title").html("Success");
				$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
				    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_users.html";
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

    let inputUsername = document.getElementById("inputUsername");
	let inputName = document.getElementById("inputName");
	let inputSurname = document.getElementById("inputSurname");
	let inputPassword = document.getElementById("inputPassword");
	let inputEmail = document.getElementById("inputEmail");
	let inputCell = document.getElementById("inputCell");
	let inputBirth = document.getElementById("inputBirth");
	let inputRole = document.getElementById("inputRole");

	inputUsername.value=data[0].username;
	inputPassword.value=data[0].password;
	inputName.value=data[0].first_name;
	inputSurname.value=data[0].surname;
	inputEmail.value=data[0].email;
	inputCell.value=data[0].cell_num;
	inputBirth.value=data[0].birth_date;

	if (data[0].role==="maintainer") {
		inputRole[2].selected=true;
	} else if (data[0].role==="planner") {
		inputRole[1].selected=true;
	} else if (data[0].role==="dbloader") {
		inputRole[3].selected=true;
	} else if (data[0].role==="sys_adm") {
		inputRole[4].selected=true;
	}
}

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_users.html";
}

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
