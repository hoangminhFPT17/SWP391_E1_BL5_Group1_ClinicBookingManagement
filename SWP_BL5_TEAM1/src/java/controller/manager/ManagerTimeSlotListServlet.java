/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.DoctorTimeSlotDAO;
import dal.TimeSlotDAO;
import dto.AssignedDoctorDTO;
import dto.TimeSlotDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.TimeSlot;

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

        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        DoctorTimeSlotDAO doctorTimeSlotDAO = new DoctorTimeSlotDAO();
        
        //request.setAttribute("daysOfWeek", List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        String keyword = request.getParameter("keyword");
        String statusParam = request.getParameter("status");
        String selectedDay = request.getParameter("dayOfTheWeek"); // "Monday", "Tuesday"
        if (selectedDay == null || selectedDay.isEmpty()) {
            selectedDay = "Monday";
        }
        
        // Add selectedDay to request scope
        request.setAttribute("selectedDay", selectedDay);

        Boolean isActive = null;
        if (statusParam != null && !statusParam.trim().isEmpty()) {
            isActive = statusParam.equalsIgnoreCase("active");
        }

        // Parse page number
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page < 1) {
                page = 1;
            }
        } catch (NumberFormatException ignored) {
        }

        // Parse page size (limit)
        int pageSize = 5; // default value
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

        // Fetch filtered and paginated time slots
        List<TimeSlot> slots = timeSlotDAO.searchTimeSlots(keyword, isActive, offset, pageSize);

        // Get DTOs
        List<TimeSlotDTO> timeSlotdtos = new ArrayList<>();
        int index = offset + 1;
        for (TimeSlot slot : slots) {
            List<AssignedDoctorDTO> doctors = doctorTimeSlotDAO.getAssignedDoctorsBySlotIdAndDay(slot.getSlotId(), selectedDay);

            TimeSlotDTO dto = new TimeSlotDTO();
            dto.setIndex(index++);
            dto.setSlotId(slot.getSlotId());
            dto.setTimeSlotName(slot.getName());
            dto.setStartTime(slot.getStartTime());
            dto.setEndTime(slot.getEndTime());
            dto.setIsActive(slot.isIsActive());
            dto.setAssignedDoctors(doctors); // update the field in DTO to match

            timeSlotdtos.add(dto);
        }

        // Send to JSP
        request.setAttribute("timeSlotList", timeSlotdtos);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", statusParam);

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
