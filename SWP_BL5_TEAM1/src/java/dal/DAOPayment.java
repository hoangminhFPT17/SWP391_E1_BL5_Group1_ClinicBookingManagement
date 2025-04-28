/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author JackGarland
 */
import model.Payment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;

/**
 * @author minht
 */
public class DAOPayment extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(DAOPayment.class.getName());

    public DAOPayment() {
        super(); // Gọi constructor của DBconnectionect để khởi tạo kết nối
    }

    public int getLatestpayment_id(int userID) {
        String sql = "SELECT TOP 1 payment_id FROM payment WHERE user_id = ? ORDER BY payment_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("payment_id");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting latest payment_id for userID: " + userID, ex);
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    // Thêm một Payment mới và trả về payment_id
    public int insertPayment(Payment payment) {
        String sql = "INSERT INTO payment (user_id, amount, payment_date, appointment_id, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getUserId());
            ps.setDouble(2, payment.getAmount());
            ps.setTimestamp(3, payment.getPaymentDate());
            ps.setInt(4, payment.getAppointmentId());
            ps.setString(5, payment.getStatus());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Inserting payment failed, no rows affected.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                } else {
                    throw new SQLException("Inserting payment failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            // Log or handle exception
            e.printStackTrace();
        }
        return generatedId;
    }

    // Lấy lịch sử thanh toán theo UserID (cho user)
    public List<Payment> getPaymentsByUserId(int userID) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT p.* FROM payment p WHERE p.user_id = ? ORDER BY p.payment_date DESC";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, userID);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    Payment payment = extractPaymentFromResultSet(rs);
                    payments.add(payment);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting payments for userID: " + userID, ex);
        }
        return payments;
    }

    public List<Payment> getPayments2() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT p.* FROM payment p "
                + "WHERE (p.status = 'Processing' OR p.status = 'Completed') "
                + "ORDER BY p.payment_date DESC";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    Payment payment = extractPaymentFromResultSet(rs);
                    payments.add(payment);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting payments for userID: ", ex);
        }
        return payments;
    }

//    // Lấy toàn bộ lịch sử thanh toán (cho admin)
//    public List<Payment> getAllPayments() {
//        List<Payment> payments = new ArrayList<>();
//        String sql = "SELECT payment_id, Payment.BookingID, FullName, Email, Price, PaymentDate, Payment.Status, PromotionID "
//                + "FROM dbo.Payment "
//                + "JOIN dbo.Users ON Users.UserID = Payment.UserID "
//                + "LEFT JOIN dbo.Booking ON Booking.BookingID = Payment.BookingID "
//                + "LEFT JOIN dbo.Tutor ON Tutor.TutorID = Booking.TutorID "
//                + "ORDER BY PaymentDate DESC";
//        try (PreparedStatement pre = connection.prepareStatement(sql)) {
//            try (ResultSet rs = pre.executeQuery()) {
//                while (rs.next()) {
//                    Payment payment = new Payment();
//                    payment.setpayment_id(rs.getInt("payment_id"));
//                    payment.setBookingID(rs.getInt("BookingID"));
//                    if (rs.wasNull()) {
//                        payment.setBookingID(0);
//                    }
//                    payment.setPaymentDate(rs.getTimestamp("PaymentDate"));
//                    payment.setStatus(rs.getString("Status"));
//                    payment.setUserName(rs.getString("FullName"));
//                    payment.setEmail(rs.getString("Email"));
//                    payment.setAmount(rs.getDouble("Price"));
//                    payment.setPromotionID(rs.getInt("PromotionID"));
//                    if (rs.wasNull()) {
//                        payment.setPromotionID(0);
//                    }
//                    payments.add(payment);
//                }
//            }
//        } catch (SQLException ex) {
//            LOGGER.log(Level.SEVERE, "Error getting all payments", ex);
//        }
//        return payments;
//    }
//    // Lấy danh sách Payment theo trang (phân trang cho admin)
//    public List<Payment> getPaymentsByPageForAdmin(int page, int pageSize) {
//        List<Payment> payments = new ArrayList<>();
//        int offset = (page - 1) * pageSize;
//        String sql = "SELECT payment_id, Payment.BookingID, FullName, Email, Price, PaymentDate, Payment.Status, PromotionID "
//                + "FROM dbo.Payment "
//                + "JOIN dbo.Users ON Users.UserID = Payment.UserID "
//                + "LEFT JOIN dbo.Booking ON Booking.BookingID = Payment.BookingID "
//                + "LEFT JOIN dbo.Tutor ON Tutor.TutorID = Booking.TutorID "
//                + "ORDER BY PaymentDate DESC "
//                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//        try (PreparedStatement pre = connection.prepareStatement(sql)) {
//            pre.setInt(1, offset);
//            pre.setInt(2, pageSize);
//            try (ResultSet rs = pre.executeQuery()) {
//                while (rs.next()) {
//                    Payment payment = new Payment();
//                    payment.setpayment_id(rs.getInt("payment_id"));
//                    payment.setBookingID(rs.getInt("BookingID"));
//                    if (rs.wasNull()) {
//                        payment.setBookingID(0);
//                    }
//                    payment.setPaymentDate(rs.getTimestamp("PaymentDate"));
//                    payment.setStatus(rs.getString("Status"));
//                    payment.setUserName(rs.getString("FullName"));
//                    payment.setEmail(rs.getString("Email"));
//                    payment.setAmount(rs.getDouble("Price"));
//                    payment.setPromotionID(rs.getInt("PromotionID"));
//                    if (rs.wasNull()) {
//                        payment.setPromotionID(0);
//                    }
//                    payments.add(payment);
//                }
//            }
//        } catch (SQLException ex) {
//            LOGGER.log(Level.SEVERE, "Error getting payments by page for admin: page=" + page + ", pageSize=" + pageSize, ex);
//        }
//        return payments;
//    }
    // Cập nhật trạng thái của Payment
    public boolean updatePaymentStatus(Payment payment) {
        if (payment == null || payment.getStatus() == null || payment.getStatus().trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid input for updating payment status: payment_id=" + (payment != null ? payment.getPaymentId() : "null"));
            return false;
        }

        String sql = "UPDATE Payment SET Status = ? WHERE payment_id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, payment.getStatus());
            pre.setInt(2, payment.getPaymentId());
            int rowsAffected = pre.executeUpdate();
            LOGGER.log(Level.INFO, "Updated payment status: payment_id={0}, Status={1}, RowsAffected={2}",
                    new Object[]{payment.getPaymentId(), payment.getStatus(), rowsAffected});
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating payment status: payment_id=" + payment.getPaymentId(), ex);
            return false;
        }
    }

