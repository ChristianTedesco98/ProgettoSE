<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	String procedure = request.getParameter("smp");
	String json = dbl.createProcedure(dbc,procedure);
%>
<%=json%>
