/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.patient;

/**
 *
 * @author JackGarland
 */
import controller.patient.Config;
import dal.AppointmentDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.DAOPayment;
import dal.InvoiceDAO;
import model.Appointment;
import model.Invoice;
import model.Payment;
import model.User;

public class VnpayReturnBooking extends HttpServlet {

    DAOPayment dao = new DAOPayment();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");

        AppointmentDAO a_dao = new AppointmentDAO();
        InvoiceDAO i_dao = new InvoiceDAO();

        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");
            // Tách paymentID từ vnp_TxnRef (có dạng paymentID_timestamp)
            String[] txnRefParts = vnp_TxnRef.split("_");
            if (txnRefParts.length < 1) {
                response.sendRedirect("paymentResult.jsp?error=Invalid transaction reference");
                return;
            }
            int paymentID;
            try {
                paymentID = Integer.parseInt(txnRefParts[0]);
            } catch (NumberFormatException e) {
                response.sendRedirect("paymentResult.jsp?error=Invalid payment ID");
                return;
            }

            Payment payment = new Payment();
            payment.setPaymentId(paymentID);
            boolean transSuccess = false;

            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                payment.setStatus("Completed");
                transSuccess = true;

                // Lấy thông tin từ session
                User user = (User) session.getAttribute("user");
                int studentID = user.getUserId();

                Appointment appointment = a_dao.getById((int) session.getAttribute("id1"));
                appointment.setStatus("Payment-Complete");
                a_dao.update(appointment);

            } else {
                payment.setStatus("Failed");
            }

            dao.updatePaymentStatus(payment);
            request.setAttribute("transResult", transSuccess);

            // Xóa dữ liệu trong session
            session.removeAttribute("totalBill");
            session.removeAttribute("paymentID");
            session.removeAttribute("id1");
            session.removeAttribute("id2");

            request.getRequestDispatcher("paymentResult.jsp").forward(request, response);
        } else {
            System.out.println("GD KO HOP LE (invalid signature)");
            response.sendRedirect("paymentResult.jsp?error=Invalid signature");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
