<%@	page import="it.unisa.se.team7.*" %>
<%
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	int id = Integer.valueOf(request.getParameter("id_procedure"));
    String json = dbl.visualizeProcedureCompetencies(id);
    out.println(json);
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
