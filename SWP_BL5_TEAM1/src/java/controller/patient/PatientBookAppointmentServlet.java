/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.patient;

import dal.AppointmentDAO;
import dal.PatientDAO;
import dal.TimeSlotDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Appointment;
import model.Patient;
import model.TimeSlot;
import model.User;

/**
 *
 * @author LENOVO
 */
public class PatientBookAppointmentServlet extends HttpServlet {

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
            out.println("<title>Servlet PatientBookAppointmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PatientBookAppointmentServlet at " + request.getContextPath() + "</h1>");
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
        List<TimeSlot> timeSlots = timeSlotDAO.getAllTimeSlots();
        request.setAttribute("timeSlots", timeSlots);

        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                PatientDAO patientDAO = new PatientDAO();
                Patient patient = patientDAO.getPatientByPhone(user.getPhone()); // assuming username is phone
                if (patient != null) {
                    request.setAttribute("fullName", patient.getFullName());
                    request.setAttribute("phone", patient.getPhone());
                    request.setAttribute("email", patient.getEmail());
                    request.setAttribute("gender", patient.getGender());
                    request.setAttribute("dateOfBirth", patient.getDateOfBirth());
                }
            }
        }

        request.getRequestDispatcher("/WEB-INF/jsp/patient/patientBookingAppointment.jsp")
                .forward(request, response);
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

        request.setCharacterEncoding("UTF-8");

        // Collect form data
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dateOfBirth");
        String doctorIdStr = request.getParameter("doctorId");
        String slotIdStr = request.getParameter("slotId");
        String appointmentDateStr = request.getParameter("appointmentDate");

        // Input validation
        String errorMessage = null;
        if (fullName == null || !fullName.matches("^[A-Za-z\\s]{3,50}$")) {
            errorMessage = "Full name must be 3-50 characters long and contain only letters and spaces.";
        } else if (phone == null || !phone.matches("^\\d{10,15}$")) {
            errorMessage = "Phone number must be between 10 and 15 digits.";
        } else if (email != null && !email.isEmpty() && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            errorMessage = "Invalid email format.";
        }

        // If validation fails, forward back with error message
        if (errorMessage != null) {
            request.setAttribute("appointmentStatus", "fail");
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("fullName", fullName);
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            request.setAttribute("gender", gender);
            request.setAttribute("dateOfBirth", dobStr);
            request.setAttribute("doctorId", doctorIdStr);
            request.setAttribute("slotId", slotIdStr);
            request.setAttribute("appointmentDate", appointmentDateStr);
            request.getRequestDispatcher("/auth/patient/patientBookingAppointment.jsp").forward(request, response);
            return;
        }

        // Parse types
        Date dateOfBirth = (dobStr != null && !dobStr.isEmpty()) ? Date.valueOf(dobStr) : null;
        int doctorId = Integer.parseInt(doctorIdStr);
        int slotId = Integer.parseInt(slotIdStr);
        Date appointmentDate = Date.valueOf(appointmentDateStr);

        // Create DAOs
        PatientDAO patientDAO = new PatientDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        boolean success = false;

        try {
            // Check if patient already exists
            Patient patient = patientDAO.getPatientByPhone(phone);
            if (patient == null) {
                patient = new Patient();
                patient.setPhone(phone);
                patient.setFullName(fullName);
                patient.setEmail(email);
                patient.setGender(gender);
                patient.setDateOfBirth(dateOfBirth);
                patientDAO.insertPatient(patient);
            }

            Appointment appointment = new Appointment();
            appointment.setPatientPhone(phone);
            appointment.setDoctorId(doctorId);
            appointment.setSlotId(slotId);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setStatus("Pending");

            appointmentDAO.insert(appointment);
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/PatientBookAppointmentServlet?status=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/PatientBookAppointmentServlet?status=fail");
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
