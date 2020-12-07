<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	String typology = request.getParameter("name_typology");
	String json = dbl.createTypology(dbc, typology);
%>
<%=json%>
