<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	Integer id_site = Integer.valueOf(request.getParameter("id_site"));
	String area = request.getParameter("area");
	String factory_site = request.getParameter("factory_site");
    String json = dbl.modifySite(dbc, id_site, area, factory_site);
%>
<%=json%>
