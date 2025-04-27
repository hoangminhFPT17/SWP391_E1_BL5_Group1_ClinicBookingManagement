/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dal.StaffAccountDAO;
import dto.StaffDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class StaffManager extends HttpServlet {
   
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String searchName = req.getParameter("searchName");
        if (searchName == null) searchName = "";

            StaffAccountDAO dao = new StaffAccountDAO();
            List<StaffDTO> staffList = dao.getAllStaff(searchName);
            req.setAttribute("staffList", staffList);
            req.setAttribute("searchName", searchName);

        req.getRequestDispatcher("/manager/staffManager.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String searchName = req.getParameter("searchName");
        if (searchName == null) searchName = "";

            StaffAccountDAO dao = new StaffAccountDAO();

            switch (action) {
                case "addStaff":
                    // fields: email, role, department
                    String email    = req.getParameter("email");
                    String role     = req.getParameter("role");
                    String dept     = req.getParameter("department");
                    dao.addStaff(email, role, dept);
                    break;

                case "editStaff":
                    // fields: staffId, email, role, department
                    int staffId     = Integer.parseInt(req.getParameter("staffId"));
                    String eEmail   = req.getParameter("email");
                    String eRole    = req.getParameter("role");
                    String eDept    = req.getParameter("department");
                    dao.updateStaff(staffId, eEmail, eRole, eDept);
                    break;

                case "delete":
                    int delId = Integer.parseInt(req.getParameter("staffId"));
                    dao.deleteStaffAccount(delId);
                    break;

                default:
                    throw new ServletException("Unknown action: " + action);
            }

        // redirect back, preserving search
        resp.sendRedirect(req.getContextPath()
            + "/StaffManager"
            + java.net.URLEncoder.encode(searchName, "UTF-8"));
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
