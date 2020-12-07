<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	String sites = dbl.visualizeSites(dbc);
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
	out.println(sites); 
%>

