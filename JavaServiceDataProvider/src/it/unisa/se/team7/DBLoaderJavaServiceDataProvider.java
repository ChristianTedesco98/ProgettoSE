package it.unisa.se.team7;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;


public class DBLoaderJavaServiceDataProvider {

    private Connection con;
    private DbConnection dbc = new DbConnection();

    /*
        Return a String in JSON Format with the attributes of the sites.
     */
    public String visualizeSites() {

        String sitesJSONFormat = "{\"id_site\":\"{ID_SITE}\",\"area\":\"{AREA}\",\"factory_site\":\"{FACTORY_SITE}\"}";
        String sitesJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select * from sites order by id_site");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = sitesJSONFormat.replace("{ID_SITE}", rs.getString(1));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(3)));

                sitesJSONResult = sitesJSONResult + JSONRow + ",";
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(sitesJSONResult) + "]";
    }

    /*
        Given the id of a specific site, return a String in JSON Format with the attributes of that particular site
     */
    public String visualizeSite( int id_site) {

        String sitesJSONFormat = "{\"id_site\":\"{ID_SITE}\",\"area\":\"{AREA}\",\"factory_site\":\"{FACTORY_SITE}\"}";
        String sitesJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select * from sites where id_site='" + id_site + "'");

            if (!rs.next()) {
                return "";
            } else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = sitesJSONFormat.replace("{ID_SITE}", rs.getString(1));
                JSONRow = JSONRow.replace("{AREA}", Util.utf8Encode(rs.getString(2)));
                JSONRow = JSONRow.replace("{FACTORY_SITE}", Util.utf8Encode(rs.getString(3)));
                sitesJSONResult = sitesJSONResult + JSONRow + ',';
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(sitesJSONResult) + "]";
    }


