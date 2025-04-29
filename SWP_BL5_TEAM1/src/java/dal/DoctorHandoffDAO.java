/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dto.DoctorHandoffDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    
    public List<DoctorHandoffDTO> getHandoffsForDoctor(int doctorId) {
        String sql =
          "SELECT dh.handoff_id, dh.from_doctor_id, dh.to_doctor_id, dh.appointment_id, " +
          "       dh.reason, dh.status, dh.created_at, " +
          "       u_from.full_name AS fromDoctorName, " +
          "       u_to.full_name   AS toDoctorName " +
          "  FROM DoctorHandoff dh " +
          "  JOIN StaffAccount sa_from ON dh.from_doctor_id = sa_from.staff_id " +
          "  JOIN `User` u_from        ON sa_from.user_id      = u_from.user_id " +
          "  JOIN StaffAccount sa_to   ON dh.to_doctor_id   = sa_to.staff_id " +
          "  JOIN `User` u_to          ON sa_to.user_id      = u_to.user_id " +
          " WHERE dh.to_doctor_id = ? " +
          " ORDER BY dh.created_at DESC";

        List<DoctorHandoffDTO> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DoctorHandoffDTO(
                        rs.getInt("handoff_id"),
                        rs.getInt("from_doctor_id"),
                        rs.getInt("to_doctor_id"),
                        rs.getInt("appointment_id"),
                        rs.getString("reason"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getString("fromDoctorName"),
                        rs.getString("toDoctorName")
                    ));
                }
            } 
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);        }
        return list;
    }
    
    
    public boolean updateHandoffStatus(int handoffId, String newStatus)  {
        String sql = "UPDATE DoctorHandoff SET status = ? WHERE handoff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, handoffId);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    public int getAppointmentIdForHandoff(int handoffId) {
        String sql = "SELECT appointment_id FROM DoctorHandoff WHERE handoff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, handoffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("appointment_id");
                } else {
                    throw new SQLException("No handoff with ID " + handoffId);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);        
        }
        return -1;
    }
}
