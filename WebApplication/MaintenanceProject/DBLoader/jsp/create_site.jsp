<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	String area = request.getParameter("area");
	String factory_site = request.getParameter("factory_site");
	String json = dbl.createSite(dbc, area, factory_site);
%>
<%=json%>
