<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	String material = request.getParameter("name_material");
	String json = dbl.createMaterial(dbc, material);
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
