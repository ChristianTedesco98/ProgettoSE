const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var params = new URLSearchParams(window.location.search);
var weekParam = params.get('week');
var idParam = params.get('id');
var areaParam = params.get('area');
var typeParam = params.get('type');
var eitParam= params.get('eit');
var id_proc;

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+"/MaintenanceProject/Planner/jsp/visualize_selected.jsp",{id_activity: idParam}, function (data) {
        renderGui(data);
    }).fail(function () {
    });

    var procedure_button = document.getElementById("procedureButton");
    procedure_button.onclick = showProcedure;
}

function showProcedure(){
    var win = window.open("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_activity_procedure.jsp?id_procedure="+id_proc, '_blank');
    win.focus()
}


function renderGui(data) {
    let weekStatic= $("#week").html();
    let activityStatic= $("#activity").html();
    let staticHtml = $("#skills-list-row-template").html();

    let noteStatic= $("#note-template").html();
    let descriptionStatic= $("#description-template").html();
    let smpStatic= $("#smp-template").html();

	document.getElementById("linkForward").href="verify_selected.html?id="+idParam+'&week='+weekParam+
    '&area='+areaParam+'&type='+typeParam+'&eit='+eitParam;

	weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
    document.getElementById('week').innerHTML=weekStatic;
    activityStatic=activityStatic.replace(/{Activity}/ig, idParam+' - '+areaParam+' - '+typeParam+' - '+eitParam+"'");
    document.getElementById('activity').innerHTML=activityStatic;
    $.each(data, function (index, obj) {
        id_proc = obj.id_procedure;
        noteStatic = noteStatic.replace(/{Note}/ig, obj.workspace_notes);
        descriptionStatic = descriptionStatic.replace(/{Description}/ig, obj.description);
        smpStatic = smpStatic.replace(/{Smp}/ig, obj.name);
        $('#note').append(noteStatic);
        $('#description').append(descriptionStatic);
        $('#smp').append(smpStatic);

        let array=obj.skills.substring(1, obj.skills.length-1);
        array= array.split(",");
        let row = staticHtml;
        var i;
        for (i = 0; i < array.length; i++) {
            row = row.replace(/{Item}/ig, array[i]);
            document.getElementById("linkForward").href+="&skill"+i+"="+array[i];
            $('#skills-list-rows').append(row);
            row = $("#skills-list-row-template").html();
        }
        document.getElementById("linkForward").href+='&note='+obj.workspace_notes;
    });

}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/Planner/html/visualize_selected.html";
    $(window).scrollTop(0);
});

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
