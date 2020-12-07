package it.unisa.se.team7;

public class test {

    public static void main(String[] args){

        DbConnection db = new DbConnection();
        DBLoaderJavaServiceDataProvider db_service = new DBLoaderJavaServiceDataProvider();
        int id_material = 11;
        String name_material = "test";
        db_service.deleteMaterial(db,id_material);
        String json = db_service.visualizeMaterials(db);
        System.out.println(json);
    }

}
