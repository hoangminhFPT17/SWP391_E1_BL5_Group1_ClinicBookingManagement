/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DoctorUnavailability;

/**
 *
 * @author LENOVO
 */
public class DoctorUnavailabilityDAO extends DBContext {

    // Check if a doctor is unavailable for a given slot and date
    public boolean isDoctorUnavailable(int staffId, int slotId, Date appointmentDate) {
        String sql = "SELECT COUNT(*) FROM doctorunavailability WHERE staff_id = ? AND slot_id = ? AND unavailable_date = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ps.setInt(2, slotId);
            ps.setDate(3, appointmentDate);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // true if there is a record
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // (Optional) Get unavailability reason if needed
    public DoctorUnavailability getDoctorUnavailability(int staffId, int slotId, Date appointmentDate) {
        String sql = "SELECT * FROM doctor_unavailability WHERE staff_id = ? AND slot_id = ? AND unavailable_date = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ps.setInt(2, slotId);
            ps.setDate(3, appointmentDate);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DoctorUnavailability du = new DoctorUnavailability();
                    du.setUnavailabilityId(rs.getInt("unavailability_id"));
                    du.setStaffId(rs.getInt("staff_id"));
                    du.setSlotId(rs.getInt("slot_id"));
                    du.setUnavailableDate(rs.getDate("unavailable_date"));
                    du.setReason(rs.getString("reason"));
                    return du;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
