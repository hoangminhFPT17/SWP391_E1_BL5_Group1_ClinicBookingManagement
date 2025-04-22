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
import java.sql.Date;
import java.net.URLEncoder; // Thêm để mã hóa tham số URL

/**
 * Servlet xử lý các yêu cầu liên quan đến hồ sơ người dùng, bao gồm xem hồ sơ, cập nhật thông tin và đổi mật khẩu.
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile-alt"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // Kích thước ngưỡng để lưu tạm file: 2MB
        maxFileSize = 1024 * 1024 * 10,      // Kích thước tối đa của một file: 10MB
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
        } else {
//            handleUpdateProfile(request, response, session, currentUser, daoUser);
        }
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, User currentUser, DAOUser daoUser) throws IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
      
            String encryptedCurrentPassword = currentPassword;
            if (!currentUser.getPasswordHash().equals(encryptedCurrentPassword)) {
                response.sendRedirect("profile-alt?error=" + URLEncoder.encode("Mật khẩu hiện tại không đúng."+currentPassword+"  "+currentUser.getPasswordHash()+"  "+newPassword+confirmPassword, "UTF-8"));
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

//    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response,
//            HttpSession session, User currentUser, DAOUser daoUser) throws IOException, ServletException {
//        int userId = currentUser.getUserID();
//        String fullName = request.getParameter("fullName");
//        String phone = request.getParameter("phone");
//        String dobParam = request.getParameter("dob");
//        String address = request.getParameter("address");
//        java.sql.Date sqlDob = parseDateOfBirth(dobParam, session);
//
//        if (sqlDob == null && session.getAttribute("error") != null) {
//            response.sendRedirect("profile?error=" + URLEncoder.encode("Ngày sinh không hợp lệ.", "UTF-8"));
//            return;
//        }
//
//        if (phone != null && !phone.trim().isEmpty() && !phone.equals(currentUser.getPhone())) {
//            try {
//                if (daoUser.isPhoneExist(phone, userId)) {
//                    response.sendRedirect("profile?error=" + URLEncoder.encode("Số điện thoại đã được sử dụng bởi người dùng khác.", "UTF-8"));
//                    return;
//                }
//            } catch (SQLException e) {
//                response.sendRedirect("profile?error=" + URLEncoder.encode("Lỗi khi kiểm tra số điện thoại: " + e.getMessage(), "UTF-8"));
//                return;
//            }
//        }
//
//        String avatarPath = handleFileUpload(request, currentUser.getAvatar());
//        User updatedUser = new User(userId, currentUser.getRoleID(), currentUser.getEmail(), fullName, phone,
//                currentUser.getCreateAt(), currentUser.getIsActive(), sqlDob, address,
//                avatarPath, currentUser.getUserName(), currentUser.getPassword());
//
//        if (daoUser.updateUser(updatedUser)) {
//            session.setAttribute("user", updatedUser);
//            response.sendRedirect("profile?message=" + URLEncoder.encode("Cập nhật hồ sơ thành công!", "UTF-8"));
//        } else {
//            response.sendRedirect("profile?error=" + URLEncoder.encode("Cập nhật hồ sơ thất bại.", "UTF-8"));
//        }
//    }

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

    // Xóa các phương thức setError và setMessage vì không cần lưu vào session nữa
}