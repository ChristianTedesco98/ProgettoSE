<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String json = "[{\"name_competency\":\"testing\"}]";
    String json = sa_service.createCompetence(db, json);
%>
<%=json%>