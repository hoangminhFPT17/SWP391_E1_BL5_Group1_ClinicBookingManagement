/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.AppointmentDAO;
import dal.InvoiceDAO;
import dal.PatientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class ManagerAnalyticServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerAnalyticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerAnalyticServlet at " + request.getContextPath() + "</h1>");
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
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        PatientDAO patientDAO = new PatientDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        
        //Data for info box
        int patientCount = patientDAO.countPatients();
        request.setAttribute("patientCount", patientCount);
        
        double totalRevenue = invoiceDAO.getTotalRevenue();
        request.setAttribute("totalRevenue", totalRevenue);
        
        int appointmentCount = appointmentDAO.countAppointments();
        request.setAttribute("appointmentCount", appointmentCount);
        
            
        Map<String, Integer> demographics = patientDAO.getPatientDemographics();
        request.setAttribute("demographics", demographics);

        // Data for pie chart
        Map<String, Integer> timeSlotBookedCountMap = appointmentDAO.countAppointmentsByTimeSlot();
        request.setAttribute("timeSlotBookedCountMap", timeSlotBookedCountMap);
       
        // Data for column chart
        List<Map<String, Object>> appointmentCountByDoctor = appointmentDAO.getAppointmentCountsByDoctor();
        for (Map<String, Object> data : appointmentCountByDoctor) {
            System.out.println("Doctor: " + data.get("doctor_name") + ", Appointments: " + data.get("appointment_count"));
        }
        request.setAttribute("appointmentCountByDoctor", appointmentCountByDoctor);
        
         request.getRequestDispatcher("/manager/managerAnalytic.jsp").forward(request, response);
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
