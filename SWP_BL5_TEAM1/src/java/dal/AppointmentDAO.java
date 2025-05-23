package dal;

import dto.AppointmentDTO;
import dto.AppointmentDetailDTO;
import dto.PatientQueueDTO;
import dto.ReceptionAppointmentDTO;
import jakarta.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Appointment;
import model.InvoiceDetailed;

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
            System.out.println("insert: " + e.getMessage());
        }
    }

    public void updatePaymentToWaitingPayment(int id) {
        String sql = "UPDATE `swp_clinic`.`appointment` SET `status` = 'Waiting-Payment' WHERE (`appointment_id` = ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
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
        String sql = "UPDATE Appointment SET patient_phone = ?, doctor_id = ?, slot_id = ?, appointment_date = ?, status = ?, description = ? "
                + "WHERE appointment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appointment.getPatientPhone());
            ps.setInt(2, appointment.getDoctorId());
            ps.setInt(3, appointment.getSlotId());
            ps.setDate(4, appointment.getAppointmentDate());
            ps.setString(5, appointment.getStatus());
            ps.setString(6, appointment.getDescription());
            ps.setInt(7, appointment.getAppointmentId());
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

    public Map<String, Integer> countAppointmentsByTimeSlot(Date startDate, Date endDate) {
        Map<String, Integer> result = new HashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT CONCAT('Slot ', s.name, ': ', DATE_FORMAT(s.start_time, '%H:%i'), '-', DATE_FORMAT(s.end_time, '%H:%i')) AS slot_name, "
                + "COUNT(a.appointment_id) AS booking_count "
                + "FROM Appointment a "
                + "JOIN TimeSlot s ON a.slot_id = s.slot_id "
                + "WHERE 1=1"
        );

        if (startDate != null) {
            sql.append(" AND a.created_at >= ?");
        }
        if (endDate != null) {
            sql.append(" AND a.created_at <= ?");
        }

        sql.append(" GROUP BY s.slot_id, s.name, s.start_time, s.end_time");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (startDate != null) {
                ps.setDate(paramIndex++, startDate);
            }
            if (endDate != null) {
                ps.setDate(paramIndex++, endDate);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String slotName = rs.getString("slot_name");
                    int bookingCount = rs.getInt("booking_count");
                    result.put(slotName, bookingCount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<Integer, Integer> getMostBookedDoctors() {
        Map<Integer, Integer> result = new LinkedHashMap<>(); //Linked to keep Order by and not get shuffle
        String sql = "SELECT doctor_id, COUNT(*) AS booking_count "
                + "FROM Appointment "
                + "GROUP BY doctor_id "
                + "ORDER BY booking_count DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                int bookingCount = rs.getInt("booking_count");
                result.put(doctorId, bookingCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Map<String, Object>> getAppointmentCountsByDoctor(Date startDate, Date endDate) {
        List<Map<String, Object>> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT u.full_name AS doctor_name, COUNT(a.appointment_id) AS appointment_count "
                + "FROM Appointment a "
                + "JOIN StaffAccount s ON a.doctor_id = s.staff_id "
                + "JOIN User u ON s.user_id = u.user_id "
                + "WHERE 1=1 "
        );

        if (startDate != null) {
            sql.append("AND a.created_at >= ? ");
        }
        if (endDate != null) {
            sql.append("AND a.created_at <= ? ");
        }

        sql.append("GROUP BY s.staff_id, u.full_name ORDER BY appointment_count DESC");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (startDate != null) {
                ps.setDate(paramIndex++, startDate);
            }
            if (endDate != null) {
                ps.setDate(paramIndex++, endDate);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> appointmentData = new HashMap<>();
                    appointmentData.put("doctor_name", rs.getString("doctor_name"));
                    appointmentData.put("appointment_count", rs.getInt("appointment_count"));
                    result.add(appointmentData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
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
        appt.setDescription(rs.getString("description"));
        appt.setPackageId(rs.getInt("package_id"));
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

    public List<AppointmentDTO> getTodayAppointmentsForCurrentSlot(int doctorId) throws SQLException {
        List<AppointmentDTO> list = new ArrayList<>();

        String sql = ""
                + "SELECT a.appointment_id, p.full_name AS patientName, p.date_of_birth, "
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

    public InvoiceDetailed getInvoiceByAppointmentId(int appointmentId) {
        InvoiceDetailed invoice = null;
        String sql = "SELECT * FROM invoices WHERE appointment_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    invoice = new InvoiceDetailed();
                    invoice.setInvoiceId(rs.getInt("invoice_id"));
                    invoice.setAppointmentId(rs.getInt("appointment_id"));
                    invoice.setAddress(rs.getString("address"));
                    invoice.setPatientName(rs.getString("patient_name"));
                    invoice.setPatientPhone(rs.getString("patient_phone"));
                    invoice.setDoctorName(rs.getString("doctor_name"));
                    invoice.setIssueDate(rs.getDate("issue_date"));
                    invoice.setDueDate(rs.getDate("due_date"));
                    invoice.setItem1Description(rs.getString("item1_description"));
                    invoice.setItem1Rate(rs.getInt("item1_rate"));
                    invoice.setItem2Description(rs.getString("item2_description"));
                    invoice.setItem2Rate(rs.getInt("item2_rate"));
                    invoice.setItem3Description(rs.getString("item3_description"));
                    invoice.setItem3Rate(rs.getInt("item3_rate"));
                    invoice.setPackageName(rs.getString("package"));
                    // subtotal and total are calculated inside the model's getter, if you designed it that way
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Or log the error
        }

        return invoice;
    }

    public List<ReceptionAppointmentDTO> getAwaitingPaymentAppointments(String phone) throws SQLException {
        List<ReceptionAppointmentDTO> list = new ArrayList<>();

        String sql = "SELECT\n"
                + "    a.appointment_id AS appointment_id,\n"
                + "    p.phone AS patient_phone,\n"
                + "    p.full_name AS patient_fullName,\n"
                + "    u.user_id AS doctor_userId,\n"
                + "    u.full_name AS doctor_fullName,\n"
                + "    ts.start_time AS slot_startTime,\n"
                + "    ts.end_time AS slot_endTime,\n"
                + "    ep.name AS package_name,\n"
                + "    a.description AS appointment_description\n"
                + "FROM Appointment a\n"
                + "INNER JOIN Patient p ON a.patient_phone = p.phone\n"
                + "INNER JOIN StaffAccount sa ON a.doctor_id = sa.staff_id\n"
                + "INNER JOIN User u ON sa.user_id = u.user_id\n"
                + "INNER JOIN TimeSlot ts ON a.slot_id = ts.slot_id\n"
                + "LEFT JOIN ExaminationPackage ep ON a.package_id = ep.package_id\n"
                + "WHERE a.status = 'Waiting-Payment' AND p.phone = ?\n"
                + "ORDER BY a.appointment_date;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone); // Bind phone to the query
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
        String sql
                = "SELECT a.appointment_id, "
                + "       p.full_name       AS patientName, "
                + "       a.patient_phone, "
                + "       u_doc.full_name   AS doctorFullName, "
                + "       ts.name           AS slot, "
                + "       a.appointment_date, "
                + "       a.status, "
                + "       a.created_at, "
                + "       a.description, "
                + "       ep.name           AS examinationPackage "
                + "  FROM Appointment a "
                + "  JOIN Patient p          ON a.patient_phone = p.phone "
                + "  JOIN TimeSlot ts        ON a.slot_id        = ts.slot_id "
                + "  JOIN ExaminationPackage ep ON a.package_id   = ep.package_id "
                + "  JOIN StaffAccount sa    ON a.doctor_id      = sa.staff_id "
                + "  JOIN `User` u_doc       ON sa.user_id        = u_doc.user_id "
                + " WHERE a.appointment_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AppointmentDetailDTO(
                            rs.getInt("appointment_id"),
                            rs.getString("patientName"), // now available
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
            System.err.println("getAppointmentDetailById error: " + e.getMessage());
        }
        return null;
    }

    public Integer getNextAppointmentIdForCurrentSlot(int doctorId) {
        String sql
                = "SELECT a.appointment_id, "
                + "       a.patient_phone, "
                + "       p.full_name       AS patientFullName, "
                + "       ts.name           AS slot, "
                + "       a.appointment_date, "
                + "       a.status, "
                + "       a.created_at "
                + "  FROM Appointment a "
                + "  JOIN Patient p    ON a.patient_phone = p.phone "
                + "  JOIN TimeSlot ts  ON a.slot_id        = ts.slot_id "
                + " WHERE a.doctor_id         = ? "
                + "   AND a.appointment_date  = CURDATE() "
                + "   AND a.status IN ('Back-from-hand-off','Pending','Waiting', 'In progress') "
                + " ORDER BY CASE a.status "
                + "            WHEN 'In progress'        THEN 1 "
                + "            WHEN 'Back-from-hand-off' THEN 2 "
                + "            WHEN 'Waiting'            THEN 3 "
                + "            WHEN 'Pending'            THEN 4 "
                + "            ELSE 5 END, "
                + "          a.created_at "
                + " LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("appointment_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("getNextAppointmentForCurrentSlot: " + e.getMessage());
        }

        return null;
    }

    public boolean updateAppointmentStatus(int appointmentId, String newStatus) {
        Boolean result = false;
        String sql = "UPDATE Appointment SET status = ? WHERE appointment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, appointmentId);
            result = ps.executeUpdate() == 1;
            return result;
        } catch (SQLException e) {
            System.err.println("updateAppointmentStatus: " + e.getMessage());
        }
        return result;
    }

    public List<AppointmentDTO> getAllAppointments(String patientNameFilter) {
        List<AppointmentDTO> list = new ArrayList<>();

        String sql = """
        SELECT @rownum := @rownum + 1 AS `index`,
               p.full_name       AS patientName,
               p.date_of_birth   AS patientDateOfBirth,
               a.appointment_date AS appointmentDate,
               ts.name           AS timeSlotName,
               u_doc.full_name   AS doctorFullName,
               a.status,
               a.appointment_id  AS appointmentId
          FROM (SELECT @rownum := 0) vars
          JOIN Patient p
            ON 1=1
          JOIN Appointment a
            ON a.patient_phone = p.phone
          JOIN TimeSlot ts
            ON a.slot_id = ts.slot_id
          JOIN StaffAccount sa
            ON a.doctor_id = sa.staff_id
          JOIN `User` u_doc
            ON sa.user_id = u_doc.user_id
         WHERE (? = '' OR LOWER(p.full_name) LIKE ?)
         ORDER BY a.appointment_date ASC, ts.start_time ASC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String f = (patientNameFilter == null ? "" : patientNameFilter.toLowerCase());
            ps.setString(1, f);
            ps.setString(2, "%" + f + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new AppointmentDTO(
                            rs.getInt("index"),
                            rs.getString("patientName"),
                            rs.getDate("patientDateOfBirth"),
                            rs.getDate("appointmentDate"),
                            rs.getString("timeSlotName"),
                            rs.getString("doctorFullName"),
                            rs.getString("status"),
                            rs.getInt("appointmentId")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("getAllAppointments: " + e.getMessage());
        }
        return list;
    }

    public boolean create(Appointment appt) {
        String sql = "INSERT INTO Appointment"
                + " (patient_phone, doctor_id, slot_id, appointment_date, status, created_at, description, package_id)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, appt.getPatientPhone());
            ps.setInt(2, appt.getDoctorId());
            ps.setInt(3, appt.getSlotId());
            ps.setDate(4, appt.getAppointmentDate());
            ps.setString(5, appt.getStatus());
            ps.setTimestamp(6, appt.getCreatedAt());
            ps.setString(7, appt.getDescription());
            if (appt.getPackageId() != null) {
                ps.setInt(8, appt.getPackageId());
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            int affected = ps.executeUpdate();
            if (affected == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        appt.setAppointmentId(keys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("create: " + e.getMessage());
        }
        return false;
    }

    public int countAppointments(Date startDate, Date endDate) {
        int totalAppointments = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS total_appointments FROM Appointment WHERE 1=1");

        if (startDate != null) {
            sql.append(" AND appointment_date >= ?");
        }
        if (endDate != null) {
            sql.append(" AND appointment_date <= ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (startDate != null) {
                ps.setDate(paramIndex++, startDate);
            }
            if (endDate != null) {
                ps.setDate(paramIndex++, endDate);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalAppointments = rs.getInt("total_appointments");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalAppointments;
    }

    public Map<Integer, Integer> getMonthlyAppointmentCounts(int year, Integer packageId, Integer doctorId) {
        Map<Integer, Integer> monthlyCounts = new HashMap<>();

        StringBuilder sql = new StringBuilder(
                "SELECT MONTH(appointment_date) AS month, COUNT(*) AS appointment_count "
                + "FROM Appointment "
                + "WHERE YEAR(appointment_date) = ? "
        );

        // Dynamically add filters if values are provided
        if (packageId != null && packageId != 0) {
            sql.append("AND package_id = ? ");
        }
        if (doctorId != null && doctorId != 0) {
            sql.append("AND doctor_id = ? ");
        }
        sql.append("GROUP BY MONTH(appointment_date) ORDER BY month ASC");

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            ps.setInt(paramIndex++, year);

            if (packageId != null && packageId != 0) {
                ps.setInt(paramIndex++, packageId);
            }
            if (doctorId != null && doctorId != 0) {
                ps.setInt(paramIndex++, doctorId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("month");
                    int count = rs.getInt("appointment_count");
                    monthlyCounts.put(month, count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ensure all months are present (even if zero)
        for (int i = 1; i <= 12; i++) {
            monthlyCounts.putIfAbsent(i, 0);
        }

        return monthlyCounts;
    }

    public PatientQueueDTO getTodayWaitingForPatient(String patientPhone) {
        String sql
                = "WITH Ordered AS ( "
                + "  SELECT "
                + "    a.appointment_id, "
                + "    sa.staff_id, u.full_name AS doctorName, "
                + "    ts.name AS slot, "
                + "    a.appointment_date, a.status, a.created_at, "
                + "    ROW_NUMBER() OVER ( "
                + "      ORDER BY "
                + "        CASE a.status "
                + "          WHEN 'In progress'        THEN 1 "
                + "          WHEN 'Back-from-hand-off' THEN 2 "
                + "          WHEN 'Waiting'            THEN 3 "
                + "          WHEN 'Pending'            THEN 4 "
                + "          ELSE 5 END, "
                + "        a.created_at "
                + "    ) AS queueNum "
                + "  FROM Appointment a "
                + "  JOIN StaffAccount sa ON a.doctor_id = sa.staff_id "
                + "  JOIN `User` u       ON sa.user_id   = u.user_id "
                + "  JOIN TimeSlot ts    ON a.slot_id    = ts.slot_id "
                + "  WHERE a.appointment_date = CURDATE() "
                + "    AND a.status = 'Waiting' "
                + ") "
                + "SELECT * FROM Ordered WHERE appointment_id = ( "
                + "  SELECT appointment_id "
                + "  FROM Appointment "
                + "  WHERE patient_phone = ? "
                + "    AND appointment_date = CURDATE() "
                + "    AND status = 'Waiting' "
                + "  LIMIT 1"
                + ")";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, patientPhone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PatientQueueDTO(
                            rs.getInt("appointment_id"),
                            rs.getString("doctorName"),
                            rs.getString("slot"),
                            rs.getDate("appointment_date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("queueNum")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("getTodayWaitingForPatient: " + e.getMessage());
        }
        return null;
    }
}
