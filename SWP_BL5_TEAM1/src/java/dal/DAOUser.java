package dal;

import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import util.MD5Util; // Import MD5Util

public class DAOUser extends DBContext {

    public DAOUser() {
        super(); // Gọi constructor của DBConnect để khởi tạo kết nối database
    }

    // Phương thức đăng ký người dùng mới (IsActive mặc định là 0)
    public int registerUser(User user) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "INSERT INTO Users (RoleID, Email, FullName, Phone, CreatedAt, IsActive, Dob, Address, Avatar, UserName, Password) "
                + "VALUES (?, ?, ?, ?, GETDATE(), 0, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, user.getRoleID());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getPhone());
            stmt.setDate(5, user.getDob());
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getAvatar());
            stmt.setString(8, user.getUserName());
            stmt.setString(9, MD5Util.getMD5Hash(user.getPassword())); // Mã hóa mật khẩu

            int rowsAffected = stmt.executeUpdate();
            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Rows affected: " + rowsAffected);

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Generated UserID: " + userId);
                    return userId;
                }
            }
            Logger.getLogger(DAOUser.class.getName()).log(Level.WARNING, "No generated keys returned");
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error registering user", ex);
            throw ex;
        }
    }

    // Phương thức lấy 5 người dùng mới nhất
    public List<User> getNewUsers() throws SQLException {
        List<User> newUsers = new ArrayList<>();
        String sql = "SELECT TOP 5 * FROM Users WHERE RoleID = 2 ORDER BY CreatedAt DESC";
        Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Executing query: {0}", sql);

        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            throw new SQLException("Database connection is not initialized.");
        }

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Query executed successfully");
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                User user = extractUserFromResultSet(rs);
                newUsers.add(user);
                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Added user: ID={0}, Name={1}, RoleID={2}",
                        new Object[]{user.getUserID(), user.getFullName(), user.getRoleID()});
            }
            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Total rows fetched: {0}, Users list size: {1}",
                    new Object[]{rowCount, newUsers.size()});
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching new users: {0}", e.getMessage());
            throw e;
        }
        return newUsers;
    }

