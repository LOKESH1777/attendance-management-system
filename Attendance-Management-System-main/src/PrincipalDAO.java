import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrincipalDAO {
    public static int principalLogin(Connection con,PrincipalModel principal){
        String query = "SELECT COUNT(*) FROM principal_login WHERE principal_id = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,principal.getPrincipalId());
            ps.setString(2,principal.getPrincipalPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next() && rs.getInt(1) > 0){
                return 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return 0;
    }
}
