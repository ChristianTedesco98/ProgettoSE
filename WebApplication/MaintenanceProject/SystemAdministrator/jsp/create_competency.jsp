<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String name_competency = request.getParameter("name_competency");
    String json = sa_service.createCompetency(db, name_competency);
%>
<%=json%>