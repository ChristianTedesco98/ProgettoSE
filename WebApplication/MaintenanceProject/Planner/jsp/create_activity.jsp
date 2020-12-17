<%@	page import="it.unisa.se.team7.*" %>
<%
	DbConnection dbc = new DbConnection();
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    String description = request.getParameter("description");
    int week = Integer.valueOf(request.getParameter("week"));
    String interruptible = request.getParameter("interruptible");
    int eit = Integer.valueOf(request.getParameter("eit"));
    int id_typology = Integer.valueOf(request.getParameter("id_typology"));
    int id_site = Integer.valueOf(request.getParameter("id_site"));
    int id_procedure = Integer.valueOf(request.getParameter("id_procedure"));
    String ewo = request.getParameter("ewo");
    int id_user = Integer.valueOf(request.getParameter("id_user"));
	response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    String json = pl_service.createActivity(dbc, description, week, interruptible, eit, id_typology, id_site, id_procedure, ewo, id_user);
%>
<%=json%>