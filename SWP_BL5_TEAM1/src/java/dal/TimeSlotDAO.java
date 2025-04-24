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

    public List<TimeSlot> searchTimeSlots(String keyword, Boolean isActive, int offset, int limit, String dayOfWeek) {
        List<TimeSlot> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT ts.* FROM TimeSlot ts "
                + "LEFT JOIN DoctorTimeSlot dts ON ts.slot_id = dts.slot_id "
                + "LEFT JOIN StaffAccount sa ON dts.staff_id = sa.staff_id "
                + "LEFT JOIN `User` u ON sa.user_id = u.user_id "
                + "WHERE 1 = 1 "
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (ts.name LIKE ? OR (u.full_name LIKE ? AND dts.day_of_week = ?)) ");
        }

        if (isActive != null) {
            sql.append("AND ts.is_active = ? ");
        }

        sql.append("ORDER BY ts.slot_id ASC LIMIT ? OFFSET ?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword.trim() + "%";
                ps.setString(paramIndex++, likeKeyword); // ts.name
                ps.setString(paramIndex++, likeKeyword); // u.full_name
                ps.setString(paramIndex++, dayOfWeek);   // filter doctor only on selected day
            }

            if (isActive != null) {
                ps.setBoolean(paramIndex++, isActive);
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex++, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToTimeSlot(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int countTimeSlots(String keyword, Boolean isActive) {
        int count = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT ts.slot_id) "
                + "FROM TimeSlot ts "
                + "LEFT JOIN DoctorTimeSlot dts ON ts.slot_id = dts.slot_id "
                + "LEFT JOIN StaffAccount sa ON dts.staff_id = sa.staff_id "
                + "LEFT JOIN `User` u ON sa.user_id = u.user_id "
                + "WHERE 1 = 1 "
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (ts.name LIKE ? OR u.full_name LIKE ?) ");
        }

        if (isActive != null) {
            sql.append("AND ts.is_active = ? ");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword.trim() + "%";
                ps.setString(paramIndex++, likeKeyword);
                ps.setString(paramIndex++, likeKeyword);
            }

            if (isActive != null) {
                ps.setBoolean(paramIndex++, isActive);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
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
        TimeSlotDAO dao = new TimeSlotDAO();

        // Example parameters
        String keyword = "";     // can also test with "Dr. Smith"
        Boolean isActive = null;        // or null if you don't want to filter by status
        int offset = 0;
        int limit = 10;
        String day = "Monday";

        List<TimeSlot> results = dao.searchTimeSlots(keyword, isActive, offset, limit, day);

        // Print results
        for (TimeSlot ts : results) {
            System.out.println("ID: " + ts.getSlotId());
            System.out.println("Name: " + ts.getName());
            System.out.println("Start: " + ts.getStartTime());
            System.out.println("End: " + ts.getEndTime());
            System.out.println("Active: " + ts.isIsActive());
            System.out.println("---------------------------");
        }
    }
}
