<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	int id = Integer.valueOf(request.getParameter("id_typology"));
	String typology = request.getParameter("name_typology");
    String json = dbl.modifyTypology(dbc, id, typology);	
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
