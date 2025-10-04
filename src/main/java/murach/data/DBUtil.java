package murach.data;

import java.sql.*;

public class DBUtil {

    // Đóng Statement hoặc PreparedStatement
    public static void closeStatement(Statement s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing Statement: " + e.getMessage());
        }
    }

    // Đóng PreparedStatement (alias để code cũ vẫn dùng được)
    public static void closePreparedStatement(PreparedStatement ps) {
        closeStatement(ps);
    }

    // Đóng ResultSet
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }
    }
}
