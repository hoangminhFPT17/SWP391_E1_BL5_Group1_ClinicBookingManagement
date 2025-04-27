package dal;

import dto.DoctorAssignDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.StaffAccount;

public class StaffAccountDAO extends DBContext {

    public StaffAccount getStaffById(int id) {
        String query = "SELECT * FROM StaffAccount WHERE staff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToStaffAccount(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<StaffAccount> getAllStaff() {
        List<StaffAccount> list = new ArrayList<>();
        String query = "SELECT * FROM StaffAccount";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToStaffAccount(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean insertStaffAccount(StaffAccount staff) {
        String query = "INSERT INTO StaffAccount (user_id, role, department) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, staff.getUserId());
            ps.setString(2, staff.getRole());
            ps.setString(3, staff.getDepartment());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateStaffAccount(StaffAccount staff) {
        String query = "UPDATE StaffAccount SET role = ?, department = ? WHERE staff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, staff.getRole());
            ps.setString(2, staff.getDepartment());
            ps.setInt(3, staff.getStaffId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteStaffAccount(int id) {
        String query = "DELETE FROM StaffAccount WHERE staff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<StaffAccount> getDoctorsByIds(List<Integer> ids) {
        List<StaffAccount> doctors = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return doctors;
        }

        String placeholders = ids.stream().map(id -> "?").collect(Collectors.joining(","));
        String query = "SELECT * FROM StaffAccount WHERE staff_id IN (" + placeholders + ") AND role = 'Doctor'";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(i + 1, ids.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doctors.add(mapToStaffAccount(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return doctors;
    }

    public List<DoctorAssignDTO> getAllDoctors() {
        List<DoctorAssignDTO> list = new ArrayList<>();
        String sql = """
            SELECT sa.staff_id, u.full_name, sa.department
            FROM StaffAccount sa
            JOIN `User` u ON sa.user_id = u.user_id
            WHERE sa.role = 'Doctor'
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int doctorId = rs.getInt("staff_id");
                String fullName = rs.getString("full_name");
                String department = rs.getString("department");
                list.add(new DoctorAssignDTO(doctorId, fullName, department));
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public StaffAccount getDoctorById(int id) {
        String query = "SELECT * FROM StaffAccount WHERE staff_id = ? AND role = 'Doctor'";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToStaffAccount(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public StaffAccount getDoctorByUserId(int id) {
        String query = "SELECT * FROM StaffAccount WHERE user_id=? AND role = 'Doctor'";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToStaffAccount(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private StaffAccount mapToStaffAccount(ResultSet rs) throws SQLException {
        StaffAccount staff = new StaffAccount();
        staff.setStaffId(rs.getInt("staff_id"));
        staff.setUserId(rs.getInt("user_id"));
        staff.setRole(rs.getString("role"));
        staff.setDepartment(rs.getString("department"));
        return staff;
    }

    public List<DoctorAssignDTO> getDoctorsBySpecialty(int specialtyId) {
        List<DoctorAssignDTO> doctorList = new ArrayList<>();
        String sql = """
                     SELECT sa.staff_id, u.full_name, ds.specialty_id 
                               FROM StaffAccount sa 
                               JOIN User u ON sa.user_id = u.user_id 
                               JOIN DoctorSpecialty ds ON sa.staff_id = ds.staff_id
                               WHERE sa.role = 'Doctor' and specialty_id = ?
                               
                     """;
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, specialtyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DoctorAssignDTO doctor = new DoctorAssignDTO();
                    doctor.setDoctorId(rs.getInt("staff_id"));
                    doctor.setFullName(rs.getString("full_name"));
                    doctor.setDepartment("");
                    doctorList.add(doctor);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doctorList;
    }
}
