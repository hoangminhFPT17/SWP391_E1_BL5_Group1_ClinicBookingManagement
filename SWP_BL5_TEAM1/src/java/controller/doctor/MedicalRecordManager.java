
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import dal.MedicalDAO;
import model.MedicalRecord;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet to manage CRUD operations for Medical Records.
 *
 * @author [Your Name]
 */
public class MedicalRecordManager extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Supports listing records, viewing a single record, and showing create/edit forms.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String patientPhone = request.getParameter("patientPhone");
        MedicalDAO dao = new MedicalDAO();

        if (action == null || action.equals("list")) {
            // List all medical records for a patient
            if (patientPhone == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing patientPhone");
                return;
            }
            List<MedicalRecord> records = dao.getMedicalRecordsByPatientPhone(patientPhone);
            request.setAttribute("records", records);
            request.setAttribute("patientPhone", patientPhone);
            request.setAttribute("action", "list");
            request.getRequestDispatcher("doctor/MedicalRecordManagement.jsp").forward(request, response);
        } else if (action.equals("view")) {
            // View a single record
            String recordIdParam = request.getParameter("recordId");
            if (recordIdParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing recordId");
                return;
            }
            try {
                int recordId = Integer.parseInt(recordIdParam);
                MedicalRecord record = dao.getRecordById(recordId);
                if (record == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Record not found");
                    return;
                }
                request.setAttribute("record", record);
                request.setAttribute("action", "view");
                request.getRequestDispatcher("doctor/MedicalRecordManagement.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recordId");
            }
        } else if (action.equals("create")) {
            // Show create form
            if (patientPhone == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing patientPhone");
                return;
            }
            request.setAttribute("patientPhone", patientPhone);
            request.setAttribute("action", "create");
            request.getRequestDispatcher("doctor/MedicalRecordManagement.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Show edit form
            String recordIdParam = request.getParameter("recordId");
            if (recordIdParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing recordId");
                return;
            }
            try {
                int recordId = Integer.parseInt(recordIdParam);
                MedicalRecord record = dao.getRecordById(recordId);
                if (record == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Record not found");
                    return;
                }
                request.setAttribute("record", record);
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("doctor/MedicalRecordManagement.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recordId");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Supports creating, updating, and deleting medical records.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        MedicalDAO dao = new MedicalDAO();

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action");
            return;
        }

        if (action.equals("create")) {
            // Create a new medical record
            String patientPhone = request.getParameter("patientPhone");
            String diagnosis = request.getParameter("diagnosis");
            String prescription = request.getParameter("prescription");
            String notes = request.getParameter("notes");

            if (patientPhone == null || diagnosis == null || prescription == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
                return;
            }

            MedicalRecord record = new MedicalRecord();
            record.setPatientPhone(patientPhone);
            record.setDiagnosis(diagnosis);
            record.setPrescription(prescription);
            record.setNotes(notes);
            record.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            dao.createRecord(record);

            // Redirect to the list of records
            response.sendRedirect("MedicalRecordManager?patientPhone=" + patientPhone);
        } else if (action.equals("update")) {
            // Update an existing medical record
            String recordIdParam = request.getParameter("recordId");
            String patientPhone = request.getParameter("patientPhone");
            String diagnosis = request.getParameter("diagnosis");
            String prescription = request.getParameter("prescription");
            String notes = request.getParameter("notes");

            if (recordIdParam == null || patientPhone == null || diagnosis == null || prescription == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
                return;
            }

            try {
                int recordId = Integer.parseInt(recordIdParam);
                MedicalRecord record = new MedicalRecord();
                record.setRecordId(recordId);
                record.setPatientPhone(patientPhone);
                record.setDiagnosis(diagnosis);
                record.setPrescription(prescription);
                record.setNotes(notes);
                record.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                dao.updateRecord(record);

                // Redirect to the list of records
                response.sendRedirect("MedicalRecordManager?patientPhone=" + patientPhone);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recordId");
            }
        } else if (action.equals("delete")) {
            // Delete a medical record
            String recordIdParam = request.getParameter("recordId");
            String patientPhone = request.getParameter("patientPhone");

            if (recordIdParam == null || patientPhone == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
                return;
            }

            try {
                int recordId = Integer.parseInt(recordIdParam);
                dao.deleteRecord(recordId);

                // Redirect to the list of records
                response.sendRedirect("MedicalRecordManager?patientPhone=" + patientPhone);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recordId");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manages CRUD operations for Medical Records";
    }
}