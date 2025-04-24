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
public class InsertDoctorAssignmentServlet extends HttpServlet {

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
            out.println("<title>Servlet InsertDoctorAssignmentServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsertDoctorAssignmentServlet at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from request
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        String dayOfWeek = request.getParameter("dayOfWeek");
        int maxAppointments = Integer.parseInt(request.getParameter("maxAppointments"));
        System.out.println("DoctorID: " + doctorId);
        System.out.println("slotId: " + doctorId);
        System.out.println("dayOfWeek: " + doctorId);
        System.out.println("maxAppointments: " + doctorId);
        
        DoctorTimeSlotDAO dao = new DoctorTimeSlotDAO();
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            boolean exists = dao.exists(doctorId, slotId, dayOfWeek);
            if (exists) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "This doctor is already assigned to that slot on the selected day.");
            } else {
                boolean inserted = dao.insert(new DoctorTimeSlot(0, doctorId, slotId, dayOfWeek, maxAppointments));
                jsonResponse.put("success", inserted);
                jsonResponse.put("message", inserted ? "Doctor assigned successfully." : "Failed to assign doctor.");
                System.out.println("Inserted? = " + inserted);
            }   
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Server error: " + e.getMessage());
        }

        // Send JSON response using Jackson
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), jsonResponse);
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
