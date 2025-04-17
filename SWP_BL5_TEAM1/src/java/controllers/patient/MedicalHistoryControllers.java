/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.patient;

import dal.MedicalDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.MedicalRecord;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="MedicalHistoryControllers", urlPatterns={"/medicalhistory"})
public class MedicalHistoryControllers extends HttpServlet {
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        System.out.println("MedicalHistoryControllers: doGet method called");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            System.out.println("MedicalHistoryControllers: No user found in session, redirecting to login");
            // Redirect to login if not logged in
            response.sendRedirect("login");
            return;
        }
        
        System.out.println("MedicalHistoryControllers: User ID from session: " + user.getUserId());
        
        MedicalDAO medicalDAO = new MedicalDAO();
        
        // Get patient phone from user ID
        String patientPhone = medicalDAO.getPatientPhoneByUserId(user.getUserId());
        System.out.println("MedicalHistoryControllers: Patient phone retrieved: " + patientPhone);
        
        if (patientPhone != null) {
            // Get medical records by patient phone
            List<MedicalRecord> records = medicalDAO.getMedicalRecordsByPatientPhone(patientPhone);
            System.out.println("MedicalHistoryControllers: Medical records found: " + records.size());
            
            request.setAttribute("medicalRecords", records);
            // Display at least the first record if available for debugging
            if (!records.isEmpty()) {
                System.out.println("MedicalHistoryControllers: First record ID: " + records.get(0).getRecordId());
            }
        } else {
            System.out.println("MedicalHistoryControllers: No patient phone found for user ID: " + user.getUserId());
            // User has no associated patient record
            request.setAttribute("message", "No patient record found for your account (ID: " + user.getUserId() + "). Please contact support.");
        }
        
        request.getRequestDispatcher("MedicalHistory.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Handle any POST requests if needed
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller for displaying patient medical history";
    }// </editor-fold>
}
