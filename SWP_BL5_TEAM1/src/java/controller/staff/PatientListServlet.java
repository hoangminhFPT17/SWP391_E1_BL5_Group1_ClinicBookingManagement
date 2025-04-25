package controller.staff;

import dal.PatientDAO;
import model.Patient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/staff/patient-list")
public class PatientListServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PatientListServlet.class.getName());
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        try {
            patientDAO = new PatientDAO();
            LOGGER.log(Level.INFO, "PatientListServlet: PatientDAO initialized successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientListServlet: Failed to initialize PatientDAO: " + e.getMessage(), e);
            throw new ServletException("Failed to initialize PatientDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Patient> patients = new ArrayList<>();

        try {
            LOGGER.log(Level.INFO, "PatientListServlet: Fetching patient list...");
            patients = patientDAO.getAllPatients();
            LOGGER.log(Level.INFO, "PatientListServlet: Successfully retrieved " + patients.size() + " patients");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientListServlet: Error retrieving patient list: " + e.getMessage(), e);
            request.setAttribute("error", "Failed to retrieve patient list: " + e.getMessage());
        }

        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/doctor/patientList.jsp").forward(request, response);
    }
}