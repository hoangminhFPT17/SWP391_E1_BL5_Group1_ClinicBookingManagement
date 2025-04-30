/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import dal.DoctorTimeSlotDAO;
import dal.StaffAccountDAO;
import dal.TimeSlotDAO;
import dal.UserDAO;
import dto.AssignedDoctorDTO;
import dto.DoctorAssignDTO;
import dto.TimeSlotDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.StaffAccount;
import model.TimeSlot;
import model.User;
import util.DAOUtils;

/**
 *
 * @author LENOVO
 */
public class ManagerTimeSlotListServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerTimeSlotListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerTimeSlotListServlet at " + request.getContextPath() + "</h1>");
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
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        DoctorTimeSlotDAO doctorTimeSlotDAO = new DoctorTimeSlotDAO();
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

        // Get filters from parameters
        String keyword = request.getParameter("keyword");
        String statusParam = request.getParameter("status");
        String selectedDay = request.getParameter("dayOfTheWeek"); // e.g., "Monday", "Tuesday"
        if (selectedDay == null || selectedDay.isEmpty()) {
            selectedDay = "Monday"; // default day
        }
        request.setAttribute("selectedDay", selectedDay);

        Boolean isActive = null;
        if (statusParam != null && !statusParam.trim().isEmpty()) {
            // Expecting 'true' or 'false' as strings
            isActive = statusParam.equalsIgnoreCase("true");
        }

        // Parse pagination parameters
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page < 1) {
                page = 1;
            }
        } catch (NumberFormatException ignored) {
        }

        int pageSize = 5; // default page size
        try {
            String pageSizeParam = request.getParameter("pageSize");
            if (pageSizeParam != null && !pageSizeParam.isEmpty()) {
                pageSize = Integer.parseInt(pageSizeParam);
                if (pageSize <= 0) {
                    pageSize = 5;
                }
            }
        } catch (NumberFormatException ignored) {
        }

        int offset = (page - 1) * pageSize;

        // Get total number of matching records and compute total pages
        int totalRecords = timeSlotDAO.countTimeSlots(keyword, isActive);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Fetch paginated time slots by day of the week
        List<TimeSlot> slots = timeSlotDAO.searchTimeSlots(keyword, isActive, offset, pageSize, selectedDay);

        // Build DTOs for each TimeSlot; include assigned doctor info filtered by day
        List<TimeSlotDTO> timeSlotdtos = new ArrayList<>();
        int index = offset + 1;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, String> assignedDoctorsJsonMap = new HashMap<>();

        for (TimeSlot slot : slots) {
            List<AssignedDoctorDTO> doctors = doctorTimeSlotDAO.getAssignedDoctorsBySlotIdAndDay(slot.getSlotId(), selectedDay);

            TimeSlotDTO dto = new TimeSlotDTO();
            dto.setIndex(index++);
            dto.setSlotId(slot.getSlotId());
            dto.setTimeSlotName(slot.getName());
            dto.setStartTime(slot.getStartTime());
            dto.setEndTime(slot.getEndTime());
            dto.setIsActive(slot.isIsActive());
            dto.setAssignedDoctors(doctors);

            timeSlotdtos.add(dto);

            // Serialize assigned doctors into JSON
            String json = objectMapper.writeValueAsString(doctors);
            assignedDoctorsJsonMap.put(slot.getSlotId(), json);
        }

        List<DoctorAssignDTO> doctorAssignDTOs = staffAccountDAO.getAllDoctors();
        String doctorAssignDTOsJson = objectMapper.writeValueAsString(doctorAssignDTOs);
        request.setAttribute("doctorAssignDTOsJson", doctorAssignDTOsJson);

        // Pass data to JSP
        request.setAttribute("assignedDoctorsJsonMap", assignedDoctorsJsonMap);
        request.setAttribute("timeSlotList", timeSlotdtos);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", statusParam);
        
        DAOUtils.disconnectAll(timeSlotDAO, doctorTimeSlotDAO, staffAccountDAO);

        request.getRequestDispatcher("/manager/managerTimeSlotList.jsp").forward(request, response);
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
