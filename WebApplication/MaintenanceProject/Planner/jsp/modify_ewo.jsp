<%@ page import = "it.unisa.se.team7.*" %>

<% 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    int id_activity = Integer.valueOf(request.getParameter("id_activity"));
    String description = request.getParameter("description");
    int eit = Integer.valueOf(request.getParameter("eit"));
    int day= Integer.valueOf(request.getParameter("day"));
    String json = pl_service.updateEWO(id_activity, description, eit, day);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>