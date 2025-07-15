import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationDAO {
    public static int insertQuery(Connection con, RegistrationModel registrationModel){
        String query = "Insert into attendance_management.student_registration values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps= con.prepareStatement(query);
            ps.setInt(1, registrationModel.getSid());
            ps.setString(2,registrationModel.getName());
            ps.setString(3, registrationModel.getAddress());
            ps.setLong(4, registrationModel.getPhonenumber());
            ps.setString(5, registrationModel.getEmail());
            ps.setString(6,registrationModel.getCourse());
            ps.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
