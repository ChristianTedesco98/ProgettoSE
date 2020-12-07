<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String id = request.getParameter("id");
    String json = "[{\"id_competency\":\"{ID_COMPETENCE}\"}]";
    json = json.replace("{ID_COMPETENCE}", id);
    json = sa_service.visualizeCompetence(db, json);
    out.println(json);
%>