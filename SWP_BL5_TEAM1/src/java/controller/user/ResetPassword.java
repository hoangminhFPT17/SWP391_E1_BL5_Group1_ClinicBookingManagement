/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.user;

/**
 *
 * @author JackGarland
 */
import dal.DAOToken;
import dal.DAOUser;
import model.Token;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Heizxje
 */
@WebServlet(name = "resetPassword", urlPatterns = {"/resetPassword"})
public class ResetPassword extends HttpServlet {

    DAOToken DAOToken = new DAOToken();
    DAOUser DAOUser = new DAOUser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        HttpSession session = request.getSession();
        if (token != null) {
            Token tokenForgetPassword = DAOToken.getTokenPassword(token);
            ResetService service = new ResetService();

            if (tokenForgetPassword == null) {
                request.setAttribute("mess", "Token invalid");
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
                return;
            }
            if (tokenForgetPassword.isUsed()) {
                request.setAttribute("mess", "Token is already used");
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
                return;
            }
            if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
                request.setAttribute("mess", "Token is expired");
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
                return;
            }

            User user = DAOUser.getUserById(tokenForgetPassword.getUserId());
            request.setAttribute("email", user.getEmail());
            session.setAttribute("token", tokenForgetPassword.getToken());
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("mess", "Confirm password must match the password"+password+confirmPassword);
            request.setAttribute("email", email);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String tokenStr = (String) session.getAttribute("token");
        Token tokenForgetPassword = DAOToken.getTokenPassword(tokenStr);
        ResetService service = new ResetService();

        if (tokenForgetPassword == null) {
            request.setAttribute("mess", "Token invalid");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }
        if (tokenForgetPassword.isUsed()) {
            request.setAttribute("mess", "Token is already used");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }
        if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
            request.setAttribute("mess", "Token is expired");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }

        tokenForgetPassword.setUsed(true);
        DAOUser.updatePassword(email, password);
        DAOToken.updateStatus(tokenForgetPassword);

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ResetPassword Servlet handles password reset functionality";
    }
}
