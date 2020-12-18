<%@	page import="it.unisa.se.team7.*" %>
<%
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	Integer id_site = Integer.valueOf(request.getParameter("id_site"));
	String area = request.getParameter("area");
	String factory_site = request.getParameter("factory_site");
    String json = dbl.modifySite(id_site, area, factory_site);
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
