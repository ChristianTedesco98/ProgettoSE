const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);

function initBrowsing() {

	document.getElementById("createButton").onclick=createUser;
	document.getElementById("backButton").onclick=goToVisualizePage;
}

function validateEmail(email) {
	const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}


function createUser() {
	let username_string=document.getElementById("inputUsername").value;
	let password_string=document.getElementById("inputPassword").value;
	let first_name_string=document.getElementById("inputName").value;
	let surname_string=document.getElementById("inputSurname").value;
	let email_string=document.getElementById("inputEmail").value;
	let cell_num_string=document.getElementById("inputCell").value;
	let birth_date_string=document.getElementById("inputBirth").value;
	let role_string = "";
	let role_html_string=document.getElementById("inputRole").value;
	if(role_html_string === "Planner"){
		role_string = "planner";
	}
	else{ 
		if(role_html_string === "Maintainer"){
			role_string = "maintainer";
		}
		else{ 
			if(role_html_string === "System Administrator"){
				role_string = "sys_adm";
			}
			else{ 
				role_string = "dbloader";
			}
		}
	}

	
	if(validateEmail(email_string) == false){
		$(".modal-header").addClass("bg-danger");
        $(".modal-title").html("Error");
        $("#ajax-response").html("Insert a valid email address!");
        $("#modal-ajax-response").modal();
        $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    $(".modal-header").removeClass("bg-danger bg-success");
                    $(".modal-title").html("")
                    $("#ajax-response").html("");
                });
        return;
	}
				
	
	

	$.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/SystemAdministrator/jsp/create_user.jsp",
        data: {username: username_string, password: password_string, 
			first_name: first_name_string, surname: surname_string, email: email_string, cell_num: cell_num_string, birth_date: birth_date_string, role: role_string},
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
				$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
				    $(".modal-header").removeClass("bg-danger bg-success");
				    $(".modal-title").html("")
				    $("#ajax-response").html("");
				});    
            }
            $("#ajax-response").html(result[0].msg);
            $("#modal-ajax-response").modal();
        },
        error: function () {
            $(".modal-header").addClass("bg-danger");
            $(".modal-title").html("Error");
            $("#ajax-response").html("Error during execute ajax operation");
        }
    });

	
}

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_users.html";
}



let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
