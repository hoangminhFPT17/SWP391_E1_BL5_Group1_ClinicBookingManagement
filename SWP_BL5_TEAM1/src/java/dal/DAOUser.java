/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author JackGarland
 */
import model.User;
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
        super(); // Gọi constructor của DBconnectionect để khởi tạo kết nối database
    }

    // Phương thức đăng ký người dùng mới (IsActive mặc định là 0)
    public int registerUser(User user) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "INSERT INTO User (email, password_hash, phone, full_name, is_verified, otp_code, otp_expiry, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getFullName());
            stmt.setBoolean(5, false);
            stmt.setString(6, user.getOtpCode());
            stmt.setTimestamp(7, user.getOtpExpiry());
            stmt.setTimestamp(8, user.getCreatedAt());

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
//    public List<User> getNewUsers() throws SQLException {
//        List<User> newUsers = new ArrayList<>();
//        String sql = "SELECT TOP 5 * FROM Users WHERE RoleID = 2 ORDER BY CreatedAt DESC";
//        Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Executing query: {0}", sql);
//
//        if (connection == null) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
//            throw new SQLException("Database connectionection is not initialized.");
//        }
//
//        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Query executed successfully");
//            int rowCount = 0;
//            while (rs.next()) {
//                rowCount++;
//                User user = extractUserFromResultSet(rs);
//                newUsers.add(user);
//                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Added user: ID={0}, Name={1}, RoleID={2}",
//                        new Object[]{user.getUserID(), user.getFullName(), user.getRoleID()});
//            }
//            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Total rows fetched: {0}, Users list size: {1}",
//                    new Object[]{rowCount, newUsers.size()});
//        } catch (SQLException e) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching new users: {0}", e.getMessage());
//            throw e;
//        }
//        return newUsers;
//    }
// Phương thức đăng nhập (hỗ trợ cả email và username)
    public User Login(String loginInput, String password) {
        User user = null;
        String hashedPassword = password;
        String sql = "SELECT * FROM User WHERE email= ? AND password_hash= ?"; //bam o client
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, loginInput);
            pre.setString(2, hashedPassword);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("phone"),
                        rs.getString("full_name"),
                        rs.getBoolean("is_verified"),
                        rs.getString("otp_code"),
                        rs.getTimestamp("otp_expiry"),
                        rs.getTimestamp("created_at")
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

//     Phương thức lấy thông tin người dùng bằng Email
    public User getUserByEmail(String email) {
        if (connection == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
            return null;
        }
        String sql = "SELECT * FROM User WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
            return null;
        }
        String sql = "SELECT * FROM User WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
            return;
        }
        String hashedPassword = password; // Mã hóa mật khẩu
        String sql = "UPDATE user SET password_hash = ? WHERE email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
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
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("phone"),
                rs.getString("full_name"),
                rs.getBoolean("is_verified"),
                rs.getString("otp_code"),
                rs.getTimestamp("otp_expiry"),
                        rs.getTimestamp("created_at")
        );
    }

    // Phương thức cập nhật mật khẩu bằng email (giữ nguyên)
    public void updatePasswordByEmail(String email, String password) {
        if (connection == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
            return;
        }
        String hashedPassword = password; // Mã hóa mật khẩu
        String sql = "UPDATE User SET Password = ? WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, hashedPassword);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating password by email", e);
        }
    }

    // Phương thức kiểm tra người dùng có tồn tại không
    public boolean checkUserExists(String username) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "SELECT * FROM User WHERE UserName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
            return false;
        }
        String hashedOldPassword = oldPassword; // Mã hóa mật khẩu cũ
        String hashedNewPassword = newPassword; // Mã hóa mật khẩu mới
        String sql = "UPDATE User SET Password = ? WHERE UserName = ? AND Password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
            return false;
        }
        String sql = "UPDATE `swp_clinic`.`user`\n"
                + "SET\n"
                + "`email` = ?,\n"
                + "`password_hash` = ?,\n"
                + "`phone` = ?,\n"
                + "`full_name` = ?,\n"
                + "`is_verified` = ?,\n"
                + "`otp_code` = ?,\n"
                + "`otp_expiry` = ?,\n"
                + "`created_at` = ?\n"
                + "WHERE `user_id` = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getFullName());
            ps.setBoolean(5, user.isIsVerified());
            ps.setString(6, user.getOtpCode());
            ps.setTimestamp(7, user.getOtpExpiry());
            ps.setTimestamp(8, user.getCreatedAt());
            ps.setInt(9, user.getUserId());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating user", e);
            return false;
        }
    }

    // Phương thức kiểm tra Email trùng lặp
    public boolean isEmailExists(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM User WHERE Phone = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM User WHERE Phone = ? AND UserID != ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "SELECT COUNT(*) FROM User WHERE UserName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        String sql = "UPDATE User SET RoleID = 3 WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức kích hoạt tài khoản (cập nhật IsActive = 1)
    public boolean activateUser(int userId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "UPDATE User SET is_verified = true WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        if (connection == null) {
            throw new SQLException("Database connectionection is not initialized.");
        }
        String sql = "UPDATE User SET IsActive = 0 WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
//        if (dao.connection == null) {
//            System.out.println("Failed to connectionect to database!");
//            return;
//        }
        int n = 0;
        
        if (n > 0) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed!");
        }
    }

    // Phương thức đóng kết nối
    public void closeconnectionection() {
        if (connection != null) {
            try {
                connection.close();
                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Database connectionection closed");
            } catch (SQLException e) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error closing connectionection", e);
            }
        }
    }

    // Phương thức kiểm tra kết nối
    public boolean isconnectionected() {
        return connection != null;
    }

    // Lấy danh sách tất cả users
