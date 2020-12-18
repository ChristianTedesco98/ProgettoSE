<%@	page import="it.unisa.se.team7.*" %>
<%
	DBLoaderJavaServiceDataProvider dbl = new DBLoaderJavaServiceDataProvider();
	
    int id_procedure = Integer.valueOf(request.getParameter("id_procedure"));
    int num_competencies = Integer.valueOf(request.getParameter("num_competencies"));
    String checks = request.getParameter("checks");
    String ids = request.getParameter("ids");

    String json = dbl.assignProcedureCompetences(id_procedure, num_competencies, ids, checks);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>