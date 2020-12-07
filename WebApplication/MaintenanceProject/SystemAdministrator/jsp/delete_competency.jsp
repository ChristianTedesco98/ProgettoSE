<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
	int id_competency = Integer.valueOf(request.getParameter("id_competency"));
    String json = sa_service.deleteCompetency(db, id_competency);
%>
<%=json%>