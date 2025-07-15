import java.sql.*;
import java.sql.Date;

public class TeacherAttendanceDAO {
    public static int markAttendance(Connection con,TeacherAttendanceModel attendance){
        String query = "INSERT INTO teacher_attendance (teachid, attendance_date, present_absent) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,attendance.getTeachId());
            ps.setDate(2,attendance.getAttendanceDate());
            ps.setString(3,attendance.getPresentAbsent());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static ResultSet getTeacherAttendance(Connection con,int teachId){
        String query = "SELECT * FROM teacher_attendance WHERE teachid = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,teachId);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isAttendanceMarked(Connection con, int teachId, Date attendanceDate){
        String query = "SELECT 1 FROM teacher_attendance WHERE teachid=? AND attendance_date = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,teachId);
            ps.setDate(2,attendanceDate);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static int markAbsence(Connection con,int teachId, Date dateAbsence, String reason){
        String query = "INSERT INTO teacher_attendance (teachid, attendance_date, present_absent, reason) VALUES (?,?,'A',?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,teachId);
            ps.setDate(2,dateAbsence);
            ps.setString(3,reason);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isPresent(Connection con,int teachId, Date attendanceDate){
        String query = "SELECT 1 FROM teacher_attendance WHERE teachid=? AND attendance_date =? AND present_absent = 'P'";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,teachId);
            ps.setDate(2,attendanceDate);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
