package Test;
import it.unisa.se.team7.*;
import junit.framework.TestCase;
import org.junit.Test;


public class TestCasesSystemAdministrator extends TestCase {

    //User Test
    @Test
    public void testCreateUser() {
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.createUser(DBC, "Antonio", "Cooper", "data", "hello123", "1122334457", "cooper@gmail.it", "planner", "aelemao");
        String responseJson1 = SA.createUser(DBC, "Antonio", "Cooper", "14/10/1997", "hello123", "1122334457", "cooper@gmail.it", "planner", "aelemao");
        String responseJson2 = SA.createUser(DBC, "Hardin", "Scott", "02/04/2002", "softing123", "3789446070", "hardin@gmail.it", "planner", "afterall");

        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"User created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testModifyUser(){
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();


        String responseJson = SA.modifyUser(DBC, 1, "Hardin", "Scott", "02/04/2002", "softing123", "378944607", "hardin@gmail.it", "planner", "afterall");
        String responseJson1 = SA.modifyUser(DBC, 2, "Christian", "Tedesco", "21/12/1998", "chuckakrikko", "3287267984", "krikko@gmail.it", "planner", "krikkoJ98");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"User modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);
    }

    @Test
    public void testDeleteUser(){
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.deleteUser(DBC,1);
        String responseJson1 = SA.deleteUser(DBC, 8);

        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"User deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }



    //Competency Test
    @Test
    public void testCreateCompetency() {
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.createCompetency(DBC, "PAV Certification" );
        String responseJson1 = SA.createCompetency(DBC, "Electric Maintenance");
        String responseJson2 = SA.createCompetency(DBC, "Electric Maintenance");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Competency created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }


    @Test
    public void testModifyCompetency(){
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.modifyCompetency(DBC, 8, "ASD");
        String responseJson1 = SA.modifyCompetency(DBC, 9, "CompetencyTest");

        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Competency modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);
    }

    @Test
    public void testDeleteCompetency(){
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.deleteCompetency(DBC, 2);
        String responseJson1 = SA.deleteCompetency(DBC, 9);
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Competency deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    //Mainteiner Competences Test
    @Test
    public void testAssignMaintainerCompetences(){
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.assignMaintainerCompetences(DBC, 2, 2, "6", "0");
        String responseJson1 = SA.assignMaintainerCompetences(DBC, 2, 1, "6", "0");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Competencies assigned correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    //Notes Test
    @Test
    public void testCreateNoteActivity() {
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.createNoteActivity(DBC, "It''s possible intervene only after 15:00", 2);
        String responseJson1 = SA.createNoteActivity(DBC, "It''s possible intervene only after 08:00", 1);
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Note created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testCreateNoteSite() {
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.createNoteSite(DBC, "The plant is closed from 00/00/20 to 00/00/20", 2);
        String responseJson1 = SA.createNoteSite(DBC, "The plant is opened from 01/00/20 to 01/00/20", 4);
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Note created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testModifyNote() {
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.modifyNote(DBC, 23, "It''s possible intervene only after 08:00");
        String responseJson1 = SA.modifyNote(DBC, 19, "The plant is dane");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Note modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteNote() {
        SystemAdministratorDataProvider SA = new SystemAdministratorDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = SA.deleteNote(DBC, 23);
        String responseJson1 = SA.deleteNote(DBC, 23);
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Note deleted correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }
}