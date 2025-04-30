/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.patient;

import dal.AppointmentDAO;
import dal.ExaminationPackageDAO;
import dal.PatientAccountDAO;
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
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Appointment;
import model.ExaminationPackage;
import model.Patient;
import model.PatientAccount;
import model.TimeSlot;
import model.User;
import util.DAOUtils;

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
        //Get current examination package for patient to book
        String examPackageStr = request.getParameter("examPackageId");
        int examPackageId = 1;
        if (examPackageStr != null) {
            examPackageId = Integer.parseInt(examPackageStr);
        }
        PatientDAO patientDAO = new PatientDAO();
        ExaminationPackageDAO examinationPackageDAO = new ExaminationPackageDAO();
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

        ExaminationPackage examinationPackage = examinationPackageDAO.getPackageById(examPackageId);
        request.setAttribute("examinationPackage", examinationPackage);

        // Get all time slots
        List<TimeSlot> timeSlots = timeSlotDAO.getAllActiveTimeSlots();
        request.setAttribute("timeSlots", timeSlots);

        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                Patient patient = patientDAO.getPatientByPhone(user.getPhone()); // key is phone
                if (patient != null) {
                    request.setAttribute("fullName", patient.getFullName());
                    request.setAttribute("phone", patient.getPhone());
                    request.setAttribute("email", patient.getEmail());
                    request.setAttribute("gender", patient.getGender());
                    request.setAttribute("dateOfBirth", patient.getDateOfBirth());
                }
            }
        }

        DAOUtils.disconnectAll(examinationPackageDAO, timeSlotDAO, patientDAO);
        request.getRequestDispatcher("/patient/patientBookingAppointment.jsp")
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
        
        //Daos
        PatientAccountDAO patientAccountDAO = new PatientAccountDAO();
        PatientDAO patientDAO = new PatientDAO();

        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        PatientAccount patientAccount = null;
        if (user != null) {
            patientAccount = patientAccountDAO.getPatientByUserId(user.getUserId());
        }

        // Collect form data
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dateOfBirth");
        String doctorIdStr = request.getParameter("doctorId");
        String slotIdStr = request.getParameter("slotId");
        String appointmentDateStr = request.getParameter("appointmentDate");
        String description = request.getParameter("description");
        String examPackageIdStr = request.getParameter("examPackageId");

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
            request.setAttribute("description", description);
            request.getRequestDispatcher("/patient/patientBookingAppointment.jsp").forward(request, response);
            return;
        }

        // Parse types
        Date dateOfBirth = (dobStr != null && !dobStr.isEmpty()) ? Date.valueOf(dobStr) : null;
        int doctorId = Integer.parseInt(doctorIdStr);
        int slotId = Integer.parseInt(slotIdStr);
        Date appointmentDate = Date.valueOf(appointmentDateStr);
        int examPackageId = Integer.parseInt(examPackageIdStr);
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        AppointmentDAO appointmentDAO = new AppointmentDAO();

        boolean success = false;

        try {
            // Check if patient already exists
            Patient patient = patientDAO.getPatientByPhone(phone);
            if (patient == null) {
                patient = new Patient();
                if (patientAccount != null) {
                    patient.setPatientAccountId(patientAccount.getPatientAccountId());
                    patient.setPhone(user.getPhone());
                    patient.setFullName(user.getFullName());
                    patient.setEmail(user.getEmail());
                    patient.setGender(gender);
                    patient.setDateOfBirth(dateOfBirth);
                    patient.setCreatedAt(createdAt);
                } else {
                    patient.setPhone(phone);
                    patient.setFullName(fullName);
                    patient.setEmail(email);
                    patient.setGender(gender);
                    patient.setDateOfBirth(dateOfBirth);
                    patient.setCreatedAt(createdAt);
                }
                patientDAO.insertPatient(patient);
                phone = patient.getPhone();
            }else {
                phone = patient.getPhone();
            }

            Appointment appointment = new Appointment();
            appointment.setPatientPhone(phone);
            appointment.setDoctorId(doctorId);
            appointment.setSlotId(slotId);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setStatus("Pending");
            appointment.setDescription(description);
            appointment.setPackageId(examPackageId);

            appointmentDAO.insert(appointment);
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            if (user != null) {
                response.sendRedirect(request.getContextPath() + "/PatientAppointmentsListServlet");
            } else {
                response.sendRedirect(request.getContextPath() + "/home.jsp");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/PatientBookAppointmentServlet?examPackageId=" + examPackageId + "&status=fail");
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
