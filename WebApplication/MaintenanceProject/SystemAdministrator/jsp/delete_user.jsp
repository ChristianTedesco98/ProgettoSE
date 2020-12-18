<%@ page import = "it.unisa.se.team7.*" %>

<% 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    int id_user = Integer.valueOf(request.getParameter("id_user"));
    String json = sa_service.deleteUser(id_user);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
