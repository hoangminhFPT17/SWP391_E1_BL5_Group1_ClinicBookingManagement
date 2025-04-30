/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import dal.DoctorTimeSlotDAO;
import dal.DoctorUnavailabilityDAO;
import dal.StaffAccountDAO;
import dal.TimeSlotDAO;
import dto.DoctorUnavailabilityDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.DoctorUnavailability;
import model.StaffAccount;
import model.TimeSlot;
import model.User;
import util.DAOUtils;

/**
 *
 * @author LENOVO
 */
public class DoctorTimeSlotListServlet extends HttpServlet {

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
            out.println("<title>Servlet DoctorTimeSlotList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorTimeSlotList at " + request.getContextPath() + "</h1>");
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

        //DAos
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        DoctorTimeSlotDAO doctorTimeSlotDAO = new DoctorTimeSlotDAO();
        DoctorUnavailabilityDAO doctorUnavailabilityDAO = new DoctorUnavailabilityDAO();

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
            request.setAttribute("errorMessage", "Access denied. Doctor role required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        List<TimeSlot> timeSlots = timeSlotDAO.getAllTimeSlots();
        Map<String, Set<Integer>> doctorTimeSlotMap = doctorTimeSlotDAO.getSlotMapByDoctorId(staffAccount.getStaffId());

        List<DoctorUnavailability> unavailabilities = doctorUnavailabilityDAO.getUnavailabilitiesByStaffId(staffAccount.getStaffId());
        List<DoctorUnavailabilityDTO> unavailabilityDTOs = new ArrayList<>();

        int index = 1;
        for (DoctorUnavailability du : unavailabilities) {
            TimeSlot slot = timeSlotDAO.getTimeSlotById(du.getSlotId());
            String slotNameWithTime = "";
            if (slot != null) {
                slotNameWithTime = slot.getName() + ": " + slot.getStartTime() + "-" + slot.getEndTime();
            }

            DoctorUnavailabilityDTO dto = new DoctorUnavailabilityDTO(
                    du.getUnavailabilityId(),
                    index++,
                    slotNameWithTime,
                    du.getUnavailableDate(),
                    du.getReason()
            );

            unavailabilityDTOs.add(dto);
        }
        String errorMessage = request.getParameter("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
            request.setAttribute("errorMessage", errorMessage);
        }

        request.setAttribute("unavailabilities", unavailabilityDTOs);
        request.setAttribute("timeSlotList", timeSlots);
        request.setAttribute("doctorSlotMap", doctorTimeSlotMap);
        request.setAttribute("staffAccount", staffAccount);
        
        DAOUtils.disconnectAll(staffAccountDAO, timeSlotDAO, doctorTimeSlotDAO, doctorUnavailabilityDAO);

        request.getRequestDispatcher("/doctor/doctorTimeSlotList.jsp").forward(request, response);
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
