<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection db = new DbConnection();
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
	String smp_string = request.getParameter("smp");
	String name_procedure = request.getParameter("name_procedure");
	byte[] b = smp_string.getBytes("ISO-8859-1");
	String json = dbl.createProcedure(db, name_procedure, b);	
%>
<%=json%>