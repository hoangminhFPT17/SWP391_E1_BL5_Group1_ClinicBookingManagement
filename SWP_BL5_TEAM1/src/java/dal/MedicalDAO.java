/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MedicalRecord;

/**
 *
 * @author ADMIN
 */
public class MedicalDAO extends DBContext{
    
    public List<MedicalRecord> getAllMedicalRecords(Timestamp startDate, Timestamp endDate) {
        List<MedicalRecord> records = new ArrayList<>();
        try {
            String sql = "SELECT * FROM MedicalRecord WHERE 1=1";
            if (startDate != null) {
                sql += " AND created_at >= ?";
            }
            if (endDate != null) {
                sql += " AND created_at <= ?";
            }
            sql += " ORDER BY created_at DESC";
            PreparedStatement st = connection.prepareStatement(sql);
            int paramIndex = 1;
            if (startDate != null) {
                st.setTimestamp(paramIndex++, startDate);
            }
            if (endDate != null) {
                st.setTimestamp(paramIndex++, endDate);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                MedicalRecord record = new MedicalRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setPatientPhone(rs.getString("patient_phone"));
                record.setDiagnosis(rs.getString("diagnosis"));
                record.setPrescription(rs.getString("prescription"));
                record.setNotes(rs.getString("notes"));
                record.setCreatedAt(rs.getTimestamp("created_at"));
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all medical records: " + e.getMessage());
        }
        return records;
    }
    
    public List<MedicalRecord> getMedicalRecordsByPatientPhoneAndDateRange(String patientPhone, Timestamp startDate, Timestamp endDate) {
    List<MedicalRecord> records = new ArrayList<>();
    
    try {
        String sql = "SELECT * FROM MedicalRecord WHERE patient_phone = ?";
        if (startDate != null) {
            sql += " AND created_at >= ?";
        }
        if (endDate != null) {
            sql += " AND created_at <= ?";
        }
        sql += " ORDER BY created_at DESC";
        
        PreparedStatement st = connection.prepareStatement(sql);
        int paramIndex = 1;
        st.setString(paramIndex++, patientPhone);
        if (startDate != null) {
            st.setTimestamp(paramIndex++, startDate);
        }
        if (endDate != null) {
            st.setTimestamp(paramIndex++, endDate);
        }
        ResultSet rs = st.executeQuery();
        
        while (rs.next()) {
            MedicalRecord record = new MedicalRecord();
            record.setRecordId(rs.getInt("record_id"));
            record.setPatientPhone(rs.getString("patient_phone"));
            record.setDiagnosis(rs.getString("diagnosis"));
            record.setPrescription(rs.getString("prescription"));
            record.setNotes(rs.getString("notes"));
            record.setCreatedAt(rs.getTimestamp("created_at"));
            records.add(record);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving medical records: " + e.getMessage());
    }
    
    return records;
}
    
    public List<MedicalRecord> getMedicalRecordsByPatientPhone(String patientPhone) {
        List<MedicalRecord> records = new ArrayList<>();
        
        try {
            // Try with "MedicalRecord" table name first (singular form)
            String sql = "SELECT * FROM MedicalRecord WHERE patient_phone = ? ORDER BY created_at DESC";
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, patientPhone);
            ResultSet rs = st.executeQuery();
            
            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                MedicalRecord record = new MedicalRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setPatientPhone(rs.getString("patient_phone"));
                record.setDiagnosis(rs.getString("diagnosis"));
                record.setPrescription(rs.getString("prescription"));
                record.setNotes(rs.getString("notes"));
                record.setCreatedAt(rs.getTimestamp("created_at"));
                
                records.add(record);
            }
            
            // If no records found with singular form, try with the plural form
            if (!hasRecords) {
                System.out.println("No records found in MedicalRecord table, trying MedicalRecords table");
                sql = "SELECT * FROM MedicalRecords WHERE patient_phone = ? ORDER BY created_at DESC";
                
                st = connection.prepareStatement(sql);
                st.setString(1, patientPhone);
                rs = st.executeQuery();
                
                while (rs.next()) {
                    MedicalRecord record = new MedicalRecord();
                    record.setRecordId(rs.getInt("record_id"));
                    record.setPatientPhone(rs.getString("patient_phone"));
                    record.setDiagnosis(rs.getString("diagnosis"));
                    record.setPrescription(rs.getString("prescription"));
                    record.setNotes(rs.getString("notes"));
                    record.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    records.add(record);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving medical records: " + e.getMessage());
        }
        
        return records;
    }
    
    public String getPatientPhoneByUserId(int userId) {
        String patientPhone = null;
        
        try {
            // Modified: Print database schema for patientaccount table to debug
            try {
                PreparedStatement debugSt = connection.prepareStatement("DESCRIBE patientaccount");
                ResultSet debugRs = debugSt.executeQuery();
                System.out.println("patientaccount table schema:");
                while (debugRs.next()) {
                    System.out.println(debugRs.getString(1) + " - " + debugRs.getString(2));
                }
            } catch (SQLException e) {
                System.out.println("Could not retrieve schema: " + e.getMessage());
            }
            
            // Modified: Try User table directly since we have userId
            String sql = "SELECT phone FROM user WHERE user_id = ?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                patientPhone = rs.getString("phone");
                System.out.println("Found phone in user table: " + patientPhone);
                return patientPhone;
            }
            
            // Modified: Get user phone from the User table through PatientAccount
            sql = "SELECT u.phone FROM user u " +
                  "JOIN patientaccount pa ON u.user_id = pa.user_id " +
                  "WHERE u.user_id = ?";
            
            st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            rs = st.executeQuery();
            
            if (rs.next()) {
                patientPhone = rs.getString("phone");
                System.out.println("Found phone via user and patientaccount join: " + patientPhone);
                return patientPhone;
            }
            
            // Modified: Direct method for last resort - try with literal column names
            sql = "SELECT * FROM patientaccount WHERE user_id = ?";
            st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            rs = st.executeQuery();
            
            if (rs.next()) {
                // Print all column values to debug
                java.sql.ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                System.out.println("patientaccount row data:");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(i);
                    System.out.println(columnName + ": " + value);
                    
                    // See if any column might contain the phone number
                    if (value != null && value.matches("\\d{10,}")) {
                        System.out.println("Potential phone number found in column: " + columnName);
                        patientPhone = value;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving patient phone: " + e.getMessage());
        }
        
        return patientPhone;
    }

    /**
     * Retrieves a single medical record by its ID.
     *
     * @param recordId The ID of the medical record.
     * @return The MedicalRecord object, or null if not found.
     */
    public MedicalRecord getRecordById(int recordId) {
        MedicalRecord record = null;
        try {
            // Try with "MedicalRecord" table name first
            String sql = "SELECT * FROM MedicalRecord WHERE record_id = ?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, recordId);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                record = new MedicalRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setPatientPhone(rs.getString("patient_phone"));
                record.setDiagnosis(rs.getString("diagnosis"));
                record.setPrescription(rs.getString("prescription"));
                record.setNotes(rs.getString("notes"));
                record.setCreatedAt(rs.getTimestamp("created_at"));
                return record;
            }
            
            // If not found, try with "MedicalRecords" table
            System.out.println("No record found in MedicalRecord table, trying MedicalRecords table");
            sql = "SELECT * FROM MedicalRecords WHERE record_id = ?";
            
            st = connection.prepareStatement(sql);
            st.setInt(1, recordId);
            rs = st.executeQuery();
            
            if (rs.next()) {
                record = new MedicalRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setPatientPhone(rs.getString("patient_phone"));
                record.setDiagnosis(rs.getString("diagnosis"));
                record.setPrescription(rs.getString("prescription"));
                record.setNotes(rs.getString("notes"));
                record.setCreatedAt(rs.getTimestamp("created_at"));
                return record;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving medical record with ID " + recordId + ": " + e.getMessage());
        }
        
        return record;
    }

    /**
     * Creates a new medical record in the database.
     *
     * @param record The MedicalRecord object to create.
     */
    public void createRecord(MedicalRecord record) {
        try {
            // Try with "MedicalRecords" table (assuming plural is standard)
            String sql = "INSERT INTO MedicalRecords (patient_phone, diagnosis, prescription, notes, created_at) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, record.getPatientPhone());
            st.setString(2, record.getDiagnosis());
            st.setString(3, record.getPrescription());
            st.setString(4, record.getNotes());
            st.setTimestamp(5, record.getCreatedAt());
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected == 0) {
                // If insert failed, try with "MedicalRecord" table
                System.out.println("Insert failed in MedicalRecords table, trying MedicalRecord table");
                sql = "INSERT INTO MedicalRecord (patient_phone, diagnosis, prescription, notes, created_at) VALUES (?, ?, ?, ?, ?)";
                
                st = connection.prepareStatement(sql);
                st.setString(1, record.getPatientPhone());
                st.setString(2, record.getDiagnosis());
                st.setString(3, record.getPrescription());
                st.setString(4, record.getNotes());
                st.setTimestamp(5, record.getCreatedAt());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error creating medical record for patientPhone " + record.getPatientPhone() + ": " + e.getMessage());
        }
    }

    /**
     * Updates an existing medical record in the database.
     *
     * @param record The MedicalRecord object with updated values.
     */
    public void updateRecord(MedicalRecord record) {
        try {
            // Try with "MedicalRecords" table first
            String sql = "UPDATE MedicalRecords SET patient_phone = ?, diagnosis = ?, prescription = ?, notes = ?, created_at = ? WHERE record_id = ?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, record.getPatientPhone());
            st.setString(2, record.getDiagnosis());
            st.setString(3, record.getPrescription());
            st.setString(4, record.getNotes());
            st.setTimestamp(5, record.getCreatedAt());
            st.setInt(6, record.getRecordId());
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected == 0) {
                // If update failed, try with "MedicalRecord" table
                System.out.println("Update failed in MedicalRecords table, trying MedicalRecord table");
                sql = "UPDATE MedicalRecord SET patient_phone = ?, diagnosis = ?, notes = ?, created_at = ? WHERE record_id = ?";
                
                st = connection.prepareStatement(sql);
                st.setString(1, record.getPatientPhone());
                st.setString(2, record.getDiagnosis());
                st.setString(3, record.getPrescription());
                st.setString(4, record.getNotes());
                st.setTimestamp(5, record.getCreatedAt());
                st.setInt(6, record.getRecordId());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error updating medical record with ID " + record.getRecordId() + ": " + e.getMessage());
        }
    }

    /**
     * Deletes a medical record from the database.
     *
     * @param recordId The ID of the medical record to delete.
     */
    public void deleteRecord(int recordId) {
        try {
            // Try with "MedicalRecords" table first
            String sql = "DELETE FROM MedicalRecords WHERE record_id = ?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, recordId);
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected == 0) {
                // If delete failed, try with "MedicalRecord" table
                System.out.println("Delete failed in MedicalRecords table, trying MedicalRecord table");
                sql = "DELETE FROM MedicalRecord WHERE record_id = ?";
                
                st = connection.prepareStatement(sql);
                st.setInt(1, recordId);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting medical record with ID " + recordId + ": " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        MedicalDAO dao = new MedicalDAO();
        int userId = 4;

        String patientPhone = dao.getPatientPhoneByUserId(userId);
        if (patientPhone == null) {
            System.out.println("Không tìm thấy số điện thoại của bệnh nhân.");
            return;
        }

        System.out.println("Số điện thoại của bệnh nhân: " + patientPhone);

        List<MedicalRecord> records = dao.getMedicalRecordsByPatientPhone(patientPhone);

        if (records.isEmpty()) {
            System.out.println("Không có hồ sơ bệnh án nào.");
        } else {
            System.out.println("Danh sách hồ sơ bệnh án:");
            for (MedicalRecord record : records) {
                System.out.println("Record ID: " + record.getRecordId());
                System.out.println("Diagnosis: " + record.getDiagnosis());
                System.out.println("Prescription: " + record.getPrescription());
                System.out.println("Notes: " + record.getNotes());
                System.out.println("Created At: " + record.getCreatedAt());
                System.out.println("-----------------------------");
            }
        }
    }
}