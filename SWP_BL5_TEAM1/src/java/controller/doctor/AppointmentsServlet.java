package controller.doctor;

import dal.AppointmentDAO;
import model.Appointment;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/doctor/appointments")
public class AppointmentsServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("patientPhone");
        List<Appointment> list = appointmentDAO.getByPatientPhone(phone);

        // Build JSON array
        JSONArray arr = new JSONArray();
        for (Appointment appt : list) {
            JSONObject obj = new JSONObject();
            obj.put("appointmentId", appt.getAppointmentId());
            obj.put("appointmentDate", appt.getAppointmentDate().toString());
            obj.put("status", appt.getStatus());
            obj.put("description", appt.getDescription());
            obj.put("createdAt", appt.getCreatedAt().toString());
            // you can add more fields if needed
            arr.put(obj);
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(arr.toString());
    }
}
