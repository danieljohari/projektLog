package Java;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;


class DBDownload {
    private static Connection connection;
    private PreparedStatement prep;
    private static Connector Connector;



    public static void main(String[] args) {
        //connection = null;

      connection = Connector.getConnection();



            //getHomeData();
            findUser("sabrina@dtu.dk");

        }

        //db.getHomeData();


    public DBDownload(){



    }

    public static void insertIntoHome(){


    }

    public static ArrayList getHomeData() {
        ArrayList data = new ArrayList();
        try {
            String sql = "select * from PatientPortal.loginoplysninger;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
//rs.getDataType( tal) ; giver os en datatype paa den 1, 2 osv. plads -
// Hvad sker hvis vi forsoger at hente et Int fra String?

                System.out.println(rs.getInt(1) + rs.getString("mail") + rs.getString("pass"));
//hvad hvis vi henviser til det ud fra navne fremfor index?
            }
            connection.close();


        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    private static String[] findUser(String mail){
        String[] result = new String[2];
        try {
            String sql = "select * from PatientPortal.loginoplysninger where mail=" +"'" +mail+"'" +"limit 1;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
//rs.getDataType( tal) ; giver os en datatype paa den 1, 2 osv. plads -
// Hvad sker hvis vi forsoger at hente et Int fra String?

                System.out.println(
                        "row:" + rs.getInt(1) +"\n" +
                                "mail: "+rs.getString("mail")+"\n" +
                                "pass:"+rs.getString("kode")+"\n");
//hvad hvis vi henviser til det ud fra navne fremfor index?
            }
            connection.close();


        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return result;

    }

    public static void insertIntoRemote(){


    }
    public static ArrayList getRemoteData(){

        return null;
    }
}
