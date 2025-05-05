/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.AppointmentDAO;
import dal.ExaminationPackageDAO;
import dto.ExaminationPackageDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import dal.SpecialtyDAO;
import dal.StaffAccountDAO;
import model.ExaminationPackage;
import model.Specialty;
import model.StaffAccount;
import model.User;

/**
 *
 * @author Admin
 */
public class ExaminationPackageManager extends HttpServlet {

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
        if (!"Manager".equalsIgnoreCase(staffAccount.getRole())) {
            // User is a staff, but not a Manager, forward to error.jsp
            request.setAttribute("errorMessage", "Access denied. Manager role required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        ExaminationPackageDAO dao = new ExaminationPackageDAO();
        List<ExaminationPackageDTO> packages = dao.getAllPackagesByDTO();
        request.setAttribute("packages", packages);

        SpecialtyDAO specialtyDAO = new SpecialtyDAO();
        List<Specialty> specialties = specialtyDAO.getAllSpecialties();
        request.setAttribute("specialties", specialties);

        request.getRequestDispatcher("/manager/serviceManager.jsp")
                .forward(request, response);
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
        String action = request.getParameter("action");

        ExaminationPackageDAO dao = new ExaminationPackageDAO();

        if ("create".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int specialtyId = Integer.parseInt(request.getParameter("specialtyId"));
            dao.insertPackage(new ExaminationPackage(name, description, specialtyId));
        } else if ("edit".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int specialtyId = Integer.parseInt(request.getParameter("specialtyId"));
            int id = Integer.parseInt(request.getParameter("packageId"));
            dao.updatePackage(new ExaminationPackage(id, name, description, specialtyId));
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("packageId"));
            dao.deletePackage(id);
        }

        response.sendRedirect("ExaminationPackageManager");
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
