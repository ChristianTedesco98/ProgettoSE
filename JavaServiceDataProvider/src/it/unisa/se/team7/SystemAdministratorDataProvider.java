package it.unisa.se.team7;

import java.sql.*;

public class SystemAdministratorDataProvider {

    private Connection con;

    public String visualizeUsers(DbConnection db) {
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

    public String visualizeUser(DbConnection db, int idUser){
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

    public String createUser(DbConnection db, String firstName, String surname, String birth_date, String password,
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

    public String modifyUser(DbConnection db, int idUser, String firstName, String surname, String birth_date, String password,
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

    public String deleteUser(DbConnection db, int idUser){

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

    public String visualizeCompetencies(DbConnection db){
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

    public String visualizeCompetency(DbConnection db, int idCompetency){
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

    public String createCompetency(DbConnection db, String nameCompetency){

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

    public String modifyCompetency(DbConnection db, int idCompetency, String nameCompetency){

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

    public String deleteCompetency(DbConnection db, int idCompetency){

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

}
