/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author JackGarland
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;

public class InvoiceDAO extends DBContext {

    public void insert(Invoice invoice) {
        String sql = "INSERT INTO `swp_clinic`.`invoice`\n"
                + "(`invoice_id`,\n"
                + "`patient_phone`,\n"
                + "`appointment_id`,\n"
                + "`payment_method`,\n"
                + "`generate_date`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoice.getInvoiceId());
            ps.setString(2, invoice.getPatientPhone());
            ps.setInt(3, invoice.getAppointmentId());
            ps.setString(4, "VNPay");
            ps.setDate(5, invoice.getGeneratedDate());
            ps.setString(6, "Processing");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Invoice getById(int id) {
        String sql = "SELECT * FROM invoice WHERE invoice_id = ?";
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

    public List<Invoice> getByPatientPhone(String phone) {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM invoice WHERE patient_phone = ?";
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

//    public void update(Invoice invoice) {
//        String sql = "UPDATE `swp_clinic`.`invoice`\n"
//                + "SET\n"
//                + "`appointment_id` = <{appointment_id: }>,\n"
//                + "`payment_method` = <{payment_method: }>,\n"
//                + "`generate_date` = <{generate_date: }>,\n"
//                + "`status` = <{status: Processing}>\n"
//                + "WHERE `invoice_id` = <{expr}>;";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, appointment.getPatientPhone());
//            ps.setInt(2, appointment.getDoctorId());
//            ps.setInt(3, appointment.getSlotId());
//            ps.setDate(4, appointment.getAppointmentDate());
//            ps.setString(5, appointment.getStatus());
//            ps.setInt(6, appointment.getAppointmentId());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void delete(int id) {
        String sql = "DELETE FROM invoice WHERE invoice_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> getAll() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM invoice";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//    public List<Appointment> searchAppointments(String phone, String keyword, String status, Integer timeSlotId, int offset, int limit) {
//        List<Appointment> list = new ArrayList<>();
//        StringBuilder sql = new StringBuilder("SELECT a.* FROM Appointment a ");
//        sql.append("JOIN StaffAccount sa ON a.doctor_id = sa.staff_id ");
//        sql.append("JOIN `User` u ON sa.user_id = u.user_id ");
//        sql.append("WHERE a.patient_phone = ? ");
//
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            sql.append("AND u.full_name LIKE ? ");
//        }
//
//        if (status != null && !status.trim().isEmpty()) {
//            sql.append("AND a.status = ? ");
//        }
//
//        if (timeSlotId != null) {
//            sql.append("AND a.slot_id = ? ");
//        }
//
//        sql.append("ORDER BY a.appointment_date DESC ");
//        sql.append("LIMIT ? OFFSET ?");
//
//        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
//            int paramIndex = 1;
//            ps.setString(paramIndex++, phone);
//
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
//            }
//
//            if (status != null && !status.trim().isEmpty()) {
//                ps.setString(paramIndex++, status.trim());
//            }
//
//            if (timeSlotId != null) {
//                ps.setInt(paramIndex++, timeSlotId);
//            }
//
//            ps.setInt(paramIndex++, limit);
//            ps.setInt(paramIndex++, offset);
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                list.add(extractAppointment(rs));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//    public int countAppointments(String phone, String keyword, String status, Integer timeSlotId) {
//        int total = 0;
//        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Appointment a ");
//        sql.append("JOIN StaffAccount sa ON a.doctor_id = sa.staff_id ");
//        sql.append("JOIN `User` u ON sa.user_id = u.user_id ");
//        sql.append("WHERE a.patient_phone = ? ");
//
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            sql.append("AND u.full_name LIKE ? ");
//        }
//
//        if (status != null && !status.trim().isEmpty()) {
//            sql.append("AND a.status = ? ");
//        }
//
//        if (timeSlotId != null) {
//            sql.append("AND a.slot_id = ? ");
//        }
//
//        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
//            int paramIndex = 1;
//            ps.setString(paramIndex++, phone);
//
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
//            }
//
//            if (status != null && !status.trim().isEmpty()) {
//                ps.setString(paramIndex++, status.trim());
//            }
//
//            if (timeSlotId != null) {
//                ps.setInt(paramIndex++, timeSlotId);
//            }
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                total = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return total;
//    }
    public double getTotalRevenue(Date startDate, Date endDate) {
        double totalRevenue = 0.0;
        StringBuilder sql = new StringBuilder(
                "SELECT SUM(ii.total_price) AS total_revenue "
                + "FROM InvoiceItem ii "
                + "JOIN Invoice i ON ii.invoice_id = i.invoice_id "
                + "WHERE i.status = 'Completed'"
        );

        if (startDate != null) {
            sql.append(" AND i.generate_date >= ?");
        }
        if (endDate != null) {
            sql.append(" AND i.generate_date <= ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (startDate != null) {
                ps.setTimestamp(paramIndex++, new Timestamp(startDate.getTime()));
            }
            if (endDate != null) {
                ps.setTimestamp(paramIndex++, new Timestamp(endDate.getTime()));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalRevenue = rs.getDouble("total_revenue");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    private Invoice extractAppointment(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getInt("invoice_id"),
                rs.getInt("appointment_id"),
                rs.getString("patient_phone"),
                rs.getString("payment_method"),
                rs.getString("status"));
    }
}
