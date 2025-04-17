/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

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
