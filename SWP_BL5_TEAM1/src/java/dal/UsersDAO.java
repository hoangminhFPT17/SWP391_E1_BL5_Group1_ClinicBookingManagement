package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 * * Data Access Object for User operations * @author ADMIN
 */


public class UsersDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(UsersDAO.class.getName());

    /**
     * * Get user profile information by userId * @param userId the user ID to
     * retrieve * @return User object with profile data or null if not found
     */
    public User getUserById(int userId) {
        String sql = "SELECT u.user_id, u.email, u.password_hash, u.full_name, u.phone, " + "u.is_verified, s.role " + "FROM user u " + "LEFT JOIN staffaccount s ON u.user_id = s.user_id " + "WHERE u.user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setFullName(rs.getString("full_name"));
                user.setPhone(rs.getString("phone"));
                user.setIsVerified(rs.getBoolean("is_verified"));
                String role = rs.getString("role");
                if (role == null) {
                    role = "Patient";
                }
//                user.setRole(role);
                return user;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user profile", ex);
        }
        return null;
    }

    /**
     * * Get user by phone number * @param phone the phone number to search for
     * * @return User object with data or null if not found
     */
    public User getUserByPhone(String phone) {
        String sql = "SELECT u.user_id, u.email, u.password_hash, u.full_name, u.phone, " + "u.is_verified, s.role " + "FROM user u " + "LEFT JOIN staffaccount s ON u.user_id = s.user_id " + "WHERE u.phone = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setFullName(rs.getString("full_name"));
                user.setPhone(rs.getString("phone"));
                user.setIsVerified(rs.getBoolean("is_verified"));
                String role = rs.getString("role");
                if (role == null) {
                    role = "Patient";
                }
//                user.setRole(role);
                return user;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user by phone", ex);
        }
        return null;
    }

    /**
     * * Get user by email
     *
     * @param email the email to search for
     * @return User object with data or null if not found
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT u.user_id, u.email, u.password_hash, u.full_name, u.phone, "
                + "u.is_verified, s.role "
                + "FROM user u "
                + "LEFT JOIN staffaccount s ON u.user_id = s.user_id "
                + "WHERE u.email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setFullName(rs.getString("full_name"));
                user.setPhone(rs.getString("phone"));
                user.setIsVerified(rs.getBoolean("is_verified"));

                String role = rs.getString("role");
                if (role == null) {
                    role = "Patient";
                }
//                user.setRole(role);

                return user;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user by email", ex);
        }
        return null;
    }

    /**
     * Update user profile information
     *
     * @param user The User object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE user SET full_name = ?, phone = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getPhone());
            stmt.setInt(3, user.getUserId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating user profile", ex);
            return false;
        }
    }

    /**
     * Update user phone number only
     *
     * @param userId The user ID
     * @param phone The new phone number
     * @return true if update was successful, false otherwise
     */
    public boolean updateUserPhone(int userId, String phone) {
        String sql = "UPDATE user SET phone = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating user phone", ex);
            return false;
        }
    }

    /**
     * Update user full name only
     *
     * @param userId The user ID
     * @param fullName The new full name
     * @return true if update was successful, false otherwise
     */
    public boolean updateUserFullName(int userId, String fullName) {
        String sql = "UPDATE user SET full_name = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating user full name", ex);
            return false;
        }
    }

    /**
     * Update user password
     *
     * @param userId The user ID
     * @param newPassword The new password hash
     * @return true if password update was successful, false otherwise
     */
    public boolean updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE user SET password_hash = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating user password", ex);
            return false;
        }
    }

    /**
     * Verify a user account
     *
     * @param userId The user ID to verify
     * @return true if verification was successful, false otherwise
     */
    public boolean verifyUser(int userId) {
        String sql = "UPDATE user SET is_verified = 1 WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error verifying user", ex);
            return false;
        }
    }

    /**
     * Create a new user account
     *
     * @param user The User object with account information
     * @return The user ID if creation was successful, -1 otherwise
     */
    public int createUser(User user) {
        String sql = "INSERT INTO user (email, password_hash, full_name, phone, is_verified) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getPhone());
            stmt.setBoolean(5, user.isIsVerified());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error creating user", ex);
        }
        return -1;
    }

    /**
     * Get list of all users (for admin purposes)
     *
     * @return List of User objects or an empty list if none found
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.email, u.full_name, u.phone, "
                + "u.is_verified, s.role "
                + "FROM user u "
                + "LEFT JOIN staffaccount s ON u.user_id = s.user_id "
                + "ORDER BY u.user_id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setPhone(rs.getString("phone"));
                user.setIsVerified(rs.getBoolean("is_verified"));

                String role = rs.getString("role");
                if (role == null) {
                    role = "Patient";
                }
//                user.setRole(role);

                users.add(user);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving all users", ex);
        }
        return users;
    }

    /**
     * Delete a user account
     *
     * @param userId The user ID to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error deleting user", ex);
            return false;
        }
    }

    /**
     * Check if phone number already exists in the database
     *
     * @param phone The phone number to check
     * @param excludeUserId Optional user ID to exclude from the check (for
     * updates)
     * @return true if phone exists, false otherwise
     */
    public boolean isPhoneExists(String phone, Integer excludeUserId) {
        String sql = "SELECT COUNT(*) FROM user WHERE phone = ?";

        if (excludeUserId != null) {
            sql += " AND user_id != ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);

            if (excludeUserId != null) {
                stmt.setInt(2, excludeUserId);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking if phone exists", ex);
        }
        return false;
    }

    /**
     * Check if email already exists in the database
     *
     * @param email The email to check
     * @param excludeUserId Optional user ID to exclude from the check (for
     * updates)
     * @return true if email exists, false otherwise
     */
    public boolean isEmailExists(String email, Integer excludeUserId) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";

        if (excludeUserId != null) {
            sql += " AND user_id != ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            if (excludeUserId != null) {
                stmt.setInt(2, excludeUserId);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking if email exists", ex);
        }
        return false;
    }
    
    public List<User> getAllUsers(String filter)  {
        List<User> list = new ArrayList<>();
        String sql =
          "SELECT @rownum := @rownum + 1 AS idx, " +
          "       u.user_id, u.email, u.phone, u.full_name, u.is_verified, u.created_at " +
          "  FROM (SELECT @rownum := 0) vars " +
          "  JOIN `User` u ON 1=1 " +
          " WHERE (? = '' OR LOWER(u.full_name) LIKE ? OR LOWER(u.email) LIKE ?) " +
          " ORDER BY u.created_at DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String f = filter == null ? "" : filter.toLowerCase();
            ps.setString(1, f);
            ps.setString(2, "%" + f + "%");
            ps.setString(3, "%" + f + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("full_name"),
                        rs.getBoolean("is_verified"),
                        rs.getTimestamp("created_at")
                    ));
                }
            }
        }
        catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking if email exists", ex);
        }
        return list;
    }
    
    public boolean updateUser(int userId, String email, String phone,
                              String fullName, boolean isVerified) {
        String sql = "UPDATE `User` " +
                     "   SET email = ?, phone = ?, full_name = ?, is_verified = ? " +
                     " WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, fullName);
            ps.setBoolean(4, isVerified);
            ps.setInt(5, userId);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking if email exists", ex);
        }
        return false;
    }
}
