const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    document.getElementById("SystemAdministratorButton").onclick=goToHomeSystemAdministratorHome;

    document.getElementById("DBLoaderButton").onclick=goToDBLoaderHome;

    document.getElementById("PlannerButton").onclick=goToPlannerHome;

}

function goToHomeSystemAdministratorHome() {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/home_sys.html";
}

function goToDBLoaderHome() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/home_db.html";
}

function goToPlannerHome() {
    window.location.pathname = "../MaintenanceProject/Planner/html/home_planner.html";
}
let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});