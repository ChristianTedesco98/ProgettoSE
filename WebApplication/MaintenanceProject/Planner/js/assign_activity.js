const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var params = new URLSearchParams(window.location.search);
var weekParam = params.get('week');
var activityParam = params.get('activity');
var skillsParam = params.get('skills');
var percentageParam = params.get('percentage');
var notesParam = params.get('notes');
var dayNameParam= params.get('dayname');
var dayNumParam= params.get('daynum');
var userParam= params.get('user');
var idParam=params.get('id');
var dateHour="";
var hourString = "";
var assigned=false;

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+"/MaintenanceProject/Planner/jsp/assign_activity.jsp", {id_maintainer: idParam, date: dayNumParam}, function (data) {
        renderGui(data);
    }).fail(function () {
    });
}

function switchHour(cell) {
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
    document.getElementById('warningMessage').style.display="block";
    document.getElementById('warningMessage').innerHTML="You selected the hour that starts at "+hourString+":00 o'clock!";
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

function decorateCellHour(val,index) {
	if (val <= 19) {
		return ' style="background-color: #FF0000; "><b class="hour" onclick=switchHour('+index+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
	} else if (val >= 20 && val <=29) {
		return ' style="background-color: #F8B924; "><b class="hour" onclick=switchHour('+index+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
	} else if (val >= 30 && val <=44) {
		return ' style="background-color: #FCFF00; "><b class="hour" onclick=switchHour('+index+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
	} else if (val >= 45 && val <=59) {
		return ' style="background-color: #52DC35; "><b class="hour" onclick=switchHour('+index+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
	} else {
		return ' style="background-color: #469C39; "><b class="hour" onclick=switchHour('+index+'); style="color: black; font-size: 10px; ">'+ val +' min </b> </a>';
	}
}

function decorateCellPercentage(value) {
	let val=parseInt(value,10);
	let perc= document.getElementById('percentage');
	if (val <= 19) {
		perc.style.backgroundColor="#FF0000";
	} else if (val >= 20 && val <=49) {
		perc.style.backgroundColor="#F8B924";
	} else if (val >= 50 && val <=79) {
		perc.style.backgroundColor="#FCFF00";
	} else if (val >= 80 && val <=99) {
		perc.style.backgroundColor="#52DC35";
	} else {
		perc.style.backgroundColor="#469C39";
	}
}

function goToVisualizePage() {
	window.location.pathname = "../MaintenanceProject/Planner/html/home_planner.html";
}


function renderGui(data) {
	let dayNum= dayNumParam.substring(0,2);
    let weekStatic= $("#week").html();
    let activityStatic= $("#activity").html();
    let dayNameStatic= $("#dayName").html();
    let dayNumStatic= $("#dayNum").html();
    let notesStatic= $("#notes").html();
    let userStatic= $("#user").html();
    let percentageStatic= $("#percentage").html();

	weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
    activityStatic=activityStatic.replace(/{Activity}/ig, activityParam+"'");
	dayNameStatic=dayNameStatic.replace(/{DayName}/ig, dayNameParam);
	dayNumStatic=dayNumStatic.replace(/{DayNum}/ig, dayNum);
	notesStatic=notesStatic.replace(/{Notes}/ig, notesParam);
	userStatic=userStatic.replace(/{User}/ig, userParam);
	percentageStatic=percentageStatic.replace(/{percentage}/ig, percentageParam+"%");

    document.getElementById('week').innerHTML=weekStatic;
    document.getElementById('activity').innerHTML=activityStatic;
    document.getElementById('dayName').innerHTML=dayNameStatic;
    document.getElementById('dayNum').innerHTML=dayNumStatic;
    document.getElementById('notes').innerHTML=notesStatic;
    document.getElementById('user').innerHTML=userStatic;
    document.getElementById('percentage').innerHTML=percentageStatic;
    decorateCellPercentage(percentageParam);

    let tableHtml = $("#hours-list-row-template").html();


    $.each(data, function (index, obj) {
        let tableRow = tableHtml;
		tableRow = tableRow.replace(/{Maintainer}/ig, userParam);
		tableRow = tableRow.replace(/>{Skills}/ig, decorateCellFraction(skillsParam));
		tableRow = tableRow.replace(/>{Hour0}/ig, decorateCellHour(obj.hour0,0));
		tableRow = tableRow.replace(/>{Hour1}/ig, decorateCellHour(obj.hour1,1));
		tableRow = tableRow.replace(/>{Hour2}/ig, decorateCellHour(obj.hour2,2));
		tableRow = tableRow.replace(/>{Hour3}/ig, decorateCellHour(obj.hour3,3));
		tableRow = tableRow.replace(/>{Hour4}/ig, decorateCellHour(obj.hour4,4));
		tableRow = tableRow.replace(/>{Hour5}/ig, decorateCellHour(obj.hour5,5));
		tableRow = tableRow.replace(/>{Hour6}/ig, decorateCellHour(obj.hour6,6));
        $('#hours-list-rows').append(tableRow);
    });

    document.getElementById("linkSend").onclick= function () {
    	if (assigned === true) {
	    	var id_activity= activityParam.split(' -')[0];
	    	var id_maintainer= idParam;
	    	dateHour= dayNumParam+" "+hourString+":00:00";
	    	$.getJSON("http://"+JAVA_TOMCAT_HOST+"/MaintenanceProject/Planner/jsp/insert_activity.jsp", {id_activity: id_activity,id_maintainer: idParam, date: dateHour}, function (data) {
	        	renderGui(data);
	    	}).fail(function () {
	    	});
	    	goToVisualizePage();
    	}
    	else {
    		document.getElementById('warningMessage').style.display="block";
    	}
    }

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

