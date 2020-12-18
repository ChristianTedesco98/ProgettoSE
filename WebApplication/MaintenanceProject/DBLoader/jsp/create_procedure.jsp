<%@	page import="it.unisa.se.team7.*" %>
<%
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
	String smp_string = request.getParameter("smp");
	String name_procedure = request.getParameter("name_procedure");
	byte[] b = smp_string.getBytes("ISO-8859-1");
	String json = dbl.createProcedure(name_procedure, b);	
%>
<%=json%>