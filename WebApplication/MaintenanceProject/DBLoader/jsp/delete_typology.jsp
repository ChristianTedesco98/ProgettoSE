<%@	page import="it.unisa.se.team7.*" %>
<%
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	int id = Integer.valueOf(request.getParameter("id_typology"));
	String json = dbl.deleteTypology(id);	
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
