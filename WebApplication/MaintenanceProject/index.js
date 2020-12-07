const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    document.getElementById("SystemAdministratorButton").onclick=goToHomeSystemAdministratorHome;

    document.getElementById("DBLoaderButton").onclick=goToDBLoaderHome;

}

function goToHomeSystemAdministratorHome() {
    window.location.pathname = "../MaintenanceProject/SystemAdministrator/html/home_sys.html";
}

function goToDBLoaderHome() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/home_db.html";
}

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});