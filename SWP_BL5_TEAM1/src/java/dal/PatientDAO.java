package dal;

import java.sql.Timestamp;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Patient;
import model.User;

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
    public int getTotalPatientsByPhoneAndGender(String phone, String gender) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patient WHERE phone LIKE ? AND gender = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + phone + "%");  // Partial matching for phone
            stmt.setString(2, gender);             // Exact matching for gender
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;  // Return 0 if no results
    }

    public List<Patient> getPatientsByPhoneAndGender(String phone, String gender, int page, int pageSize) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE phone LIKE ? AND gender = ? ORDER BY full_name LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + phone + "%");  // Partial matching for phone
            stmt.setString(2, gender);             // Exact matching for gender
            stmt.setInt(3, pageSize);              // Number of records to return
            stmt.setInt(4, (page - 1) * pageSize); // Calculate offset (page starts at 1)
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        }
        return patients;
    }

    public Patient updateBasicInfo(String phone, String fullName, String email, Date dob) throws SQLException {
        String sql = "UPDATE Patients SET fullName = ?, email = ?, dateOfBirth = ? WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setDate(3, dob);
            ps.setString(4, phone);
            ps.executeUpdate();
        }
        return getPatientByPhone(phone); // fetch fresh record
    }

    // Modified to support pagination
    public List<Patient> getAllPatients(int page, int pageSize) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient ORDER BY full_name LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);
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

    // New method for total patient count
    public int getTotalPatients() throws SQLException {
        String sql = "SELECT COUNT(*) FROM patient";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // New method for search by phone with pagination
    public List<Patient> getPatientsByPhone(String phone, int page, int pageSize) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE phone LIKE ? ORDER BY full_name LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + phone + "%");
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
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
        }
        return patients;
    }

    // New method for total patients by phone
    public int getTotalPatientsByPhone(String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patient WHERE phone LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + phone + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // New method for filter by gender with pagination
    public List<Patient> getPatientsByGender(String gender, int page, int pageSize) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE gender = ? ORDER BY full_name LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gender);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
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
        }
        return patients;
    }

    // New method for total patients by gender
    public int getTotalPatientsByGender(String gender) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patient WHERE gender = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gender);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
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

    public boolean updatePatientPhone(String phone, int id) {
        String sql = "UPDATE patient SET phone = ? WHERE patient_account_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            stmt.setInt(2, id);

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

    public boolean connectPatient(int num1, int num2) {
        String sql = "INSERT INTO `swp_clinic`.`patientaccount`\n"
                + "(`patient_account_id`,\n"
                + "`user_id`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, num1);
            stmt.setInt(2, num2);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error inserting new patient", ex);
            return false;
        }
    }

    public int countPatients(Date startDate, Date endDate) {
        int patientCount = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS patient_count FROM Patient WHERE 1=1");

        if (startDate != null) {
            sql.append(" AND created_at >= ?");
        }
        if (endDate != null) {
            sql.append(" AND created_at <= ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (startDate != null) {
                ps.setTimestamp(paramIndex++, new Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                ps.setTimestamp(paramIndex++, new Timestamp(endDate.getTime()));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    patientCount = rs.getInt("patient_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientCount;
    }

    public Map<String, Integer> getPatientDemographics(Date startDate, Date endDate) {
        Map<String, Integer> demographics = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT date_of_birth FROM Patient WHERE 1=1");

        if (startDate != null) {
            sql.append(" AND created_at >= ?");
        }
        if (endDate != null) {
            sql.append(" AND created_at <= ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (startDate != null) {
                ps.setDate(paramIndex++, startDate);
            }
            if (endDate != null) {
                ps.setDate(paramIndex++, endDate);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date birthDate = rs.getDate("date_of_birth");
                    if (birthDate != null) {
                        int age = calculateAge(birthDate);
                        String ageRange = getAgeRange(age);
                        demographics.put(ageRange, demographics.getOrDefault(ageRange, 0) + 1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return demographics;
    }

// Helper method to calculate age from birth date
    private int calculateAge(Date birthDate) {
        LocalDate birthLocalDate = birthDate.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthLocalDate, currentDate).getYears();
    }

// Helper method to determine age range
    private String getAgeRange(int age) {
        if (age >= 18 && age <= 25) {
            return "18-25";
        } else if (age >= 26 && age <= 35) {
            return "26-35";
        } else if (age >= 36 && age <= 45) {
            return "36-45";
        } else if (age >= 46 && age <= 55) {
            return "46-55";
        } else if (age >= 56 && age <= 65) {
            return "56-65";
        } else {
            return "66+";
        }
    }

}
