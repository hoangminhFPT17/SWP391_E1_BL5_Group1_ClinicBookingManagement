package controller.doctor;

import dal.MedicalDAO;
import model.MedicalRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/doctor/medical-records-json")
public class MedicalRecordsJsonServlet extends HttpServlet {
    private MedicalDAO medicalDAO;

    @Override
    public void init() throws ServletException {
        medicalDAO = new MedicalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("patientPhone");

        // fetch all, then filter
        List<MedicalRecord> list = medicalDAO.getAllMedicalRecords(null, null);
        list.removeIf(r -> !r.getPatientPhone().equals(phone));

        JSONArray arr = new JSONArray();
        for (MedicalRecord rec : list) {
            JSONObject obj = new JSONObject();
            obj.put("recordId", rec.getRecordId());
            obj.put("diagnosis", rec.getDiagnosis());
            obj.put("prescription", rec.getPrescription());
            obj.put("notes", rec.getNotes());
            obj.put("createdAt", rec.getCreatedAt().toString());
            arr.put(obj);
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(arr.toString());
    }
}
