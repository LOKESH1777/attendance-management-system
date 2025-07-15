import java.sql.Connection;
import java.sql.DriverManager;

public class GetConnection {
    private final static String url = "jdbc:mysql://localhost:3306/attendance_management";
    private final static String username = "root";
    private final static String password = "root";

    public static Connection getConnection(){
        Connection con =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            System.out.println("Class not Loaded");
        }

        return con;

    }
}
