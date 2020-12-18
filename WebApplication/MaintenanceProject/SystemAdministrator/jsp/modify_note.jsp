<%@ page import = "it.unisa.se.team7.*" %>

<% 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    int id_note = Integer.valueOf(request.getParameter("id_note"));
	String description = request.getParameter("description");
    String json = sa_service.modifyNote(id_note, description);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>