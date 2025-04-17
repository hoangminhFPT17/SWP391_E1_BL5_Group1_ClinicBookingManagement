/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AppointmentDAO;
import dal.PatientDAO;
import dal.StaffAccountDAO;
import dal.TimeSlotDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Appointment;
import model.Patient;
import model.StaffAccount;
import model.TimeSlot;

/**
 *
 * @author LENOVO
 */
public class GetPatientAppointmentsServlet extends HttpServlet {

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

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        PatientDAO patientDAO = new PatientDAO();
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

        String phone = request.getParameter("phone");
        if (phone == null || phone.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Phone number is required.");
            return;
        }

        Patient patient = patientDAO.getByPhone(phone);
        if (patient == null) {
            request.setAttribute("error", "Patient not found.");
            request.getRequestDispatcher("/views/patient-appointments.jsp").forward(request, response);
            return;
        }

        List<Appointment> appointments = appointmentDAO.getByPatientPhone(phone);

        Map<Integer, StaffAccount> doctorMap = new HashMap<>();
        Map<Integer, TimeSlot> slotMap = new HashMap<>();

        for (Appointment appt : appointments) {
            int doctorId = appt.getDoctorId();
            int slotId = appt.getSlotId();

            if (!doctorMap.containsKey(doctorId)) {
                doctorMap.put(doctorId, staffAccountDAO.getStaffById(doctorId));
            }

            if (!slotMap.containsKey(slotId)) {
                slotMap.put(slotId, timeSlotDAO.getTimeSlotById(slotId));
            }
        }

        request.setAttribute("appointments", appointments);
        request.setAttribute("doctorMap", doctorMap);
        request.setAttribute("slotMap", slotMap);
        request.setAttribute("patient", patient);  // Pass patient info to JSP if needed
        request.getRequestDispatcher("/WEB-INF/jsp/patient/patientAppointmentList.jsp").forward(request, response);
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
