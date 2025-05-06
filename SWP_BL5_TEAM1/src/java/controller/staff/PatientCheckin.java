/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dal.AppointmentDAO;
import dal.ExaminationPackageDAO;
import dal.PatientDAO;
import dal.StaffAccountDAO;
import dal.TimeSlotDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Appointment;
import model.Patient;
import model.TimeSlot;
import java.sql.Date;
import model.StaffAccount;
import model.User;

/**
 *
 * @author Admin
 */
public class PatientCheckin extends HttpServlet {

    private final PatientDAO patientDao = new PatientDAO();
    private final AppointmentDAO apptDao = new AppointmentDAO();
    private final TimeSlotDAO slotDao = new TimeSlotDAO();
    private final ExaminationPackageDAO pkgDao = new ExaminationPackageDAO();
    private final StaffAccountDAO staffDao = new StaffAccountDAO();

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
        if (!"Receptionist".equalsIgnoreCase(staffAccount.getRole())) {
            // User is a staff, but not a Manager, forward to error.jsp
            req.setAttribute("errorMessage", "Access denied. Manager role required.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        
        

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        // Show initial form
        req.getRequestDispatcher("receptionist/patientCheckIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String phone = req.getParameter("phone");
        PatientDAO patientDao = new PatientDAO();
        // always reload the lists
        req.setAttribute("timeSlots", slotDao.getAllTimeSlots());
        req.setAttribute("packages", pkgDao.getAllPackages());
        req.setAttribute("doctors", staffDao.getAllDoctors());

        if ("checkPhone".equals(action)) {
            Patient p = patientDao.getPatientByPhone(phone);
            if (p != null) {
                // existing patient → skip to appointment form
                req.setAttribute("patient", p);
                req.setAttribute("showAppointmentForm", true);
            } else {
                // new patient → show editable form
                req.setAttribute("showPatientForm", true);
            }

        } else if ("createPatient".equals(action)) {
            // only create if truly new
            Patient existing = patientDao.getPatientByPhone(phone);
            if (existing == null) {
                String fullName = req.getParameter("fullName");
                Date dob = Date.valueOf(req.getParameter("dob"));
                String gender = req.getParameter("gender");
                String email = req.getParameter("email");
                patientDao.insertPatient(new Patient(phone, null, fullName, dob, gender, email, null));
                existing = patientDao.getPatientByPhone(phone);
            }
            req.setAttribute("patient", existing);
            req.setAttribute("showAppointmentForm", true);

        } else if ("createAppointment".equals(action)) {
            Appointment appt = new Appointment(
                    phone,
                    Integer.parseInt(req.getParameter("doctorId")),
                    Integer.parseInt(req.getParameter("slotId")),
                    Date.valueOf(req.getParameter("appointmentDate")),
                    req.getParameter("status"),
                    new java.sql.Timestamp(System.currentTimeMillis()),
                    req.getParameter("description"),
                    Integer.valueOf(req.getParameter("packageId"))
            );
            apptDao.create(appt);
        }

        req.getRequestDispatcher("/receptionist/patientCheckIn.jsp")
                .forward(req, resp);
    }
    
    

}
