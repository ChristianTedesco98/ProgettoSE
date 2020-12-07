package it.unisa.se.team7;
import java.sql.*;
import java.util.HashMap;


public class DBLoaderJavaServiceDataProvider {

    private Connection con;

    public String visualizeSites(DbConnection dbc) {
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

    public String visualizeSite(DbConnection dbc, int id_site) {
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

    public String createSite(DbConnection dbc, String area, String factory_site) {
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

    public String modifySite(DbConnection dbc, int id_site, String area, String factory_site) {
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

    public String deleteSite(DbConnection dbc, int id_site) {
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

    public String visualizeTypologies(DbConnection dbc) {
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


    public String visualizeTypology(DbConnection dbc, int id_typology) {
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


    public String createTypology(DbConnection dbc, String name_typology) {
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

    public String modifyTypology(DbConnection dbc, int id_typology, String name_tapology) {
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

    public String deleteTypology(DbConnection dbc, int id_typology) {
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

    public String visualizeProcedures(DbConnection dbc) {
        String proceduresJSONFormat = "{\"id_procedure\":\"{ID_PROCEDURE}\",\"smp\":\"{SMP}\"}";
        String proceduresJSONResult = "";
        String JSONRow = "";
        this.con = dbc.connect();

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            rs = stmt.executeQuery("select * from maintenance_procedures order by id_procedure");

            if (!rs.next())
                return "";
            else
                rs.beforeFirst();

            while (rs.next()) {
                JSONRow = proceduresJSONFormat.replace("{ID_PROCEDURE}", rs.getString(1));
                JSONRow = JSONRow.replace("{SMP}", Util.utf8Encode(rs.getString(2)));
                proceduresJSONResult = proceduresJSONResult + JSONRow + ",";
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(proceduresJSONResult) + "]";
    }

    public String visualizeProcedure(DbConnection dbc, int id_procedure) {
        String procedureJSONFormat = "{\"id_procedure\":\"{ID_PROCEDURE}\",\"smp\":\"{SMP}\"}";
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
                JSONRow = JSONRow.replace("{SMP}", Util.utf8Encode(rs.getString(2)));
                procedureJSONResult = procedureJSONResult + JSONRow + ',';
            }

            this.con.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return "[" + Util.removeLastChar(procedureJSONResult) + "]";
    }

    public String createProcedure(DbConnection dbc, String smp) {
        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkInsert = stmt.executeUpdate("insert into maintenance_procedures(smp) values ('" + smp + "')");
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

    public String modifyProcedure(DbConnection dbc, int id_procedure, String smp) {
        this.con = dbc.connect();
        String responseJSON = "[{\"err_code\":\"{ERR_CODE}\", \"msg\":\"{MSG}\"}]";

        try {
            Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int checkModify = stmt.executeUpdate("update maintenance_procedures set smp='" + smp + "' where id_procedure='" + id_procedure + "'");
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

    public String deleteProcedure(DbConnection dbc, int id_procedure) {
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

    public String visualizeProcedureCompetencies(DbConnection dbc, int id_procedure) {
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

    public String assignProcedureCompetences(DbConnection dbc, int id_procedure, int num_competencies,
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
}
