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
import model.Specialty;

public class DoctorSpecialtyDAO extends DBContext {

    // Add a specialty to a doctor
    public boolean assignSpecialtyToDoctor(int doctorId, int specialtyId) {
        String sql = "INSERT INTO DoctorSpecialty (staff_id, specialty_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, specialtyId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove a specialty from a doctor
    public boolean removeSpecialtyFromDoctor(int doctorId, int specialtyId) {
        String sql = "DELETE FROM DoctorSpecialty WHERE staff_id = ? AND specialty_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, specialtyId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all specialties for a doctor
    public List<Specialty> getSpecialtiesByDoctor(int doctorId) {
        List<Specialty> list = new ArrayList<>();
        String sql = """
            SELECT s.specialty_id, s.name 
            FROM Specialty s 
            JOIN DoctorSpecialty ds ON s.specialty_id = ds.specialty_id 
            WHERE ds.staff_id = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Specialty s = new Specialty();
                s.setSpecialtyId(rs.getInt("specialty_id"));
                s.setName(rs.getString("name"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Check if a doctor already has a specialty assigned
    public boolean hasSpecialty(int doctorId, int specialtyId) {
        String sql = "SELECT * FROM DoctorSpecialty WHERE staff_id = ? AND specialty_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, specialtyId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

