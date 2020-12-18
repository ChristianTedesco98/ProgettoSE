<%@ page import = "it.unisa.se.team7.*" %>

<% 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    String json = pl_service.visualizeEWOs();
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>