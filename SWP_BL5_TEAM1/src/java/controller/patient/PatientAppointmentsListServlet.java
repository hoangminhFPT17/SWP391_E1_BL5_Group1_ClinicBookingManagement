/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.patient;

import dal.AppointmentDAO;
import dal.PatientDAO;
import dal.StaffAccountDAO;
import dal.TimeSlotDAO;
import dal.UserDAO;
import dto.AppointmentDTO;
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
import model.Appointment;
import model.Patient;
import model.StaffAccount;
import model.TimeSlot;
import model.User;
import util.DAOUtils;

/**
 *
 * @author LENOVO
 */
public class PatientAppointmentsListServlet extends HttpServlet {

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
            out.println("<title>Servlet GetPatientAppointmentsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetPatientAppointmentsServlet at " + request.getContextPath() + "</h1>");
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
        System.out.println("Appointment List doGet");
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        PatientDAO patientDAO = new PatientDAO();
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        UserDAO userDAO = new UserDAO();

        // Step 1: Get user from session
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }

        // Step 2: Use user's phone
        String phone = loggedInUser.getPhone();

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

        // Optional filters
        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");
        String slotIdParam = request.getParameter("slotId");
        Integer slotId = (slotIdParam != null && !slotIdParam.isEmpty()) ? Integer.parseInt(slotIdParam) : null;

        // Get filtered + paged appointments
        List<Appointment> appointments = appointmentDAO.searchAppointments(phone, keyword, status, slotId, offset, pageSize);

        // Get total count for pagination
        int totalAppointments = appointmentDAO.countAppointments(phone, keyword, status, slotId);
        int totalPages = (int) Math.ceil((double) totalAppointments / pageSize);

        List<AppointmentDTO> dtoList = new ArrayList<>();
        Patient patient = patientDAO.getPatientByPhone(phone);

        int index = offset + 1;
        for (Appointment appt : appointments) {
            AppointmentDTO dto = new AppointmentDTO();
            dto.setIndex(index++);
            dto.setAppointmentId(appt.getAppointmentId());
            dto.setAppointmentDate(appt.getAppointmentDate());
            dto.setStatus(appt.getStatus());
            dto.setDescription(appt.getDescription());

            if (patient != null) {
                dto.setPatientName(patient.getFullName());
                dto.setPatientDateOfBirth(patient.getDateOfBirth());
            }

            TimeSlot slot = timeSlotDAO.getTimeSlotById(appt.getSlotId());
            if (slot != null) {
                dto.setTimeSlotName(slot.getName() + ": " + slot.getStartTime() + " - " + slot.getEndTime());
                dto.setTimeSlotId(slot.getSlotId());
            }

            StaffAccount doctorAccount = staffAccountDAO.getStaffById(appt.getDoctorId());
            if (doctorAccount != null) {
                User doctorUser = userDAO.getUserById(doctorAccount.getUserId());
                if (doctorUser != null) {
                    dto.setDoctorFullName(doctorUser.getFullName());
                }
            }

            dtoList.add(dto);
        }

        // Time slot options
        List<TimeSlot> timeSlots = timeSlotDAO.getAllTimeSlots();
        request.setAttribute("timeSlots", timeSlots);
        
        // Pass data to JSP
        request.setAttribute("patient", patient);
        request.setAttribute("appointments", dtoList);
        request.setAttribute("totalAppointments", totalAppointments);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize); // Pass this to retain selected dropdown
        request.setAttribute("phone", phone);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);
        request.setAttribute("slotId", slotId);
        
        DAOUtils.disconnectAll(appointmentDAO, patientDAO,staffAccountDAO, timeSlotDAO, userDAO );

        request.getRequestDispatcher("/patient/patientAppointmentList.jsp").forward(request, response);
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
        doGet(request, response);
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
