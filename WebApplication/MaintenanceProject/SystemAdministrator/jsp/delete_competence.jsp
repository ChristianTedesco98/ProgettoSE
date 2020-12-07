<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String id = request.getParameter("deleteJson");
    String json = "[{\"id_competency\":\""+id+"\"}]";
    String json = sa_service.deleteCompetence(db, json);
%>
<%=json%>