/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.patient;

import dal.AppointmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import model.Appointment;

/**
 *
 * @author LENOVO
 */
public class DeleteAppointmentServlet extends HttpServlet {

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
            out.println("<title>Servlet DeleteAppointmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteAppointmentServlet at " + request.getContextPath() + "</h1>");
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

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing appointment ID.");
            return;
        }

        try {
            int appointmentId = Integer.parseInt(idParam);
            AppointmentDAO dao = new AppointmentDAO();
            Appointment appointment = dao.getById(appointmentId);

            if (appointment == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found.");
                return;
            }

            if (!"Pending".equalsIgnoreCase(appointment.getStatus())) {
                request.setAttribute("error", "Only pending appointments can be canceled.");
                // If needed, you can forward to a proper error page here
                response.sendRedirect("PatientAppointmentsListServlet?error=Only%20pending%20appointments%20can%20be%20canceled");
                return;
            }

            dao.delete(appointmentId);

            String referer = request.getHeader("referer");
            String successMessage = "Appointment canceled successfully";
            String encodedMessage = URLEncoder.encode(successMessage, "UTF-8");

            if (referer != null) {
                String redirectUrl = referer.contains("message=") ? referer : (referer.contains("?") ? referer + "&message=" + encodedMessage : referer + "?message=" + encodedMessage);
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("PatientAppointmentsListServlet?message=" + encodedMessage);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid appointment ID.");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
