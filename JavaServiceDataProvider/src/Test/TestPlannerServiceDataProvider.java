package Test;
import it.unisa.se.team7.*;
import junit.framework.TestCase;
import org.junit.Test;

public class TestPlannerServiceDataProvider extends TestCase{

    //Activity Test
    @Test
    public void testCreateActivity() {
        PlannerServiceDataProvider P = new PlannerServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = P.createActivity(DBC, "Repair", 18, "true", 28, 2, 2, 2, "false", 2);
        String responseJson1 = P.createActivity(DBC, "Test", 18, "true", 24, 1, 1, 2, "false", 1);
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Activity created correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testModifyActivity() {
        PlannerServiceDataProvider P = new PlannerServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = P.modifyActivity(DBC, 1,"Repair", 18, "true", 25, 2, 2, 3, "false", 1);
        String responseJson1 = P.modifyActivity(DBC, 1,"Repair", 18, "true", 25, 2, 2, 3, "false", 1);
        String responseJsonActual = "[{\"err_code\":\"0\", \"msg\":\"Activity modified correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testDeleteActivity() {
        PlannerServiceDataProvider P = new PlannerServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = P.deleteActivity(DBC, 1);
        String responseJson1 = P.deleteActivity(DBC, 5);

        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Activity deleted correctly!\"}]";

        assertEquals(responseJson, responseJsonActual);

    }

    //Activity Maintainer Test
    @Test
    public void testAssignmentActivityMaintener(){
        PlannerServiceDataProvider P = new PlannerServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = P.assignmentActivityMaintainer(DBC, 3, 2, "date");
        String responseJson1 = P.assignmentActivityMaintainer(DBC, 3, 2, "14/12/2020");
        String responseJson2 = P.assignmentActivityMaintainer(DBC, 4, 4, "21/12/2020");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Activity assigned correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    //EWO Test
    @Test
    public void testUpdateEWO() {
        PlannerServiceDataProvider P = new PlannerServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = P.updateEWO(DBC,4, "Repairs", 29, 1);
        String responseJson1 = P.updateEWO(DBC, 4,"Repairs", 18,1);
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"EWO updated correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testAssignmentEWOCompetencies(){
        PlannerServiceDataProvider P = new PlannerServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = P.assignEwoCompetencies(DBC, 1,2,"4,5","0,1,0");
        String responseJson1 = P.assignEwoCompetencies(DBC, 1,2, "1,2", "1,1");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Competencies assigned correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }

    @Test
    public void testAssignmentEWOMaintainer(){
        PlannerServiceDataProvider P = new PlannerServiceDataProvider();
        DbConnection DBC = new DbConnection();

        String responseJson = P.assignmentEwoMaintainer(DBC, 2,2,"2020-04-27 16:00:00");
        String responseJson1 = P.assignmentEwoMaintainer(DBC, 3,1, "2020-12-14 10:06:00");
        String responseJsonActual = "[{\"err_code\":\"0\",\"msg\":\"Activity assigned correctly!\"}]";

        assertEquals(responseJson1, responseJsonActual);

    }
}
