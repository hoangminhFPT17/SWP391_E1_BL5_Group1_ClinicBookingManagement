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
    }

    // Manual‐assign button?
    String manualId   = request.getParameter("manualAssignDoctorId");
    String manualPrio = request.getParameter("manualPriority");
    if (manualId != null && manualPrio != null) {
        int docId = Integer.parseInt(manualId);
        int prio  = Integer.parseInt(manualPrio);
        dao.manualAssignPatient(docId, prio);
    }

    // Redirect to the GET URL so the browser's last action is a GET.
    String context = request.getContextPath();                // e.g. "/yourapp"
    String redirectUrl = context + "/staff/doctorStatus";      // matches @WebServlet
    response.sendRedirect(redirectUrl);
}

}
