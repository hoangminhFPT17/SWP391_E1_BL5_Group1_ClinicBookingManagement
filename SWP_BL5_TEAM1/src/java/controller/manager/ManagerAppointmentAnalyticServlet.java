/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.AppointmentDAO;
import dal.DoctorTimeSlotDAO;
import dal.ExaminationPackageDAO;
import dal.StaffAccountDAO;
import dto.DoctorAssignDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Year;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ExaminationPackage;

/**
 *
 * @author LENOVO
 */
public class ManagerAppointmentAnalyticServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerAppointmentAnalyticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerAppointmentAnalyticServlet at " + request.getContextPath() + "</h1>");
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
        String doctorIdParam = request.getParameter("doctorId");
        String packageIdParam = request.getParameter("packageId");
        String yearParam = request.getParameter("year");

        Integer doctorId = (doctorIdParam != null && !doctorIdParam.isEmpty()) ? Integer.parseInt(doctorIdParam) : null;
        Integer packageId = (packageIdParam != null && !packageIdParam.isEmpty()) ? Integer.parseInt(packageIdParam) : null;
        Integer year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : Year.now().getValue();

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
        ExaminationPackageDAO examinationPackageDAO = new ExaminationPackageDAO();

        //Data for dropbox
        List<ExaminationPackage> examinationPackages = examinationPackageDAO.getAllPackages();
        request.setAttribute("examinationPackages", examinationPackages);

        List<DoctorAssignDTO> allDoctors = staffAccountDAO.getAllDoctors();
        request.setAttribute("allDoctors", allDoctors);

        request.setAttribute("currentYear", Year.now().getValue());

        // Data for graph
        Map<Integer, Integer> monthlyAppointmentCountsMap = appointmentDAO.getMonthlyAppointmentCounts(year, packageId, doctorId);
        request.setAttribute("monthlyAppointmentCountsMap", monthlyAppointmentCountsMap);

        request.setAttribute("selectedDoctorId", doctorId);
        request.setAttribute("selectedPackageId", packageId);
        request.setAttribute("currentYear", year);
        request.getRequestDispatcher("/manager/managerAppointmentAnalytic.jsp").forward(request, response);

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
