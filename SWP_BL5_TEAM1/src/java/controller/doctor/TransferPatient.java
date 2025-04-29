/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.doctor;

import dal.AppointmentDAO;
import dal.DoctorHandoffDAO;
import dal.StaffAccountDAO;
import dto.DoctorHandoffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TransferPatient extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");

        // look up staff_id for this user
        StaffAccountDAO saDao = new StaffAccountDAO();
        int doctorId = saDao.getDoctorByUserId(userId).getStaffId();

        DoctorHandoffDAO dao = new DoctorHandoffDAO();
        List<DoctorHandoffDTO> handoffs = dao.getHandoffsForDoctor(doctorId);

        req.setAttribute("handoffs", handoffs);

        req.getRequestDispatcher("/doctor/transferPatient.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int handoffId = Integer.parseInt(req.getParameter("handoffId"));
        String action = req.getParameter("action");

        // You’ll need instances of both DAOs:
        DoctorHandoffDAO handoffDao = new DoctorHandoffDAO();
        AppointmentDAO apptDao = new AppointmentDAO();

            if ("start".equals(action)) {
                // 1) Mark handoff In Progress
                handoffDao.updateHandoffStatus(handoffId, "In Progress");

            } else if ("finish".equals(action)) {
                // 1) Mark handoff Completed
                handoffDao.updateHandoffStatus(handoffId, "Completed");
                // 2) Fetch linked appointment and mark it back-from-hand-off
                int apptId = handoffDao.getAppointmentIdForHandoff(handoffId);
                apptDao.updateAppointmentStatus(apptId, "Back-from-hand-off");
            }

        // Redirect back to the GET to refresh the table
        resp.sendRedirect(req.getContextPath() + "/TransferPatient");
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
