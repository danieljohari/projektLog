import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;

public class CGIDelete {
    public CGIDelete(){

    }

    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static final String dbUrl = "jdbc:mariadb://su2.eduhost.dk:3306/";
    private static final String dbName = "PatientPortal?";
    private static final String dbUsername = "daniel";
    private static final String dbPassword = "Johari";
    private static Connection connection = null;
    static String cprsql = null;
    static Time timeSql = null;
    static Date dateSql = null;
    static int idBestilling;
    static String hospitalSql = null;
    static String departmentsSql2 = null;
   static   CGIAppointment cgiAppointment;


    public static void main(String[] args) throws IOException {
                getConnection();
        BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
        String[] data = new String[]{in.readLine()};
        getID();
        getCPR();

        showHead();
        deleteAppointment();
        getAppointment();
        showTail();


    }

    private static void getID() {
        try {
            String sql3 = "SELECT * FROM PatientPortal.Bestilling;";
            Statement statement1 = connection.createStatement();
            ResultSet set = statement1.executeQuery(sql3);
            while (set.next()) {
                idBestilling = set.getInt("idBestilling");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAppointment() {
        try {
            String sql2 = "DELETE FROM PatientPortal.Bestilling WHERE idBestilling = "+ idBestilling+" ";
            PreparedStatement pstatement = connection.prepareStatement(sql2);
            pstatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getAppointment() {
        try{
            String sql1 = "SELECT Tid,Dato,Hospital,Afdeling, idBestilling FROM PatientPortal.Bestilling WHERE CPR= '" + cprsql + "'";
            Statement statement = connection.createStatement();
            ResultSet rs1 = statement.executeQuery(sql1);
            while (rs1.next()) {
                timeSql = rs1.getTime("Tid");
                dateSql = rs1.getDate("Dato");
                hospitalSql = rs1.getString("Hospital");
                departmentsSql2 = rs1.getString("Afdeling");
                idBestilling = rs1.getInt("idBestilling");
                showBody();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getCPR() {
        try {
            String sql3 = "SELECT CPR FROM PatientPortal.Bestilling WHERE idBestilling =' "+idBestilling + "'";
            Statement statement1 = connection.createStatement();
            ResultSet set = statement1.executeQuery(sql3);
            if (set.next()) {
                cprsql = set.getString("CPR");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showTail() {
        System.out.println(" </tbody>\n" +
                "        </table>\n" +
                "</body>\n" +
                "</html>");
    }

    private static void showBody() {
        System.out.println("    <tr>\n" +
                "        <td>" + hospitalSql + "</td>\n" +
                "        <td>" + departmentsSql2.replaceAll("%C3%B8", "ø") + "</td>\n" +
                "        <td>" + dateSql + "</td>\n" +
                "        <td>" + timeSql + "</td>\n" +
                "<td>\n" +
                "                <form action=\"/cgi-bin/CGIDelete\" method=\"post\"><input type=\"submit\" value=\"delete\"><input type=\"hidden\" value=\""+idBestilling+"\"> </form>\n" +
                "            </td>"+
                "    </tr>\n");
    }

    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>The CGIpost application</TITLE>");
        System.out.println("    <link rel=\"stylesheet\" type=\"text/css\" href=\"../CSS/Indkaldelser.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\">\n");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html;\" charset=\" UTF-8\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        System.out.println("</HEAD>");
        System.out.println("<body>\n" +
                "<header>\n" +
                "    <div class=\"main\">\n" +
                "        <div class=\"logo\">\n" +
                "            <img src=\"../IMG/Logo.png\">\n" +
                "        </div>\n" +
                "        <ul>\n" +
                "            <li class=\"active\"><a href=\"/Forside.html\"><b>Hjem</b></a> </li>\n" +
                "            <li><a href=\"#\"><b>Aftaler</b></a>\n" +
                "                <ul>\n" +
                "                    <li><a href=\"/Tid.html\">Tider og Bestilling</a></li>\n" +
                "                    <br><br>\n" +
                "                    <li><a href=\"/Indkaldelse.html\">Se Indkaldelser</a></li>\n" +
                "                </ul>\n" +
                "            </li>\n" +
                "            <li><a href=\"#\"><b>Journal</b></a>\n" +
                "                <ul>\n" +
                "                    <li><a href=\"/Journal.html\">Se Journal</a></li>\n" +
                "                    <br><br>\n" +
                "\n" +
                "\n" +
                "                </ul>\n" +
                "            </li>\n" +
                "            <li><a href=\"/Kontakt.html\"><b>Kontakt</b></a>\n" +
                "\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "    </div>\n" +
                "    <div class = indkald>\n" +
                "        <h1>Indkaldelser</h1>\n" +
                "    </div>\n" +
                "</header>\n" +
                "    \n" +
                "        <table>\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th id=\"hospital\">Hospital:</th>\n" +
                "                <th id=\"afdeling\">Afdeling:</th>\n" +
                "                <th id=\"dato\">Dato:</th>\n" +
                "                <th id=\"tid\">Tid:</th>\n" +
                "                <th id=\"Fjern\">Fjern:</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>");
    }

    private static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(jdbcDriver);
                connection = DriverManager.getConnection(dbUrl + dbName, dbUsername, dbPassword);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
