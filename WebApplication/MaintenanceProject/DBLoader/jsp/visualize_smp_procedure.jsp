<%@	page import="it.unisa.se.team7.*" %>
<%
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	int id_activity = Integer.valueOf(request.getParameter("id_procedure"));
    byte[] smp = dbl.visualizeProcedureSMP(id_activity);
	response.setHeader("Access-Control-Allow-Origin", "*");
    response.setContentType("application/pdf");
    response.getOutputStream().write(smp);
    response.getOutputStream().flush();	
%>