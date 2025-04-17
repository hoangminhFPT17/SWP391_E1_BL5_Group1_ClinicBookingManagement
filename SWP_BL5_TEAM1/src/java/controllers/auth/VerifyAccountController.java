package controllers.auth;

import dal.AuthDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles email verification process
 * 
 * @author ADMIN
 */
@WebServlet(name = "VerifyAccountController", urlPatterns = {"/verify"})
public class VerifyAccountController extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(VerifyAccountController.class.getName());

    /**
     * Handles the HTTP <code>GET</code> method.
     * Processes account verification when user clicks link in email
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");
        
        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
            request.setAttribute("errorLogin", "Invalid verification link. Please try registering again.");
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
            return;
        }
        
        AuthDAO authDAO = new AuthDAO();
        boolean verified = authDAO.verifyAccount(email, otp);
        
        if (verified) {
            LOGGER.log(Level.INFO, "Account verified successfully for email: {0}", email);
            request.setAttribute("verificationSuccess", "Your account has been verified successfully. You can now login.");
        } else {
            LOGGER.log(Level.WARNING, "Account verification failed for email: {0}", email);
            request.setAttribute("errorLogin", "Account verification failed. The link may have expired or is invalid.");
        }
        
        request.getRequestDispatcher("Auth.jsp").forward(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Account Verification Controller";
    }
}
