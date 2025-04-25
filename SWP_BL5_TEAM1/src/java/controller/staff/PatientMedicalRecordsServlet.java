package controller.staff;

import dal.MedicalDAO;
import model.MedicalRecord;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/staff/patient-medical-records")
public class PatientMedicalRecordsServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PatientMedicalRecordsServlet.class.getName());
    private MedicalDAO medicalDAO;

    @Override
    public void init() throws ServletException {
        try {
            medicalDAO = new MedicalDAO();
            LOGGER.log(Level.INFO, "PatientMedicalRecordsServlet: MedicalDAO initialized successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientMedicalRecordsServlet: Failed to initialize MedicalDAO: " + e.getMessage(), e);
            throw new ServletException("Failed to initialize MedicalDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String patientPhone = request.getParameter("patientPhone");

        if (patientPhone == null || patientPhone.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Patient phone number is required");
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            LOGGER.log(Level.INFO, "PatientMedicalRecordsServlet: Fetching medical records for patientPhone=" + patientPhone);
            List<MedicalRecord> records = medicalDAO.getMedicalRecordsByPatientPhone(patientPhone);

            JSONArray recordsArray = new JSONArray();
            for (MedicalRecord record : records) {
                JSONObject recordJson = new JSONObject();
                recordJson.put("recordId", record.getRecordId());
                recordJson.put("patientPhone", record.getPatientPhone());
                recordJson.put("diagnosis", record.getDiagnosis() != null ? record.getDiagnosis() : "");
                recordJson.put("prescription", record.getPrescription() != null ? record.getPrescription() : "");
                recordJson.put("notes", record.getNotes() != null ? record.getNotes() : "");
                recordJson.put("createdAt", record.getCreatedAt() != null ? record.getCreatedAt().toString() : "");
                recordsArray.put(recordJson);
            }

            JSONObject responseJson = new JSONObject();
            responseJson.put("records", recordsArray);
            LOGGER.log(Level.INFO, "PatientMedicalRecordsServlet: Successfully retrieved " + recordsArray.length() + " medical records");

            out.print(responseJson.toString());
            out.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientMedicalRecordsServlet: Error retrieving medical records: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving medical records");
        }
    }
}