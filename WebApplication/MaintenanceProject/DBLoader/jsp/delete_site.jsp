<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	int id_site = Integer.valueOf(request.getParameter("id_site"));
	String json = dbl.deleteSite(dbc,id_site);
%>
<%=json%>
