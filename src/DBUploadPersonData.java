import java.sql.Connection;
import java.sql.PreparedStatement;



public class DBUploadPersonData {
    private static UIPersonData ui;
    private static String CPR;
    private static String Fornavn;
    private static String Efternavn;
    private static String Kon;
    private static Connection connection;

    public static void main(String[] args) {
        connection = Connector.getConnection();

       // System.out.println("Im Gone");

        //getHomeData();

        {

            try {

                ui = new UIPersonData();
                CPR = ui.askForCPR();
                System.out.println("Brugeren tastede: " + CPR);

                Fornavn = ui.askForFornavn();
                System.out.println("Brugeren indtastede: " + Fornavn);

                Efternavn = ui.askForEfternavn();
                System.out.println("Brugeren indtastede: " + Efternavn);

                Kon = ui.askForKon();
                System.out.println("Brugeren indtastede: " + Kon);

                // Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=Europe/Amsterdam&amp", "root", "Johari");


                String query = " insert into PatientPortal.PersonData (CPR, Fornavn, Efternavn, Køn)"
                        + " values (?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, CPR);
                preparedStmt.setString(2, Fornavn);
                preparedStmt.setString(3, Efternavn);
                preparedStmt.setString(4, Kon);


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




