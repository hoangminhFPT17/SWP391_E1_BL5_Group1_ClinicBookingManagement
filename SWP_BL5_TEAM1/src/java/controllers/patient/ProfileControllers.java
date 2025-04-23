/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.patient;

import dal.PatientDAO;
import dal.UsersDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Patient;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ProfileControllers", urlPatterns={"/profile"})
public class ProfileControllers extends HttpServlet {
   
    private static final Logger LOGGER = Logger.getLogger(ProfileControllers.class.getName());

    /** 
     * Handles the HTTP <code>GET</code> method.
     * Retrieves user data from session and forwards to profile page
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Get user from session
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        
        // Check if user is logged in
        if (sessionUser == null) {
            // Redirect to login page if not logged in
            response.sendRedirect("login");
            return;
        }
        
        // Get up-to-date user info from database
        UsersDAO usersDAO = new UsersDAO();
        User user = usersDAO.getUserById(sessionUser.getUserId());
        
        if (user != null) {
            request.setAttribute("user", user);
            
            // Get patient info if this is a patient account
            if (user.getRole().equals("Patient")) {
                PatientDAO patientDAO = new PatientDAO();
                Patient patient = patientDAO.getPatientByUserId(user.getUserId());
                if (patient != null) {
                    request.setAttribute("patient", patient);
                }
            }
        } else {
            // If can't get from DB, use session data
            request.setAttribute("user", sessionUser);
        }
        
        // Forward to profile page
        request.getRequestDispatcher("Profile.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * Handles profile update
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        
        if (sessionUser == null) {
            response.sendRedirect("login");
            return;
        }
        
        // Get form data
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone"); // This is now read-only, but we still get the value
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        
        try {
            PatientDAO patientDAO = new PatientDAO();
            boolean success = false;
            
            // Parse date of birth
            Date dateOfBirth = null;
            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                try {
                    dateOfBirth = Date.valueOf(dateOfBirthStr);
                } catch (IllegalArgumentException e) {
                    request.setAttribute("errorMessage", "Invalid date format. Please use YYYY-MM-DD format.");
                    doGet(request, response);
                    return;
                }
            }
            
            // Update profile fields except phone
            success = patientDAO.updatePatientProfileWithoutPhone(phone, fullName, dateOfBirth, gender, sessionUser.getUserId());
            
            if (success) {
                // Update session user information for name only (phone doesn't change)
                sessionUser.setFullName(fullName);
                session.setAttribute("user", sessionUser);
                
                request.setAttribute("successMessage", "Profile updated successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to update profile");
            }
        } catch (SQLException ex) {
            // Handle other database errors
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            LOGGER.log(Level.SEVERE, "Database error updating profile", ex);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "General error updating profile", e);
        }
        
        // Redirect back to profile page
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles user profile display and updates";
    }
}
