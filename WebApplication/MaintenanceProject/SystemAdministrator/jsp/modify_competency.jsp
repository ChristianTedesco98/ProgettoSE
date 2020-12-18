<%@ page import = "it.unisa.se.team7.*" %>

<% 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    int id_competency = Integer.valueOf(request.getParameter("id_competency"));
	String name_competency = request.getParameter("name_competency");
    String json = sa_service.modifyCompetency(id_competency, name_competency);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>