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
import model.Patient;

public class PatientDAO extends DBContext {

    public void insert(Patient patient) {
        String sql = "INSERT INTO Patient (phone, patient_account_id, full_name, date_of_birth, gender, email, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, patient.getPhone());
            if (patient.getPatientAccountId() != null)
                ps.setInt(2, patient.getPatientAccountId());
            else
                ps.setNull(2, Types.INTEGER);
            ps.setString(3, patient.getFullName());
            ps.setDate(4, patient.getDateOfBirth());
            ps.setString(5, patient.getGender());
            ps.setString(6, patient.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patient getByPhone(String phone) {
        String sql = "SELECT * FROM Patient WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patient p = new Patient();
                p.setPhone(rs.getString("phone"));
                p.setPatientAccountId(rs.getObject("patient_account_id") != null ? rs.getInt("patient_account_id") : null);
                p.setFullName(rs.getString("full_name"));
                p.setDateOfBirth(rs.getDate("date_of_birth"));
                p.setGender(rs.getString("gender"));
                p.setEmail(rs.getString("email"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Patient patient) {
        String sql = "UPDATE Patient SET patient_account_id = ?, full_name = ?, date_of_birth = ?, gender = ?, email = ? WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (patient.getPatientAccountId() != null)
                ps.setInt(1, patient.getPatientAccountId());
            else
                ps.setNull(1, Types.INTEGER);
            ps.setString(2, patient.getFullName());
            ps.setDate(3, patient.getDateOfBirth());
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getEmail());
            ps.setString(6, patient.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String phone) {
        String sql = "DELETE FROM Patient WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> getAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patient";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Patient p = new Patient();
                p.setPhone(rs.getString("phone"));
                p.setPatientAccountId(rs.getObject("patient_account_id") != null ? rs.getInt("patient_account_id") : null);
                p.setFullName(rs.getString("full_name"));
                p.setDateOfBirth(rs.getDate("date_of_birth"));
                p.setGender(rs.getString("gender"));
                p.setEmail(rs.getString("email"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
