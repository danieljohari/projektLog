package Java;

import Java.Connector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

public class DBUploadLog {
    private static UIlogin ui;
    private static String CPR;
    private static String Mail;
    private static String Password;
    private static Connection connection;



    public static void main(String[] args) {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        //mysql skal ændres senere til MariaDB, localhost til en IPaddresse -
        connection = Connector.getConnection();
        //System.out.println("Im in");

        //getHomeData();

        {

            try {

                ui = new UIlogin();
                CPR = ui.askForCPR();
                System.out.println("Brugeren tastede: " + CPR);

                Mail = ui.askForMail();
                System.out.println("Brugeren indtastede: " + Mail);

                Password = ui.askForPassword();
                System.out.println("Brugeren indtastede: " + Password);

                // Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=Europe/Amsterdam&amp", "root", "Johari");


                String query = " insert into PatientPortal.loginoplysninger (CPR, Mail,Kode)"
                        + " values (?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, CPR);
                preparedStmt.setString(2, Mail);
                preparedStmt.setString(3, Password);


                // execute the preparedstatement
                preparedStmt.execute();

                connection.close();
            } catch (Exception e) {
                System.err.println("Got an exception!" + e);
                // System.err.println(e.getMessage());
            }
        }
    }
}




