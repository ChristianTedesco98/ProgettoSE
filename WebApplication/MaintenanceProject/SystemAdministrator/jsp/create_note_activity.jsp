<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String description = request.getParameter("description");
    int id_activity = Integer.valueOf(request.getParameter("id_activity"));
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    String json = sa_service.createNoteActivity(dbc, description, id_activity);
%>
<%=json%>