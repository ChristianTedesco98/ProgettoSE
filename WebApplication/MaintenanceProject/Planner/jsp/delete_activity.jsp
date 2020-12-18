<%@	page import="it.unisa.se.team7.*" %>
<%
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
	int id = Integer.valueOf(request.getParameter("id_activity"));
	String json = pl_service.deleteActivity(id);	
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
