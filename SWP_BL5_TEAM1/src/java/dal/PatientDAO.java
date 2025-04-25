package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Patient;

/**
 * Data Access Object for Patient operations
 *
 * @author ADMIN
 */
public class PatientDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(PatientDAO.class.getName());

    /**
     * Get patient information by userId
     *
     * @param userId the user ID to retrieve
     * @return Patient object with data or null if not found
     */
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient ORDER BY full_name";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPhone(rs.getString("phone"));
                patient.setPatientAccountId(rs.getInt("patient_account_id") != 0 ? rs.getInt("patient_account_id") : null);
                patient.setFullName(rs.getString("full_name"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setEmail(rs.getString("email"));
                patient.setCreatedAt(rs.getTimestamp("created_at"));
                patients.add(patient);
            }
            LOGGER.log(Level.INFO, "Retrieved " + patients.size() + " patients");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving all patients", ex);
            throw ex;
        }
        return patients;
    }
    public Patient getPatientByUserId(int userId) {
        String sql = "SELECT p.* FROM patient p "
                + "INNER JOIN user u ON p.phone = u.phone "
                + "WHERE u.user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setPhone(rs.getString("phone"));
                patient.setPatientAccountId(rs.getInt("patient_account_id"));
                patient.setFullName(rs.getString("full_name"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setEmail(rs.getString("email"));
                patient.setCreatedAt(rs.getTimestamp("created_at"));

                return patient;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving patient profile", ex);
        }
        return null;
    }

    /**
     * Get patient by phone number
     *
     * @param phone the phone number to search for
     * @return Patient object with data or null if not found
     */
    public Patient getPatientByPhone(String phone) {
        String sql = "SELECT * FROM patient WHERE phone = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setPhone(rs.getString("phone"));
                patient.setPatientAccountId(rs.getInt("patient_account_id"));
                patient.setFullName(rs.getString("full_name"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setEmail(rs.getString("email"));
                patient.setCreatedAt(rs.getTimestamp("created_at"));

                return patient;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving patient by phone", ex);
        }
        return null;
    }

    /**
     * Update patient name only
     *
     * @param phone The patient phone
     * @param fullName The new full name
     * @return true if update was successful, false otherwise
     */
    public boolean updatePatientName(String phone, String fullName) {
        String sql = "UPDATE patient SET full_name = ? WHERE phone = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, phone);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating patient name", ex);
            return false;
        }
    }

    /**
     * Update patient profile information without changing the phone number
     *
     * @param phone The patient's phone number (not to be changed)
     * @param fullName The patient's full name
     * @param dateOfBirth The patient's date of birth
     * @param gender The patient's gender
     * @param userId The user ID associated with this patient
     * @return true if update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updatePatientProfileWithoutPhone(String phone, String fullName,
            Date dateOfBirth, String gender, int userId)
            throws SQLException {
        boolean success = false;

        try {
            // Start transaction
            connection.setAutoCommit(false);

            // Update patient fields without changing phone
            String updatePatientSql = "UPDATE patient SET full_name = ?, date_of_birth = ?, gender = ? WHERE phone = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updatePatientSql)) {
                stmt.setString(1, fullName);
                stmt.setDate(2, dateOfBirth);
                stmt.setString(3, gender);
                stmt.setString(4, phone);
                int updated = stmt.executeUpdate();
                LOGGER.info("Updated profile for patient with phone: " + phone);

                if (updated == 0) {
                    // If no patient record exists, this could be a new patient
                    LOGGER.warning("No patient record found for phone: " + phone);
                    connection.rollback();
                    return false;
                }
            }

            // Update name in User table
            String updateUserNameSql = "UPDATE user SET full_name = ? WHERE user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateUserNameSql)) {
                stmt.setString(1, fullName);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
                LOGGER.info("Updated name in user table for user ID " + userId);
            }

            // Commit transaction
            connection.commit();
            success = true;
            LOGGER.info("Successfully updated profile for phone " + phone);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating patient profile: " + ex.getMessage(), ex);
            try {
                // Roll back on error
                connection.rollback();
                LOGGER.info("Transaction rolled back due to error");
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Error rolling back transaction", rollbackEx);
            }
            throw ex;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error resetting auto-commit", ex);
            }
        }

        return success;
    }

    public boolean insertPatient(Patient patient) {
        String sql = "INSERT INTO patient (phone, patient_account_id, full_name, date_of_birth, gender, email, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getPhone());

            if (patient.getPatientAccountId() != null) {
                stmt.setInt(2, patient.getPatientAccountId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.setString(3, patient.getFullName());
            stmt.setDate(4, patient.getDateOfBirth());
            stmt.setString(5, patient.getGender());
            stmt.setString(6, patient.getEmail());
            stmt.setTimestamp(7, patient.getCreatedAt());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error inserting new patient", ex);
            return false;
        }
    }
}
