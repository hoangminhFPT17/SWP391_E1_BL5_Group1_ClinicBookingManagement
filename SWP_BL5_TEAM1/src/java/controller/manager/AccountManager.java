/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.AppointmentDAO;
import dal.ExaminationPackageDAO;
import dal.StaffAccountDAO;
import dal.UsersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.StaffAccount;
import model.User;

/**
 *
 * @author Admin
 */
public class AccountManager extends HttpServlet {

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
            out.println("<title>Servlet AccountManager</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AccountManager at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. Get logged-in user from session
        User loggedInUser = (User) req.getSession().getAttribute("user");
        if (loggedInUser == null) {
            // User not logged in, redirect to login
            resp.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }
        //DAOs
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();

        // 2. Check if user has a StaffAccount
        StaffAccount staffAccount = staffAccountDAO.getStaffByUserId(loggedInUser.getUserId());
        if (staffAccount == null) {
            // User is not a staff member, redirect to login
            resp.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }
        // 3. Check if StaffAccount role is "Manager"
        if (!"Manager".equalsIgnoreCase(staffAccount.getRole())) {
            // User is a staff, but not a Manager, forward to error.jsp
            req.setAttribute("errorMessage", "Access denied. Manager role required.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        
        
        String q = req.getParameter("search");
        if (q == null) {
            q = "";
        }
        UsersDAO dao = new UsersDAO();
        List<User> users = dao.getAllUsers(q);
        req.setAttribute("userList", users);
        req.setAttribute("search", q);

        req.getRequestDispatcher("/manager/AccountManager.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String search = req.getParameter("search");
        if (search == null) {
            search = "";
        }

        UsersDAO dao = new UsersDAO();
        switch (action) {

            case "edit":
                dao.updateUser(
                        Integer.parseInt(req.getParameter("userId")),
                        req.getParameter("email"),
                        req.getParameter("phone"),
                        req.getParameter("fullName"),
                        "on".equals(req.getParameter("isVerified"))
                );
                break;
            case "delete":
                dao.deleteUser(Integer.parseInt(req.getParameter("userId")));
                break;
        }

        resp.sendRedirect(req.getContextPath()
                + "/AccountManager?search="
                + java.net.URLEncoder.encode(search, "UTF-8"));
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
