<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String json = sa_service.visualizeUsers(db);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>