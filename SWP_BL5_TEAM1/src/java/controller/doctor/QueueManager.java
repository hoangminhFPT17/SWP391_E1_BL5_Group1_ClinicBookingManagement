/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import dal.AppointmentDAO;
import dal.DoctorHandoffDAO;
import dal.MedicalDAO;
import dal.StaffAccountDAO;
import dto.AppointmentDTO;
import dto.AppointmentDetailDTO;
import dto.DoctorAssignDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.MedicalRecord;

/**
 *
 * @author Admin
 */
public class QueueManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        AppointmentDAO dao = new AppointmentDAO();
        AppointmentDetailDTO detail = null;
        StaffAccountDAO staffDao = new StaffAccountDAO();
        Integer doctorId = staffDao.getDoctorByUserId(userId).getStaffId();

        Integer nextId = dao.getNextAppointmentIdForCurrentSlot(doctorId);
        if (nextId != null) {
            detail = dao.getAppointmentDetailById(nextId);
            // NEW: fetch medical records for this patient
            MedicalDAO mrDao = new MedicalDAO();
            List<MedicalRecord> records = mrDao.getMedicalRecordsByPatientPhoneNew(detail.getPatientPhone());
            request.setAttribute("medicalRecords", records);
        }

        List<DoctorAssignDTO> doctors = staffDao.getAllDoctors();
        request.setAttribute("doctorList", doctors);
        request.setAttribute("appointmentDetail", detail);
        request.getRequestDispatcher("/doctor/QueueManager.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2) Pull doctorId from session
        Integer userId = (Integer) session.getAttribute("userId");

        // 3) Look up the next appointment’s ID, then its full DTO
        AppointmentDAO dao = new AppointmentDAO();
        StaffAccountDAO staffDao = new StaffAccountDAO();

        Integer loggedDoctor = staffDao.getDoctorByUserId(userId).getStaffId();

        String action = request.getParameter("action");

        DoctorHandoffDAO doctorHandoffDao = new DoctorHandoffDAO();

        if ("start".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            dao.updateAppointmentStatus(appointmentId, "In Progress");
        } else if ("sendService".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            // form fields from modal
            int toDoctorId = Integer.parseInt(request.getParameter("toDoctorId"));
            String reason = request.getParameter("reason");
            // 1) update appointment
            dao.updateAppointmentStatus(appointmentId, "On-hand-off");
            // 2) insert handoff record
            doctorHandoffDao.insert(loggedDoctor, toDoctorId, appointmentId, reason);
        } else if ("finish ".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            dao.updateAppointmentStatus(appointmentId, "Waiting payment");
        } else if ("addRecord".equals(action)) {
            String patientPhone = request.getParameter("patientPhone");
            String diagnosis = request.getParameter("diagnosis");
            String prescription = request.getParameter("prescription");
            String notes = request.getParameter("notes");

            MedicalDAO mrDao = new MedicalDAO();
            mrDao.addMedicalRecord(new MedicalRecord(
                    0, // recordId auto
                    patientPhone,
                    diagnosis,
                    prescription,
                    notes,
                    null // createdAt default
            ));

        }

        // reload to show updated next appointment
        response.sendRedirect(request.getContextPath() + "/QueueManager?doctorId=1");
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
