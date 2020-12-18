<%@	page import="it.unisa.se.team7.*" %>
<%
	SystemAdministratorDataProvider sa = new SystemAdministratorDataProvider();
	int id = Integer.valueOf(request.getParameter("id_user"));
	String json = sa.visualizeMaintainerCompetencies(id);
    out.println(json);
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
