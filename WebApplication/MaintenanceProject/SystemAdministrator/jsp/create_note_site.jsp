<%@	page import="it.unisa.se.team7.*" %>
<%
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String description = request.getParameter("description");
    int id_site = Integer.valueOf(request.getParameter("id_site"));
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    String json = sa_service.createNoteSite(description, id_site);
%>
<%=json%>