// Phương thức đăng nhập (hỗ trợ cả email và username)
    public User Login(String loginInput, String password) {
        User user = null;
        String hashedPassword = MD5Util.getMD5Hash(password); // Mã hóa mật khẩu nhập vào
        String sql = "SELECT * FROM Users WHERE (UserName = ? OR Email = ?) AND Password = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, loginInput);
            pre.setString(2, loginInput);
            pre.setString(3, hashedPassword);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("UserID"),
                        rs.getInt("RoleID"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("Phone"),
                        rs.getDate("CreatedAt"),
                        rs.getInt("IsActive"),
                        rs.getDate("Dob"),
                        rs.getString("Address"),
                        rs.getString("Avatar"),
                        rs.getString("UserName"),
                        rs.getString("Password")
                );
                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Login successful for: " + loginInput);
            } else {
                Logger.getLogger(DAOUser.class.getName()).log(Level.WARNING, "No user found for: " + loginInput + ", hashedPassword: " + hashedPassword);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error logging in user", ex);
        }
        return user;
    }

    // Phương thức lấy thông tin người dùng bằng Email
    public User getUserByEmail(String email) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return null;
        }
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching user by email", e);
        }
        return null;
    }

    // Phương thức lấy thông tin người dùng bằng UserID
    public User getUserById(int userId) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return null;
        }
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching user by ID", e);
        }
        return null;
    }

    // Phương thức cập nhật mật khẩu
    public void updatePassword(String email, String password) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return;
        }
        String hashedPassword = MD5Util.getMD5Hash(password); // Mã hóa mật khẩu
        String sql = "UPDATE Users SET Password = ? WHERE Email = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, hashedPassword);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating password", e);
        }
    }

    // Phương thức trích xuất thông tin người dùng từ ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("UserID"),
                rs.getInt("RoleID"),
                rs.getString("Email"),
                rs.getString("FullName"),
                rs.getString("Phone"),
                rs.getDate("CreatedAt"),
                rs.getInt("IsActive"),
                rs.getDate("Dob"),
                rs.getString("Address"),
                rs.getString("Avatar"),
                rs.getString("UserName"),
                rs.getString("Password")
        );
    }

    // Phương thức cập nhật mật khẩu bằng email (giữ nguyên)
    public void updatePasswordByEmail(String email, String password) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return;
        }
        String hashedPassword = MD5Util.getMD5Hash(password); // Mã hóa mật khẩu
        String sql = "UPDATE Users SET Password = ? WHERE Email = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, hashedPassword);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating password by email", e);
        }
    }

    // Phương thức kiểm tra người dùng có tồn tại không
    public boolean checkUserExists(String username) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "SELECT * FROM Users WHERE UserName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error checking user existence", ex);
            throw ex;
        }
    }

    // Phương thức thay đổi mật khẩu
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return false;
        }
        String hashedOldPassword = MD5Util.getMD5Hash(oldPassword); // Mã hóa mật khẩu cũ
        String hashedNewPassword = MD5Util.getMD5Hash(newPassword); // Mã hóa mật khẩu mới
        String sql = "UPDATE Users SET Password = ? WHERE UserName = ? AND Password = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, hashedNewPassword);
            st.setString(2, username);
            st.setString(3, hashedOldPassword);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error changing password", e);
            return false;
        }
    }

    // Phương thức cập nhật thông tin người dùng
    public boolean updateUser(User user) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return false;
        }
        String sql = "UPDATE Users SET RoleID = ?, Email = ?, FullName = ?, Phone = ?, Dob = ?, Address = ?, Avatar = ?, UserName = ?, Password = ? WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getRoleID());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPhone());
            ps.setDate(5, user.getDob());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getAvatar());
            ps.setString(8, user.getUserName());
            ps.setString(9, MD5Util.getMD5Hash(user.getPassword())); // Mã hóa mật khẩu
            ps.setInt(10, user.getUserID());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating user", e);
            return false;
        }
    }

    // Phương thức kiểm tra Email trùng lặp
    public boolean isEmailExists(String email) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Phương thức kiểm tra SDT trùng lặp
    public boolean isPhoneExists(String phone) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM Users WHERE Phone = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Phương thức kiểm tra SDT trùng lặp cho ProfileServlet
    public boolean isPhoneExist(String phone, int excludeUserId) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM Users WHERE Phone = ? AND UserID != ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            stmt.setInt(2, excludeUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Phương thức kiểm tra Username trùng lặp
    public boolean isUsernameExists(String username) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM Users WHERE UserName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Phương thức cập nhật RoleID
    public void updateUserRole(int userId) {
        String sql = "UPDATE Users SET RoleID = 3 WHERE UserID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức kích hoạt tài khoản (cập nhật IsActive = 1)
    public boolean activateUser(int userId) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "UPDATE Users SET IsActive = 1 WHERE UserID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "User activated: " + userId);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error activating user", ex);
            throw ex;
        }
    }

    // Phương thức vô hiệu hóa tài khoản (cập nhật IsActive = 0)
    public boolean deactivateUser(int userId) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String sql = "UPDATE Users SET IsActive = 0 WHERE UserID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "User deactivated: " + userId);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error deactivating user", ex);
            throw ex;
        }
    }

    // Phương thức main để kiểm tra
    public static void main(String[] args) {
        DAOUser dao = new DAOUser();
        if (dao.conn == null) {
            System.out.println("Failed to connect to database!");
            return;
        }
        int n = 0;
        try {
            n = dao.registerUser(new User(1, 1, "example@example.com", "John Doe", "123456789", null, 1,
                    java.sql.Date.valueOf("1990-01-01"), "123 Main St", "avatar.jpg",
                    "johndoe", "password123"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (n > 0) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed!");
        }
    }

    // Phương thức đóng kết nối
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Database connection closed");
            } catch (SQLException e) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error closing connection", e);
            }
        }
    }

    // Phương thức kiểm tra kết nối
    public boolean isConnected() {
        return conn != null;
    }

    // Lấy danh sách tất cả users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching all users", e);
        }
        return users;
    }

    // Xóa user theo UserID
    public boolean deleteUser(int userId) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return false;
        }

        // Xóa các bản ghi liên quan trong HistoryLog trước
        String deleteHistorySql = "DELETE FROM HistoryLog WHERE UserID = ?";
        String deleteUserSql = "DELETE FROM Users WHERE UserID = ?";

        try {
            // Bắt đầu giao dịch
            conn.setAutoCommit(false);

            // 1. Xóa bản ghi trong HistoryLog
            try (PreparedStatement psHistory = conn.prepareStatement(deleteHistorySql)) {
                psHistory.setInt(1, userId);
                int historyRowsAffected = psHistory.executeUpdate();
                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO,
                        "Deleted " + historyRowsAffected + " history logs for UserID: " + userId);
            }

            // 2. Xóa bản ghi trong Users
            try (PreparedStatement psUser = conn.prepareStatement(deleteUserSql)) {
                psUser.setInt(1, userId);
                int userRowsAffected = psUser.executeUpdate();
                if (userRowsAffected > 0) {
                    Logger.getLogger(DAOUser.class.getName()).log(Level.INFO,
                            "User with ID " + userId + " deleted successfully");
                    conn.commit();
                    return true;
                } else {
                    Logger.getLogger(DAOUser.class.getName()).log(Level.WARNING,
                            "No user found with ID " + userId + " to delete");
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE,
                        "Error deleting user with ID " + userId + ", rolled back", e);
            } catch (SQLException rollbackEx) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE,
                        "Error during rollback", rollbackEx);
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE,
                        "Error resetting auto-commit", ex);
            }
        }
    }

    // Lấy danh sách users theo RoleID
    public List<User> getUsersByRole(int roleID) {
        List<User> users = new ArrayList<>();
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null in getUsersByRole");
            return users;
        }
        String sql = "SELECT * FROM Users WHERE RoleID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Executing query: " + sql + " with RoleID = " + roleID);
            try (ResultSet rs = ps.executeQuery()) {
                int rowCount = 0;
                while (rs.next()) {
                    rowCount++;
                    User user = new User(
                            rs.getInt("UserID"),
                            rs.getInt("RoleID"),
                            rs.getString("Email"),
                            rs.getString("FullName"),
                            rs.getString("Phone"),
                            rs.getDate("CreatedAt"),
                            rs.getInt("IsActive"),
                            rs.getDate("Dob"),
                            rs.getString("Address"),
                            rs.getString("Avatar"),
                            rs.getString("UserName"),
                            rs.getString("Password")
                    );
                    users.add(user);
                    Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Added user: ID=" + user.getUserID() + ", Name=" + user.getFullName());
                }
                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Rows fetched: " + rowCount + ", Total users in list: " + users.size());
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching users by role: " + e.getMessage(), e);
        }
        return users;
    }

    // Cập nhật RoleID cho user
    public int updateRole(int userID, int newRoleID) {
        int n = 0;
        String sql = "UPDATE Users SET RoleID = ? WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newRoleID);
            ps.setInt(2, userID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating role: " + e.getMessage(), e);
        }
        return n;
    }

    // Lấy tổng số user từ Users
    public int getTotalUsers() throws SQLException {
        int totalUsers = 0;
        String sql = "SELECT COUNT(*) AS TotalUsers FROM Users";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalUsers = rs.getInt("TotalUsers");
            }
        }
        return totalUsers;
    }

    // Phương thức cập nhật trạng thái người dùng
    public boolean updateUserStatus(int userId, int status) {
        if (conn == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connection is null");
            return false;
        }
        String sql = "UPDATE [dbo].[Users] SET isActive = ? WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, userId);
            int rowsAffected = ps.executeUpdate();
            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "User status updated: ID=" + userId + ", Status=" + status);
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating user status", e);
            return false;
        }
    }
}
