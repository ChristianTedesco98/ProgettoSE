<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    int id_competency = Integer.valueOf(request.getParameter("id_competency"));
	String name_competency = request.getParameter("name_competency");
    String json = sa_service.modifyCompetency(db, id_competency, name_competency);
%>
<%=json%>