//    // Cập nhật BookingID của Payment
//    public boolean updatePaymentBookingId(Payment payment) {
//        String sql = "UPDATE Payment SET BookingID = ? WHERE payment_id = ?";
//        try (PreparedStatement pre = connection.prepareStatement(sql)) {
//            if (payment.getBookingID() != 0) {
//                pre.setInt(1, payment.getBookingID());
//            } else {
//                pre.setNull(1, java.sql.Types.INTEGER);
//            }
//            pre.setInt(2, payment.getpayment_id());
//            int rowsAffected = pre.executeUpdate();
//            LOGGER.log(Level.INFO, "Updated payment BookingID: payment_id={0}, BookingID={1}, RowsAffected={2}",
//                    new Object[]{payment.getpayment_id(), payment.getBookingID(), rowsAffected});
//            return rowsAffected > 0;
//        } catch (SQLException ex) {
//            LOGGER.log(Level.SEVERE, "Error updating payment BookingID: payment_id=" + payment.getpayment_id(), ex);
//            return false;
//        }
//    }
    // Lấy Payment theo payment_id
    public Payment getPaymentById(int payment_id) {
        String sql = "SELECT * FROM Payment WHERE payment_id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, payment_id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting payment by payment_id: " + payment_id, ex);
        }
        return null;
    }

//    // Lấy Payment theo BookingID
//    public Payment getPaymentByBookingId(int bookingID) {
//        String sql = "SELECT * FROM Payment WHERE BookingID = ? ORDER BY PaymentDate DESC";
//        try (PreparedStatement pre = connection.prepareStatement(sql)) {
//            pre.setInt(1, bookingID);
//            try (ResultSet rs = pre.executeQuery()) {
//                if (rs.next()) {
//                    return extractPaymentFromResultSet(rs);
//                }
//            }
//        } catch (SQLException ex) {
//            LOGGER.log(Level.SEVERE, "Error getting payment by BookingID: " + bookingID, ex);
//        }
//        return null;
//    }
    // Xóa Payment với trạng thái Processing
    public boolean deletePayment(int payment_id) {
        String sql = "DELETE FROM Payment WHERE payment_id = ? AND Status = 'Processing'";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, payment_id);
            int rowsAffected = pre.executeUpdate();
            LOGGER.log(Level.INFO, "Deleted payment: payment_id={0}, RowsAffected={1}",
                    new Object[]{payment_id, rowsAffected});
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error deleting payment: payment_id=" + payment_id, ex);
            return false;
        }
    }

