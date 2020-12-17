const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var params = new URLSearchParams(window.location.search);
var weekParam = params.get('week');
var idParam= params.get('id');
var areaParam= params.get('area');
var typeParam= params.get('type');
var eitParam= params.get('eit');
var notesParam=params.get('notes');
var dayNameParam= params.get('dayname');
var dayNumParam= params.get('daynum');
var dateParam= params.get('date');
var skillsNumParam= params.get('skillsNum');
var assigned=false;
var tbaString="";
var aString="";
var id_main;
var dateHour;

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+ "/MaintenanceProject/Planner/jsp/ewo_daily_availability.jsp", {skills: skillsToString(), date: dateParam}, function (data) {
        renderGui(data);
    }).fail(function () {
        let weekStatic= $("#week").html();
        weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
        document.getElementById('week').innerHTML=weekStatic;
    });

}

function skillsToString() {
    let skillString="";
    for (var i=0;i<parseInt(skillsNumParam,10);i++) {
        skillString+=params.get('skill'+i)+", ";
    }
    skillString=skillString.substring(0,skillString.length-2);
    return skillString;
}

function skillsToArray() {
    let arr=[];
    for (var i=0;i<parseInt(skillsNumParam,10);i++) {
        arr[i]=params.get('skill'+i);
    }
    return arr;
}

function switchHour(cell,val,id) {
    assigned=true;
    switch(cell) {
        case 0:
            hourString="08";
            break;
        case 1:
            hourString="09";
            break;
        case 2:
            hourString="10";
            break;
        case 3:
            hourString="11";
            break;
        case 4:
            hourString="14";
            break;
        case 5:
            hourString="15";
            break;
        case 6:
            hourString="16";
            break;
    }
    let tbaTimeStatic= $("#toBeAssignedTime").html();
    let aTimeStatic= $("#assignedTime").html();
    var actual = "";
    if(val >= eitParam){
        actual = "0";
    }
    else{
        actual = (eitParam - val).toString();
    }
    //tbaTimeStatic=tbaTimeStatic.replace(tbaString, eitParam-val );
    tbaTimeStatic=tbaTimeStatic.replace(tbaString, actual);
    aTimeStatic=aTimeStatic.replace(aString, val);
    document.getElementById('toBeAssignedTime').innerHTML=tbaTimeStatic;
    document.getElementById('assignedTime').innerHTML=aTimeStatic;
    document.getElementById('warningMessage').style.display="block";
    document.getElementById('warningMessage').innerHTML="You selected the hour that starts at "+hourString+":00 o'clock!";
    aString=val.toString();
    //tbaString=(eitParam-val).toString()
    tbaString = actual;
    id_main=id;
}

function decorateCellFraction(value) {
    let val=parseInt(value[0],10);
    let gap=parseInt(value[2],10);
    let division=val/gap;
    if (division <= 0.19) {
        return ' style="background-color: #FF0000; "> <b style="color: black; font-size: 10px; ">'+ value +'</b>';
    } else if (division >= 0.2 && division <=0.49) {
        return ' style="background-color: #F8B924; "> <b style="color: black; font-size: 10px; ">'+ value +'</b>';
    } else if (division >= 0.5 && division <=0.79) {
        return ' style="background-color: #FCFF00; "> <b style="color: black; font-size: 10px; ">'+ value +'</b>';
    } else if (division >= 0.8 && division <=0.99) {
        return ' style="background-color: #52DC35; "> <b style="color: black; font-size: 10px; ">'+ value +'</b>';
    } else {
        return ' style="background-color: #469C39; "> <b style="color: black; font-size: 10px; ">'+ value +'</b>';
    }
}

function decorateCellHour(val,index,id) {
    if (val <= 19) {
        return ' style="background-color: #FF0000; "><b class="hour" onclick=switchHour('+index+','+val+','+id+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
    } else if (val >= 20 && val <=29) {
        return ' style="background-color: #F8B924; "><b class="hour" onclick=switchHour('+index+','+val+','+id+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
    } else if (val >= 30 && val <=44) {
        return ' style="background-color: #FCFF00; "><b class="hour" onclick=switchHour('+index+','+val+','+id+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
    } else if (val >= 45 && val <=59) {
        return ' style="background-color: #52DC35; "><b class="hour" onclick=switchHour('+index+','+val+','+id+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
    } else {
        return ' style="background-color: #469C39; "><b class="hour" onclick=switchHour('+index+','+val+','+id+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
    }
}

