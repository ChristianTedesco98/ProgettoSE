
<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	int id = Integer.valueOf(request.getParameter("id_procedure"));
	String smp = request.getParameter("smp");
    String json = dbl.modifyProcedure(dbc, id, smp);
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>