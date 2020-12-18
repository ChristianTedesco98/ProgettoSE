<%@	page import="it.unisa.se.team7.*" %>
<%
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	String sites = dbl.visualizeSites();
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=sites%>

