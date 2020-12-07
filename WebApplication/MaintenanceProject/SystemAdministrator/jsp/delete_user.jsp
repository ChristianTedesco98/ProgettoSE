<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    int id_user = Integer.valueOf(request.getParameter("id_user"));
    String json = sa_service.deleteUser(db, id_user);
%>
<%=json%>
