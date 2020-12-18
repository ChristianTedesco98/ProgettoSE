<%@ page import = "it.unisa.se.team7.*" %>

<% 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    int week = Integer.valueOf(request.getParameter("week"));
    String json = pl_service.visualizeWeekActivities(week);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>