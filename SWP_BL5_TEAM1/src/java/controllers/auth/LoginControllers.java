package controllers.auth;

import dal.AuthDAO;
import dal.AuthDAO.UnverifiedAccountException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="LoginControllers", urlPatterns={"/login"})
public class LoginControllers extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.Forwards to the login page (Auth.jsp).
     * @param request
     * @param response
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Auth.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.Processes login credentials, authenticates user, and redirects based on role.
     * @param request
     * @param response
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usernameOrEmail = request.getParameter("email");
        String password = request.getParameter("password");

        AuthDAO authDAO = new AuthDAO();
        try {
            User user = authDAO.authenticate(usernameOrEmail, password);

            if (user != null) {
                // Authentication successful, set user in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirect based on role
                String role = user.getRole();
                switch (role) {
                    case "Patient" -> response.sendRedirect("home");
                    case "Doctor" -> response.sendRedirect("doctor/index");
                    case "Manager" -> response.sendRedirect("Manager/index");
                    case "Receptionist" -> response.sendRedirect("Receptionist/index");
                    default -> {
                        request.setAttribute("errorLogin", "Invalid user role.");
                        request.getRequestDispatcher("Auth.jsp").forward(request, response);
                    }
                }
            } else {
                request.setAttribute("errorLogin", "Invalid username/email or password.");
                request.getRequestDispatcher("Auth.jsp").forward(request, response);
            }
        } catch (UnverifiedAccountException ex) {
            request.setAttribute("errorLogin", ex.getMessage());
            request.getRequestDispatcher("Auth.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Handles user login and role-based redirection";
    }
}