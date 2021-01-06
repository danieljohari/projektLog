import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class CGIAppointment {
    public CGIAppointment(){

    }

    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static final String dbUrl = "jdbc:mariadb://su2.eduhost.dk:3306/";
    private static final String dbName = "PatientPortal?";
    private static final String dbUsername = "daniel";
    private static final String dbPassword = "Johari";
    private static Connection connection = null;
    static String inputCGI = null;
    static String[] data;
    static String[] appointment;
    static String cprsql = null;
    static String timeString = null;
    static Time timeSql = null;
    static Date dateSql = null;
    static String hospitalSql = null;
    static String departmentsSql = null;
    static String departmentsSql1 = null;
    static String departmentsSql2 = null;


    public static void main(String[] args) {
        getConnection();
        try{
            String[] cpr;
            String[] hospital;
            String[] departments;
            String[] date;
            String[] time;
            BufferedReader in =  new BufferedReader(new InputStreamReader(System.in));
            data = new String[]{in.readLine()};
            inputCGI = data[0];
            appointment = inputCGI.split("&");
            hospital = appointment[0].split("=");
            hospitalSql = hospital[1].replaceAll("\\+", " ");
            departments = appointment[1].split("=");
            departmentsSql = departments[1].replaceAll("\\+", " ");
            departmentsSql1 = departmentsSql.replaceAll("%2C", ",");
            departmentsSql2 = departmentsSql1.replaceAll("%C3%B8", "oe");
            date = appointment[2].split("=");
            dateSql = Date.valueOf(date[1]);
            time = appointment[3].split("=");
            timeString = time[1].replaceAll("%3A", ":");
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            java.sql.Time sqlTime = new java.sql.Time(format.parse(timeString).getTime());
            timeSql = sqlTime;
            cpr = appointment[4].split("=");
            cprsql = cpr[1];
            setAppointment(cprsql,timeSql,dateSql,hospitalSql,departmentsSql2);
            showHead();
            getAppointment();
            showTail();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showTail() {
        System.out.println("</table>\n" + "</body>\n" + "</HTML>");
    }

    private static void getAppointment() {
        try{
            String sql1 = "SELECT Tid,Dato,Hospital,Afdeling FROM PatientPortal.Bestilling WHERE CPR= '" +
                    cprsql + "'";
            Statement statement = connection.createStatement();
            ResultSet rs1 = statement.executeQuery(sql1);
            while (rs1.next()) {
                timeSql = rs1.getTime("Tid");
                dateSql = rs1.getDate("Dato");
                hospitalSql = rs1.getString("Hospital");
                departmentsSql2 = rs1.getString("Afdeling");
                showBody();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showBody() {
        System.out.println("    <tr>\n" +
                "        <td>" + hospitalSql + "</td>\n" +
                "        <td>" + departmentsSql + "</td>\n" +
                "        <td>" + timeSql + "</td>\n" +
                "        <td>" + dateSql + "</td>\n" +
                "    </tr>\n");
    }

    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>The CGIpost application</TITLE>");
        System.out.println("    <link rel=\"stylesheet\" type=\"text/css\" href=\"../CSS/tid.css\">\n" +
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
                "            <li class=\"active\"><a href=\"Forside.html\"><b>Hjem</b></a> </li>\n" +
                "            <li><a href=\"#\"><b>Service</b></a>\n" +
                "                <ul>\n" +
                "                    <li><a href=\"/cgi-bin/CGIAppointment\">Tider og Bestilling</a></li>\n" +
                "                    <br><br>\n" +
                "                    <li><a href=\"Indkaldelse.html\">Se Indkaldelser</a></li>\n" +
                "                </ul>\n" +
                "            </li>\n" +
                "            <li><a href=\"#\"><b>Journal</b></a>\n" +
                "                <ul>\n" +
                "                    <li><a href=\"Journal.html\">Se Journal</a></li>\n" +
                "                    <br><br>\n" +
                "\n" +
                "                </ul>\n" +
                "            </li>\n" +
                "\n" +
                "            <li><a href=\"Kontakt.html\"><b>Kontakt</b></a>\n" +
                "\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "\n" +
                "    </div>\n" +
                "</header>\n" +
                "\n" +
                "\n" +
                "        <div class=\"container-form\" >\n" +
                "            <form action=\"#\">\n" +
                "                <h2 class=\"heading-head\">Bestil Her - Vælg:</h2>\n" +
                "\n" +
                "                <div class=\"form-field\">\n" +
                "                    <p>CPR:</p>\n" +
                "                <input type=\"username\" placeholder=\"Indtast CPR\">\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"form-field\">\n" +
                "                    <p>Vælg Hospital:</p>\n" +
                "                    <select name=\"select\" id=\"#\">\n" +
                "                        <option value=\"1\"></option>\n" +
                "                        <option value=\"2\">Rigs Hospital</option>\n" +
                "                        <option value=\"3\">Glostrup Hospital</option>\n" +
                "                        <option value=\"4\">Bispebjerg Hospital</option>\n" +
                "                    </select>\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"form-field\">\n" +
                "                    <p>Vælg Afdeling:</p>\n" +
                "                    <select name=\"select\" id=\"#\">\n" +
                "                        <option value=\"1\"></option>\n" +
                "                        <option value=\"2\">Røntgen afdeling</option>\n" +
                "                        <option value=\"3\">Kardiologisk afdeling</option>\n" +
                "                        <option value=\"4\">Reumatologisk afdeling</option>\n" +
                "                    </select>\n" +
                "\n" +
                "                </div>\n" +
                "\n" +
                "\n" +
                "                <div class=\"form-field\">\n" +
                "                    <p>Dato:</p>\n" +
                "                    <input type=\"date\">\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"form-field\">\n" +
                "                    <p>Tid:</p>\n" +
                "                    <input type=\"time\">\n" +
                "                </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "                <div class=\"text-field\">\n" +
                "                        <p>Angående:</p>\n" +
                "                   <textarea></textarea>\n" +
                "                </div>\n" +
                "\n" +
                "                    <button class=\"btn\">Bestil</button>\n" +
                "\n" +
                "\n" +
                "\n" +
                "            </form>\n" +
                "\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "\n");
    }

    private static void setAppointment(String cprsql, Time timeSql, Date dateSql, String hospitalSql, String departmentsSql2) {
        try{
            String sql = "INSERT INTO PatientPortal.Bestilling (CPR, Hospital,Afdeling, Dato, Tid) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1,cprsql);
            preparedStatement.setString(2,hospitalSql);
            preparedStatement.setString(3, departmentsSql2);
            preparedStatement.setTime(4, timeSql);
            preparedStatement.setDate(5, dateSql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
