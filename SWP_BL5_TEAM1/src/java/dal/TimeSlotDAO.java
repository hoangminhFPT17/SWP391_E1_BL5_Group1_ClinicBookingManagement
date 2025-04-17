/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author LENOVO
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TimeSlot;

public class TimeSlotDAO extends DBContext {

    public TimeSlot getTimeSlotById(int id) {
        String query = "SELECT * FROM TimeSlot WHERE slot_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToTimeSlot(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<TimeSlot> getAllTimeSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        String query = "SELECT * FROM TimeSlot";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                slots.add(mapToTimeSlot(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return slots;
    }

    public boolean insertTimeSlot(TimeSlot slot) {
        String query = "INSERT INTO TimeSlot (name, start_time, end_time, is_active) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, slot.getName());
            ps.setTime(2, slot.getStartTime());
            ps.setTime(3, slot.getEndTime());
            ps.setBoolean(4, slot.isIsActive());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateTimeSlot(TimeSlot slot) {
        String query = "UPDATE TimeSlot SET name = ?, start_time = ?, end_time = ?, is_active = ? WHERE slot_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, slot.getName());
            ps.setTime(2, slot.getStartTime());
            ps.setTime(3, slot.getEndTime());
            ps.setBoolean(4, slot.isIsActive());
            ps.setInt(5, slot.getSlotId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteTimeSlot(int id) {
        String query = "DELETE FROM TimeSlot WHERE slot_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private TimeSlot mapToTimeSlot(ResultSet rs) throws SQLException {
        TimeSlot slot = new TimeSlot();
        slot.setSlotId(rs.getInt("slot_id"));
        slot.setName(rs.getString("name"));
        slot.setStartTime(rs.getTime("start_time"));
        slot.setEndTime(rs.getTime("end_time"));
        slot.setIsActive(rs.getBoolean("is_active"));
        return slot;
    }
    
    //main just for testing
    public static void main(String[] args) {
         TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        List<TimeSlot> timeSlots = timeSlotDAO.getAllTimeSlots();
        for (TimeSlot slot : timeSlots) {
            System.out.println(slot);
        }
    }
}

