<%@ page import = "it.unisa.se.team7.*" %>



<% 


    PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();

    String procedureSkills = request.getParameter("skills");

    String dates= request.getParameter("dates");
    
    String[] procedureSkills_array = procedureSkills.split(", ");
    
    String[] dates_array = dates.split(",");
    
    String availabilityWeek = pl_service.maintainersAvailabilityWeek(procedureSkills_array, dates_array);

    response.setContentType("application/json");

    response.setHeader("Access-Control-Allow-Origin", "*");

    out.println(availabilityWeek);

%>