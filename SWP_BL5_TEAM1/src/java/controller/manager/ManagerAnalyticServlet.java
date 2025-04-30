/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.AppointmentDAO;
import dal.InvoiceDAO;
import dal.PatientDAO;
import dal.StaffAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import model.StaffAccount;
import model.User;
import util.DAOUtils;

/**
 *
 * @author LENOVO
 */
public class ManagerAnalyticServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerAnalyticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerAnalyticServlet at " + request.getContextPath() + "</h1>");
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
        // 1. Get logged-in user from session
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null) {
            // User not logged in, redirect to login
            response.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }

        //DAOs
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        PatientDAO patientDAO = new PatientDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();

        // 2. Check if user has a StaffAccount
        StaffAccount staffAccount = staffAccountDAO.getStaffByUserId(loggedInUser.getUserId());
        if (staffAccount == null) {
            // User is not a staff member, redirect to login
            response.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }

        // 3. Check if StaffAccount role is "Manager"
        if (!"Manager".equalsIgnoreCase(staffAccount.getRole())) {
            // User is a staff, but not a Manager, forward to error.jsp
            request.setAttribute("errorMessage", "Access denied. Manager role required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }   

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        Date startDate = null;
        Date endDate = null;

        if (startDateStr != null && !startDateStr.isEmpty()) {
            try {
                startDate = Date.valueOf(startDateStr); // format: yyyy-MM-dd
            } catch (IllegalArgumentException e) {
                // Handle invalid format if needed
                e.printStackTrace();
            }
        }

        if (endDateStr != null && !endDateStr.isEmpty()) {
            try {
                endDate = Date.valueOf(endDateStr);
            } catch (IllegalArgumentException e) {
                // Handle invalid format if needed
                e.printStackTrace();
            }
        }

        //Data for info box, handle start date and end date
        int patientCount = patientDAO.countPatients(startDate, endDate);
        request.setAttribute("patientCount", patientCount);

        double totalRevenue = invoiceDAO.getTotalRevenue(startDate, endDate);
        request.setAttribute("totalRevenue", totalRevenue);

        int appointmentCount = appointmentDAO.countAppointments(startDate, endDate);
        request.setAttribute("appointmentCount", appointmentCount);

        // Data for column chart
        List<Map<String, Object>> appointmentCountByDoctor = appointmentDAO.getAppointmentCountsByDoctor(startDate, endDate);
        for (Map<String, Object> data : appointmentCountByDoctor) {
            System.out.println("Doctor: " + data.get("doctor_name") + ", Appointments: " + data.get("appointment_count"));
        }
        request.setAttribute("appointmentCountByDoctor", appointmentCountByDoctor);

        // Data for pie chart
        Map<String, Integer> timeSlotBookedCountMap = appointmentDAO.countAppointmentsByTimeSlot(startDate, endDate);
        request.setAttribute("timeSlotBookedCountMap", timeSlotBookedCountMap);

        // Data for bar chart
        Map<String, Integer> demographics = patientDAO.getPatientDemographics(startDate, endDate);
        request.setAttribute("demographics", demographics);
        
        DAOUtils.disconnectAll(appointmentDAO, patientDAO, invoiceDAO, staffAccountDAO);

        request.getRequestDispatcher("/manager/managerAnalytic.jsp").forward(request, response);
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
        processRequest(request, response);
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
