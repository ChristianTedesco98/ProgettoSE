const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var params = new URLSearchParams(window.location.search);
var weekParam = params.get('week');
var idParam = params.get('id');
var areaParam = params.get('area');
var typeParam = params.get('type');
var eitParam= params.get('eit');
var notesParam= params.get('note');
var arraySkills=[];
var arrayDates = [];

let skillParam= params.get('skill0');
var i=0;
while (skillParam!=null) {
	arraySkills.push(skillParam);
	i++;
	skillParam= params.get('skill'+i);
}

function formatDate(date){
	let day = ("0" + date.getDate()).slice(-2);
	let month = ("0" + (date.getMonth() + 1)).slice(-2);
	let year = date.getFullYear();
	return day + "/" + month + "/" + year;
}

function initBrowsing() {
	var d = new Date();
	var year = d.getYear()+1900;
	var week = weekParam;
	var d = new Date("Jan 01, " + year + " 01:00:00");
	var w = d.getTime() + 604800000 * (week - 1);
	wd=new Date(w);
	var daynum=wd.getDate();
	var monthnum=wd.getMonth();
	var dayname=wd.getDay();
	if (dayname!=0 && daynum!=1 && monthnum!=0) {
		var d2 = new Date(w - ((dayname-1)*86400000) + 518400000);
	} else {
		var d2 = new Date(w + ((7-dayname)*86400000))
	}
	var d1 = new Date(w - ((dayname-1)*86400000) );
	var i=0;
	while (d1.valueOf() != d2.valueOf()) {
		arrayDates[i]=formatDate(d1);
		i++;
		timeD=d1.getTime();
		d1= new Date(timeD+ 86400000);
	}
	arrayDates[i]=formatDate(d1);

    $.getJSON("http://"+JAVA_TOMCAT_HOST+"/MaintenanceProject/Planner/jsp/verify_selected.jsp",{skills: arraySkills.toString(), dates: arrayDates.toString()}, function (data) {
         renderGui(data);
    }).fail(function () {
    });
}

function decorateCellFraction(value) {
	let val=parseInt(value[0],10);
	let gap=parseInt(value[2],10);
	let division=val/gap;
	if (division <= 0.19) {
		return ' style="background-color: #FF0000; "> <b style="color: white; font-size: 10px; ">'+ value +'</b>';
	} else if (division >= 0.2 && division <=0.49) {
		return ' style="background-color: #F8B924; "> <b style="color: white; font-size: 10px; ">'+ value +'</b>';
	} else if (division >= 0.5 && division <=0.79) {
		return ' style="background-color: #FCFF00; "> <b style="color: white; font-size: 10px; ">'+ value +'</b>';
	} else if (division >= 0.8 && division <=0.99) {
		return ' style="background-color: #52DC35; "> <b style="color: white; font-size: 10px; ">'+ value +'</b>';
	} else {
		return ' style="background-color: #469C39; "> <b style="color: white; font-size: 10px; ">'+ value +'</b>';
	}
}

