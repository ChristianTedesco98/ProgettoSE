<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
	SystemAdministratorDataProvider sa = new SystemAdministratorDataProvider();
	
    int id_user = Integer.valueOf(request.getParameter("id_user"));
    int num_competencies = Integer.valueOf(request.getParameter("num_competencies"));
    String checks = request.getParameter("checks");
    String ids = request.getParameter("ids");

    String json = sa.assignMaintainerCompetences(dbc, id_user, num_competencies, ids, checks);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>