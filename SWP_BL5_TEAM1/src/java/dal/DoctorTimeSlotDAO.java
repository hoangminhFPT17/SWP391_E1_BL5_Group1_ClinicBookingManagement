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

    public boolean insert(DoctorTimeSlot dts) {
        String query = "INSERT INTO DoctorTimeSlot (staff_id, slot_id, day_of_week) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, dts.getStaffId());
            ps.setInt(2, dts.getSlotId());
            ps.setString(3, dts.getDayOfWeek());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update(DoctorTimeSlot dts) {
        String query = "UPDATE DoctorTimeSlot SET staff_id = ?, slot_id = ?, day_of_week = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, dts.getStaffId());
            ps.setInt(2, dts.getSlotId());
            ps.setString(3, dts.getDayOfWeek());
            ps.setInt(4, dts.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM DoctorTimeSlot WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DoctorTimeSlotDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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

