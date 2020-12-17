const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {
	
	document.getElementById("activitiesButton").onclick=goToMaintenanceActivitiesPage;

    document.getElementById("maintenanceButton").onclick=goToActivitiesPage;

    document.getElementById("ticketButton").onclick=goToTicketsPage;

    document.getElementById("ewoButton").onclick=goToEwosPage;

	document.getElementById("backButton").onclick=goToHomePage;

}

function goToActivitiesPage() {
	let week = prompt("Please select a week number (between 1 and 53).", "1");
	if (week != null && week>=1 && week<=53) {
    	window.location.href = "visualize_activities.html?week="+week;
	} else {
    	alert("The week number has to be between 1 and 53!");
	}
}

function goToMaintenanceActivitiesPage(){
	window.location.pathname = "../MaintenanceProject/Planner/html/visualize_maintenance_activities.html";
}

function askDay(week) {

	let dayName="";
	let dayNum = parseInt(prompt("Please select a day of the week (between 1 and 7).", "1"));
	switch(dayNum) {
		case 1:
			dayName="Monday";
			break;
		case 2:
			dayName="Tuesday";	
			break;
		case 3:
			dayName="Wednesday";
			break;
		case 4:
			dayName="Thursday";
			break;
		case 5:
			dayName="Friday";
			break;
		case 6:
			dayName="Saturday";
			break;
		case 7:
			dayName="Sunday";
			break;
	}
	if (dayNum != null && dayNum>=1 && dayNum<=7) {
    	window.location.href = "visualize_tickets.html?week="+week+"&daynum="+dayNum+"&dayname="+dayName;
	} else {
    	alert("The day number has to be between 1 and 7!");
	}
}

function goToTicketsPage() {
	let week = parseInt(prompt("Please select a week number (between 1 and 53).", "1"));
	if (week != null && week>=1 && week<=53) {
		askDay(week);
	} else {
    	alert("The week number has to be between 1 and 53!");
	}
}

function goToEwosPage() {
	let week = prompt("Please select a week number (between 1 and 53).", "1");
	let weekInt=parseInt(week);
	if (week != null && week>=1 && week<=53) {
    	window.location.href = "visualize_EWOs.html?week="+week;
	} else {
    	alert("The week number has to be between 1 and 53!");
	}
}

function goToHomePage() {
    window.location.pathname = "../MaintenanceProject/";
}

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});