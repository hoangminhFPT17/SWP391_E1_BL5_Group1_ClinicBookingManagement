/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import dal.DoctorTimeSlotDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import model.DoctorTimeSlot;

/**
 *
 * @author LENOVO
 */
public class UpdateDoctorAssignmentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateDoctorAssignmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDoctorAssignmentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int oldDoctorId = Integer.parseInt(request.getParameter("oldDoctorId"));
        int newDoctorId = Integer.parseInt(request.getParameter("newDoctorId"));
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        String dayOfWeek = request.getParameter("dayOfWeek");
        int maxAppointments = Integer.parseInt(request.getParameter("maxAppointments"));

        DoctorTimeSlotDAO dao = new DoctorTimeSlotDAO();

        boolean success;
        if (oldDoctorId != newDoctorId && dao.exists(newDoctorId, slotId, dayOfWeek)) {
            success = false; // duplicate
        } else {
            success = dao.updateAssignment(oldDoctorId, newDoctorId, slotId, dayOfWeek, maxAppointments);
        }

        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Create a response object
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("success", success);

        // Convert the response to JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // Write JSON response to output stream
        try (PrintWriter out = response.getWriter()) {
            objectMapper.writeValue(out, jsonResponse); // Serialize to JSON
            out.flush();
        }
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
