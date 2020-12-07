package it.unisa.se.team7;

public class test {

    public static void main(String[] args){

        DbConnection db = new DbConnection();
        SystemAdministratorDataProvider sa_service = new SystemAdministratorDataProvider();
        int id_competency = 17;
        String name_competency = "test123";
        sa_service.modifyCompetency(db, id_competency, name_competency);
    }

}
