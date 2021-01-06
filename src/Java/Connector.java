package Java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    //private static Connection connection;


    public Connector() {
        System.out.println("du er nu bla bla");
    }


    public static Connection getConnection() {
        Connection connection = null;
        try {
            try {
                if (connection == null || connection.isClosed()) {
                    try {
                       // Class.forName("com.mysql.cj.jdbc.Driver");
                       // Class.forName("org.mariadb.jdbc.Driver");

                        //mysql skal andres senere til MariaDB, localhost til en IPaddresse -

                        String user, pass, url;
                        user = "daniel";
                        pass = "Johari";
                        //url = "jdbc:mariadb://192.168.239.22:3306/PatientPortal?serverTimezone=Europe/Amsterdam&amp";
                       // url = "jdbc:mariadb://192.168.239.22:3306/PatientPortal?serverTimezone=Europe/Amsterdam&amp";
                        //url = "jdbc:mariadb://130.226.195.37:39022/PatientPortal?";
                        url = "jdbc:mariadb://su2.eduhost.dk:3306/PatientPortal?";




                        // Skal man fx. bruge 127.0.0.1 til en remote maskine?
//Connection connection =
 //DriverManager.getConnection("jdbc:mariadb://localhost:39022/PatientPortal?user=daniel&password=Johari");
                        //tank jer om - kan man opnaa mariadb forbindelse til en anden maskine uden at andre denne her?
                       // System.out.println("din far");
                       connection = DriverManager.getConnection(url, user, pass);

                        System.out.println("Im in");

                        //getHomeData();

                    } catch (
                            SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        getConnection();
        System.out.println("hala bel hala");
    }
}



