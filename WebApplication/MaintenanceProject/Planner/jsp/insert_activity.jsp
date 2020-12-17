<%@ page import = "it.unisa.se.team7.*" %>



<% 
    DbConnection db = new DbConnection(); 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
    int id_activity = Integer.valueOf(request.getParameter("id_activity"));
    int id_maintainer = Integer.valueOf(request.getParameter("id_maintainer"));
    String date = request.getParameter("date");
    String availabilityWeek = pl_service.assignmentActivityMaintainer(db, id_activity, id_maintainer, date);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    out.println(availabilityWeek);
%>