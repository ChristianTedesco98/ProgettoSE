package it.unisa.se.team7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PlannerServiceDataProvider {

    private Connection con;
    private DbConnection db = new DbConnection();

    /*
        Method that given the number of a week, returns in a String in JSON Format the attributes request for the
        activities scheduled for that week
     */
    public String visualizeWeekActivities( int week){

        String JSONFormat = "{\"id_activity\":\"{ID_ACTIVITY}\",\"area\":\"{AREA} - {FACTORY_SITE}\",\"type\":\"{TYPE}\",\"eit\":\"{EIT}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select maintenance_activities.id_activity, sites.area, sites.factory_site, typologies.name_typology, maintenance_activities.eit\n" +
                    "from maintenance_activities\n" +
                    "join sites on maintenance_activities.id_site = sites.id_site\n" +
                    "join typologies on maintenance_activities.id_typology = typologies.id_typology\n" +
                    "where maintenance_activities.week = "+week+" and maintenance_activities.ewo = 'false' and maintenance_activities.id_activity not in (select interventions.id_activity\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t\t\t\t\t\t\t\t  from interventions)" +
                    "order by maintenance_activities.id_activity");

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_ACTIVITY}", rs.getString(1));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{TYPE}", Util.utf8Encode(rs.getString(4)));
                JSONRow = JSONRow.replace("{EIT}", Util.utf8Encode(rs.getString(5)));
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();

        } catch (SQLException throwables) {
            String responseJson= "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
            return responseJson;
        }

        return "[" + Util.removeLastChar(JSONResult) + "]";
    }

    /*
        Given the id of a specific activity, the method returns a String in JSON format with the attributes of that
        specific activity
     */
    public String visualizeActivityDetails( int id_activity){
        String usersJSONFormat = "{\"workspace_notes\":\"{WORKSPACE_NOTES}\",\"description\":\"{DESCRIPTION}\",\"id_procedure\":\"{ID_PROCEDURE}\",\"name\":\"{NAME}\",\"skills\":\"{SKILLS}\"}";
        String usersJSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select workspace_notes.description, maintenance_activities.description, maintenance_procedures.id_procedure, maintenance_procedures.name\n" +
                    "from maintenance_activities\n" +
                    "join maintenance_procedures on maintenance_activities.id_procedure = maintenance_procedures.id_procedure\n" +
                    "left join workspace_notes on workspace_notes.id_activity = maintenance_activities.id_activity\n" +
                    "where maintenance_activities.id_activity = "+id_activity);
            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();
            int id_procedure = 0;
            while (rs.next()) {
                String notes = rs.getString(1);
                if(notes == null)
                    notes = "";
                JSONRow = usersJSONFormat.replace("{WORKSPACE_NOTES}", notes);
                JSONRow = JSONRow.replace("{DESCRIPTION}", Util.utf8Encode(rs.getString(2)));
                id_procedure = rs.getInt(3);
                JSONRow = JSONRow.replace("{ID_PROCEDURE}", String.valueOf(id_procedure));
                JSONRow = JSONRow.replace("{NAME}", Util.utf8Encode(rs.getString(4)));
            }
            List<String> skills = new ArrayList<String>();
            rs = stmt.executeQuery("select distinct competencies.name_competency\n" +
                    "from competencies\n" +
                    "join procedure_skills on competencies.id_competency = procedure_skills.id_competency\n" +
                    "where procedure_skills.id_procedure = "+id_procedure);
            while (rs.next()) {
                skills.add(rs.getString(1));
            }
            JSONRow = JSONRow.replace("{SKILLS}", Util.utf8Encode(skills.toString()));
            usersJSONResult = usersJSONResult + JSONRow + ",";
            this.con.close();
        } catch (SQLException throwables) {
            String responseJson= "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
            return responseJson;
        }

        return "[" + Util.removeLastChar(usersJSONResult) + "]";
    }


    /*
        Given the id of a maintainer and a specific date, the method returns an array of 7 values which rapresent the
        daily availability (in percentage) of that specific maintainer
     */
    private int[] dailyAvailability(Connection con, int id_maintainer, String date){
        int[] availabilty = new int[7];
        for(int i=0; i<7; i++)
            availabilty[i] = 60;
        String[] ore = {"8","9","10","11","14","15","16"};
        int i = 0;

        while(i<7){
            int block = i;
            String query = "select interventions.date_time_interv, sum(maintenance_activities.eit)\n" +
                    "from interventions join maintenance_activities on interventions.id_activity = maintenance_activities.id_activity\n" +
                    "where interventions.date_time_interv = '"+date+" {HOUR}:00:00'\n" +
                    "and interventions.id_user = "+id_maintainer+
                    "group by interventions.date_time_interv";
            query = query.replace("{HOUR}", ore[i]);

            Statement stmt = null;
            try {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs;
                rs = stmt.executeQuery(query);

                int minutes = 0;
                if (!rs.next()) {
                    minutes = 0;
                }
                else
                    rs.beforeFirst();


                while (rs.next()) {
                    minutes = rs.getInt(2);
                }

                if(minutes <= 0)
                    i++;
                else {
                    while (minutes > 0) {
                        if (minutes <= availabilty[i]) {
                            availabilty[i] -= minutes;
                            minutes = 0;
                            if(block == i)
                                i++;
                        } else {
                            minutes -= availabilty[i];
                            availabilty[i] = 0;
                            i++;
                        }

                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return availabilty;
    }

    /*
        Given an array of skills and an array of dates, the method returns a String in JSON format with the id of the
        maintainers, the ratio of their skills respect the given skills and their availability for each date in the
        array (in percentage)
     */
    public String maintainersAvailabilityWeek( String[] procedureSkills_array, String[] dates){
        List<String> procedureSkills = Arrays.asList(procedureSkills_array);
        String JSONFormat = "{\"id_maintainer\":\"{ID_MAINTAINER}\",\"name\":\"{NAME}\",\"skills\":\"{SKILLS}\",\"day0\":\"{DAY0}\",\"day1\":\"{DAY1}\",\"day2\":\"{DAY2}\",\"day3\":\"{DAY3}\",\"day4\":\"{DAY4}\",\"day5\":\"{DAY5}\",\"day6\":\"{DAY6}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select id_user, first_name from users where role = 'maintainer'");
            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();
            while (rs.next()){
                int id_maintainer = rs.getInt(1);
                String name = rs.getString(2);
                JSONRow= JSONFormat.replace("{ID_MAINTAINER}", String.valueOf(id_maintainer));
                JSONRow = JSONRow.replace("{NAME}", name);
                String skills_query = "select competencies.name_competency\n" +
                        "from competencies\n" +
                        "join maintainer_skills on competencies.id_competency = maintainer_skills.id_competency\n" +
                        "where maintainer_skills.id_user = "+id_maintainer;
                int procedure_skills = procedureSkills.size();
                int maintainer_skills = 0;
                ResultSet rs2;
                rs2 = stmt.executeQuery(skills_query);
                while(rs2.next()){
                    String skill = rs2.getString(1);
                    if(procedureSkills.contains(skill))
                        maintainer_skills++;
                }
                String skills = "{MAINTAINER_SKILLS}/{PROCEDURE_SKILLS}";
                skills = skills.replace("{MAINTAINER_SKILLS}", String.valueOf(maintainer_skills));
                skills = skills.replace("{PROCEDURE_SKILLS}", String.valueOf(procedure_skills));
                JSONRow = JSONRow.replace("{SKILLS}", skills);

                for(int i=0; i<7; i++){
                    int[] availability = this.dailyAvailability(this.con, id_maintainer, dates[i]);
                    int sum_availability = 0;
                    for(int j=0; j<7; j++)
                        sum_availability += availability[j];
                    double percentage_availability = (double)sum_availability / 420 * 100;
                    int int_part = (int)percentage_availability;
                    if(percentage_availability - int_part >= 0.6)
                        int_part++;
                    JSONRow = JSONRow.replace("{DAY"+i+"}", String.valueOf(int_part));
                }

                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();
        } catch (SQLException throwables) {
        }
        return "[" + Util.removeLastChar(JSONResult) + "]";
    }

    /*
        Given the id of a maintainer and a specific date, the method returns a String in JSON format with the availability
        of the maintainer in the 7 hour intervals of that day
     */
    public String maintainerAvailabilityDay( int id_maintainer, String date){
        String JSONFormat = "{\"hour0\":\"{HOUR0}\",\"hour1\":\"{HOUR1}\",\"hour2\":\"{HOUR2}\",\"hour3\":\"{HOUR3}\",\"hour4\":\"{HOUR4}\",\"hour5\":\"{HOUR5}\",\"hour6\":\"{HOUR6}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();
        int[] availability = this.dailyAvailability(this.con, id_maintainer, date);
        JSONRow = JSONFormat;
        for(int i=0; i<7; i++)
            JSONRow = JSONRow.replace("{HOUR"+i+"}", String.valueOf(availability[i]));
        JSONResult = JSONResult + JSONRow;
        return "[" + JSONResult + "]";
    }



    /*
        Given the id of an activity, the id of a specific maintainer and a String rapresenting the date/time of the
        intervention, the method inserts in the database the new intervention and return a String in JSON format
        rapresenting the operation result
     */
    public String assignmentActivityMaintainer( int id_activity, int id_maintainer, String date_time){
        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String insert_query = "insert into interventions values ({ID_USER}, {ID_ACTIVITY}, '{DATE_TIME}')";
        insert_query = insert_query.replace("{ID_USER}", Util.utf8Encode(String.valueOf(id_maintainer)));
        insert_query = insert_query.replace("{ID_ACTIVITY}", Util.utf8Encode(String.valueOf(id_activity)));
        insert_query = insert_query.replace("{DATE_TIME}", Util.utf8Encode(date_time));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(insert_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Activity assigned correctly!");
            this.con.close();
        } catch (SQLException throwables) {
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
        }

        return responseJson;
    }

    /*
        Return a String in JSON Format with the attributes of the planners
     */
    public String visualizePlanners(DbConnection db){

        String JSONFormat = "{\"id_planner\":\"{ID_PLANNER}\",\"first_name\":\"{FIRST_NAME}\",\"surname\":\"{SURNAME}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select id_user, first_name, surname from users where role = 'planner'");

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_PLANNER}", String.valueOf(rs.getInt(1)));
                JSONRow = JSONRow.replace("{FIRST_NAME}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{SURNAME}", Util.utf8Encode(rs.getString(3)));
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();

        } catch (SQLException throwables) {
            String responseJson= "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
            return responseJson;
        }
        return "[" + Util.removeLastChar(JSONResult) + "]";
    }

    /*
        Return a String in JSON Format with the attributes of the activities (both EWO and planned activities)
     */
    public String visualizeActivities(DbConnection db) {

        String JSONFormat = "{\"id_activity\":\"{ID_ACTIVITY}\",\"week\":\"{WEEK}\",\"interruptible\":\"{INTERRUPTIBLE}\",\"eit\":\"{EIT}\",\"area\":\"{AREA} - {FACTORY_SITE}\",\"type\":\"{TYPE}\",\"name\":\"{NAME}\",\"username\":\"{USERNAME}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();
        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select maintenance_activities.id_activity, maintenance_activities.week, maintenance_activities.interruptible, maintenance_activities.eit, typologies.name_typology, sites.area, sites.factory_site, maintenance_procedures.name, users.username\n" +
                    "from maintenance_activities\n" +
                    "join sites on maintenance_activities.id_site = sites.id_site\n" +
                    "join typologies on maintenance_activities.id_typology = typologies.id_typology\n" +
                    "join maintenance_procedures on maintenance_activities.id_procedure = maintenance_procedures.id_procedure\n" +
                    "join users on maintenance_activities.id_user = users.id_user\n" +
                    "where maintenance_activities.id_activity not in (select id_activity from interventions)" +
                    "order by id_activity");
            if (!rs.next()) {
                return "";
            } else
            rs.beforeFirst();
            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_ACTIVITY}", rs.getString(1));
                JSONRow = JSONRow.replace("{WEEK}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{INTERRUPTIBLE}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{EIT}", Util.utf8Encode(rs.getString(4)));
                JSONRow = JSONRow.replace("{TYPE}", Util.utf8Encode(rs.getString(5)));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(6)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(7)));
                JSONRow = JSONRow.replace("{NAME}", Util.utf8Encode(rs.getString(8)));
                JSONRow = JSONRow.replace("{USERNAME}", Util.utf8Encode(rs.getString(9)));
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();
        } catch (SQLException throwables) {
            String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson = responseJson.replace("{ERR_CODE}", "1");
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJson = responseJson.replace("{MSG}", msg);
            return responseJson;
        }

        return "[" + Util.removeLastChar(JSONResult) + "]";
    }


    /*
        Given the id of a specific activity, return a String in JSON Format with the attributes of that particular
        activity
     */
    public String visualizeActivity( int id_activity) {

        String JSONFormat = "{\"description\":\"{DESCRIPTION}\",\"week\":\"{WEEK}\",\"interruptible\":\"{INTERRUPTIBLE}\",\"eit\":\"{EIT}\",\"area\":\"{AREA} - {FACTORY_SITE}\",\"typology\":\"{TYPOLOGY}\",\"name_procedure\":\"{NAME_PROCEDURE}\",\"id_user\":\"{ID_USER}\",\"ewo\":\"{EWO}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();
        String query = "select maintenance_activities.description, maintenance_activities.week, maintenance_activities.interruptible, \n" +
                "maintenance_activities.eit, typologies.name_typology, sites.area, sites.factory_site, maintenance_procedures.name, maintenance_activities.id_user,\n" +
                "maintenance_activities.ewo\n" +
                "from maintenance_activities\n" +
                "join sites on maintenance_activities.id_site = sites.id_site\n" +
                "join typologies on maintenance_activities.id_typology = typologies.id_typology\n" +
                "join maintenance_procedures on maintenance_activities.id_procedure = maintenance_procedures.id_procedure\n" +
                "where maintenance_activities.id_activity = "+id_activity;
        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery(query);
            if (!rs.next()) {
                return "";
            } else
            rs.beforeFirst();
            while (rs.next()) {
                JSONRow = JSONFormat.replace("{DESCRIPTION}", rs.getString(1));
                JSONRow = JSONRow.replace("{WEEK}", String.valueOf(rs.getInt(2)));
                JSONRow = JSONRow.replace("{INTERRUPTIBLE}", String.valueOf(rs.getBoolean(3)));
                JSONRow = JSONRow.replace("{EIT}", String.valueOf(rs.getInt(4)));
                JSONRow = JSONRow.replace("{TYPOLOGY}", Util.utf8Encode(rs.getString(5)));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(6)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(7)));
                JSONRow = JSONRow.replace("{NAME_PROCEDURE}", Util.utf8Encode(rs.getString(8)));
                JSONRow = JSONRow.replace("{ID_USER}", Util.utf8Encode(rs.getString(9)));
                JSONRow = JSONRow.replace("{EWO}", String.valueOf(rs.getBoolean(10)));
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();
        } catch (SQLException throwables) {
            String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson = responseJson.replace("{ERR_CODE}", "1");
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJson = responseJson.replace("{MSG}", msg);
            return responseJson;
        }

        return "[" + Util.removeLastChar(JSONResult) + "]";
    }

    /*
        Given the attributes of a new activity, the method inserts it in the database and return a message in JSON Format
        that reports the operation result
     */
    public String createActivity( String description, int week, String interruptible,
                                 int eit, int id_typology, int id_site, int id_procedure, String ewo, int id_user)
    {
        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String insert_query = "insert into maintenance_activities (description, week, interruptible, eit, id_typology, id_site, id_procedure, ewo, id_user) values ('{DESCRIPTION}','{WEEK}','{INTERRUPTIBLE}','{EIT}','{ID_TYPOLOGY}','{ID_SITE}','{ID_PROCEDURE}','{EWO}','{ID_USER}')";
        insert_query = insert_query.replace("{DESCRIPTION}", Util.utf8Encode(description));
        insert_query = insert_query.replace("{WEEK}", String.valueOf(week));
        insert_query = insert_query.replace("{INTERRUPTIBLE}", Util.utf8Encode(interruptible));
        insert_query = insert_query.replace("{EIT}", String.valueOf(eit));
        insert_query = insert_query.replace("{ID_TYPOLOGY}", String.valueOf(id_typology));
        insert_query = insert_query.replace("{ID_SITE}", String.valueOf(id_site));
        insert_query = insert_query.replace("{ID_PROCEDURE}", String.valueOf(id_procedure));
        insert_query = insert_query.replace("{EWO}", Util.utf8Encode(ewo));
        insert_query = insert_query.replace("{ID_USER}", String.valueOf(id_user));
        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(insert_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Activity created correctly!");
            this.con.close();
        } catch (SQLException throwables) {
        responseJson=responseJson.replace("{ERR_CODE}", "1");
        String msg= throwables.getMessage().replace('\"', '*');
        msg= msg.replace('\n', ' ');
        responseJson=responseJson.replace("{MSG}", msg);
    }
        return responseJson;
    }

    /*
        Given the attributes of a specific activity, the method modify its attributes in the database and returns a
        message in JSON Format that reports the operation result
     */
    public String modifyActivity( int id_activity, String description, int week, String interruptible,
                                 int eit, int id_typology, int id_site, int id_procedure, String ewo, int id_user)
    {
        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String update_query = "update maintenance_activities set description = '{DESCRIPTION}', week = {WEEK}, " +
                "interruptible = '{INTERRUPTIBLE}', eit = {EIT}, id_typology = {ID_TYPOLOGY}, id_site = {ID_SITE}," +
                "id_procedure = {ID_PROCEDURE}, ewo = '{EWO}', id_user = {ID_USER} where id_activity = "+id_activity;
        update_query = update_query.replace("{DESCRIPTION}", Util.utf8Encode(description));
        update_query = update_query.replace("{WEEK}", String.valueOf(week));
        update_query = update_query.replace("{INTERRUPTIBLE}", Util.utf8Encode(interruptible));
        update_query = update_query.replace("{EIT}", String.valueOf(eit));
        update_query = update_query.replace("{ID_TYPOLOGY}", String.valueOf(id_typology));
        update_query = update_query.replace("{ID_SITE}", String.valueOf(id_site));
        update_query = update_query.replace("{ID_PROCEDURE}", String.valueOf(id_procedure));
        update_query = update_query.replace("{EWO}", Util.utf8Encode(ewo));
        update_query = update_query.replace("{ID_USER}", String.valueOf(id_user));
        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(update_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Activity modified correctly!");
            this.con.close();
        } catch (SQLException throwables) {
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
        }
        return responseJson;
    }

    /*
        Given the id of a specific activity, the method deletes it from the database and return a
        message in JSON Format that reports the operation result
     */
    public String deleteActivity( int idActivity){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String deleteQuery = "delete from maintenance_activities where id_activity = '{ID_ACTIVITY}'";
        deleteQuery = deleteQuery.replace("{ID_ACTIVITY}", Util.utf8Encode(String.valueOf(idActivity)));
        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(deleteQuery);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Activity deleted correctly!");
            this.con.close();
        } catch (SQLException throwables) {
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
        }

        return responseJson;
    }

    /*
        Return a String in JSON Format with the attributes of the EWOs
     */
    public String visualizeEWOs(DbConnection db){
        String JSONFormat = "{\"id_activity\":\"{ID_ACTIVITY}\",\"area\":\"{AREA} - {FACTORY_SITE}\",\"type\":\"{TYPE}\",\"eit\":\"{EIT}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select maintenance_activities.id_activity, sites.area, sites.factory_site, typologies.name_typology, maintenance_activities.eit\n" +
                    "from maintenance_activities\n" +
                    "join sites on maintenance_activities.id_site = sites.id_site\n" +
                    "join typologies on maintenance_activities.id_typology = typologies.id_typology\n" +
                    "where maintenance_activities.ewo = true and maintenance_activities.id_activity not in (select interventions.id_activity\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t\t\t\t\t\t\t\t  from interventions)" +
                    "order by maintenance_activities.id_activity");

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_ACTIVITY}", rs.getString(1));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{TYPE}", Util.utf8Encode(rs.getString(4)));
                JSONRow = JSONRow.replace("{EIT}", Util.utf8Encode(rs.getString(5)));
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();

        } catch (SQLException throwables) {
            String responseJson= "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
            return responseJson;
        }

        return "[" + Util.removeLastChar(JSONResult) + "]";
    }

    /*
        Given the id of a specific ewo, return a String in JSON Format with the attributes of that particular ewo
     */
    public String visualizeEWO( int id_activity){
        String JSONFormat = "{\"workspace_note\":\"{WORKSPACE_NOTE}\",\"description\":\"{DESCRIPTION}\",\"eit\":\"{EIT}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select maintenance_activities.description, maintenance_activities.eit, workspace_notes.description\n" +
                    "from maintenance_activities \n" +
                    "left join workspace_notes on maintenance_activities.id_activity = workspace_notes.id_activity\n" +
                    "where maintenance_activities.id_activity = "+id_activity);

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                String description = rs.getString(1);
                if(description == null)
                    description = "";
                JSONRow = JSONFormat.replace("{DESCRIPTION}", Util.utf8Encode(description));
                JSONRow = JSONRow.replace("{EIT}", Util.utf8Encode(rs.getString(2)));
                String workspace_note = rs.getString(3);
                if(workspace_note == null)
                    workspace_note = "";
                JSONRow = JSONRow.replace("{WORKSPACE_NOTE}", Util.utf8Encode(workspace_note));
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();

        } catch (SQLException throwables) {
            String responseJson= "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
            return responseJson;
        }

        return "[" + Util.removeLastChar(JSONResult) + "]";
    }

    /*
        Given the attributes of a specific ewo, the method modify its attributes in the database and return a
        message in JSON Format that reports the operation result
     */
    public String updateEWO( int id_activity, String description, int eit, int day){
        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String updateQuery = "update maintenance_activities set  description = '{DESCRIPTION}', eit = {EIT}, ewo_day = {EWO_DAY} where id_activity = '{ID_ACTIVITY}'";
        updateQuery = updateQuery.replace("{ID_ACTIVITY}", Util.utf8Encode(String.valueOf(id_activity)));
        updateQuery = updateQuery.replace("{DESCRIPTION}", Util.utf8Encode(description));
        updateQuery = updateQuery.replace("{EIT}", Util.utf8Encode(String.valueOf(eit)));
        updateQuery = updateQuery.replace("{EWO_DAY}", Util.utf8Encode(String.valueOf(day)));
        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(updateQuery);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Activity updated correctly!");
            this.con.close();
        } catch (SQLException throwables) {
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
        }

        return responseJson;
    }

    /*
        Given the id of a specific ewo, the number of competencies, the ids of the competencies and an array which
        rapresents if a specific competency must be associated with the EWO, the method insert the associations and
        return a String in JSON Format which rapresents the operation result
     */
    public String assignEwoCompetencies(int id_activity, int num_competencies,
                                             String id_competencies, String checks) {

        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String[] checks_array = checks.split("\\,", -1);
        String[] id_competencies_array = id_competencies.split("\\,", -1);
        this.con = db.connect();
        String query = "";
        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int checkDelete = stmt.executeUpdate("delete from ewo_skills where id_activity='" + id_activity + "'");
            for (int i = 0; i < num_competencies; i++) {
                if (checks_array[i].compareTo("1") == 0) {

                    query = "insert into ewo_skills values ({ID_ACTIVITY},{ID_COMPETENCY})";
                    query = query.replace("{ID_ACTIVITY}", String.valueOf(id_activity));
                    query = query.replace("{ID_COMPETENCY}", id_competencies_array[i]);
                    int checkInsert = stmt.executeUpdate(query);
                }
            }
            responseJson = responseJson.replace("{ERR_CODE}", "0");
            responseJson = responseJson.replace("{MSG}", "Competencies assigned correctly!");
            this.con.close();
        } catch (SQLException throwables) {
            responseJson = responseJson.replace("{ERR_CODE}", "1");
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJson = responseJson.replace("{MSG}", msg);
        }
        return responseJson;
    }

    /*
        Given the id of a specific ewo, the id of a maintainer and a String which rapresents date/time, the method
        create a new interventions, remove the interventions related to the interruptible activity and returns a String
        in JSON format with the operation result
     */
    public String assignmentEwoMaintainer( int id_activity, int id_maintainer, String date_time){
        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String delete_query = "delete from interventions \n" +
                "where id_user = "+id_maintainer+" and date_time_interv = '"+date_time+"'\n" +
                "and interventions.id_activity in (select maintenance_activities.id_activity\n" +
                "\t\t\t\t\t\t\t\t  from maintenance_activities\n" +
                "\t\t\t\t\t\t\t\t  where maintenance_activities.interruptible = 'true')";
        String insert_query = "insert into interventions values ({ID_USER}, {ID_ACTIVITY}, '{DATE_TIME}')";
        insert_query = insert_query.replace("{ID_USER}", Util.utf8Encode(String.valueOf(id_maintainer)));
        insert_query = insert_query.replace("{ID_ACTIVITY}", Util.utf8Encode(String.valueOf(id_activity)));
        insert_query = insert_query.replace("{DATE_TIME}", Util.utf8Encode(date_time));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(delete_query);
            res = stmt.executeUpdate(insert_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Activity assigned correctly!");
            this.con.close();
        } catch (SQLException throwables) {
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
        }
        return responseJson;
    }

    /*
        Return a String in JSON Format with the status of EWOs.
     */
    public String visualizeStatusEwos( int week, int ewo_day){
        String JSONFormat = "{\"id_activity\":\"{ID_ACTIVITY}\",\"area\":\"{AREA} - {FACTORY_SITE}\",\"type\":\"{TYPE}\",\"eit\":\"{EIT}\",\"state_department\":\"{STATE_DEPARTMENT}\",\"state_maintainer\":\"{STATE_MAINTAINER}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select maintenance_activities.id_activity, sites.area, sites.factory_site, typologies.name_typology, maintenance_activities.eit\n" +
                    "from maintenance_activities\n" +
                    "join sites on maintenance_activities.id_site = sites.id_site\n" +
                    "join typologies on maintenance_activities.id_typology = typologies.id_typology\n" +
                    "where maintenance_activities.ewo = true and maintenance_activities.week = "+week+"and maintenance_activities.ewo_day = "+ewo_day+" " +
                    "and maintenance_activities.id_activity not in (select interventions.id_activity\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t\t\t\t\t\t\t\t  from interventions)");

            if (!rs.next()) {
                JSONRow =  "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_ACTIVITY}", rs.getString(1));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{TYPE}", Util.utf8Encode(rs.getString(4)));
                JSONRow = JSONRow.replace("{EIT}", Util.utf8Encode(rs.getString(5)));
                JSONRow = JSONRow.replace("{STATE_DEPARTMENT}", "NOT SENT");
                JSONRow = JSONRow.replace("{STATE_MAINTAINER}", "NOT SENT");
                JSONResult = JSONResult + JSONRow + ",";
            }


            rs = stmt.executeQuery("select maintenance_activities.id_activity, sites.area, sites.factory_site, typologies.name_typology, maintenance_activities.eit\n" +
                    "from maintenance_activities\n" +
                    "join sites on maintenance_activities.id_site = sites.id_site\n" +
                    "join typologies on maintenance_activities.id_typology = typologies.id_typology\n" +
                    "where maintenance_activities.ewo = true and maintenance_activities.week = "+week+"and maintenance_activities.ewo_day = "+ewo_day+" " +
                    "and maintenance_activities.id_activity in (select interventions.id_activity\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t\t\t\t\t\t\t\t  from interventions)");

            if (!rs.next()) {
                JSONRow = "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_ACTIVITY}", rs.getString(1));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{TYPE}", Util.utf8Encode(rs.getString(4)));
                JSONRow = JSONRow.replace("{EIT}", Util.utf8Encode(rs.getString(5)));
                JSONRow = JSONRow.replace("{STATE_DEPARTMENT}", "SENT");
                JSONRow = JSONRow.replace("{STATE_MAINTAINER}", "SENT");
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();

        } catch (SQLException throwables) {
            String responseJson= "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
            responseJson=responseJson.replace("{ERR_CODE}", "1");
            String msg= throwables.getMessage().replace('\"', '*');
            msg= msg.replace('\n', ' ');
            responseJson=responseJson.replace("{MSG}", msg);
            return responseJson;
        }

        if (JSONResult.isEmpty())
            return JSONResult;
        return "[" + Util.removeLastChar(JSONResult) + "]";
    }

    /*
        Given the id of a specific EWO, return a String in JSON Format with the attributes of the competencies and if
        they're associated with that specific EWO
     */
    public String visualizeEwoCompetencies(int id_activity) {
        String sitesJSONFormat = "{\"id_competency\":\"{ID_COMPETENCY}\",\"name_competency\":\"{NAME_COMPETENCY}\",\"assigned\":\"{ASSIGNED}\"}";
        String sitesJSONResult = "";
        String JSONRow = "";
        this.con = db.connect();
        HashMap<Integer, Integer> assigned_map = new HashMap<Integer, Integer>();
        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select id_competency from ewo_skills where id_activity = " + id_activity + " order by id_competency");
            if (!rs.next())
                ;
            else {
                rs.beforeFirst();
                while (rs.next()) {
                    assigned_map.put(rs.getInt(1), 1);
                }
            }
            rs = stmt.executeQuery("select id_competency, name_competency from competencies order by id_competency");
            if (!rs.next())
                return "";
            else {
                int id_competency, assigned;
                String name_competency;
                rs.beforeFirst();
                while (rs.next()) {
                    id_competency = rs.getInt(1);
                    name_competency = rs.getString(2);
                    if (assigned_map.containsKey(id_competency))
                        assigned = 1;
                    else
                        assigned = 0;
                    JSONRow = sitesJSONFormat.replace("{ID_COMPETENCY}", String.valueOf(id_competency));
                    JSONRow = JSONRow.replace("{NAME_COMPETENCY}", name_competency);
                    JSONRow = JSONRow.replace("{ASSIGNED}", String.valueOf(assigned));
                    sitesJSONResult = sitesJSONResult + JSONRow + ',';
                }
            }

            this.con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "[" + Util.removeLastChar(sitesJSONResult) + "]";
    }

    /*
        Given an array of skills and a date, the method returns a String in JSON format with the id of the
        maintainers, the ratio of their skills respect the given skills and their availability for each hour interval
        in the day (in minutes)
     */
    public String maintainersDailyAvailability( String[] procedureSkills_array, String date){
        List<String> procedureSkills = Arrays.asList(procedureSkills_array);
        String JSONFormat = "{\"id_maintainer\":\"{ID_MAINTAINER}\",\"name\":\"{NAME}\",\"skills\":\"{SKILLS}\",\"hour0\":\"{HOUR0}\",\"hour1\":\"{HOUR1}\",\"hour2\":\"{HOUR2}\",\"hour3\":\"{HOUR3}\",\"hour4\":\"{HOUR4}\",\"hour5\":\"{HOUR5}\",\"hour6\":\"{HOUR6}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select id_user, first_name from users where role = 'maintainer'");
            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();
            while (rs.next()){
                int id_maintainer = rs.getInt(1);
                String name = rs.getString(2);
                JSONRow= JSONFormat.replace("{ID_MAINTAINER}", String.valueOf(id_maintainer));
                JSONRow = JSONRow.replace("{NAME}", name);
                String skills_query = "select competencies.name_competency\n" +
                        "from competencies\n" +
                        "join maintainer_skills on competencies.id_competency = maintainer_skills.id_competency\n" +
                        "where maintainer_skills.id_user = "+id_maintainer;
                int procedure_skills = procedureSkills.size();
                int maintainer_skills = 0;
                ResultSet rs2;
                rs2 = stmt.executeQuery(skills_query);
                while(rs2.next()){
                    String skill = rs2.getString(1);
                    if(procedureSkills.contains(skill))
                        maintainer_skills++;
                }
                String skills = "{MAINTAINER_SKILLS}/{PROCEDURE_SKILLS}";
                skills = skills.replace("{MAINTAINER_SKILLS}", String.valueOf(maintainer_skills));
                skills = skills.replace("{PROCEDURE_SKILLS}", String.valueOf(procedure_skills));
                JSONRow = JSONRow.replace("{SKILLS}", skills);

                int[] availability = this.dailyAvailability(this.con, id_maintainer, date);

                for(int i=0; i<7; i++){
                    JSONRow = JSONRow.replace("{HOUR"+i+"}", String.valueOf(availability[i]));
                }
                JSONResult = JSONResult + JSONRow + ",";
            }
            this.con.close();
        } catch (SQLException throwables) {
        }
        return "[" + Util.removeLastChar(JSONResult) + "]";
    }


}
