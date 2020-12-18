package it.unisa.se.team7;

import java.sql.*;
import java.util.HashMap;

public class SystemAdministratorDataProvider {

    private Connection con;
    private DbConnection db = new DbConnection();

    /*
        Return a String in a JSON Format with the attributes of the users.
     */
    public String visualizeUsers() {
        String usersJSONFormat = "{\"id_user\":\"{ID_USER}\",\"first_name\":\"{FIRST_NAME}\",\"surname\":\"{SURNAME}\",\"birth_date\":\"{BIRTH_DATE}\",\"username\":\"{USERNAME}\",\"password\":\"{PASSWORD}\",\"cell_num\":\"{CELL_NUMBER}\",\"email\":\"{EMAIL}\",\"role\":\"{ROLE}\"}";
        String usersJSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM users ORDER BY id_user");

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = usersJSONFormat.replace("{ID_USER}", rs.getString(1));
                JSONRow = JSONRow.replace("{FIRST_NAME}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{SURNAME}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{BIRTH_DATE}", Util.utf8Encode(rs.getString(4)));
                JSONRow = JSONRow.replace("{PASSWORD}", Util.utf8Encode(rs.getString(5)));
                JSONRow = JSONRow.replace("{CELL_NUMBER}", Util.utf8Encode(rs.getString(6)));
                JSONRow = JSONRow.replace("{EMAIL}", Util.utf8Encode(rs.getString(7)));
                JSONRow = JSONRow.replace("{ROLE}", Util.utf8Encode(rs.getString(8)));
                JSONRow = JSONRow.replace("{USERNAME}", Util.utf8Encode(rs.getString(9)));
                usersJSONResult = usersJSONResult + JSONRow + ",";
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

        return "[" + Util.removeLastChar(usersJSONResult) + "]";
    }

    /*
        Given the id of a specific user, return a String in a JSON Format with the attributes of that specific user.
     */
    public String visualizeUser( int idUser){
        String usersJSONFormat = "{\"id_user\":\"{ID_USER}\",\"first_name\":\"{FIRST_NAME}\",\"surname\":\"{SURNAME}\",\"birth_date\":\"{BIRTH_DATE}\",\"username\":\"{USERNAME}\",\"password\":\"{PASSWORD}\",\"cell_num\":\"{CELL_NUMBER}\",\"email\":\"{EMAIL}\",\"role\":\"{ROLE}\"}";
        String usersJSONResult = "";
        String JSONRow = "";

        this.con = db.connect();

        String visualize_query = "select * from users where id_user = {ID_USER}";
        visualize_query = visualize_query.replace("{ID_USER}", Util.utf8Encode(String.valueOf(idUser)));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery(visualize_query);

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = usersJSONFormat.replace("{ID_USER}", Util.utf8Encode(rs.getString(1)));
                JSONRow = JSONRow.replace("{FIRST_NAME}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{SURNAME}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{BIRTH_DATE}", Util.utf8Encode(rs.getString(4)));
                JSONRow = JSONRow.replace("{PASSWORD}", Util.utf8Encode(rs.getString(5)));
                JSONRow = JSONRow.replace("{CELL_NUMBER}", Util.utf8Encode(rs.getString(6)));
                JSONRow = JSONRow.replace("{EMAIL}", Util.utf8Encode(rs.getString(7)));
                JSONRow = JSONRow.replace("{ROLE}", Util.utf8Encode(rs.getString(8)));
                JSONRow = JSONRow.replace("{USERNAME}", Util.utf8Encode(rs.getString(9)));
                usersJSONResult = usersJSONResult + JSONRow + ",";
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
        return "[" + Util.removeLastChar(usersJSONResult) + "]";
    }

