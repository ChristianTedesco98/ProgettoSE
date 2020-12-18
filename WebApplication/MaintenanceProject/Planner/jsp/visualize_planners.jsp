<%@ page import = "it.unisa.se.team7.*" %>

<% 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    String json = pl_service.visualizePlanners();
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>