function decorateCellPercentage(value,index,skills,name,id) {
	var activityString= idParam+' - '+areaParam+' - '+typeParam+' - '+eitParam;
	let val=parseInt(value,10);
	let dayNum=arrayDates[index];
	let dayName="";
	switch (index) {
		case 0:
			dayName="Monday";
			break;
		case 1:
			dayName="Tuesday";
			break;
		case 2:
			dayName="Wednesday";
			break;
		case 3:
			dayName="Thursday";
			break;
		case 4:
			dayName="Friday";
			break;
		case 5:
			dayName="Saturday";
			break;
		case 6:
			dayName="Sunday";
			break;
	}

	if (val <= 19) {
		return ' style="background-color: #FF0000; ">  <a href="assign_activity.html?daynum='+dayNum+'&dayname='+dayName+'&week='+weekParam+'&skills='+skills+'&notes='+notesParam+'&percentage='+value+'&activity='+activityString+'&user='+name+'&id='+id+'"> <b style="color: white; font-size: 10px; ">'+ value +'% </b> </a>';
	} else if (val >= 20 && val <=49) {
		return ' style="background-color: #F8B924; "> <a href="assign_activity.html?daynum='+dayNum+'&dayname='+dayName+'&week='+weekParam+'&skills='+skills+'&notes='+notesParam+'&percentage='+value+'&activity='+activityString+'&user='+name+'&id='+id+'"> <b style="color: white; font-size: 10px; ">'+ value +'% </b> </a>';
	} else if (val >= 50 && val <=79) {
		return ' style="background-color: #FCFF00; "> <a href="assign_activity.html?daynum='+dayNum+'&dayname='+dayName+'&week='+weekParam+'&skills='+skills+'&notes='+notesParam+'&percentage='+value+'&activity='+activityString+'&user='+name+'&id='+id+'"> <b style="color: white; font-size: 10px; ">'+ value +'% </b> </a>';
	} else if (val >= 80 && val <=99) {
		return ' style="background-color: #52DC35; "> <a href="assign_activity.html?daynum='+dayNum+'&dayname='+dayName+'&week='+weekParam+'&skills='+skills+'&notes='+notesParam+'&percentage='+value+'&activity='+activityString+'&user='+name+'&id='+id+'"> <b style="color: white; font-size: 10px; ">'+ value +'% </b> </a>';
	} else {
		return ' style="background-color: #469C39; "> <a href="assign_activity.html?daynum='+dayNum+'&dayname='+dayName+'&week='+weekParam+'&skills='+skills+'&notes='+notesParam+'&percentage='+value+'&activity='+activityString+'&user='+name+'&id='+id+'"> <b style="color: white; font-size: 10px; ">'+ value +'% </b> </a>';
	}
}


function renderGui(data) {
    let weekStatic= $("#week").html();
    let activityStatic= $("#activity").html();
    let staticHtml = $("#skills-list-row-template").html();

    let noteStatic= $("#note-template").html();
    let descriptionStatic= $("#description-template").html();
    let smpStatic= $("#smp-template").html();




	weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
	document.getElementById('week').innerHTML = weekStatic;
    activityStatic=activityStatic.replace(/{Activity}/ig, idParam+' - '+areaParam+' - '+typeParam+' - '+eitParam+"'");
    document.getElementById('activity').innerHTML=activityStatic;
    let tableHtml = $("#maintainers-list-row-template").html();
    let row = staticHtml;
    for (i = 0; i < arraySkills.length; i++) {
    	row = row.replace(/{Item}/ig, arraySkills[i]);
        $('#skills-list-rows').append(row);
        row = $("#skills-list-row-template").html();
    }

    $.each(data, function (index, obj) {
        let tableRow = tableHtml;
		tableRow = tableRow.replace(/{Maintainer}/ig, obj.name);
		tableRow = tableRow.replace(/>{Skills}/ig, decorateCellFraction(obj.skills));
		tableRow = tableRow.replace(/>{Monday}/ig, decorateCellPercentage(obj.day0,0,obj.skills,obj.name,obj.id_maintainer));
		tableRow = tableRow.replace(/>{Tuesday}/ig, decorateCellPercentage(obj.day1,1,obj.skills,obj.name,obj.id_maintainer));
		tableRow = tableRow.replace(/>{Wednesday}/ig, decorateCellPercentage(obj.day2,2,obj.skills,obj.name,obj.id_maintainer));
		tableRow = tableRow.replace(/>{Thursday}/ig, decorateCellPercentage(obj.day3,3,obj.skills,obj.name,obj.id_maintainer));
		tableRow = tableRow.replace(/>{Friday}/ig, decorateCellPercentage(obj.day4,4,obj.skills,obj.name,obj.id_maintainer));
		tableRow = tableRow.replace(/>{Saturday}/ig, decorateCellPercentage(obj.day5,5,obj.skills,obj.name,obj.id_maintainer));
		tableRow = tableRow.replace(/>{Sunday}/ig, decorateCellPercentage(obj.day6,6,obj.skills,obj.name,obj.id_maintainer));
        $('#maintainers-list-rows').append(tableRow);
    });

}

$('body').on('#modals #modal-ajax-response hidden.bs.modal', function () {
    window.location.pathname = "../MaintenanceProject/Planner/html/visualize_selected.html";
    $(window).scrollTop(0);
});


/*function deleteUser(id) {

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
}*/

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});

