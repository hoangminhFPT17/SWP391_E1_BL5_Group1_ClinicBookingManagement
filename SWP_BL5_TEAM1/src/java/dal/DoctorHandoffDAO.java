/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;

/**
 *
 * @author Admin
 */
public class DoctorHandoffDAO extends DBContext {

    public boolean insert(int fromDoctorId, int toDoctorId, int appointmentId, String reason) {
        
        Boolean result = false;
        
        String sql
                = "INSERT INTO DoctorHandoff (from_doctor_id, to_doctor_id, appointment_id, reason) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, fromDoctorId);
            ps.setInt(2, toDoctorId);
            ps.setInt(3, appointmentId);
            ps.setString(4, reason);
            result = ps.executeUpdate() == 1;
            return result;
        } catch (SQLException e) {
            System.err.println("updateAppointmentStatus: " + e.getMessage());
        }
        return result;
    }
}
