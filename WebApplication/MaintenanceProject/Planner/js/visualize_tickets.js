const JAVA_TOMCAT_HOST = "127.0.0.1:8080";
var params = new URLSearchParams(window.location.search);
var weekParam = params.get('week');
var dayNumParam= params.get('daynum');
var dayNameParam= params.get('dayname');

function initBrowsing() {

    $.getJSON("http://"+JAVA_TOMCAT_HOST+"/MaintenanceProject/Planner/jsp/visualize_tickets.jsp", {week: weekParam, day: dayNumParam}, function (data) {
        renderGui(data);
    }).fail(function () {

        let weekStatic= $("#week").html();
        let dayNameStatic= $("#dayName").html();
        let dayNumStatic= $("#dayNum").html();

        weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
        document.getElementById('week').innerHTML=weekStatic;
        dayNameStatic=dayNameStatic.replace(/{DayName}/ig, dayNameParam);
        document.getElementById('dayName').innerHTML=dayNameStatic;
        dayNumStatic=dayNumStatic.replace(/{DayNum}/ig, getDate(dayNumParam));
        document.getElementById('dayNum').innerHTML=dayNumStatic;
        
    });

}

function getDate(daynumber) {
    var d = new Date();
    var year = d.getYear()+1900;
    var week = weekParam;
    var d = new Date("Jan 01, " + year + " 01:00:00");
    var w = d.getTime() + 604800000 * (week - 1);
    wd=new Date(w);
    var daynum=wd.getDate();
    var monthnum=wd.getMonth();
    var dayname=wd.getDay();
    var d1 = new Date(w - ((dayname-1)*86400000) );
    var d2 = new Date(d1.getTime() + (daynumber-1)*86400000);
    return d2.getDate();
}

function decorateCell(string) {
    if (string==="SENT") {
        return ' style = "background-color: #E7EB19;"><b>SENT</b>';
    } else if (string==="Received") {
        return ' style = "background-color: #05A14A;"> <b>Received</b>';
    } else if (string==="CLOSED") {
        return ' style = "background-color: #05A14A;"> <b>CLOSED</b>';
    } else if (string==="IN PROGRESS") {
        return ' style = "background-color: #E7EB19;"><b>IN <br> PROGRESS</b>';
    } else if (string==="NOT STARTED") {
        return ' style = "background-color: #E7EB19;"><b>NOT <br> STARTED</b>';
    } else if (string==="NOT SENT") {
        return ' style = "background-color: #E7EB19;"> <b>NOT SENT</b>';
    }
}

function renderGui(data) {
    let weekStatic= $("#week").html();
    let dayNameStatic= $("#dayName").html();
    let dayNumStatic= $("#dayNum").html();
    let staticHtml = $("#tickets-list-row-template").html();

	weekStatic=weekStatic.replace(/{Week}/ig, weekParam);
    document.getElementById('week').innerHTML=weekStatic;
    dayNameStatic=dayNameStatic.replace(/{DayName}/ig, dayNameParam);
    document.getElementById('dayName').innerHTML=dayNameStatic;
    dayNumStatic=dayNumStatic.replace(/{DayNum}/ig, getDate(dayNumParam));
    document.getElementById('dayNum').innerHTML=dayNumStatic;
    
    $.each(data, function (index, obj) {
        let row = staticHtml;
        row = row.replace(/{Id}/ig, obj.id_activity);
        row = row.replace(/{Area}/ig, obj.area);
        row = row.replace(/{Type}/ig, obj.type);
        row = row.replace(/{EIT}/ig, obj.eit);
        row = row.replace(/>{Department}/ig, decorateCell(obj.state_department));
        row = row.replace(/>{Maintainer}/ig, decorateCell(obj.state_maintainer));
        row = row.replace(/>{Site}/ig, decorateCell("NOT STARTED"));
        $('#tickets-list-rows').append(row);
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
