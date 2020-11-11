

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    //private static Connection connection;


    public Connector() {

    }


    public static Connection getConnection() {
        Connection connection = null;
        try {
            try {
                if (connection == null || connection.isClosed()) {
                    try {
                        // Class.forName("com.mysql.cj.jdbc.Driver");
                        //mysql skal andres senere til MariaDB, localhost til en IPaddresse -

                        String user, pass, url;
                        user = "daniel";
                        pass = "Johari";
                        url = "jdbc:mysql://192.168.239.22:3306/PatientPortal?serverTimezone=Europe/Amsterdam&amp";



                        // Skal man fx. bruge 127.0.0.1 til en remote maskine?
//Connection connection =
// DriverManager.getConnection("jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword");
                        //tank jer om - kan man opnaa mariadb forbindelse til en anden maskine uden at andre denne her?

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
}



