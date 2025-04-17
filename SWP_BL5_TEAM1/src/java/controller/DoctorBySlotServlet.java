/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dal.DoctorTimeSlotDAO;
import dal.StaffAccountDAO;
import dal.UserDAO;
import dto.DoctorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.StaffAccount;
import model.User;

/**
 *
 * @author LENOVO
 */
public class DoctorBySlotServlet extends HttpServlet {

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
            out.println("<title>Servlet DoctorBySlotServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorBySlotServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String slotIdStr = request.getParameter("slotId");

        if (slotIdStr == null || slotIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty slotId");
            return;
        }

        int slotId;
        try {
            slotId = Integer.parseInt(slotIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid slotId");
            return;
        }

        DoctorTimeSlotDAO doctorTimeSlotDAO = new DoctorTimeSlotDAO();
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
        UserDAO userDAO = new UserDAO(); // You need to create this if you don't have one yet

        List<Integer> staffIds = doctorTimeSlotDAO.getDoctorIdsBySlotId(slotId);
        List<StaffAccount> doctors = staffAccountDAO.getDoctorsByIds(staffIds);

        List<DoctorDTO> enrichedDoctors = new ArrayList<>();
        for (StaffAccount doc : doctors) {
            User user = userDAO.getUserById(doc.getUserId());
            if (user != null) {
                enrichedDoctors.add(new DoctorDTO(
                        doc.getStaffId(),
                        doc.getUserId(),
                        user.getFullName(),
                        doc.getRole(),
                        doc.getDepartment()
                ));
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), enrichedDoctors);
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
        processRequest(request, response);
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