    /*
        Given the attributes of a new site, the method inserts it in the database and return a message in JSON Format
        that reports the operation result
     */
    public String createSite( String area, String factory_site) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkInsert = stmt.executeUpdate("insert into sites(area,factory_site) values ('" + area + "','" + factory_site + "')");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Site created correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;
    }


    /*
        Given the attributes of a specific site, the method modify its attributes in the database and return a
        message in JSON Format that reports the operation result
     */
    public String modifySite( int id_site, String area, String factory_site) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int checkModify = stmt.executeUpdate("update sites set area='" + area + "', factory_site='" + factory_site + "' where id_site='" + id_site + "'");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Site modified correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;
    }

    /*
        Given the id of a specific site, the method deletes it from the database and return a
        message in JSON Format that reports the operation result
     */
    public String deleteSite( int id_site) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkDelete = stmt.executeUpdate("delete from sites where id_site='" + id_site + "'");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Site deleted correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;
    }

    /*
        Return a String in JSON Format with the attributes of the typologies.
     */

    public String visualizeTypologies() {

        String typologiesJSONFormat = "{\"id_typology\":\"{ID_TYPOLOGY}\",\"name_typology\":\"{NAME_TYPOLOGY}\"}";
        String typologiesJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select * from typologies order by id_typology");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = typologiesJSONFormat.replace("{ID_TYPOLOGY}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME_TYPOLOGY}", Util.utf8Encode(rs.getString(2)));
                typologiesJSONResult = typologiesJSONResult + JSONRow + ",";
            }
            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(typologiesJSONResult) + "]";
    }


    /*
        Given the id of a specific typology, return a String in JSON Format with the attributes of that particular
        typology
     */
    public String visualizeTypology(int id_typology) {

        String typologyJSONFormat = "{\"id_typology\":\"{ID_TYPOLOGY}\",\"name_typology\":\"{NAME_TYPOLOGY}\"}";
        String typologyJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select * from typologies where id_typology='" + id_typology + "'");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = typologyJSONFormat.replace("{ID_TYPOLOGY}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME_TYPOLOGY}", Util.utf8Encode(rs.getString(2)));
                typologyJSONResult = typologyJSONResult + JSONRow + ',';
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(typologyJSONResult) + "]";
    }


    /*
        Given the attributes of a new typology, the method inserts it in the database and return a message in JSON
        Format that reports the operation result
     */
    public String createTypology(String name_typology) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkInsert = stmt.executeUpdate("insert into typologies(name_typology) values ('" + name_typology + "')");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Typology created correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;

    }

    /*
        Given the attributes of a specific typology, the method modify its attributes in the database and return a
        message in JSON Format that reports the operation result
     */
    public String modifyTypology( int id_typology, String name_tapology) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkModify = stmt.executeUpdate("update typologies set name_typology='" + name_tapology + "' where id_typology='" + id_typology + "'");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Typology modified correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;

    }

    /*
        Given the id of a specific typology, the method deletes it from the database and return a
        message in JSON Format that reports the operation result
     */
    public String deleteTypology( int id_typology) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int checkDelete = stmt.executeUpdate("delete from typologies where id_typology='" + id_typology + "'");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Typology deleted correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;

    }

    /*
        Return a String in JSON Format with the attributes of the procedures.
     */
    public String visualizeProcedures() {

        String proceduresJSONFormat = "{\"id_procedure\":\"{ID_PROCEDURE}\",\"name\":\"{NAME}\"}";
        String proceduresJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select id_procedure, name from maintenance_procedures order by id_procedure");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = proceduresJSONFormat.replace("{ID_PROCEDURE}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME}", Util.utf8Encode(rs.getString(2)));
                proceduresJSONResult = proceduresJSONResult + JSONRow + ",";
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(proceduresJSONResult) + "]";
    }

    /*
       Given the id of a specific site, return a byte array which represents the SMP of that specific procedure
     */
    public byte[] visualizeProcedureSMP(int id_procedure){

        byte[] smp = null;
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT smp FROM maintenance_procedures WHERE id_procedure = "+id_procedure);
            while (rs.next()) {
                smp = rs.getBytes(1);
            }
            rs.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return smp;
    }

    /*
        Given the id of a specific procedure, return a String in JSON Format with the attributes of that particular site
        (excluding the SMP)
     */
    public String visualizeProcedure(int id_procedure) {

        String procedureJSONFormat = "{\"id_procedure\":\"{ID_PROCEDURE}\",\"name\":\"{NAME}\"}";
        String procedureJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select * from maintenance_procedures where id_procedure = '" + id_procedure + "'");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = procedureJSONFormat.replace("{ID_PROCEDURE}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME}", Util.utf8Encode(rs.getString(2)));
                procedureJSONResult = procedureJSONResult + JSONRow + ',';
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(procedureJSONResult) + "]";
    }

    /*
        Given the attributes of a new procedure, the method inserts it in the database and return a message in JSON
        Format that reports the operation result
     */
    public String createProcedure( String name, byte[] pdf) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            PreparedStatement ps = null;
            ps = con.prepareStatement("INSERT INTO maintenance_procedures (name, smp) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setBytes(2, pdf);
            ps.executeUpdate();
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Procedure created correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;
    }

    /*
        Given the id and the new name of a specific procedure, the method modify its name in the database and return a
        message in JSON Format that reports the operation result
     */
    public String modifyProcedureName(int id_procedure, String name_procedure) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkModify = stmt.executeUpdate("update maintenance_procedures set name='" + name_procedure + "' where id_procedure='" + id_procedure + "'");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Procedure modified correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;
    }

    /*
        Given the id and the new SMP of a specific procedure, the method modify its SMP in the database and return a
        message in JSON Format that reports the operation result
     */
    public String modifyProcedureSMP(int id_procedure, String name_procedure, byte[] smp){

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            PreparedStatement ps = con.prepareStatement("update maintenance_procedures set name = ?, smp = ? where id_procedure = ?");
            ps.setString(1, name_procedure);
            ps.setBytes(2, smp);
            ps.setInt(3, id_procedure);
            ps.executeUpdate();
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Procedure modified correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;

    }

    /*
        Given the id of a specific procedure, the method deletes it from the database and return a
        message in JSON Format that reports the operation result
     */
    public String deleteProcedure(int id_procedure) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkDelete = stmt.executeUpdate("delete from maintenance_procedures where id_procedure='" + id_procedure + "'");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Procedure deleted correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;
    }

    /*
        Given the id of a specific procedure, the method returns a String in JSON Format with the competencies related
        to that procedure and if they're assigned to it or not
     */
    public String visualizeProcedureCompetencies( int id_procedure) {

        String sitesJSONFormat = "{\"id_competency\":\"{ID_COMPETENCY}\",\"name_competency\":\"{NAME_COMPETENCY}\",\"assigned\":\"{ASSIGNED}\"}";
        String sitesJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();
        HashMap<Integer, Integer> assigned_map = new HashMap<Integer, Integer>();
        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select id_competency from procedure_skills where id_procedure = " + id_procedure + " order by id_competency");
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
        Given the id of a specific procedure, the number of competencies that have to be assigned, a String which
        represents the id of the competencies and a String which represents if every competency has to be associate
        with that specific procedure; the method associate the competencies to the procedure and return the operation
        result in a String in JSON format.
     */
    public String assignProcedureCompetences( int id_procedure, int num_competencies,
                                             String id_competencies, String checks) {



        String responseJson = "[{\"err_code\":\"{ERR_CODE}\",\"msg\":\"{MSG}\"}]";
        String[] checks_array = checks.split("\\,", -1);
        String[] id_competencies_array = id_competencies.split("\\,", -1);
        this.con = dbc.connect();
        String query = "";
        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int checkDelete = stmt.executeUpdate("delete from procedure_skills where id_procedure='" + id_procedure + "'");
            for (int i = 0; i < num_competencies; i++) {
                if (checks_array[i].compareTo("1") == 0) {

                    query = "insert into procedure_skills values ({ID_PROCEDURE},{ID_COMPETENCY})";
                    query = query.replace("{ID_PROCEDURE}", String.valueOf(id_procedure));
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
        Return a String in JSON Format with the attributes of the materials
    */
    public String visualizeMaterials() {

        String materialsJSONFormat = "{\"id_material\":\"{ID_MATERIAL}\",\"name_material\":\"{NAME_MATERIAL}\"}";
        String materialsJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select * from materials order by id_material");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = materialsJSONFormat.replace("{ID_MATERIAL}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME_MATERIAL}", Util.utf8Encode(rs.getString(2)));
                materialsJSONResult = materialsJSONResult + JSONRow + ",";
            }
            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(materialsJSONResult) + "]";
    }


    /*
        Given the id of a specific material, return a String in JSON Format with the attributes of that particular
        material
     */
    public String visualizeMaterial( int id_material) {

        String materialsJSONFormat = "{\"id_material\":\"{ID_MATERIAL}\",\"name_material\":\"{NAME_MATERIAL}\"}";
        String materialsJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs;
            rs = stmt.executeQuery("select * from materials where id_material='" + id_material + "'");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = materialsJSONFormat.replace("{ID_MATERIAL}", rs.getString(1));
                JSONRow = JSONRow.replace("{NAME_MATERIAL}", Util.utf8Encode(rs.getString(2)));
                materialsJSONResult = materialsJSONResult + JSONRow + ',';
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(materialsJSONResult) + "]";
    }


    /*
        Given the attributes of a new material, the method inserts it in the database and return a message in JSON Format
        that reports the operation result
     */
    public String createMaterial(String name_material) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkInsert = stmt.executeUpdate("insert into materials(name_material) values ('" + name_material + "')");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Material created correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;

    }

    /*
        Given the id of a specific material, the method deletes it from the database and return a
        message in JSON Format that reports the operation result
     */
    public String deleteMaterial( int id_material) {

        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int checkDelete = stmt.executeUpdate("delete from materials where id_material='" + id_material + "'");
            responseJSON = responseJSON.replace("{ERR_CODE}", "0");
            responseJSON = responseJSON.replace("{MSG}", "Material deleted correctly!");
            this.con.close();

        } catch (SQLException throwables) {
            String msg = throwables.getMessage().replace('\"', '*');
            msg = msg.replace('\n', ' ');
            responseJSON = responseJSON.replace("{ERR_CODE}", "1");
            responseJSON = responseJSON.replace("{MSG}", msg);
        }

        return responseJSON;

    }
}
