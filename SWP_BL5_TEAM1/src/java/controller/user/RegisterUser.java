/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.user;

/**
 *
 * @author JackGarland
 */
import model.User;
import model.Patient;
import dal.DAOUser;
import dal.DAOToken;
import dal.PatientAccountDAO;
import dal.PatientDAO;
import model.Token; // Sử dụng entity.Token
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.time.LocalDate;
import model.PatientAccount;

@WebServlet(name = "UserRegister", urlPatterns = {"/User"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class RegisterUser extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterUser.class.getName());
    private static final String REGISTER_JSP = "signup.jsp";
    private static final String LOGIN_JSP = "login.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if ("registerUser".equals(service)) {
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid service parameter");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String service = request.getParameter("service");

        if (!"registerUser".equals(service)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid service parameter");
            return;
        }

        String submit = request.getParameter("submit");
        if (submit == null) {
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
            return;
        }

        DAOUser dao = new DAOUser();
        PatientDAO patient_dao = new PatientDAO();
        PatientAccountDAO patientAccountDAO = new PatientAccountDAO();
        if (!dao.isConnected()) {
            LOGGER.log(Level.SEVERE, "Database connection is null");
            request.setAttribute("error", "Không thể kết nối cơ sở dữ liệu. Vui lòng thử lại sau.");
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
            return;
        }

        // Lấy dữ liệu từ form
        String email = request.getParameter("Email");
        String password = request.getParameter("Password");
        String phone = request.getParameter("Phone");
        String fullName = request.getParameter("FullName");
        boolean isVerified = false;
        String otpCode = "123456";
        Timestamp createAt = new Timestamp(System.currentTimeMillis());
        Timestamp otpExpiry = new Timestamp(System.currentTimeMillis());
        // Giữ plaintext

        try {
            String error = validateInput(dao, email, fullName, phone, password, request);
            if (!error.isEmpty()) {
                request.setAttribute("error", error);
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
                return;
            }

            // Tạo đối tượng User với mật khẩu plaintext
            User newUser = new User(0, email, password, phone, fullName, isVerified, otpCode, createAt, otpExpiry);
            PatientAccount newPatientAccount = new PatientAccount();

            // Đăng ký người dùng và lấy UserID
            int userId = dao.registerUser(newUser);

            //Add new patientAccount when register
//            patientAccountDAO.insertPatientAccount(userId);
            patient_dao.connectPatient(userId, userId);

            if (userId > 0) {
                // Tạo token kích hoạt bằng resetService
                ResetService resetSvc = new ResetService();
                String token = resetSvc.generateToken();

                Token activationToken = new Token(
                        userId, false, token, resetSvc.expireDateTime()
                );
                DAOToken daoToken = new DAOToken();
                boolean isTokenSaved = daoToken.insertTokenForget(activationToken);

                if (!isTokenSaved) {
                    request.setAttribute("error", "Lỗi khi lưu token kích hoạt. Vui lòng thử lại.");
                    request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
                    return;
                }

                // Gửi email kích hoạt
                String activationLink = "http://localhost:8080/SWP_BL5_TEAM1/activate?token=" + token;
                boolean isEmailSent = resetSvc.sendActivationEmail(email, activationLink, fullName);

                if (!isEmailSent) {
                    request.setAttribute("error", "Không thể gửi email kích hoạt. Vui lòng thử lại.");
                    request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
                    return;
                }

                session.setAttribute("success", "Đăng ký thành công! Vui lòng kiểm tra email để kích hoạt tài khoản.");
                request.removeAttribute("last_email");
                request.removeAttribute("last_phone");
                request.removeAttribute("last_name");

                patient_dao.connectPatient(userId, userId);

                Patient patient = patient_dao.getPatientByPhone(phone);
                if (patient == null) {
                    patient = new Patient();
                    patient.setPatientAccountId(userId);
                    patient.setPhone(phone);
                    patient.setFullName(fullName);
                    patient.setEmail(email);
                    patient.setGender("Male");
                    patient.setDateOfBirth(Date.valueOf(LocalDate.now()));
                    patient_dao.insertPatient(patient);
                }
                response.sendRedirect(LOGIN_JSP);
            } else {
                request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error registering user", ex);
            request.setAttribute("error", "Lỗi hệ thống: " + ex.getMessage());
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
        }
    }

    // Các phương thức khác giữ nguyên
    private String handleFileUpload(HttpServletRequest request) throws IOException, ServletException {
        try {
            Part filePart = request.getPart("avatar");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = extractFileName(filePart);
                if (fileName != null && isImageFile(fileName)) {
                    String uploadPath = getServletContext().getRealPath("") + "uploads/";
                    java.io.File uploadDir = new java.io.File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    filePart.write(uploadPath + fileName);
                    return "uploads/" + fileName;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error uploading file", e);
        }
        return null;
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return null;
    }

    private boolean isImageFile(String fileName) {
        String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for (String ext : allowedExtensions) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private String validateInput(DAOUser dao, String email, String fullName, String phone, String password, HttpServletRequest request) throws SQLException {
        request.setAttribute("last_email", email);
        request.setAttribute("last_phone", phone);
        request.setAttribute("last_name", fullName);
        if (dao.isEmailExists(email)) {
            request.setAttribute("last_email", "");
            return "Email đã được sử dụng.";
        }
        if (dao.isPhoneExists(phone)) {
            request.setAttribute("last_phone", "");
            return "Số điện thoại đã được sử dụng.";
        }
        if (password == null || password.length() < 8) {
            return "Mật khẩu phải dài ít nhất 8 ký tự.";
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ và tên không được để trống.";
        }
        return "";
    }

    @Override
    public String getServletInfo() {
        return "Servlet for user registration";
    }
}
