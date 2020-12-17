<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection db = new DbConnection(); 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
	
    int id_activity = Integer.valueOf(request.getParameter("id_activity"));
    int num_competencies = Integer.valueOf(request.getParameter("num_competencies"));
    String checks = request.getParameter("checks");
    String ids = request.getParameter("ids");

    String json = pl_service.assignEwoCompetencies(db, id_activity, num_competencies, ids, checks);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(json);
%>