<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String json = request.getParameter("modifyJson");
    String json = sa_service.modifyCompetence(db, json);
%>
<%=json%>