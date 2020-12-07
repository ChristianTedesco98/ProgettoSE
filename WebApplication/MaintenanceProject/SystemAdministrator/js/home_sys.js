const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    document.getElementById("userButton").onclick=goToUserPage;

    document.getElementById("competencyButton").onclick=goToCompetenciesPage;
    
	document.getElementById("backButton").onclick=goToHomePage;

}

function goToUserPage() {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_users.html";
}

function goToCompetenciesPage() {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/visualize_competencies.html";
}

function goToHomePage() {
    window.location.pathname = "../MaintenanceProject/";
}
let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});