package controller.doctor;

import dal.MedicalDAO;
import model.MedicalRecord;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;


public class MedicalRecordsServlet extends HttpServlet {
    private MedicalDAO medicalDAO;

    @Override
    public void init() throws ServletException {
        medicalDAO = new MedicalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String patientPhone = request.getParameter("patientPhone");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        Timestamp startDate = null;
        Timestamp endDate = null;

        if (startDateStr != null && !startDateStr.isEmpty()) {
            try {
                LocalDate startLocalDate = LocalDate.parse(startDateStr);
                startDate = Timestamp.valueOf(startLocalDate.atStartOfDay());
            } catch (DateTimeParseException e) {
                request.setAttribute("error", "Invalid start date format. Use YYYY-MM-DD.");
            }
        }

        if (endDateStr != null && !endDateStr.isEmpty()) {
            try {
                LocalDate endLocalDate = LocalDate.parse(endDateStr);
                endDate = Timestamp.valueOf(endLocalDate.atTime(23, 59, 59));
            } catch (DateTimeParseException e) {
                request.setAttribute("error", "Invalid end date format. Use YYYY-MM-DD.");
            }
        }

        List<MedicalRecord> records = medicalDAO.getAllMedicalRecords(startDate, endDate);

        // Filter by patientPhone if provided
        if (patientPhone != null && !patientPhone.isEmpty()) {
            records.removeIf(record -> !record.getPatientPhone().equals(patientPhone));
        }

        request.setAttribute("records", records);
        request.setAttribute("patientPhone", patientPhone);
        request.setAttribute("startDate", startDateStr);
        request.setAttribute("endDate", endDateStr);

        request.getRequestDispatcher("/doctor/medicalRecords.jsp").forward(request, response);
    }
}