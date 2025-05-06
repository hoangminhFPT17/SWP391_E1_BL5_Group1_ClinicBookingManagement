/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dal.AppointmentDAO;
import dal.StaffAccountDAO;
import dto.AppointmentDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.StaffAccount;
import model.User;

/**
 *
 * @author Admin
 */
public class AppointmentManager extends HttpServlet {

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
            out.println("<title>Servlet AppointmentManager</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AppointmentManager at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        // 1. Get logged-in user from session
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null) {
            // User not logged in, redirect to login
            response.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }
        //DAOs
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();

        // 2. Check if user has a StaffAccount
        StaffAccount staffAccount = staffAccountDAO.getStaffByUserId(loggedInUser.getUserId());
        if (staffAccount == null) {
            // User is not a staff member, redirect to login
            response.sendRedirect("/SWP_BL5_TEAM1/login");
            return;
        }
        // 3. Check if StaffAccount role is "Manager"
        if (!"Receptionist".equalsIgnoreCase(staffAccount.getRole())) {
            // User is a staff, but not a Manager, forward to error.jsp
            request.setAttribute("errorMessage", "Access denied. Manager role required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String searchName = request.getParameter("searchName");
        if (searchName == null) {
            searchName = "";
        }

        AppointmentDAO dao = new AppointmentDAO();
        List<AppointmentDTO> allAppointments;
        allAppointments = dao.getAllAppointments(searchName);

        request.setAttribute("appointments", allAppointments);
        request.setAttribute("searchName", searchName);
        request.getRequestDispatcher("/receptionist/appointmentManager.jsp")
                .forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // 1) Authentication / role checks (same as in doGet)
    HttpSession session = request.getSession(false);
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
    StaffAccount staffAccount = staffAccountDAO.getStaffByUserId(loggedInUser.getUserId());
    if (staffAccount == null || !"Receptionist".equalsIgnoreCase(staffAccount.getRole())) {
        request.setAttribute("errorMessage", "Access denied.");
        request.getRequestDispatcher("/error.jsp").forward(request, response);
        return;
    }

    // 2) Read parameters
    String action = request.getParameter("action");
    String rawId = request.getParameter("appointmentId");
    if (action != null && rawId != null) {
        int appointmentId = Integer.parseInt(rawId);
        AppointmentDAO dao = new AppointmentDAO();

        switch (action) {
            case "toWaiting":
                dao.updateAppointmentStatus(appointmentId, "Waiting");
                break;
            case "complete":
                dao.updateAppointmentStatus(appointmentId, "Payment-Complete");
                break;
        }
    }

    // 3) Redirect back to GET so the table reloads
    response.sendRedirect(request.getContextPath() + "/AppointmentManager");
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
