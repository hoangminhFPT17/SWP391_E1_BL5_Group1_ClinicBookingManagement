/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import com.fasterxml.jackson.databind.ObjectMapper;
import dal.DoctorTimeSlotDAO;
import dal.StaffAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import model.StaffAccount;
import model.User;
import util.DAOUtils;

/**
 *
 * @author LENOVO
 */
public class UpdateDoctorTimeSlotServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateDoctorTimeSlotServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDoctorTimeSlotServlet at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Get logged-in user from session
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null) {
            // User not logged in, redirect to login
            response.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }

        //DAOs
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
        DoctorTimeSlotDAO doctorTimeSlotDAO = new DoctorTimeSlotDAO();

        // 2. Check if user has a StaffAccount
        StaffAccount staffAccount = staffAccountDAO.getStaffByUserId(loggedInUser.getUserId());
        if (staffAccount == null) {
            // User is not a staff member, redirect to login
            response.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }

        // 3. Check if StaffAccount role is "Doctor"
        if (!"Doctor".equalsIgnoreCase(staffAccount.getRole())) {
            // User is a staff, but not a Manager, forward to error.jsp
            request.setAttribute("errorMessage", "Access denied. Manager role required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        String dayOfWeek = request.getParameter("dayOfWeek");
        boolean isWorking = "1".equals(request.getParameter("isWorking"));
        
        int doctorId = staffAccount.getStaffId(); // replace with your actual way to get logged-in doctor
        int maxAppointments = 5; //Default max Appointments
        
        boolean success;
        if (isWorking) {
            success = doctorTimeSlotDAO.addDoctorToTimeSlot(doctorId, slotId, dayOfWeek, maxAppointments);
        } else {
            success = doctorTimeSlotDAO.removeDoctorFromTimeSlot(doctorId, slotId, dayOfWeek);
        }

        // Prepare a response map
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", success);
        responseMap.put("message", success ? "Schedule updated successfully." : "Failed to update schedule.");

        // Set JSON response type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        DAOUtils.disconnectAll(staffAccountDAO, doctorTimeSlotDAO);

        // Write JSON using Jackson
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), responseMap);
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
