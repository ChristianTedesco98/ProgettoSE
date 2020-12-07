const JAVA_TOMCAT_HOST = "127.0.0.1:8080";

function initBrowsing() {

    document.getElementById("siteButton").onclick=goToSitesPage;

    document.getElementById("typologyButton").onclick=goToTypologiesPage;

    document.getElementById("procedureButton").onclick=goToProceduresPage;

    document.getElementById("materialButton").onclick=goToMaterialsPage;

	document.getElementById("backButton").onclick=goToHomePage;

}

function goToSitesPage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_sites.html";
}

function goToTypologiesPage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_typologies.html";
}

function goToMaterialsPage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_materials.html";
}

function goToProceduresPage() {
    window.location.pathname = "../MaintenanceProject/DBLoader/html/visualize_procedures.html";
}

function goToHomePage() {
    window.location.pathname = "../MaintenanceProject/";
}

let debug = false;
$(document).ready(function () {
    if(!debug)
        initBrowsing();
});