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
            cpr = appointment[0].split("=");
            cprsql = cpr[1].replaceAll("\\+", " ");
            hospital = appointment[1].split("=");
            hospitalSql = hospital[1].replaceAll("\\+", " ");
            departments = appointment[2].split("=");
            departmentsSql = departments[1].replaceAll("\\+", " ");
            departmentsSql1 = departmentsSql.replaceAll("%2C", ",");
            departmentsSql2 = departmentsSql1.replaceAll("%C3%B8", "oe");
            date = appointment[3].split("=");
            dateSql = Date.valueOf(date[1]);
            time = appointment[4].split("=");
            timeString = time[1].replaceAll("%3A", ":");
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            java.sql.Time sqlTime = new java.sql.Time(format.parse(timeString).getTime());
            timeSql = sqlTime;
            setAppointment(cprsql, hospitalSql, departmentsSql2, dateSql, timeSql);
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
        System.out.println(" </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
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
                "        <td>" + departmentsSql.replaceAll("%C3%B8", "ø") + "</td>\n" +
                "        <td>" + dateSql + "</td>\n" +
                "        <td>" + timeSql + "</td>\n" +
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
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset= UTF-8\">");
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
                "            <li><a href=\"#\"><b>Service</b></a>\n" +
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
                "    <div class=\"title\">\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <th>Hospital:</th>\n" +
                "                <th>Afdeling:</th>\n" +
                "                <th>Dato:</th>\n" +
                "                <th>Tid:</th>\n" +
                "            </tr>"
        );
    }

    private static void setAppointment(String cprsql, String hospitalSql, String departmentsSql2, Date dateSql, Time timeSql) {
        try{
            String sql = "INSERT INTO PatientPortal.Bestilling (CPR, Hospital,Afdeling, Dato, Tid) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1,cprsql);
            preparedStatement.setString(2,hospitalSql);
            preparedStatement.setString(3, departmentsSql2);
            preparedStatement.setDate(4, dateSql);
            preparedStatement.setTime(5, timeSql);
            preparedStatement.execute();

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
