const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

var params = new URLSearchParams(window.location.search);
var myParam = params.get('id');

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/visualize_procedure.jsp", {id_procedure: myParam}, function (data) {
        renderGui(data);
    })

    document.getElementById("modifyButton").onclick=modifyProcedure;
    document.getElementById("backButton").onclick=goToVisualizePage;
}


function getByteArray(file) {
    return new Promise(function(resolve, reject) {
        let fileReader = new FileReader();
        fileReader.readAsArrayBuffer(file);
        fileReader.onload = function(ev) {
            const array = new Uint8Array(ev.target.result);
            const fileByteArray = [];
            for (let i = 0; i < array.length; i++) {
                fileByteArray.push(array[i]);
            }
            resolve(array);
        }
        fileReader.onerror = reject;
    })
 }
 

 function bin2String(array) {
    return String.fromCharCode.apply(String, array);
}

function string2Bin(str) {
    var result = [];
    for (var i = 0; i < str.length; i++) {
      result.push(str.charCodeAt(i));
    }
    return result;
}

function modifyProcedure() {

	let id_procedure_string=myParam;
    let name_procedure_string=document.getElementById("inputName").value;
    let smp_file = document.getElementById ("inputSMP").files[0];



    if(name_procedure_string == null || name_procedure_string.length < 4){

        $(".modal-header").addClass("bg-danger");
        $(".modal-title").html("Error");
        $("#ajax-response").html("Insert a name with 4 characters minimum!");
        $("#modal-ajax-response").modal();
        $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    $(".modal-header").removeClass("bg-danger bg-success");
                    $(".modal-title").html("")
                    $("#ajax-response").html("");
                });
        return;
            
    }


    if(smp_file == null){

        $(".modal-header").addClass("bg-danger");
        $(".modal-title").html("Error");
        $("#ajax-response").html("Load a PDF file!");
        $("#modal-ajax-response").modal();
        $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    $(".modal-header").removeClass("bg-danger bg-success");
                    $(".modal-title").html("")
                    $("#ajax-response").html("");
                });
        return;
            
    }

    if(smp_file == null){
        $.ajax({
            type: "POST",
            url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/modify_procedure_name.jsp",
            data: {id_procedure:id_procedure_string, name_procedure: name_procedure_string},
            dataType: "html",
            success: function (msg) {
                let result = JSON.parse(msg);
                if (result[0].err_code == "0") {
                    $(".modal-header").addClass("bg-success");
                    $(".modal-title").html("Success");
                    $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_procedures.html";
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
    else{

        getByteArray(smp_file).then((byteArray) => {
            var smp_string = bin2String(byteArray);
    
            $.ajax({
                type: "POST",
                url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/DBLoader/jsp/modify_procedure_smp.jsp",
                data: {id_procedure:id_procedure_string, name_procedure: name_procedure_string, smp: smp_string},
                dataType: "html",
                success: function (msg) {
                    let result = JSON.parse(msg);
                    if (result[0].err_code == "0") {
                        $(".modal-header").addClass("bg-success");
                        $(".modal-title").html("Success");
                        $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                        window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_procedures.html";
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
        })







    }
	
}

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_procedures.html";
}


function renderGui(data) {

    let inputName = document.getElementById("inputName");
	inputName.value=data[0].name;
}



let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});