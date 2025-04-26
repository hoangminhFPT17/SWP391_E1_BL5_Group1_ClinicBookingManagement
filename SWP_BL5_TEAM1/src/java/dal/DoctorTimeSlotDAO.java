/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author LENOVO
 */
import dto.AssignedDoctorDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DoctorTimeSlot;

public class DoctorTimeSlotDAO extends DBContext {

    public DoctorTimeSlot getById(int id) {
        String query = "SELECT * FROM DoctorTimeSlot WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToDoctorTimeSlot(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<DoctorTimeSlot> getAll() {
        List<DoctorTimeSlot> list = new ArrayList<>();
        String query = "SELECT * FROM DoctorTimeSlot";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToDoctorTimeSlot(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<DoctorTimeSlot> getByDoctorId(int staffId) {
        List<DoctorTimeSlot> list = new ArrayList<>();
        String query = "SELECT * FROM DoctorTimeSlot WHERE staff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToDoctorTimeSlot(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean insert(DoctorTimeSlot slot) {
        String query = "INSERT INTO DoctorTimeSlot (staff_id, slot_id, day_of_week, max_appointments) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, slot.getStaffId());
            ps.setInt(2, slot.getSlotId());
            ps.setString(3, slot.getDayOfWeek());
            ps.setInt(4, slot.getMaxAppointments());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateAssignment(int oldDoctorId, int newDoctorId, int slotId, String dayOfWeek, int maxAppointments) {
        String sql = "UPDATE DoctorTimeSlot "
                + "SET staff_id = ?, max_appointments = ? "
                + "WHERE staff_id = ? AND slot_id = ? AND day_of_week = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, newDoctorId);      // new doctor
            ps.setInt(2, maxAppointments);  // new limit
            ps.setInt(3, oldDoctorId);      // locate existing row
            ps.setInt(4, slotId);
            ps.setString(5, dayOfWeek);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAssignment(int doctorId, int slotId, String dayOfWeek) {
        String sql = "DELETE FROM DoctorTimeSlot WHERE staff_id = ? AND slot_id = ? AND day_of_week = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, slotId);
            ps.setString(3, dayOfWeek);

            return ps.executeUpdate() > 0;  // Returns true if the delete operation was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getDoctorIdsBySlotId(int slotId) {
        List<Integer> doctorIds = new ArrayList<>();
        String query = "SELECT DISTINCT staff_id FROM DoctorTimeSlot WHERE slot_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, slotId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doctorIds.add(rs.getInt("staff_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doctorIds;
    }

    public List<String> getDoctorNamesBySlotId(int slotId) {
        List<String> doctorNames = new ArrayList<>();
        String sql = """
        SELECT u.full_name
        FROM DoctorTimeSlot dts
        JOIN StaffAccount sa ON dts.staff_id = sa.staff_id
        JOIN `User` u ON sa.user_id = u.user_id
        WHERE dts.slot_id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, slotId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doctorNames.add(rs.getString("full_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorNames;
    }

    public List<AssignedDoctorDTO> getAssignedDoctorsBySlotIdAndDay(int slotId, String dayOfWeek) {
        List<AssignedDoctorDTO> list = new ArrayList<>();
        String sql = """
        SELECT dts.staff_id, u.full_name, dts.max_appointments
        FROM DoctorTimeSlot dts
        JOIN StaffAccount sa ON dts.staff_id = sa.staff_id
        JOIN `User` u ON sa.user_id = u.user_id
        WHERE dts.slot_id = ? AND dts.day_of_week = ? AND sa.role = 'Doctor'
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, slotId);
            ps.setString(2, dayOfWeek); // e.g., "Monday"
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int doctorId = rs.getInt("staff_id");
                String fullName = rs.getString("full_name");
                int max = rs.getInt("max_appointments");
                list.add(new AssignedDoctorDTO(doctorId, fullName, max));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean exists(int doctorId, int slotId, String dayOfWeek) {
        String sql = "SELECT 1 FROM DoctorTimeSlot WHERE staff_id = ? AND slot_id = ? AND day_of_week = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, slotId);
            ps.setString(3, dayOfWeek);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Set<Integer>> getSlotMapByDoctorId(int doctorId) {
        Map<String, Set<Integer>> map = new HashMap<>(); //Use set to avoid duplicates (even tho is unique)
        String query = "SELECT day_of_week, slot_id FROM DoctorTimeSlot WHERE staff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getString("day_of_week");
                int slotId = rs.getInt("slot_id");
                map.computeIfAbsent(day, k -> new HashSet<>()).add(slotId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public boolean addDoctorToTimeSlot(int doctorId, int slotId, String dayOfWeek) {
        String sql = "INSERT INTO DoctorTimeSlot (staff_id, slot_id, day_of_week, max_appointments) VALUES (?, ?, ?, 10)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, slotId);
            ps.setString(3, dayOfWeek);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean removeDoctorFromTimeSlot(int doctorId, int slotId, String dayOfWeek) {
        String sql = "DELETE FROM DoctorTimeSlot WHERE staff_id = ? AND slot_id = ? AND day_of_week = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, slotId);
            ps.setString(3, dayOfWeek);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Get list of DoctorTimeSlot based on slotId and dayOfWeek
    public List<DoctorTimeSlot> getDoctorSlotsBySlotAndDay(int slotId, String dayOfWeek) {
        List<DoctorTimeSlot> list = new ArrayList<>();
        String sql = "SELECT * FROM doctor_time_slot WHERE slot_id = ? AND day_of_week = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, slotId);
            ps.setString(2, dayOfWeek);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DoctorTimeSlot dts = new DoctorTimeSlot();
                    dts.setId(rs.getInt("id"));
                    dts.setStaffId(rs.getInt("staff_id"));
                    dts.setSlotId(rs.getInt("slot_id"));
                    dts.setDayOfWeek(rs.getString("day_of_week"));
                    dts.setMaxAppointments(rs.getInt("max_appointments"));
                    list.add(dts);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 1. Get all doctor staff IDs working a specific slot on a specific day
    public List<Integer> getDoctorIdsBySlotAndDay(int slotId, String dayOfWeek) {
        List<Integer> doctorIds = new ArrayList<>();
        String sql = "SELECT staff_id FROM doctor_time_slot WHERE slot_id = ? AND day_of_week = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, slotId);
            ps.setString(2, dayOfWeek);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                doctorIds.add(rs.getInt("staff_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorIds;
    }

    // 2. Get the DoctorTimeSlot details (including max appointments) for a doctor on a slot and day
    public DoctorTimeSlot getDoctorTimeSlot(int staffId, int slotId, String dayOfWeek) {
        String sql = "SELECT * FROM doctor_time_slot WHERE staff_id = ? AND slot_id = ? AND day_of_week = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ps.setInt(2, slotId);
            ps.setString(3, dayOfWeek);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DoctorTimeSlot doctorTimeSlot = new DoctorTimeSlot();
                doctorTimeSlot.setId(rs.getInt("id"));
                doctorTimeSlot.setStaffId(rs.getInt("staff_id"));
                doctorTimeSlot.setSlotId(rs.getInt("slot_id"));
                doctorTimeSlot.setDayOfWeek(rs.getString("day_of_week"));
                doctorTimeSlot.setMaxAppointments(rs.getInt("max_appointments"));
                return doctorTimeSlot;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private DoctorTimeSlot mapToDoctorTimeSlot(ResultSet rs) throws SQLException {
        DoctorTimeSlot dts = new DoctorTimeSlot();
        dts.setId(rs.getInt("id"));
        dts.setStaffId(rs.getInt("staff_id"));
        dts.setSlotId(rs.getInt("slot_id"));
        dts.setDayOfWeek(rs.getString("day_of_week"));
        return dts;
    }
}
