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
import dal.DAOUser;
import dal.PatientDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.net.URLEncoder;
import Service.RandomStringService;// Thêm để mã hóa tham số URL
import java.util.concurrent.ThreadLocalRandom;

/**
 * Servlet xử lý các yêu cầu liên quan đến hồ sơ người dùng, bao gồm xem hồ sơ,
 * cập nhật thông tin và đổi mật khẩu.
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile-alt"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // Kích thước ngưỡng để lưu tạm file: 2MB
        maxFileSize = 1024 * 1024 * 10, // Kích thước tối đa của một file: 10MB
        maxRequestSize = 1024 * 1024 * 50)   // Kích thước tối đa của toàn bộ request: 50MB
public class ProfileServlet extends HttpServlet {

    private static final String PROFILE_PAGE = "/profile_user.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        request.setAttribute("user", currentUser);
        request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        String action = request.getParameter("action");
        DAOUser daoUser = new DAOUser();

        if ("changePassword".equals(action)) {
            handleChangePassword(request, response, session, currentUser, daoUser);
        } else if ("changeProfilePicture".equals(action)) {
            String path = handleFileUpload(request, "DSADD");
            daoUser.updateImagePath(currentUser.getUserId(), path);
            currentUser.setImgPath(path);
            response.sendRedirect("profile-alt?message=" + URLEncoder.encode("NICE JOB. " + path, "UTF-8"));

        } else if ("medicalHistory".equals(action)) {
            String path2 = handlePdfUpload(request, currentUser.getPdfPath());
            daoUser.updatePdfPath(currentUser.getUserId(), path2);
            currentUser.setPdfPath(path2);
            response.sendRedirect("profile-alt?message=" + URLEncoder.encode("NICE JOB. " + path2, "UTF-8"));
        } else {
            handleUpdateProfile(request, response, session, currentUser, daoUser);
        }
    }

    private boolean uploadFile(InputStream is, String path) {
        boolean test = false;

        try {
            byte[] byt = new byte[is.available()];
            is.read(byt);
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();

            test = true;
        } catch (Exception E) {
            E.printStackTrace();
        }

        return test;
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, User currentUser, DAOUser daoUser) throws IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        try {

            String encryptedCurrentPassword = currentPassword;
            if (!currentUser.getPasswordHash().equals(encryptedCurrentPassword)) {
                response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Mật khẩu hiện tại không đúng." + currentPassword + "  " + currentUser.getPasswordHash() + "  " + newPassword + confirmPassword, "UTF-8"));
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Mật khẩu mới và xác nhận mật khẩu không khớp.", "UTF-8"));
                return;
            }

            if (newPassword.length() < MIN_PASSWORD_LENGTH) {
                response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Mật khẩu mới phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự.", "UTF-8"));
                return;
            }

            currentUser.setPasswordHash(newPassword);
            if (daoUser.updateUser(currentUser)) {
                User updatedUser = daoUser.getUserById(currentUser.getUserId()); // Cập nhật từ DB
                session.setAttribute("user", updatedUser);
                response.sendRedirect("profile-alt?message=" + URLEncoder.encode("NICE JOB.", "UTF-8"));
            } else {
                response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Đổi mật khẩu thất bại.", "UTF-8"));
            }
        } catch (Exception e) {
            response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Lỗi khi xử lý mật khẩu: " + e.getMessage(), "UTF-8"));
        }
    }

    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, User currentUser, DAOUser daoUser) throws IOException, ServletException {
        int userId = currentUser.getUserId();
        String firstName = request.getParameter("name1").trim();
        String lastName = request.getParameter("name2").trim();

        String fullName = firstName + " " + lastName;
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String bio = request.getParameter("bio");
        
        PatientDAO p_dao = new PatientDAO();

        if (phone != null && !phone.equals(currentUser.getPhone())) {
            try {
                if (daoUser.isPhoneExist(phone, userId)) {
                    response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Số điện thoại đã được sử dụng bởi người dùng khác.", "UTF-8"));
                    return;
                }
            } catch (SQLException e) {
                response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Lỗi khi kiểm tra số điện thoại: " + e.getMessage(), "UTF-8"));
                return;
            }
        }

        if (email != null && !email.equals(currentUser.getEmail())) {
            try {
                if (daoUser.isEmailExists(email)) {
                    response.sendRedirect("profile-alt?error=" + URLEncoder.encode("email đã được sử dụng bởi người dùng khác.", "UTF-8"));
                    return;
                }
            } catch (SQLException e) {
                response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Lỗi khi kiểm tra email: " + e.getMessage(), "UTF-8"));
                return;
            }
        }

        User updatedUser = new User(userId, email, currentUser.getPasswordHash(), phone, fullName, true,
                currentUser.getOtpCode(), currentUser.getOtpExpiry(), currentUser.getCreatedAt(), bio, currentUser.getImgPath(), currentUser.getPdfPath());

        if (daoUser.updateUser2(updatedUser)) {
            session.setAttribute("user", updatedUser);
            p_dao.updatePatientPhone(phone, userId);
            response.sendRedirect("profile-alt?message=" + URLEncoder.encode("Cập nhật hồ sơ thành công!", "UTF-8"));
        } else {
            response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Cập nhật hồ sơ thất bại.", "UTF-8"));
        }
    }

    private java.sql.Date parseDateOfBirth(String dobParam, HttpSession session) {
        if (dobParam == null || dobParam.trim().isEmpty()) {
            return null;
        }
        try {
            LocalDate localDate = LocalDate.parse(dobParam);
            return java.sql.Date.valueOf(localDate);
        } catch (java.time.format.DateTimeParseException e) {
            session.setAttribute("error", "Ngày sinh không hợp lệ."); // Lưu lỗi tạm thời để kiểm tra
            return null;
        }
    }

    private String handleFileUpload(HttpServletRequest request, String currentAvatar) throws IOException, ServletException {
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
            } else {
                return currentAvatar;
            }
        }
        return currentAvatar;
    }

    private String handlePdfUpload(HttpServletRequest request, String currentPdf) throws IOException, ServletException {
        Part filePart = request.getPart("pdf");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = extractFileName2(filePart);
            if (fileName != null && isImageFile(fileName)) {
                String uploadPath = getServletContext().getRealPath("") + "uploads/";
                java.io.File uploadDir = new java.io.File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                filePart.write(uploadPath + fileName);
                return "uploads/" + fileName;
            } else {
                return currentPdf;
            }
        }
        return currentPdf;
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

    private String extractFileName2(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        if (contentDisposition == null) {
            return null;
        }

        String[] items = contentDisposition.split(";");
        String fileName = null;

        // Extract original filename
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
                break;
            }
        }

        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        // Split filename and extension
        int lastDotIndex = fileName.lastIndexOf('.');
        String baseName = fileName;
        String extension = "";

        if (lastDotIndex != -1) {
            baseName = fileName.substring(0, lastDotIndex);
            extension = fileName.substring(lastDotIndex);
        }

        // Generate 6-digit random number
        int randomDigits = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);

        // Construct new filename
        return baseName + "_" + randomDigits + extension;
    }

    private boolean isImageFile(String fileName) {
        String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".pdf"};
        for (String ext : allowedExtensions) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    // Xóa các phương thức setError và setMessage vì không cần lưu vào session nữa
}
