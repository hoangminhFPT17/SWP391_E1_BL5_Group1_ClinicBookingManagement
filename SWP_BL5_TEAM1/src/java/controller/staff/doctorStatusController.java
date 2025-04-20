package controller.staff;

import dal.PatientQueueDAO;
import dto.DoctorStatusDTO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/staff/doctorStatus")
public class doctorStatusController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PatientQueueDAO dao = new PatientQueueDAO();
        List<DoctorStatusDTO> statusList = dao.getDoctorStatusWithPriorities();
        request.setAttribute("doctorStatusList", statusList);
        request.getRequestDispatcher("staff/doctorStatusManager.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PatientQueueDAO dao = new PatientQueueDAO();

        // Auto‐assign button?
        String autoId = request.getParameter("autoAssignDoctorId");
        if (autoId != null) {
            int docId = Integer.parseInt(autoId);
            dao.autoAssignPatient(docId);
            doGet(request, response);
            return;
        }
        doGet(request, response);
    }
}
