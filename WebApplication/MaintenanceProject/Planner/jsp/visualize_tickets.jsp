<%@ page import = "it.unisa.se.team7.*" %>



<% 

    DbConnection db = new DbConnection(); 

    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();

    int week=Integer.valueOf(request.getParameter("week"));

    int day=Integer.valueOf(request.getParameter("day"));

    String json = pl_service.visualizeStatusEwos(db, week, day);

    response.setContentType("application/json");

    response.setHeader("Access-Control-Allow-Origin", "*");

    out.println(json);

%>