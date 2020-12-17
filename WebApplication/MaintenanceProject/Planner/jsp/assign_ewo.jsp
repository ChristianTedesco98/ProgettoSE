<%@ page import = "it.unisa.se.team7.*" %>



<% 

    DbConnection db = new DbConnection(); 

    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();

    int id_activity = Integer.valueOf(request.getParameter("id_activity"));
    
    int id_maintainer = Integer.valueOf(request.getParameter("id_maintainer"));

    String date_time= request.getParameter("date_time");
    
    String json = pl_service.assignmentEwoMaintainer(db, id_activity, id_maintainer, date_time);

    response.setContentType("application/json");

    response.setHeader("Access-Control-Allow-Origin", "*");

    out.println(json);

%>