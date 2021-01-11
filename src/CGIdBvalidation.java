

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CGIdBvalidation {
   public CGIdBvalidation() {

   }

    //public static Connection connection;
    //static Connector connector = new Connector();

    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static final String dbUrl = "jdbc:mariadb://su2.eduhost.dk:3306/";
    private static final String dbName = "PatientPortal?";
    private static final String dbUsername = "daniel";
    private static final String dbPassword = "Johari";
    private static Connection connection = null;


    static String inputFraCgi = null;
    static String[] data;
    static String[] clientResponse;
    static String cprTilDb;
    static String paswdTilDb;
    static String cprsql = null;
    static String paswdSql = null;
    static Time timeSql = null;
    static Date dateSql = null;
    static String meddelelserSql = null;
    static int idBestilling = 0;
    static String hospitalSql = null;
    static String departmentsSql = null;
    static String departmentsSql1 = null;
    static String departmentsSql2 = null;

    public static void main(String[] args) {
        showHead();
       //connection= connector.getConnection();
        getConnection();


        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            data = new String[]{in.readLine()};
            inputFraCgi = data[0];
            clientResponse = inputFraCgi.split("&");
            String[] cpr;
            cpr = clientResponse[0].split("=");
            cprTilDb = cpr[1];
            String[] paswd;
            paswd = clientResponse[1].split("=");
            paswdTilDb = paswd[1];
            if (findUser(cprTilDb, paswdTilDb)) {
                getAppointment();
            }
        } catch (IOException ioe) {


            System.out.println("<P>IOException reading POST data: " + ioe + "</P>");
        }
        showTail();

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




    private static void getAppointment() {
        try{
            String sql1 = "SELECT Tid,Dato,Hospital,Afdeling, idBestilling FROM PatientPortal.Bestilling WHERE CPR= '" +
                    cprsql + "'";
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

    private static void showBody() {
        System.out.println("    <tr>\n" +
                "        <td>" + hospitalSql + "</td>\n" +
                "        <td>" + departmentsSql2.replaceAll("%C3%B8", "ø") + "</td>\n" +
                "        <td>" + dateSql + "</td>\n" +
                "        <td>" + timeSql + "</td>\n" +
                "<td>\n" +
                "                <form action=\"/cgi-bin/CGIDelete\"><input type=\"submit\" value=\"delete\"><input type=\"hidden\" value=\""+idBestilling+"\"> </form>\n" +
                "            </td>"+
                "    </tr>\n");
    }

    private static void showTail() {
        System.out.println("</table>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>");

    }


    private static boolean findUser(String cprTilDb, String paswdTilDb) {

            try {
                String sql = "select CPR,Kode from PatientPortal.loginoplysninger where CPR= " + "'" + cprTilDb + "'" + "and Kode ="
                        + "'" + paswdTilDb + "'";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                rs.next();
                cprsql = rs.getString("CPR");
                paswdSql = rs.getString("Kode");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cprTilDb.equals(cprsql) && paswdTilDb.equals(paswdSql);
        }


    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>The CGIpost application</TITLE>");
        System.out.println("    <link rel=\"stylesheet\" type=\"text/css\" href=\"../CSS/Forside.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\">\n");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
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
                "\n" +
                "            <li><a href=\"#\"><b>Service</b></a>\n" +
                "                <ul>\n" +
                "                    <li><a href=\"/Tid.html\">Tider og Bestilling</a></li>\n" +
                "                    <br><br>\n" +
                "                    <li><a href=\"/Indkaldelse.html\">Se Indkaldelser</a></li>\n" +
                "                </ul>\n" +
                "            </li>\n" +
                "\n" +
                "            <li><a href=\"#\"><b>Journal</b></a>\n" +
                "                <ul>\n" +
                "                    <li><a href=\"/Journal.html\">Se Journal</a></li>\n" +
                "                    <br><br>\n" +
                "                </ul>\n" +
                "            </li>\n" +
                "\n" +
                "            <li><a href=\"/Kontakt.html\"><b>Kontakt</b></a></li>\n" +
                "            <li><a href=\"/index.html\"><b>Log Ud</b></a></li>\n" +
                "\n" +
                "\n" +
                "        </ul>\n" +
                "</div>\n" +
                "</header>\n" +
                "<table>\n" +
                "    <thead>\n" +
                "    <tr>\n" +
                "        <th id=\"hospital\">Hospital:</th>\n" +
                "        <th id=\"afdeling\">Afdeling:</th>\n" +
                "        <th id=\"dato\">Dato:</th>\n" +
                "        <th id=\"tid\">Tid:</th>\n" +
                "        <th id=\"delete\">Fjern:</th>\n" +
                "    </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n"
                );

    }


}
