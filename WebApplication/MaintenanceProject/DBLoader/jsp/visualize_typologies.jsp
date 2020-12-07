<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	String typologies = dbl.visualizeTypologies(dbc);
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
	out.println(typologies); 	
%>
