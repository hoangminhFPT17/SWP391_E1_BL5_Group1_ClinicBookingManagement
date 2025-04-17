/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AppointmentDAO;
import dal.PatientDAO;
import dal.TimeSlotDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Appointment;
import model.Patient;
import model.TimeSlot;

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

        // Parse types
        Date dateOfBirth = (dobStr != null && !dobStr.isEmpty()) ? Date.valueOf(dobStr) : null;
        int doctorId = Integer.parseInt(doctorIdStr);
        int slotId = Integer.parseInt(slotIdStr);
        Date appointmentDate = Date.valueOf(appointmentDateStr);

        // Create DAOs
        PatientDAO patientDAO = new PatientDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        // Check if patient already exists
        Patient patient = patientDAO.getPatientByPhone(phone);
        boolean success = false;

        try {
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

        // Redirect or forward to confirmation
        request.setAttribute("appointmentStatus", success ? "success" : "fail");
        request.getRequestDispatcher("/patientBookingAppointment.jsp").forward(request, response);
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
