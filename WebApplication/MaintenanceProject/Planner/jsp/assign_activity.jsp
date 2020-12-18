<%@ page import = "it.unisa.se.team7.*" %>



<% 
    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();

    int id_maintainer = Integer.valueOf(request.getParameter("id_maintainer"));

    String date = request.getParameter("date");

    String availabilityWeek = pl_service.maintainerAvailabilityDay(id_maintainer, date);

    response.setContentType("application/json");

    response.setHeader("Access-Control-Allow-Origin", "*");

    out.println(availabilityWeek);
%>