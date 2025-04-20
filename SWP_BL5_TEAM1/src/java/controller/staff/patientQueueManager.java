/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dal.PatientQueueDAO;
import dto.PatientQueueDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.PatientQueue;

/**
 *
 * @author Admin
 */
public class patientQueueManager extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String docIdParam = request.getParameter("doctorId");
    if (docIdParam == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing doctorId");
        return;
    }

    int doctorId;
    try {
        doctorId = Integer.parseInt(docIdParam);
    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid doctorId");
        return;
    }

    PatientQueueDAO dao = new PatientQueueDAO();
    List<PatientQueueDTO> activeAppointments = dao.getActiveAppointments(doctorId);

    request.setAttribute("activeAppointments", activeAppointments);
    request.setAttribute("doctorId", doctorId);  // so the view can re‑submit it
    request.getRequestDispatcher("staff/patientQueueManager.jsp")
           .forward(request, response);
}

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read the submitted parameters
        String[] idParams = request.getParameterValues("id");
        String[] priorityParams = request.getParameterValues("priority");

        if (idParams == null || priorityParams == null || idParams.length != priorityParams.length) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reorder parameters.");
            return;
        }

        // Convert to Integer lists
        List<Integer> ids = new ArrayList<>();
        List<Integer> prios = new ArrayList<>();
        try {
            for (int i = 0; i < idParams.length; i++) {
                ids.add(Integer.valueOf(idParams[i]));
                prios.add(Integer.valueOf(priorityParams[i]));
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format in parameters.");
            return;
        }

        // Perform the reorder in the database
        PatientQueueDAO dao = new PatientQueueDAO();
        dao.reorderQueue(ids, prios);

        // Redirect back to the main patient queue page
        doGet(request, response);
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
