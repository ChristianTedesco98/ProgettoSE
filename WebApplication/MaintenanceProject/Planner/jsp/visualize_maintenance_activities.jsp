<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    String json = pl_service.visualizeActivities(db);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>