package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author ADMIN
 */
public class AuthDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(AuthDAO.class.getName());

    public static class UnverifiedAccountException extends Exception {
        public UnverifiedAccountException(String message) {
            super(message);
        }
    }

    /**
     * Authenticates a user by username or email and password.Returns a User object with role information if authentication is successful, else null.Throws UnverifiedAccountException if the account is not verified.
     * 
     * @param Email
     * @param password
     * @return 
     * @throws dal.AuthDAO.UnverifiedAccountException 
     */
    public User authenticate(String Email, String password) throws UnverifiedAccountException {
        String sql = "SELECT u.user_id, u.email, u.password_hash, u.full_name, u.phone, " +
                     "u.is_verified, s.role, p.patient_account_id " +
                     "FROM user u " +
                     "LEFT JOIN staffaccount s ON u.user_id = s.user_id " +
                     "LEFT JOIN patientaccount p ON u.user_id = p.user_id " +
                     "WHERE u.email = ? AND u.password_hash = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Email);
            stmt.setString(2, password); 

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boolean isVerified = rs.getBoolean("is_verified");
                if (!isVerified) {
                    throw new UnverifiedAccountException("Cần xác minh tài khoản trước khi đăng nhập");
                }

                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));

                String role = rs.getString("role");
                if (role == null) {
                    // Check if user exists in patientaccount table
                    if (rs.getObject("patient_account_id") != null) {
                        role = "Patient";
                    } else {
                        // User has no role assigned in either table
                        role = "Unknown";
                    }
                }
                user.setRole(role);

                return user;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Authentication error", ex);
        }
        return null;
    }

    /**
     * Checks if an email already exists in the database
     * 
     * @param email The email to check
     * @return true if email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking email existence", ex);
        }
        return false;
    }
    
    /**
     * Checks if a phone number already exists in the database
     * 
     * @param phone The phone number to check
     * @return true if phone exists, false otherwise
     */
    public boolean isPhoneExists(String phone) {
        String sql = "SELECT COUNT(*) FROM user WHERE phone = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking phone existence", ex);
        }
        return false;
    }
    
    /**
     * Registers a new user in the database
     * 
     * @param fullName User's full name
     * @param email User's email
     * @param phone User's phone number
     * @param passwordHash User's password hash
     * @return The newly created User object, or null if registration failed
     */
    public User registerUser(String fullName, String email, String phone, String passwordHash) {
        PreparedStatement userStmt = null;
        PreparedStatement patientAccountStmt = null;
        PreparedStatement patientStmt = null;
        
        try {
            // Use the existing connection from DBContext
            if (connection == null || connection.isClosed()) {
                LOGGER.log(Level.SEVERE, "Database connection is unavailable");
                return null;
            }
            
            connection.setAutoCommit(false);
            
            // Generate OTP for verification
            String otpCode = generateOTP();
            
            // Calculate timestamp for created_at and OTP expiry (24 hours)
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp otpExpiry = new Timestamp(now.getTime() + (24 * 60 * 60 * 1000));
            
            // 1. Insert into user table with is_verified=0 and OTP fields
            String userSql = "INSERT INTO user (email, password_hash, phone, full_name, is_verified, otp_code, otp_expiry, created_at) "
                           + "VALUES (?, ?, ?, ?, 0, ?, ?, ?)";
            
            userStmt = connection.prepareStatement(userSql, PreparedStatement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, email);
            userStmt.setString(2, passwordHash);
            userStmt.setString(3, phone);
            userStmt.setString(4, fullName);
            userStmt.setString(5, otpCode);
            userStmt.setTimestamp(6, otpExpiry);
            userStmt.setTimestamp(7, now);
            
            int affectedRows = userStmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            // Get the generated user ID
            int userId;
            try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                    LOGGER.log(Level.INFO, "User created with ID: {0}", userId);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            // 2. Insert into patientaccount table
            String patientAccountSql = "INSERT INTO patientaccount (user_id) VALUES (?)";
            patientAccountStmt = connection.prepareStatement(patientAccountSql, PreparedStatement.RETURN_GENERATED_KEYS);
            patientAccountStmt.setInt(1, userId);
            int patientAccountAffectedRows = patientAccountStmt.executeUpdate();
            
            if (patientAccountAffectedRows == 0) {
                throw new SQLException("Creating patient account failed, no rows affected.");
            }
            
            // Get the generated patient_account_id
            int patientAccountId;
            try (ResultSet generatedKeys = patientAccountStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patientAccountId = generatedKeys.getInt(1);
                    LOGGER.log(Level.INFO, "Patient account created with ID: {0}", patientAccountId);
                } else {
                    throw new SQLException("Creating patient account failed, no ID obtained.");
                }
            }
            
            // 3. Insert into patient table
            String patientSql = "INSERT INTO patient (phone, patient_account_id, full_name, email, created_at) "
                             + "VALUES (?, ?, ?, ?, ?)";
            patientStmt = connection.prepareStatement(patientSql);
            patientStmt.setString(1, phone);
            patientStmt.setInt(2, patientAccountId);
            patientStmt.setString(3, fullName);
            patientStmt.setString(4, email);
            patientStmt.setTimestamp(5, now);
            int patientAffectedRows = patientStmt.executeUpdate();
            
            if (patientAffectedRows == 0) {
                throw new SQLException("Creating patient record failed, no rows affected.");
            }
            
            // If we got here, all inserts succeeded
            connection.commit();
            LOGGER.log(Level.INFO, "Successfully registered user: {0} with email: {1}", new Object[]{fullName, email});
            
            // Create and return the User object with OTP fields
            User newUser = new User();
            newUser.setUserId(userId);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setFullName(fullName);
            newUser.setIsVerified(false);
            newUser.setOtpCode(otpCode);
            newUser.setOtpExpiry(otpExpiry);
            newUser.setCreatedAt(now);
            newUser.setRole("Patient");
            
            return newUser;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error registering user: " + ex.getMessage(), ex);
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.rollback();
                    LOGGER.log(Level.INFO, "Transaction rolled back");
                }
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Error rolling back transaction: " + rollbackEx.getMessage(), rollbackEx);
            }
        } finally {
            try {
                if (userStmt != null) userStmt.close();
                if (patientAccountStmt != null) patientAccountStmt.close();
                if (patientStmt != null) patientStmt.close();
                if (connection != null && !connection.isClosed()) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error closing resources: " + ex.getMessage(), ex);
            }
        }
        return null;
    }

    /**
     * Verifies a user account by email and OTP code
     * 
     * @param email The user's email
     * @param otp The OTP code sent to the user
     * @return true if verification succeeded, false otherwise
     */
    public boolean verifyAccount(String email, String otp) {
        String sql = "UPDATE user SET is_verified = 1, otp_code = NULL, otp_expiry = NULL " +
                     "WHERE email = ? AND otp_code = ? AND otp_expiry > NOW()";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, otp);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error verifying account", ex);
        }
        return false;
    }

    /**
     * Generates a random OTP code for email verification
     * 
     * @return A random OTP code
     */
    private String generateOTP() {
        // Implementation for generating a random OTP code
        return String.valueOf((int) (Math.random() * 900000) + 100000); // 6-digit random number
    }
}