<%@ page import = "it.unisa.se.team7.*" %>

<% 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
	int id_competency = Integer.valueOf(request.getParameter("id_competency"));
    String json = sa_service.deleteCompetency(id_competency);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>