function renderGui(data) {
    let weekStatic= $("#week").html();
    let activityStatic= $("#activity").html();
    let dayNameStatic= $("#dayName").html();
    let dayNumStatic= $("#dayNum").html();
    let notesStatic= $("#notes").html();
    let rtimeStatic= $("#requiredTime").html();

    notesStatic= notesStatic.replace(/{Notes}/ig, notesParam);
    weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
    activityStatic=activityStatic.replace(/{Activity}/ig, idParam+' - '+areaParam+' - '+typeParam+' - '+eitParam+"'");
    dayNameStatic=dayNameStatic.replace(/{DayName}/ig, dayNameParam);
    dayNumStatic=dayNumStatic.replace(/{DayNum}/ig, dayNumParam);
    rtimeStatic=rtimeStatic.replace(/{RTime}/ig, eitParam);

    document.getElementById('week').innerHTML=weekStatic;
    document.getElementById('activity').innerHTML=activityStatic;
    document.getElementById('dayName').innerHTML=dayNameStatic;
    document.getElementById('dayNum').innerHTML=dayNumStatic;
    document.getElementById('requiredTime').innerHTML=rtimeStatic;

    document.getElementById('notes').innerHTML=notesStatic;
    let tableHtml = $("#hours-list-row-template").html();
    let staticHtml = $("#skills-list-row-template").html();
    let arraySkills=skillsToArray();
    let row=staticHtml;
    for (i = 0; i < arraySkills.length; i++) {
        row = row.replace(/{Item}/ig, arraySkills[i]);
        $('#skills-list-rows').append(row);
        row = $("#skills-list-row-template").html();
    }

    $.each(data, function (index, obj) {
        let tableRow = tableHtml;
        tableRow = tableRow.replace(/{Maintainer}/ig, obj.name);
        tableRow = tableRow.replace(/>{Skills}/ig, decorateCellFraction(obj.skills));
        tableRow = tableRow.replace(/>{Hour0}/ig, decorateCellHour(obj.hour0,0,obj.id_maintainer));
        tableRow = tableRow.replace(/>{Hour1}/ig, decorateCellHour(obj.hour1,1,obj.id_maintainer));
        tableRow = tableRow.replace(/>{Hour2}/ig, decorateCellHour(obj.hour2,2,obj.id_maintainer));
        tableRow = tableRow.replace(/>{Hour3}/ig, decorateCellHour(obj.hour3,3,obj.id_maintainer));
        tableRow = tableRow.replace(/>{Hour4}/ig, decorateCellHour(obj.hour4,4,obj.id_maintainer));
        tableRow = tableRow.replace(/>{Hour5}/ig, decorateCellHour(obj.hour5,5,obj.id_maintainer));
        tableRow = tableRow.replace(/>{Hour6}/ig, decorateCellHour(obj.hour6,6,obj.id_maintainer));
        $('#hours-list-rows').append(tableRow);
    });

    document.getElementById("linkSend").onclick= function () {
        if (assigned === true) {
            dateHour= dateParam+" "+hourString+":00:00";
            $.getJSON("http://"+JAVA_TOMCAT_HOST+"/MaintenanceProject/Planner/jsp/assign_ewo.jsp", {id_activity: idParam, id_maintainer: id_main, date_time: dateHour}, function (data) {
                window.location.pathname="../MaintenanceProject/Planner/html/home_planner.html";
            }).fail(function () {
            });
        }
        else {
            document.getElementById('warningMessage').style.display="block";
        }
    }

    let tbaTimeStatic= $("#toBeAssignedTime").html();
    tbaTimeStatic=tbaTimeStatic.replace(/{TBATime}/ig, eitParam);
    document.getElementById('toBeAssignedTime').innerHTML=tbaTimeStatic;
    let aTimeStatic= $("#assignedTime").html();
    aTimeStatic=aTimeStatic.replace(/{ATime}/ig, "0");
    document.getElementById('assignedTime').innerHTML=aTimeStatic;
    tbaString = eitParam.toString();
    aString = "0";
}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/Planner/html/visualize_activities.html";
    $(window).scrollTop(0);
});

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});
