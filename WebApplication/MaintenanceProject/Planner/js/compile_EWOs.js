const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var params = new URLSearchParams(window.location.search);
var weekParam = params.get('week');
var dayParam= params.get('day');
var idParam= params.get('id');
var areaParam= params.get('area');
var typeParam= params.get('type');
var eitParam= params.get('eit');
var ids = [];
var names=[];
var notesParam="";
var DayName="";
var DayNum=0;
var skillsQueryString="";
var skillsNum=0;
var hrefString = "";

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_ewo_details.jsp",{id_activity: idParam}, function (data) {
        renderGui(data);
    }).fail(function () {
        let weekStatic= $("#week").html();
        weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
        document.getElementById('week').innerHTML=weekStatic;
    });

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/visualize_competencies.jsp",{id_activity: idParam},function (data) {
        renderSkills(data);
    }).fail(function () {
    });

    document.getElementById('forwardButton').onclick = forwardEWO;
    document.getElementById("time").oninput = function() {
        var max = parseInt(this.max);
        var min = parseInt(this.min);
        if (parseInt(this.value) > max) {
            this.value = max;
        }
        if (parseInt(this.value) < min) {
            this.value = min;
        }
    }
}

function formatDate(date){
    let day = ("0" + date.getDate()).slice(-2);
    let month = ("0" + (date.getMonth() + 1)).slice(-2);
    let year = date.getFullYear();
    return day + "/" + month + "/" + year;
}

function forwardEWO() {
    let desc=document.getElementById('description').value;
    let eit=document.getElementById('time').value;

    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+"/MaintenanceProject/Planner/jsp/modify_ewo.jsp",
        data: {id_activity: idParam, description: desc, eit: eit, day: dayParam},
        dataType: "html"
    });

    var one_competency_checked = false;
    let checks = [];
    ids.forEach(function (id){
        if($('#competency'+id).is(":checked")){
            checks.push(1);
            one_competency_checked = true;
        }
        else{
            checks.push(0);
        }
    });


    if(one_competency_checked == false){
        
        $(".modal-header").addClass("bg-danger");
        $(".modal-title").html("Error");
        $("#ajax-response").html("Select at least one skill needed!");
        $("#modal-ajax-response").modal();
        $('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
                    $(".modal-header").removeClass("bg-danger bg-success");
                    $(".modal-title").html("")
                    $("#ajax-response").html("");
                });
        document.getElementById("forwardLink").href = "#";
        document.getElementById("forwardLink").onclick = "return false;";
        return;
    }

    document.getElementById("forwardLink").href = hrefString;
    document.getElementById("forwardLink").onclick = "";
    

    $.ajax({
        type: "POST",
        url: "http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/assign_ewo_skills.jsp",
        data: {id_activity: idParam, num_competencies: checks.length, checks: checks.toString(), ids: ids.toString()},
        dataType: "html"
    });

    var j=0;
    for(var i=0;i<checks.length; i++) {
        if (checks[i]===1) {
            skillsQueryString+="&skill"+j+"="+names[i];
            j+=1;
            skillsNum+=1;
        }
    }
    document.getElementById("forwardLink").href+=skillsQueryString;
    document.getElementById("forwardLink").href+="&skillsNum="+skillsNum;
}

function switchDay(value) {
    switch (value) {
        case 1:
            return "Monday";
        case 2:
            return "Tuesday";
        case 3:
            return "Wednesday";
        case 4:
            return "Thursday";
        case 5:
            return "Friday";
        case 6:
            return "Saturday";
        case 7:
            return "Sunday";

    }
}


function renderGui(data) {
    var d = new Date();
    var year = d.getYear()+1900;
    var week = weekParam;
    var d = new Date("Jan 01, " + year + " 01:00:00");
    var w = d.getTime() + 604800000 * (week - 1);
    wd=new Date(w);
    var daynum=wd.getDate();
    var monthnum=wd.getMonth();
    var dayname=wd.getDay();
    var d1 = new Date(w - ((dayname-1)*86400000) +(dayParam-1)*86400000 );
    dayParam=d1.getDay();
    var dayName = switchDay(d1.getDay());
    var dayNum = d1.getDate();
    DayName = dayName;
    DayNum = dayNum;
    let weekStatic= $("#week").html();
    let activityStatic= $("#activity").html();
    let dayNameStatic= $("#dayName").html();
    let dayNumStatic= $("#dayNum").html();

    weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
    activityStatic=activityStatic.replace(/{Activity}/ig, idParam+' - '+areaParam+' - '+typeParam+' - '+eitParam+"'");
    dayNameStatic=dayNameStatic.replace(/{DayName}/ig, dayName);
    dayNumStatic=dayNumStatic.replace(/{DayNum}/ig, dayNum);

    document.getElementById('week').innerHTML=weekStatic;
    document.getElementById('activity').innerHTML=activityStatic;
    document.getElementById('dayName').innerHTML=dayNameStatic;
    document.getElementById('dayNum').innerHTML=dayNumStatic;

    let notesStatic= $("#notes").html();
    notesParam=data[0].workspace_note;
    notesStatic= notesStatic.replace(/{Notes}/ig, data[0].workspace_note);
    document.getElementById('notes').innerHTML=notesStatic;
    document.getElementById('description').value=data[0].description;
    document.getElementById('time').value=parseInt(data[0].eit,10); 
    hrefString = "assign_EWOs.html?week="+weekParam+"&date="+formatDate(d1)+"&dayname="+DayName+"&daynum="+DayNum+"&id="+idParam+"&area="+areaParam+"&type="+typeParam+"&eit="+eitParam+"&notes="+notesParam;
    document.getElementById("forwardLink").href=hrefString;

}

function renderSkills(data) {

    let staticHtml = $("#skills-list-row-template").html();

    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{competencies}/ig, obj.name_competency);
        let string_checked = "";
        if(obj.assigned == "1")
            string_checked = "checked";
        let checkbox = '<input '+string_checked+' type="checkbox" class="checkbox_competencies" id="competency'+obj.id_competency+'">'
        ids.push(obj.id_competency);
        names.push(obj.name_competency);
        row = row.replace(/{check}/ig, checkbox);
        $('#skills-list-rows').append(row);
    });

}

/*$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/Planner/html/visualize_activities.html";
    $(window).scrollTop(0);
});*/

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
