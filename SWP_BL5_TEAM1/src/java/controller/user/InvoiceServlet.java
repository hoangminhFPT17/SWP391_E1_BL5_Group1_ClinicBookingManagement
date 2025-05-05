/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.AppointmentDAO;
import dal.InvoiceDAO;
import dal.InvoiceDetailedDAO;
import dal.InvoiceItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import model.Invoice;
import model.InvoiceDetailed;
import model.InvoiceItem;

/**
 *
 * @author JackGarland
 */
public class InvoiceServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InvoiceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvoiceServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Read all parameters
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            int invoiceId = Integer.parseInt(request.getParameter("invoiceNumber"));
            String address = request.getParameter("address");
            String patientName = request.getParameter("patientName");
            String patientPhone = request.getParameter("patientPhone");
            String packageName = request.getParameter("packageName");
            String doctorName = request.getParameter("doctorName");

            String issueDateStr = request.getParameter("issueDate");
            String dueDateStr = request.getParameter("dueDate");

            java.sql.Date issueDate = null;
            java.sql.Date dueDate = null;

            if (issueDateStr != null && !issueDateStr.isEmpty()) {
                issueDate = java.sql.Date.valueOf(issueDateStr);
            }
            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                dueDate = java.sql.Date.valueOf(dueDateStr);
            }

            String item1Description = request.getParameter("item1Description");
            int item1Rate = Integer.parseInt(request.getParameter("item1Rate"));
            String item2Description = request.getParameter("item2Description");
            int item2Rate = Integer.parseInt(request.getParameter("item2Rate"));
            String item3Description = request.getParameter("item3Description");
            int item3Rate = Integer.parseInt(request.getParameter("item3Rate"));

            Invoice basicInvoice = new Invoice(
                    invoiceId,
                    appointmentId,
                    patientPhone,
                    "VNPay",
                    "Processing"
            );

            InvoiceDAO basicInvoiceDao = new InvoiceDAO();
            basicInvoiceDao.insert(basicInvoice);
            
            InvoiceItemDAO itemDAO = new InvoiceItemDAO();
            itemDAO.addInvoiceItem(new InvoiceItem(invoiceId, item1Description, 1, item1Rate));
            itemDAO.addInvoiceItem(new InvoiceItem(invoiceId, item2Description, 1, item2Rate));
            itemDAO.addInvoiceItem(new InvoiceItem(invoiceId, item3Description, 1, item3Rate));
            
            // Insert invoice details
            InvoiceDetailed invoice = new InvoiceDetailed(
                    invoiceId,
                    appointmentId,
                    address,
                    patientName,
                    patientPhone,
                    packageName,
                    doctorName,
                    issueDate,
                    dueDate,
                    item1Description, item1Rate,
                    item2Description, item2Rate,
                    item3Description, item3Rate
            );
            InvoiceDetailedDAO invoiceDAO = new InvoiceDetailedDAO();
            invoiceDAO.insertInvoice(invoice);

            // Update appointment status
            AppointmentDAO a_dao = new AppointmentDAO();
            a_dao.updatePaymentToWaitingPayment(appointmentId);

            response.sendRedirect("ReceptionAppointmentsListServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error inserting invoice: " + e.getMessage());
            request.getRequestDispatcher("invoice_error.jsp").forward(request, response);
        }
    }

}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
