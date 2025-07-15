import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AttendenceDAO {

    public static int selectQuery(Connection con, AttendenceModel attendenceModel) {
        String query = "SELECT COUNT(*) FROM teacher_login WHERE teachid = ? AND email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, attendenceModel.getTeachid());
            ps.setString(2, attendenceModel.getTechemail());

            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return 1; // Login success
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Login failed
    }
    public static int insertAttendance(Connection con, AttendenceModel attendModel){
        String query = "Insert into attendance_management.attendence values(?,?,?,?,?)";
        try {
            PreparedStatement ps= con.prepareStatement(query);
            ps.setInt(1,attendModel.getSubid());
            ps.setInt(2,attendModel.getStudid());
            ps.setString(3,attendModel.getSubject());
            ps.setString(4, attendModel.getPreorabs());
            ps.setInt(5,attendModel.getSubid());
            ps.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static Map<String, Integer> countOverallAttendance(Connection con, AttendenceModel attenModel){
        String query = "SELECT COUNT(*) AS total_present_days,COUNT(DISTINCT subject) AS subjects_attended FROM attendance_management.attendence WHERE studid = ? AND pres_abs = 'P'";
        Map<String, Integer> attendanceData = new HashMap<>();
        attendanceData.put("total_present_days", 0);
        attendanceData.put("subjects_attended", 0);


        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, attenModel.getStudid()); // Set the student ID in the query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) { // Check if result exists
                attendanceData.put("total_present_days", rs.getInt("total_present_days"));
                attendanceData.put("subjects_attended", rs.getInt("subjects_attended"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return attendanceData;
    }
    public static int countParticularAttendance(Connection con,AttendenceModel attenModel){
        String query = "SELECT COUNT(*) AS total_present_count FROM attendance_management.attendence WHERE studid = ? AND subid = ? AND pres_abs = 'P'";

        int count = 0; // Initialize count

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, attenModel.getStudid()); // Set student ID
            ps.setInt(2, attenModel.getSubid()); // Set subject ID
            ResultSet rs = ps.executeQuery();

            if (rs.next()) { // Check if result exists
                count = rs.getInt("total_present_count"); // Get count from result
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return count;
    }
}
