    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import dal.AppointmentDAO;
import dal.DoctorTimeSlotDAO;
import dal.DoctorUnavailabilityDAO;
import dal.StaffAccountDAO;
import dal.UserDAO;
import dto.DoctorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import model.DoctorTimeSlot;
import model.StaffAccount;
import model.User;
import util.DAOUtils;

/**
 *
 * @author LENOVO
 */
public class DoctorBySlotServlet extends HttpServlet {

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
            out.println("<title>Servlet DoctorBySlotServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorBySlotServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Get parameters
            String slotIdStr = request.getParameter("slotId");
            String appointmentDateStr = request.getParameter("appointmentDate");
            System.out.println("Received params: slotId=" + slotIdStr + ", appointmentDate=" + appointmentDateStr);

            if (slotIdStr == null || appointmentDateStr == null) {
                System.out.println("Missing parameters");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
                return;
            }

            int slotId = Integer.parseInt(slotIdStr);
            Date appointmentDate = Date.valueOf(appointmentDateStr); // yyyy-MM-dd format
            System.out.println("Parsed slotId=" + slotId + ", appointmentDate=" + appointmentDate);

            // 2. Figure out day of week from appointment date
            String dayOfWeek = getDayOfWeek(appointmentDate); // Monday, Tuesday, etc.
            System.out.println("Calculated dayOfWeek=" + dayOfWeek);

            // 3. Initialize DAOs
            DoctorTimeSlotDAO doctorTimeSlotDAO = new DoctorTimeSlotDAO();
            DoctorUnavailabilityDAO doctorUnavailabilityDAO = new DoctorUnavailabilityDAO();
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
            UserDAO userDAO = new UserDAO();
            System.out.println("DAOs initialized");

            // 4. Get all doctor IDs assigned to this slot on this day, order by least to most appointment that day
            List<Integer> workingDoctorIds = doctorTimeSlotDAO.getDoctorIdsBySlotAndDayOrderedByLeastAppointments(slotId, dayOfWeek, appointmentDate);
            System.out.println("Working doctor IDs: " + workingDoctorIds);

            List<DoctorDTO> availableDoctors = new ArrayList<>();

            for (Integer staffId : workingDoctorIds) {
                System.out.println("Checking doctor staffId=" + staffId);

                // 5. Check if doctor is unavailable on this date
                boolean isUnavailable = doctorUnavailabilityDAO.isDoctorUnavailable(staffId, slotId, appointmentDate);
                System.out.println("Doctor staffId=" + staffId + " isUnavailable=" + isUnavailable);
                if (isUnavailable) {
                    continue;
                }

                // 6. Check if doctor has reached max appointments
                int currentAppointments = appointmentDAO.countAppointmentsByDoctorAndSlotAndDate(staffId, slotId, appointmentDate);
                DoctorTimeSlot doctorTimeSlot = doctorTimeSlotDAO.getDoctorTimeSlot(staffId, slotId, dayOfWeek);
                System.out.println("Doctor staffId=" + staffId + " currentAppointments=" + currentAppointments + ", maxAppointments=" + (doctorTimeSlot != null ? doctorTimeSlot.getMaxAppointments() : "null"));

                if (doctorTimeSlot != null && currentAppointments >= doctorTimeSlot.getMaxAppointments()) {
                    System.out.println("Doctor staffId=" + staffId + " has reached max appointments");
                    continue;
                }

                // 7. Fetch StaffAccount and User
                StaffAccount staffAccount = staffAccountDAO.getStaffById(staffId);
                if (staffAccount == null) {
                    System.out.println("StaffAccount not found for staffId=" + staffId);
                    continue;
                }
                System.out.println("StaffAccount found: " + staffAccount.getStaffId());

                User user = userDAO.getUserById(staffAccount.getUserId());
                if (user == null) {
                    System.out.println("User not found for userId=" + staffAccount.getUserId());
                    continue;
                }
                System.out.println("User found: " + user.getFullName());

                // 8. Create DoctorDTO
                DoctorDTO doctorDTO = new DoctorDTO(
                        staffAccount.getStaffId(),
                        user.getUserId(),
                        user.getFullName(),
                        staffAccount.getRole(),
                        staffAccount.getDepartment()
                );
                System.out.println("DoctorDTO created: " + doctorDTO.getFullName());

                availableDoctors.add(doctorDTO);
            }
            
            DAOUtils.disconnectAll(doctorTimeSlotDAO, doctorUnavailabilityDAO, appointmentDAO, staffAccountDAO,userDAO);
            
            // 9. Output available doctors as JSON
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            mapper.writeValue(response.getOutputStream(), availableDoctors);
            System.out.println("Returned " + availableDoctors.size() + " available doctors");

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    private String getDayOfWeek(Date date) {
        LocalDate localDate = date.toLocalDate();
        return localDate.getDayOfWeek().toString().substring(0, 1).toUpperCase()
                + localDate.getDayOfWeek().toString().substring(1).toLowerCase();
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
