package controller.staff;

import dal.PatientDAO;
import model.Patient;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet({"/staff/patient-list", "/doctor/update-patient"})
public class PatientListServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PatientListServlet.class.getName());
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        try {
            patientDAO = new PatientDAO();
            LOGGER.info("PatientDAO initialized");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to init PatientDAO", e);
            throw new ServletException(e);
        }
    }

    // GET: list patients with search, filter, and pagination
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Retrieve parameters from the request
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");
        int page = 1;
        try {
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
        } catch (NumberFormatException e) {
            // Default to page 1 if invalid
            page = 1;
        }
        int pageSize = 10; // Number of patients per page, can be made configurable

        List<Patient> patients = new ArrayList<>();
        int totalPatients = 0;
        int totalPages = 0;

        try {
            // Determine which DAO method to call based on parameters
            if (phone != null && !phone.trim().isEmpty() && gender != null && !gender.trim().isEmpty()) {
                patients = patientDAO.getPatientsByPhoneAndGender(phone, gender, page, pageSize);
                totalPatients = patientDAO.getTotalPatientsByPhoneAndGender(phone, gender);
            } else if (phone != null && !phone.trim().isEmpty()) {
                patients = patientDAO.getPatientsByPhone(phone, page, pageSize);
                totalPatients = patientDAO.getTotalPatientsByPhone(phone);
            } else if (gender != null && !gender.trim().isEmpty()) {
                patients = patientDAO.getPatientsByGender(gender, page, pageSize);
                totalPatients = patientDAO.getTotalPatientsByGender(gender);
            } else {
                patients = patientDAO.getAllPatients(page, pageSize);
                totalPatients = patientDAO.getTotalPatients();
            }
            // Calculate total pages
            totalPages = (totalPatients + pageSize - 1) / pageSize; // Ceiling division
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error fetching patients", e);
            req.setAttribute("error", "Failed to load patient list: " + e.getMessage());
            patients = new ArrayList<>(); // Empty list on error
            totalPages = 0;
        }

        // Set attributes for JSP
        req.setAttribute("patients", patients);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        // Forward to JSP
        req.getRequestDispatcher("/doctor/patientList.jsp").forward(req, resp);
    }

    // POST: update basic info
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getServletPath().equals("/doctor/update-patient")) {
            resp.setContentType("application/json;charset=UTF-8");
            try {
                // read JSON body
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = req.getReader()) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }
                JSONObject json = new JSONObject(sb.toString());
                String phone = json.getString("phone");
                String fullName = json.getString("fullName");
                String email = json.getString("email");
                LocalDate dobLocal = LocalDate.parse(json.getString("dateOfBirth"));
                Date dob = Date.valueOf(dobLocal);

                // validate inputs
                if (phone == null || phone.trim().isEmpty() || fullName == null || fullName.trim().isEmpty()) {
                    throw new IllegalArgumentException("Phone and full name are required");
                }

                // update DB
                Patient updated = patientDAO.updateBasicInfo(phone, fullName, email, dob);

                // respond with updated patient JSON
                JSONObject out = new JSONObject();
                out.put("phone", updated.getPhone());
                out.put("fullName", updated.getFullName());
                out.put("email", updated.getEmail());
                out.put("dateOfBirth", updated.getDateOfBirth().toString());
                resp.getWriter().write(out.toString());
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error updating patient", e);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject error = new JSONObject();
                error.put("error", "Failed to update patient: " + e.getMessage());
                resp.getWriter().write(error.toString());
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Invalid input: " + e.getMessage());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JSONObject error = new JSONObject();
                error.put("error", e.getMessage());
                resp.getWriter().write(error.toString());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Unexpected error updating patient", e);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject error = new JSONObject();
                error.put("error", "Unexpected error: " + e.getMessage());
                resp.getWriter().write(error.toString());
            }
        } else {
            super.doPost(req, resp);
        }
    }
}