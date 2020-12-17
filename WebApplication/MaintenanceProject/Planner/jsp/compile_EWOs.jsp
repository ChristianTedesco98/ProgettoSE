<%@ page import = "it.unisa.se.team7.*" %>



<% 

    DbConnection db = new DbConnection(); 

    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();

    response.setContentType("application/json");

    response.setHeader("Access-Control-Allow-Origin", "*");

    out.println("[{\"ciao\": \"ciao\"}]");

%>