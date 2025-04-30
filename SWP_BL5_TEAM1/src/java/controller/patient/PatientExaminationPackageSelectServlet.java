/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.patient;

import dal.ExaminationPackageDAO;
import dal.SpecialtyDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ExaminationPackage;
import model.Specialty;
import util.DAOUtils;

/**
 *
 * @author LENOVO
 */
public class PatientExaminationPackageSelectServlet extends HttpServlet {

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
            out.println("<title>Servlet PatientExaminationPackageSelectServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PatientExaminationPackageSelectServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Daos
        ExaminationPackageDAO packageDAO = new ExaminationPackageDAO();
        SpecialtyDAO specialtyDAO = new SpecialtyDAO();

        String tier = request.getParameter("tier");
        String specialtyIdStr = request.getParameter("specialtyId");
        String search = request.getParameter("search");
        String pageStr = request.getParameter("page");

        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
        int pageSize = 8;

        Integer specialtyId = null;
        if (specialtyIdStr != null && !specialtyIdStr.isEmpty()) {
            specialtyId = Integer.parseInt(specialtyIdStr);
        }

        List<Specialty> specialties = specialtyDAO.getAllSpecialties();

        // Get filtered & paginated results
        List<ExaminationPackage> examinationPackages = packageDAO.getFilteredPackages(tier, specialtyId, search, page, pageSize);
        int totalCount = packageDAO.countFilteredPackages(tier, specialtyId, search);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("examinationPackages", examinationPackages);
        request.setAttribute("specialties", specialtyDAO.getAllSpecialties());

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        DAOUtils.disconnectAll(packageDAO, specialtyDAO);

        request.getRequestDispatcher("/patient/patientExaminationPackageSelect.jsp").forward(request, response);
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
        processRequest(request, response);
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