//    public List<User> getAllUsers() {
//        List<User> users = new ArrayList<>();
//        String sql = "SELECT * FROM Users";
//        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                users.add(extractUserFromResultSet(rs));
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching all users", e);
//        }
//        return users;
//    }
    // Xóa user theo UserID
    public boolean deleteUser(int userId) {
        if (connection == null) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
            return false;
        }

        // Xóa các bản ghi liên quan trong HistoryLog trước
        String deleteHistorySql = "DELETE FROM HistoryLog WHERE UserID = ?";
        String deleteUserSql = "DELETE FROM User WHERE UserID = ?";

        try {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // 1. Xóa bản ghi trong HistoryLog
            try (PreparedStatement psHistory = connection.prepareStatement(deleteHistorySql)) {
                psHistory.setInt(1, userId);
                int historyRowsAffected = psHistory.executeUpdate();
                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO,
                        "Deleted " + historyRowsAffected + " history logs for UserID: " + userId);
            }

            // 2. Xóa bản ghi trong Users
            try (PreparedStatement psUser = connection.prepareStatement(deleteUserSql)) {
                psUser.setInt(1, userId);
                int userRowsAffected = psUser.executeUpdate();
                if (userRowsAffected > 0) {
                    Logger.getLogger(DAOUser.class.getName()).log(Level.INFO,
                            "User with ID " + userId + " deleted successfully");
                    connection.commit();
                    return true;
                } else {
                    Logger.getLogger(DAOUser.class.getName()).log(Level.WARNING,
                            "No user found with ID " + userId + " to delete");
                    connection.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE,
                        "Error deleting user with ID " + userId + ", rolled back", e);
            } catch (SQLException rollbackEx) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE,
                        "Error during rollback", rollbackEx);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE,
                        "Error resetting auto-commit", ex);
            }
        }
    }

    // Lấy danh sách users theo RoleID
//    public List<User> getUsersByRole(int roleID) {
//        List<User> users = new ArrayList<>();
//        if (connection == null) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null in getUsersByRole");
//            return users;
//        }
//        String sql = "SELECT * FROM Users WHERE RoleID = ?";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, roleID);
//            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Executing query: " + sql + " with RoleID = " + roleID);
//            try (ResultSet rs = ps.executeQuery()) {
//                int rowCount = 0;
//                while (rs.next()) {
//                    rowCount++;
//                    User user = new User(
//                            rs.getInt("UserID"),
//                            rs.getInt("RoleID"),
//                            rs.getString("Email"),
//                            rs.getString("FullName"),
//                            rs.getString("Phone"),
//                            rs.getDate("CreatedAt"),
//                            rs.getInt("IsActive"),
//                            rs.getDate("Dob"),
//                            rs.getString("Address"),
//                            rs.getString("Avatar"),
//                            rs.getString("UserName"),
//                            rs.getString("Password")
//                    );
//                    users.add(user);
//                    Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Added user: ID=" + user.getUserID() + ", Name=" + user.getFullName());
//                }
//                Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "Rows fetched: " + rowCount + ", Total users in list: " + users.size());
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error fetching users by role: " + e.getMessage(), e);
//        }
//        return users;
//    }
    // Cập nhật RoleID cho user
    public int updateRole(int userID, int newRoleID) {
        int n = 0;
        String sql = "UPDATE User SET RoleID = ? WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
        String sql = "SELECT COUNT(*) AS TotalUsers FROM User";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalUsers = rs.getInt("TotalUsers");
            }
        }
        return totalUsers;
    }

    // Phương thức cập nhật trạng thái người dùng
//    public boolean updateUserStatus(int userId, int status) {
//        if (connection == null) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Database connectionection is null");
//            return false;
//        }
//        String sql = "UPDATE [dbo].[Users] SET isActive = ? WHERE UserID = ?";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, status);
//            ps.setInt(2, userId);
//            int rowsAffected = ps.executeUpdate();
//            Logger.getLogger(DAOUser.class.getName()).log(Level.INFO, "User status updated: ID=" + userId + ", Status=" + status);
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error updating user status", e);
//            return false;
//        }
//    }
}
