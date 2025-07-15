import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherDAO {
    public static int registerTeacher(Connection con,TeacherModel teacher){
        String query = "INSERT INTO teacher_login(teachid, name, email,course, role) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,teacher.getTeachId());
            ps.setString(2,teacher.getTeachName());
            ps.setString(3, teacher.getTeachEmail());
            ps.setString(4,teacher.getTeachCourse());
            ps.setString(5, teacher.getTeachRole());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
