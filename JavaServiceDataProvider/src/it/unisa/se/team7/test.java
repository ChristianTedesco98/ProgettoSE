package it.unisa.se.team7;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {

    public static void main(String[] args) throws FileNotFoundException {

        DbConnection db = new DbConnection();
        Connection con = db.connect();
        PlannerServiceDataProvider pl_service = new PlannerServiceDataProvider();
        DBLoaderJavaServiceDataProvider db_service = new DBLoaderJavaServiceDataProvider();

        String json = db_service.visualizeMaterials();
        System.out.println(json);

    }

}
