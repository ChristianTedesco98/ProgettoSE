<%@ page import = "it.unisa.se.team7.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>

<% 
    DbConnection db = new DbConnection();
    Connection con = db.connect();
    int id_procedure = Integer.valueOf(request.getParameter("id_procedure"));
    DBLoaderJavaServiceDataProvider db_service = new DBLoaderJavaServiceDataProvider();
    byte[] pdfBytes = db_service.visualizeProcedureSMP(db, id_procedure);

    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setContentType("application/pdf");
    response.getOutputStream().write(pdfBytes);
    response.getOutputStream().flush();
%>