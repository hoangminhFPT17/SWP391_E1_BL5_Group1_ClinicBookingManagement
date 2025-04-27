package dal;

import dto.AppointmentDTO;
import dto.AppointmentDetailDTO;
import dto.ReceptionAppointmentDTO;
import jakarta.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;

public class AppointmentDAO extends DBContext {

    public void insert(Appointment appointment) {
        String sql = "INSERT INTO Appointment (patient_phone, doctor_id, slot_id, appointment_date, status, description, package_id, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appointment.getPatientPhone());
            ps.setInt(2, appointment.getDoctorId());
            ps.setInt(3, appointment.getSlotId());
            ps.setDate(4, appointment.getAppointmentDate());
            ps.setString(5, appointment.getStatus());
            ps.setString(6, appointment.getDescription());
            if (appointment.getPackageId() != null) {
                ps.setInt(7, appointment.getPackageId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
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

    public List<Appointment> getAllWaitingPayment() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM swp_clinic.appointment WHERE status = 'completed'";
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

    public int countAppointmentsByDoctorAndSlotAndDate(int doctorId, int slotId, Date appointmentDate) {
        String sql = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ? AND slot_id = ? AND appointment_date = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, slotId);
            ps.setDate(3, appointmentDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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

    public List<AppointmentDTO> getTodayAppointmentsForCurrentSlot(int doctorId) throws SQLException {
        List<AppointmentDTO> list = new ArrayList<>();

        String sql = "SELECT a.appointment_id, p.full_name AS patientName, p.date_of_birth, "
                   + "       a.appointment_date, ts.name AS timeSlotName, "
                   + "       u.full_name AS doctorFullName, a.status "
                   + "  FROM Appointment a "
                   + "  JOIN Patient p ON a.patient_phone = p.phone "
                   + "  JOIN TimeSlot ts ON a.slot_id = ts.slot_id "
                   + "  JOIN StaffAccount sa ON a.doctor_id = sa.staff_id "
                   + "  JOIN User u ON sa.user_id = u.user_id "
                   + " WHERE a.doctor_id = ? "
                   + "   AND a.appointment_date = CURRENT_DATE() "
                   + " ORDER BY ts.start_time, a.created_at";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                int idx = 1;
                while (rs.next()) {
                    AppointmentDTO dto = new AppointmentDTO();
                    dto.setIndex(idx++);
                    dto.setPatientName(rs.getString("patientName"));
                    dto.setPatientDateOfBirth(rs.getDate("date_of_birth"));
                    dto.setAppointmentDate(rs.getDate("appointment_date"));
                    dto.setTimeSlotName(rs.getString("timeSlotName"));
                    dto.setDoctorFullName(rs.getString("doctorFullName"));
                    dto.setStatus(rs.getString("status"));
                    dto.setAppointmentId(rs.getInt("appointment_id"));
                    list.add(dto);
                }
            }
        }

        return list;
    }

    public List<ReceptionAppointmentDTO> getCompletedAppointments() throws SQLException {
        List<ReceptionAppointmentDTO> list = new ArrayList<>();

        String sql = "SELECT a.appointment_id, p.phone AS patient_phone, p.full_name AS patient_fullName, "
                   + "u.user_id AS doctor_userId, u.full_name AS doctor_fullName, ts.start_time AS slot_startTime, "
                   + "ts.end_time AS slot_endTime, ep.name AS package_name, a.description AS appointment_description "
                   + "FROM Appointment a "
                   + "INNER JOIN Patient p ON a.patient_phone = p.phone "
                   + "INNER JOIN StaffAccount sa ON a.doctor_id = sa.staff_id "
                   + "INNER JOIN User u ON sa.user_id = u.user_id "
                   + "INNER JOIN TimeSlot ts ON a.slot_id = ts.slot_id "
                   + "LEFT JOIN ExaminationPackage ep ON a.package_id = ep.package_id "
                   + "WHERE a.status = 'Completed' "
                   + "ORDER BY a.appointment_date;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReceptionAppointmentDTO dto = new ReceptionAppointmentDTO(
                        rs.getInt("appointment_id"),
                        rs.getString("patient_phone"),
                        rs.getString("patient_fullName"),
                        rs.getInt("doctor_userId"),
                        rs.getString("doctor_fullName"),
                        rs.getTime("slot_startTime"),
                        rs.getString("package_name"),
                        rs.getString("appointment_description"),
                        rs.getTime("slot_endTime")
                    );
                    list.add(dto);
                }
            }
        }

        return list;
    }

    public AppointmentDetailDTO getAppointmentDetailById(int id) {
        String sql = "SELECT a.appointment_id, a.patient_phone, u_doc.full_name AS doctorFullName, "
                   + "ts.name AS slot, a.appointment_date, a.status, a.created_at, "
                   + "a.description, ep.name AS examinationPackage "
                   + "FROM Appointment a "
                   + "JOIN TimeSlot ts ON a.slot_id = ts.slot_id "
                   + "JOIN ExaminationPackage ep ON a.package_id = ep.package_id "
                   + "JOIN StaffAccount sa ON a.doctor_id = sa.staff_id "
                   + "JOIN User u_doc ON sa.user_id = u_doc.user_id "
                   + "WHERE a.appointment_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AppointmentDetailDTO(
                        rs.getInt("appointment_id"),
                        rs.getString("patient_phone"),
                        rs.getString("doctorFullName"),
                        rs.getString("slot"),
                        rs.getDate("appointment_date"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getString("description"),
                        rs.getString("examinationPackage")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("getAppointmentDetailById error: " + e.getMessage());
        }
        return null;
    }
}
