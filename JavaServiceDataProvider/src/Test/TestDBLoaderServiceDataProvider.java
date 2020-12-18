package Test;
import it.unisa.se.team7.*;
import junit.framework.TestCase;
import org.junit.Test;

public class TestDBLoaderServiceDataProvider extends TestCase{

    //Sites Test
    @Test
    public void testCreateSite() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.createSite( "Morra", "Painting");
        String responseJson1 = DBL.createSite( "Firenze", "FactorySiteTest");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Site created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testModifySite() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.modifySite( 2,"Morra", "Painting");
        String responseJson1 = DBL.modifySite( 2,"Verona", "Painting");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Site modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteSite() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.deleteSite( 3);
        String responseJson1 = DBL.deleteSite( 12);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Site deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    //Typology Test
    @Test
    public void testCreateTypology() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.createTypology( "Hydraulic");
        String responseJson1 = DBL.createTypology( "TypologyTest");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Typology created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    @Test
    public void testModifyTypology() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.modifyTypology( 4, "Physical Typology");
        String responseJson1 = DBL.modifyTypology( 6, "Electronics");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Typology modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteTypology() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.deleteTypology( 2);
        String responseJson1 = DBL.deleteTypology( 1);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Typology deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    //Procedures Test
    @Test
    public void testModifyProcedureName() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.modifyProcedureName( 3, "prova1.pdf");
        String responseJson1 = DBL.modifyProcedureName( 3, "prova1.pdf");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Procedure modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteProcedure() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.deleteProcedure( 1);
        String responseJson1 = DBL.deleteProcedure( 5);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Procedure deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    //Material test
    @Test
    public void testCreateMaterial() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.createMaterial( "Materiali Lapidei");
        String responseJson1 = DBL.createMaterial( "Rame");
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Material created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteMaterial() {
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.deleteMaterial( 1);
        String responseJson1 = DBL.deleteMaterial( 16);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Material deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    //Assign Procedure Competences

    @Test
    public void testAssignProcedureCompetences(){
        DBLoaderJavaServiceDataProvider DBL = new DBLoaderJavaServiceDataProvider();


        String responseJson = DBL.assignProcedureCompetences( 2, 2, "4", "0");
        String responseJson1 = DBL.assignProcedureCompetences( 2, 1, "4", "0");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Competencies assigned correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

}
