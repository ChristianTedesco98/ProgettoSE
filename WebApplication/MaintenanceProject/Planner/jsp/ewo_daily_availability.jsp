<%@ page import = "it.unisa.se.team7.*" %>



<% 

    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();

    String procedureSkills = request.getParameter("skills");

    String date= request.getParameter("date");
    
    String[] procedureSkills_array = procedureSkills.split(", ");
    
    String availabilityWeek = pl_service.maintainersDailyAvailability(procedureSkills_array, date);

    response.setContentType("application/json");

    response.setHeader("Access-Control-Allow-Origin", "*");

    out.println(availabilityWeek);

%>