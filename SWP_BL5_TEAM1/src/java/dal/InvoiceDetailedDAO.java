/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author JackGarland
 */
import model.InvoiceDetailed;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Invoice operations: insert and fetch by patient phone.
 */
public class InvoiceDetailedDAO extends DBContext {

    /**
     * Inserts a new Invoice into the database. The invoiceId will be set on the
     * passed object.
     */
    public void insertInvoice(InvoiceDetailed invoice) throws SQLException {
        String sql = "INSERT INTO `swp_clinic`.`invoices`\n"
                + "(`invoice_id`,\n"
                + "`appointment_id`,\n"
                + "`address`,\n"
                + "`patient_name`,\n"
                + "`patient_phone`,\n"
                + "`package`,\n"
                + "`doctor_name`,\n"
                + "`issue_date`,\n"
                + "`due_date`,\n"
                + "`item1_description`,\n"
                + "`item1_rate`,\n"
                + "`item2_description`,\n"
                + "`item2_rate`,\n"
                + "`item3_description`,\n"
                + "`item3_rate`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, invoice.getInvoiceId());
            ps.setInt(2, invoice.getAppointmentId());
            ps.setString(3, invoice.getAddress());
            ps.setString(4, invoice.getPatientName());
            ps.setString(5, invoice.getPatientPhone());
            ps.setString(6, invoice.getPackageName());
            ps.setString(7, invoice.getDoctorName());
            ps.setDate(8, invoice.getIssueDate());
            ps.setDate(9, invoice.getDueDate());
            ps.setString(10, invoice.getItem1Description());
            ps.setInt(11, invoice.getItem1Rate());
            ps.setString(12, invoice.getItem2Description());
            ps.setInt(13, invoice.getItem2Rate());
            ps.setString(14, invoice.getItem3Description());
            ps.setInt(15, invoice.getItem3Rate());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Creating invoice failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    invoice.setInvoiceId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating invoice failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Retrieves a list of Invoices for the given patient phone, ordered by
     * invoiceId ascending.
     */
    public List<InvoiceDetailed> getInvoicesByPatientPhone(String patientPhone) throws SQLException {
        String sql = "SELECT * FROM invoices WHERE patient_phone = ? ORDER BY invoice_id ASC";
        List<InvoiceDetailed> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, patientPhone);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InvoiceDetailed inv = mapRowToInvoice(rs);
                    list.add(inv);
                }
            }
        }
        return list;
    }

    /**
     * Helper to map a ResultSet row to an Invoice object.
     */
    private InvoiceDetailed mapRowToInvoice(ResultSet rs) throws SQLException {
        InvoiceDetailed inv = new InvoiceDetailed();
        inv.setInvoiceId(rs.getInt("invoice_id"));
        inv.setAppointmentId(rs.getInt("appointment_id"));
        inv.setAddress(rs.getString("address"));
        inv.setPatientName(rs.getString("patient_name"));
        inv.setPatientPhone(rs.getString("patient_phone"));
        inv.setDoctorName(rs.getString("doctor_name"));
        inv.setIssueDate(rs.getDate("issue_date"));
        inv.setDueDate(rs.getDate("due_date"));
        inv.setItem1Description(rs.getString("item1_description"));
        inv.setItem1Rate(rs.getInt("item1_rate"));
        inv.setItem2Description(rs.getString("item2_description"));
        inv.setItem2Rate(rs.getInt("item2_rate"));
        inv.setItem3Description(rs.getString("item3_description"));
        inv.setItem3Rate(rs.getInt("item3_rate"));
        return inv;
    }
}
