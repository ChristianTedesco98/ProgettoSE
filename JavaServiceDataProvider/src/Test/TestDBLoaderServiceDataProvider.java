package Test;
import it.unisa.se.team7.*;
import junit.framework.TestCase;
import org.junit.Test;

public class TestDBLoaderServiceDataProvider extends TestCase{

    //Sites Test
    @Test
    public void testCreateSite() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.createSite(DBC, "Morra", "Painting");
        String responseJson1 = DBL.createSite(DBC, "Firenze", "FactorySiteTest");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Site created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testModifySite() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.modifySite(DBC, 2,"Morra", "Painting");
        String responseJson1 = DBL.modifySite(DBC, 2,"Verona", "Painting");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Site modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteSite() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.deleteSite(DBC, 3);
        String responseJson1 = DBL.deleteSite(DBC, 12);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Site deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    //Typology Test
    @Test
    public void testCreateTypology() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.createTypology(DBC, "Hydraulic");
        String responseJson1 = DBL.createTypology(DBC, "TypologyTest");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Typology created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    @Test
    public void testModifyTypology() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.modifyTypology(DBC, 4, "Physical Typology");
        String responseJson1 = DBL.modifyTypology(DBC, 6, "Electronics");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Typology modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteTypology() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.deleteTypology(DBC, 2);
        String responseJson1 = DBL.deleteTypology(DBC, 1);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Typology deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    //Procedures Test
    @Test
    public void testModifyProcedureName() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.modifyProcedureName(DBC, 3, "prova1.pdf");
        String responseJson1 = DBL.modifyProcedureName(DBC, 3, "prova1.pdf");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Procedure modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteProcedure() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.deleteProcedure(DBC, 1);
        String responseJson1 = DBL.deleteProcedure(DBC, 5);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Procedure deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    //Material test
    @Test
    public void testCreateMaterial() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.createMaterial(DBC, "Materiali Lapidei");
        String responseJson1 = DBL.createMaterial(DBC, "Rame");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Material created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteMaterial() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.deleteMaterial(DBC, 1);
        String responseJson1 = DBL.deleteMaterial(DBC, 16);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Material deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    //Assign Procedure Competences

    @Test
    public void testAssignProcedureCompetences(){
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = DBL.assignProcedureCompetences(DBC, 2, 2, "4", "0");
        String responseJson1 = DBL.assignProcedureCompetences(DBC, 2, 1, "4", "0");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Competencies assigned correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

}
