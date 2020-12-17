<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
	int id = Integer.valueOf(request.getParameter("id_activity"));
	String json = pl_service.deleteActivity(dbc, id);	
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
