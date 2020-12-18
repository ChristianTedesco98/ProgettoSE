<%@ page import = "it.unisa.se.team7.*" %>

<% 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    int id_activity = Integer.valueOf(request.getParameter("id_activity"));
    String json = pl_service.visualizeEwoCompetencies(id_activity);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>