    /*
       Given the attributes of a new user, the method insert it in the database and return a message in a JSON Format
       that reports the operation result.
     */
    public String createUser( String firstName, String surname, String birth_date, String password,
                           String cell_num, String email, String role, String username){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String insert_query = "insert into users (first_name, surname, birth_date, password, cell_num, email, role, username) values ('{FIRST_NAME}','{SURNAME}','{BIRTH_DATE}','{PASSWORD}','{CELL_NUM}','{EMAIL}','{ROLE}','{USERNAME}')";
        insert_query = insert_query.replace("{FIRST_NAME}", Util.utf8Encode(firstName));
        insert_query = insert_query.replace("{SURNAME}", Util.utf8Encode(surname));
        insert_query = insert_query.replace("{BIRTH_DATE}", Util.utf8Encode(birth_date));
        insert_query = insert_query.replace("{PASSWORD}", Util.utf8Encode(password));
        insert_query = insert_query.replace("{CELL_NUM}", Util.utf8Encode(cell_num));
        insert_query = insert_query.replace("{EMAIL}", Util.utf8Encode(email));
        insert_query = insert_query.replace("{ROLE}", Util.utf8Encode(role));
        insert_query = insert_query.replace("{USERNAME}", Util.utf8Encode(username));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(insert_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "User created correctly!");
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
       Given the attributes of a specific user, the method modify its attributes in the database and return a message in a
       JSON Format that reports the operation result.
     */

    public String modifyUser( int idUser, String firstName, String surname, String birth_date, String password,
                           String cell_num, String email, String role, String username) {

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";


        String modify_query = "update users set first_name = '{FIRST_NAME}', surname = '{SURNAME}', birth_date = " +
                "'{BIRTH_DATE}', password = '{PASSWORD}', cell_num = '{CELL_NUM}', email = '{EMAIL}', role = '{ROLE}'" +
                ", username = '{USERNAME}' where id_user = {ID_USER}";
        modify_query = modify_query.replace("{ID_USER}", Util.utf8Encode(String.valueOf(idUser)));
        modify_query = modify_query.replace("{FIRST_NAME}", Util.utf8Encode(firstName));
        modify_query = modify_query.replace("{SURNAME}", Util.utf8Encode(surname));
        modify_query = modify_query.replace("{BIRTH_DATE}", Util.utf8Encode(birth_date));
        modify_query = modify_query.replace("{PASSWORD}", Util.utf8Encode(password));
        modify_query = modify_query.replace("{CELL_NUM}", Util.utf8Encode(cell_num));
        modify_query = modify_query.replace("{EMAIL}", Util.utf8Encode(email));
        modify_query = modify_query.replace("{ROLE}", Util.utf8Encode(role));
        modify_query = modify_query.replace("{USERNAME}", Util.utf8Encode(username));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(modify_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "User modified correctly!");
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
       Given the id of a specific user, the method delete it from the database and return a message in a
       JSON Format that reports the operation result.
     */

    public String deleteUser( int idUser){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String deleteQuery = "delete from users where id_user = '{ID_USER}'";
        deleteQuery = deleteQuery.replace("{ID_USER}", Util.utf8Encode(String.valueOf(idUser)));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(deleteQuery);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "User deleted correctly!");

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
        Return a String in a JSON Format with the attributes of the competencies.
     */

    public String visualizeCompetencies(){
        String competenciesJSONFormat = "{\"id_competency\":\"{ID_COMPETENCY}\", \"name_competency\":\"{NAME_COMPETENCY}\"}";
        String competenciesJSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM competencies ORDER BY id_competency");

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = competenciesJSONFormat.replace("{ID_COMPETENCY}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME_COMPETENCY}", rs.getString(2));
                competenciesJSONResult = competenciesJSONResult + JSONRow + ",";
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

        return "[" + Util.removeLastChar(competenciesJSONResult) + "]";
    }

    /*
        Given the id of a specific competency, return a String in a JSON Format with the attributes of
        that specific competency.
     */

    public String visualizeCompetency( int idCompetency){
        String competenciesJSONFormat = "{\"id_competency\":\"{ID_COMPETENCY}\", \"name\":\"{NAME_COMPETENCY}\"}";
        String competenciesJSONResult = "";
        String JSONRow = "";

        this.con = db.connect();


        String visualize_query = "select * from competencies where id_competency = '{ID_COMPETENCY}'";
        visualize_query = visualize_query.replace("{ID_COMPETENCY}", Util.utf8Encode(String.valueOf(idCompetency)));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery(visualize_query);

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = competenciesJSONFormat.replace("{ID_COMPETENCY}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME_COMPETENCY}", rs.getString(2));
                competenciesJSONResult = competenciesJSONResult + JSONRow + ",";
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

        return "[" + Util.removeLastChar(competenciesJSONResult) + "]";
    }

    /*
       Given the attributes of a new competency, the method insert it in the database and return a message in a
       JSON Format that reports the operation result.
     */

    public String createCompetency( String nameCompetency){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String insert_query = "insert into competencies (name_competency) values ('{NAME_COMPETENCY}')";
        insert_query = insert_query.replace("{NAME_COMPETENCY}", Util.utf8Encode(nameCompetency));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(insert_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Competency created correctly!");
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
       Given the attributes of a specific competency, the method modify its attributes in the database and
       return a message in a JSON Format that reports the operation result.
     */

    public String modifyCompetency( int idCompetency, String nameCompetency){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String modify_query = "update competencies set name_competency = '{NAME_COMPETENCY}' where id_competency = '{ID_COMPETENCY}'";

        modify_query = modify_query.replace("{NAME_COMPETENCY}", Util.utf8Encode(nameCompetency));
        modify_query = modify_query.replace("{ID_COMPETENCY}", Util.utf8Encode(String.valueOf(idCompetency)));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(modify_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Competency modified correctly!");
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
       Given the id of a specific competency, the method delete it from the database and return a message in a
       JSON Format that reports the operation result.
     */

    public String deleteCompetency( int idCompetency){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String delete_query = "delete from competencies where id_competency = '{ID_COMPETENCY}'";
        delete_query = delete_query.replace("{ID_COMPETENCY}", Util.utf8Encode(String.valueOf(idCompetency)));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(delete_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Competency deleted correctly!");
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
        Return a String in a JSON Format with the attributes of the users whose role is maintainer.
     */

    public String visualizeMaintainers(){
        String usersJSONFormat = "{\"id_user\":\"{ID_USER}\",\"first_name\":\"{FIRST_NAME}\",\"surname\":\"{SURNAME}\",\"username\":\"{USERNAME}\",\"cell_num\":\"{CELL_NUMBER}\",\"email\":\"{EMAIL}\"}";
        String usersJSONResult = "";
        String JSONRow = "";

        this.con = db.connect();

        String visualize_query = "select * from users where role = 'maintainer'";


        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery(visualize_query);

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = usersJSONFormat.replace("{ID_USER}", Util.utf8Encode(rs.getString(1)));
                JSONRow = JSONRow.replace("{FIRST_NAME}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{SURNAME}", Util.utf8Encode(rs.getString(3)));
                JSONRow = JSONRow.replace("{CELL_NUMBER}", Util.utf8Encode(rs.getString(6)));
                JSONRow = JSONRow.replace("{EMAIL}", Util.utf8Encode(rs.getString(7)));
                JSONRow = JSONRow.replace("{USERNAME}", Util.utf8Encode(rs.getString(9)));
                usersJSONResult = usersJSONResult + JSONRow + ",";
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
        return "[" + Util.removeLastChar(usersJSONResult) + "]";
    }

    /*
        Given the id of a specific user whose role is maintainer, return a String in a JSON Format
        with the attributes of compentencies associated to maintainer.
     */

    public String visualizeMaintainerCompetencies(int id_user) {
        String sitesJSONFormat = "{\"id_competency\":\"{ID_COMPETENCY}\",\"name_competency\":\"{NAME_COMPETENCY}\",\"assigned\":\"{ASSIGNED}\"}";
        String sitesJSONResult = "";
        String JSONRow = "";
        this.con = db.connect();
        HashMap<Integer, Integer> assigned_map = new HashMap<Integer, Integer>();
        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select id_competency from maintainer_skills where id_user = " + id_user + " order by id_competency");
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
       Given the attributes for assign a competence to a maintainer, the method insert it in the database
       and return a message in a JSON Format that reports the operation result.
     */

    public String assignMaintainerCompetences(int id_user, int num_competencies,
                                             String id_competencies, String checks) {

        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String[] checks_array = checks.split("\\,", -1);
        String[] id_competencies_array = id_competencies.split("\\,", -1);
        this.con = db.connect();
        String query = "";
        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int checkDelete = stmt.executeUpdate("delete from maintainer_skills where id_user ='" + id_user + "'");
            for (int i = 0; i < num_competencies; i++) {
                if (checks_array[i].compareTo("1") == 0) {

                    query = "insert into maintainer_skills values ({ID_USER},{ID_COMPETENCY})";
                    query = query.replace("{ID_USER}", String.valueOf(id_user));
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
        Return a String in a JSON Format with the attributes of the notes.
     */

    public String visualizeNotes(){
        String notesJSONFormat = "{\"id_note\":\"{ID_NOTE}\",\"description\":\"{DESCRIPTION}\",\"id_activity\":\"{ID_ACTIVITY}\",\"id_site\":\"{ID_SITE}\"}";
        String notesJSONResult = "";
        String JSONRow = "";

        this.con = db.connect();

        String visualize_query = "select * from workspace_notes";


        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery(visualize_query);

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = notesJSONFormat.replace("{ID_NOTE}", String.valueOf(rs.getString(1)));
                JSONRow = JSONRow.replace("{DESCRIPTION}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{ID_ACTIVITY}", String.valueOf(rs.getString(3)));
                JSONRow = JSONRow.replace("{ID_SITE}", String.valueOf(rs.getString(4)));
                notesJSONResult = notesJSONResult + JSONRow + ",";
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
        return "[" + Util.removeLastChar(notesJSONResult) + "]";
    }

     /*
        Given the id of a specific note, return a String in a JSON Format with the attributes of
        that specific note.
     */

    public String visualizeNote( int id_note){
        String notesJSONFormat = "{\"id_note\":\"{ID_NOTE}\",\"description\":\"{DESCRIPTION}\"}";
        String notesJSONResult = "";
        String JSONRow = "";

        this.con = db.connect();

        String visualize_query = "select * from workspace_notes where id_note =" +id_note;


        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery(visualize_query);

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = notesJSONFormat.replace("{ID_NOTE}", String.valueOf(rs.getString(1)));
                JSONRow = JSONRow.replace("{DESCRIPTION}", Util.utf8Encode(rs.getString(2)));
                notesJSONResult = notesJSONResult + JSONRow + ",";
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
        return "[" + Util.removeLastChar(notesJSONResult) + "]";
    }

    /*
       Given the attributes of a new note of activity, the method insert it in the database and
       return a message in a JSON Format that reports the operation result.
     */

    public String createNoteActivity( String description, int id_activity){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String insert_query = "insert into workspace_notes (description, id_activity) values ('{DESCRIPTION}','{ID_ACTIVITY}')";
        insert_query = insert_query.replace("{DESCRIPTION}", Util.utf8Encode(description));
        insert_query = insert_query.replace("{ID_ACTIVITY}", String.valueOf(id_activity));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(insert_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Note created correctly!");
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
       Given the attributes of a new note of site, the method insert it in the database and
       return a message in a JSON Format that reports the operation result.
     */

    public String createNoteSite( String description, int id_site){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String insert_query = "insert into workspace_notes (description, id_site) values ('{DESCRIPTION}','{ID_SITE}')";
        insert_query = insert_query.replace("{DESCRIPTION}", Util.utf8Encode(description));
        insert_query = insert_query.replace("{ID_SITE}", String.valueOf(id_site));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(insert_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Note created correctly!");
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
       Given the attributes of a specific note, the method modify its attributes in the database and
       return a message in a JSON Format that reports the operation result.
     */


    public String modifyNote( int idNote, String description){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String modify_query = "update workspace_notes set description = '{DESCRIPTION}' where id_note = '{ID_NOTE}'";

        modify_query = modify_query.replace("{DESCRIPTION}", Util.utf8Encode(description));
        modify_query = modify_query.replace("{ID_NOTE}", Util.utf8Encode(String.valueOf(idNote)));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(modify_query);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Note modified correctly!");
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
       Given the id of a specific note, the method delete it from the database and return a message in a
       JSON Format that reports the operation result.
     */

    public String deleteNote( int id_note){

        this.con = db.connect();
        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";

        String deleteQuery = "delete from workspace_notes where id_note = '{ID_NOTE}'";
        deleteQuery = deleteQuery.replace("{ID_NOTE}", Util.utf8Encode(String.valueOf(id_note)));

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            int res = stmt.executeUpdate(deleteQuery);
            responseJson=responseJson.replace("{ERR_CODE}", "0");
            responseJson=responseJson.replace("{MSG}", "Note deleted correctly!");

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
        Return a String in a JSON Format with the attributes of the sites.
     */

    public String visualizeSite(){

        String JSONFormat = "{\"id_site\":\"{ID_SITE}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select id_site from sites");

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_SITE}", String.valueOf(rs.getInt(1)));
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
        Return a String in a JSON Format with the attributes of the activity.
     */

    public String visualizeActivity(){

        String JSONFormat = "{\"id_activity\":\"{ID_ACTIVITY}\"}";
        String JSONResult = "";
        String JSONRow = "";
        this.con = db.connect();

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select id_activity from maintenance_activities\n"+
                                    "where id_activity not in (select interventions.id_activity from interventions)");

            if (!rs.next()) {
                return "";
            }
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = JSONFormat.replace("{ID_ACTIVITY}", String.valueOf(rs.getInt(1)));
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



}