//    // Lấy danh sách Payment theo trang (phân trang)
//    public List<Payment> getPaymentsByPage(int page, int pageSize) {
//        List<Payment> paymentList = new ArrayList<>();
//        int offset = (page - 1) * pageSize;
//        String sql = "SELECT * FROM Payment ORDER BY PaymentDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//        try (PreparedStatement pre = connection.prepareStatement(sql)) {
//            pre.setInt(1, offset);
//            pre.setInt(2, pageSize);
//            try (ResultSet rs = pre.executeQuery()) {
//                while (rs.next()) {
//                    Payment payment = extractPaymentFromResultSet(rs);
//                    paymentList.add(payment);
//                }
//            }
//        } catch (SQLException ex) {
//            LOGGER.log(Level.SEVERE, "Error getting payments by page: page=" + page + ", pageSize=" + pageSize, ex);
//        }
//        return paymentList;
//    }
    // Lấy tổng số Payment để tính số trang
    public int getTotalPayments() {
        String sql = "SELECT COUNT(*) FROM Payment";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting total payments", ex);
        }
        return 0;
    }

    private Payment extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        Payment payment = new Payment();

        // payment_id (PK)
        payment.setPaymentId(rs.getInt("payment_id"));

        // user_id (nullable)
        int uid = rs.getInt("user_id");
        if (rs.wasNull()) {
            payment.setUserId(null);
        } else {
            payment.setUserId(uid);
        }

        // amount (double)
        payment.setAmount(rs.getDouble("amount"));

        // payment_date (Timestamp, nullable)
        Timestamp ts = rs.getTimestamp("payment_date");
        payment.setPaymentDate(ts);

        // appointment_id (nullable)
        int apptId = rs.getInt("appointment_id");
        if (rs.wasNull()) {
            payment.setAppointmentId(null);
        } else {
            payment.setAppointmentId(apptId);
        }

        // status (String)
        payment.setStatus(rs.getString("status"));

        return payment;
    }

//    // Test method
//    public static void main(String[] args) {
//        DAOPayment dao = new DAOPayment();
//        Payment payment = new Payment();
//        payment.setUserID(5);
//        payment.setAmount(23232);
//        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
//        payment.setPaymentMethod("VNPAY");
//        payment.setSubjectID(2);
//        payment.setBookingID(0);
//        payment.setStatus("Processing");
//
//        int payment_id = dao.insertPayment(payment);
//        if (payment_id > 0) {
//            System.out.println("Chèn Payment thành công: payment_id=" + payment_id);
//        } else {
//            System.out.println("Chèn Payment thất bại");
//        }
//
//        System.out.println(payment_id);
//    }
//    // Lấy tổng Amount từ Payment với trạng thái Completed
//    public double getTotalProfit() throws SQLException {
//        double totalProfit = 0.0;
//        String sql = "SELECT SUM(Amount) AS TotalProfit FROM Payment WHERE Status = 'Completed'";
//        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                totalProfit = rs.getDouble("TotalProfit");
//            }
//        }
//        return totalProfit;
//    }
//    // Lấy 5 giao dịch gần nhất
//    public List<Payment> getRecentPayments() throws SQLException {
//        List<Payment> payments = new ArrayList<>();
//        String sql = "SELECT TOP 5 payment_id, BookingID, UserID, Amount, PaymentMethod, PaymentDate, PromotionID, SubjectID, Status "
//                + "FROM Payment "
//                + "ORDER BY PaymentDate DESC";
//        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Payment payment = new Payment();
//                payment.setpayment_id(rs.getInt("payment_id"));
//                payment.setBookingID(rs.getInt("BookingID"));
//                payment.setUserID(rs.getInt("UserID"));
//                payment.setAmount(rs.getDouble("Amount"));
//                payment.setPaymentMethod(rs.getString("PaymentMethod"));
//                payment.setPaymentDate(rs.getTimestamp("PaymentDate"));
//                payment.setPromotionID(rs.getInt("PromotionID"));
//                payment.setSubjectID(rs.getInt("SubjectID"));
//                payment.setStatus(rs.getString("Status"));
//                payments.add(payment);
//            }
//        }
//        return payments;
//    }
}
