package murach.data;

import java.sql.*;
import java.util.ArrayList;
import murach.business.User;

public class UserDB {

    // Thêm user mới
    public static int insert(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO Users (Email, FirstName, LastName) VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert error: " + e.getMessage());
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // Cập nhật thông tin user
    public static int update(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE Users SET FirstName = ?, LastName = ? WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update error: " + e.getMessage());
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // Xóa user
    public static int delete(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM Users WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Delete error: " + e.getMessage());
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // Kiểm tra email tồn tại
    public static boolean emailExists(String email) {
        return selectUser(email) != null;
    }

    // Lấy 1 user theo email
    public static User selectUser(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Users WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
            }
            return user;
        } catch (SQLException e) {
            System.err.println("Select user error: " + e.getMessage());
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // Lấy tất cả user (nếu cần)
    public static ArrayList<User> selectUsers() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Users ORDER BY LastName, FirstName";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.err.println("Select users error: " + e.getMessage());
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
