<%@ page import = "it.unisa.se.team7.*" %>

<% 
    DbConnection db = new DbConnection(); 
    SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
    String first_name = request.getParameter("first_name");
    String surname = request.getParameter("surname");
    String birth_date = request.getParameter("birth_date");
    String password = request.getParameter("password");
    String cell_num = request.getParameter("cell_num");
    String email = request.getParameter("email");
    String role = request.getParameter("role");
    String username = request.getParameter("username");
    String json = sa_service.createUser(db, first_name, surname, birth_date, password, cell_num, email, role, username);
%>
<%=json%>