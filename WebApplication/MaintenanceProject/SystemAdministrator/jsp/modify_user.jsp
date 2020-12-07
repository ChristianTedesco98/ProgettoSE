<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();    
    int id_user = Integer.valueOf(request.getParameter("id_user"));
    String first_name = request.getParameter("first_name");
    String surname = request.getParameter("surname");
    String birth_date = request.getParameter("birth_date");
    String password = request.getParameter("password");
    String cell_num = request.getParameter("cell_num");
    String email = request.getParameter("email");
    String role = request.getParameter("role");
    String username = request.getParameter("username");
    String json=sa_service.modifyUser(db, id_user, first_name, surname, birth_date, password, cell_num, email, role, username);
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<%=json%>
