/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.auth;

import Service.MailService;
import dal.AuthDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="RegisterControllers", urlPatterns={"/register"})
public class RegisterControllers extends HttpServlet {
   
    private static final Logger LOGGER = Logger.getLogger(RegisterControllers.class.getName());
    
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("Auth.jsp").forward(request, response);
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
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        
        // Basic validation
        if (fullname == null || email == null || phone == null || password == null ||
            fullname.trim().isEmpty() || email.trim().isEmpty() || 
            phone.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorRegister", "All fields are required");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
            return;
        }
        
        // Email format validation
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("errorRegister", "Invalid email format");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
            return;
        }
        
        // Phone format validation (simple check for numeric and length)
        if (!phone.matches("\\d{10,11}")) {
            request.setAttribute("errorRegister", "Invalid phone number format");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
            return;
        }
        
        // Password strength validation (at least 6 characters)
        if (password.length() < 6) {
            request.setAttribute("errorRegister", "Password must be at least 6 characters");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
            return;
        }
        
        AuthDAO authDAO = new AuthDAO();
        
        // Check if email exists
        if (authDAO.isEmailExists(email)) {
            request.setAttribute("errorRegister", "Email is already registered");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
            return;
        }
        
        // Check if phone exists
        if (authDAO.isPhoneExists(phone)) {
            request.setAttribute("errorRegister", "Phone number is already registered");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
            return;
        }
        
        // All validation passed, register the user
        User newUser = authDAO.registerUser(fullname, email, phone, password);
        
        if (newUser != null) {
            try {
                // Generate verification link
                String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + 
                                request.getServerPort() + request.getContextPath();
                String verifyLink = baseUrl + "/verify?email=" + email + "&otp=" + newUser.getOtpCode();
                
                // Set success message
                request.getSession().setAttribute("userId", newUser.getUserId());
                request.setAttribute("registerComplete", "Registration successful! Please check your email to verify your account.");
                
                // Send verification email with verification link
                try {
                    String emailSubject = "Verify Your Clinic Booking System Account";
                    String emailContent = "Hello " + fullname + ",\n\n"
                            + "Thank you for registering with our Clinic Booking System.\n\n"
                            + "Please click the link below to verify your account:\n"
                            + verifyLink + "\n\n"
                            + "This link will expire in 24 hours.\n\n"
                            + "Best regards,\nClinic Booking Team";
                    
                    MailService.sendMail(email, emailSubject, emailContent);
                    LOGGER.log(Level.INFO, "Verification email sent to: {0}", email);
                } catch (Exception mailEx) {
                    LOGGER.log(Level.WARNING, "Could not send verification email: {0}", mailEx.getMessage());
                    request.setAttribute("emailWarning", "We couldn't send a verification email. Please contact support.");
                }
                
                // Forward to the login page with success message
                request.getRequestDispatcher("Auth.jsp").forward(request, response);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error in registration process: {0}", ex.getMessage());
                request.setAttribute("errorRegister", "Registration processing error. Please try again later.");
                request.getRequestDispatcher("Auth.jsp").forward(request, response);
            }
        } else {
            LOGGER.log(Level.SEVERE, "User registration failed - newUser object is null");
            request.setAttribute("errorRegister", "Registration failed. Please try again later or contact support.");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
