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
import model.Appointment;

public class AppointmentDAO extends DBContext {

    public void insert(Appointment appointment) {
        String sql = "INSERT INTO Appointment (patient_phone, doctor_id, slot_id, appointment_date, status, created_at) "
                + "VALUES (?, ?, ?, ?, ?, NOW())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appointment.getPatientPhone());
            ps.setInt(2, appointment.getDoctorId());
            ps.setInt(3, appointment.getSlotId());
            ps.setDate(4, appointment.getAppointmentDate());
            ps.setString(5, appointment.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Appointment getById(int id) {
        String sql = "SELECT * FROM Appointment WHERE appointment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractAppointment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Appointment> getByPatientPhone(String phone) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE patient_phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Appointment appointment) {
        String sql = "UPDATE Appointment SET patient_phone = ?, doctor_id = ?, slot_id = ?, appointment_date = ?, status = ? "
                + "WHERE appointment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appointment.getPatientPhone());
            ps.setInt(2, appointment.getDoctorId());
            ps.setInt(3, appointment.getSlotId());
            ps.setDate(4, appointment.getAppointmentDate());
            ps.setString(5, appointment.getStatus());
            ps.setInt(6, appointment.getAppointmentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Appointment WHERE appointment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAll() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointment";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Appointment> searchAppointments(String phone, String keyword, String status, Integer timeSlotId, int offset, int limit) {
        List<Appointment> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT a.* FROM Appointment a ");
        sql.append("JOIN StaffAccount sa ON a.doctor_id = sa.staff_id ");
        sql.append("JOIN `User` u ON sa.user_id = u.user_id ");
        sql.append("WHERE a.patient_phone = ? ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND u.full_name LIKE ? ");
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append("AND a.status = ? ");
        }

        if (timeSlotId != null) {
            sql.append("AND a.slot_id = ? ");
        }

        sql.append("ORDER BY a.appointment_date DESC ");
        sql.append("LIMIT ? OFFSET ?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            ps.setString(paramIndex++, phone);

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }

            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status.trim());
            }

            if (timeSlotId != null) {
                ps.setInt(paramIndex++, timeSlotId);
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex++, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countAppointments(String phone, String keyword, String status, Integer timeSlotId) {
        int total = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Appointment a ");
        sql.append("JOIN StaffAccount sa ON a.doctor_id = sa.staff_id ");
        sql.append("JOIN `User` u ON sa.user_id = u.user_id ");
        sql.append("WHERE a.patient_phone = ? ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND u.full_name LIKE ? ");
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append("AND a.status = ? ");
        }

        if (timeSlotId != null) {
            sql.append("AND a.slot_id = ? ");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            ps.setString(paramIndex++, phone);

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }

            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status.trim());
            }

            if (timeSlotId != null) {
                ps.setInt(paramIndex++, timeSlotId);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    private Appointment extractAppointment(ResultSet rs) throws SQLException {
        Appointment appt = new Appointment();
        appt.setAppointmentId(rs.getInt("appointment_id"));
        appt.setPatientPhone(rs.getString("patient_phone"));
        appt.setDoctorId(rs.getInt("doctor_id"));
        appt.setSlotId(rs.getInt("slot_id"));
        appt.setAppointmentDate(rs.getDate("appointment_date"));
        appt.setStatus(rs.getString("status"));
        appt.setCreatedAt(rs.getTimestamp("created_at"));
        return appt;
    }

    //Main just for testing
    public static void main(String[] args) {
        AppointmentDAO dao = new AppointmentDAO();

        // Test parameters
        String phone = "3333333333";          // Replace with existing patient phone
        String keyword = "";              // Search keyword (Doctor name or TimeSlot name), can be null or ""
        String status = "";            // Status filter, can be null or ""
        Integer timeSlotId = null;
        int page = 1;                         // Page number
        int pageSize = 5;                     // Number of records per page

        int offset = (page - 1) * pageSize;

        // Test search
        List<Appointment> appointments = dao.searchAppointments(phone, keyword, status, timeSlotId, offset, pageSize);
        int totalRecords = dao.countAppointments(phone, keyword, status, timeSlotId);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Output result
        System.out.println("Total appointments found: " + totalRecords);
        System.out.println("Total pages: " + totalPages);
        System.out.println("Current page: " + page);
        System.out.println("Appointments on this page:");

        for (Appointment appt : appointments) {
            System.out.println("ID: " + appt.getAppointmentId()
                    + ", Phone: " + appt.getPatientPhone()
                    + ", Doctor ID: " + appt.getDoctorId()
                    + ", Slot ID: " + appt.getSlotId()
                    + ", Date: " + appt.getAppointmentDate()
                    + ", Status: " + appt.getStatus());
        }
    }
}
