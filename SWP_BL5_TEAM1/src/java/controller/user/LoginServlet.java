/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.user;

/**
 *
 * @author JackGarland
 */
import dal.DAOHistoryLog;
import dal.DAOUser;
import model.GoogleAccount;
import model.User;
import dal.DAOToken;
import dal.PatientAccountDAO;
import dal.StaffAccountDAO;
import model.Token;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.PatientAccount;
import model.StaffAccount;
import util.MD5Util; // Import MD5Util

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);
        DAOUser dao = new DAOUser();
        DAOHistoryLog logDAO = new DAOHistoryLog();

        String service = request.getParameter("service");

        if (service == null) {
            service = "googleLogin";
        }

        switch (service) {
            case "googleLogin":
                handleGoogleLogin(request, response, session, dao, logDAO);
                break;

            case "loginUser":
                handleUserLogin(request, response, session, dao, logDAO);
                break;

            default:
                request.setAttribute("error", "Dịch vụ đăng nhập không hợp lệ.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;
        }
    }

    private void handleGoogleLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, DAOUser dao, DAOHistoryLog logDAO)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if (error != null) {
            request.setAttribute("error", "Bạn đã từ chối quyền truy cập Google");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (code != null) {
            GoogleLogin gg = new GoogleLogin();
            String accessToken = gg.getToken(code);
            GoogleAccount googleAccount = gg.getUserInfo(accessToken);

            if (googleAccount != null) {
                User user = dao.getUserByEmail(googleAccount.getEmail());

                if (user == null) {
                    request.setAttribute("error", "Tài khoản Google chưa được đăng ký trong hệ thống.");
                    session.setAttribute("googleAccount", googleAccount);
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                } else if (user.isIsVerified() == false) {
//                    handleDeactivatedAccount(user, request, response);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.getUserId());
                    try {
                        logDAO.logLogin(user.getUserId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    redirectBasedOnRole(user, request, response);
                }
            } else {
                request.setAttribute("error", "Không lấy được thông tin tài khoản Google.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void handleUserLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, DAOUser dao, DAOHistoryLog logDAO)
            throws ServletException, IOException {

        String submit = request.getParameter("submit");
        if (submit == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            String loginInput = request.getParameter("loginInput");
            String password = request.getParameter("password");

            System.out.println("Login attempt: loginInput=" + loginInput + ", password=" + password);

            User user = dao.Login(loginInput, password);
            if (user == null) {
                System.out.println("Login failed: No user found or incorrect credentials");
                request.setAttribute("error", "Thông tin đăng nhập hoặc mật khẩu không chính xác");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else if (!user.isIsVerified()) {
                System.out.println("Login failed: User is deactivated, UserID=" + user.getUserId());
                request.setAttribute("error", "Tài khoản của bạn đã bị vô hiệu hóa.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());

                try {
                    logDAO.logLogin(user.getUserId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Check whether user is staff or patient
                StaffAccountDAO staffAccountDAO = new StaffAccountDAO();
                PatientAccountDAO patientAccountDAO = new PatientAccountDAO();

                StaffAccount staff = staffAccountDAO.getStaffByUserId(user.getUserId());
                //PatientAccount patient = patientAccountDAO.getPatientByUserId(user.getUserId());

                if (staff != null) {
                    session.setAttribute("staffAccount", staff);
                    switch (staff.getRole()) {
                        case "Manager":
                            response.sendRedirect("ManagerAnalyticServlet");
                            break;
                        case "Receptionist":
                            response.sendRedirect("PatientCheckin");
                            break;
                        case "Doctor":
                            response.sendRedirect("QueueManager");
                            break;
                        default:
                            request.setAttribute("error", "Vai trò nhân viên không xác định.");
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                            break;
                    }
                } else {
                    //session.setAttribute("patientAccount", patient);
                    response.sendRedirect("home.jsp");
                }
            }
        }
    }

    // Phương thức xử lý tài khoản bị deactivate
//    private void handleDeactivatedAccount(User user, HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        if (user.getRoleID() == 4) {
//            request.setAttribute("error", "Tài khoản của bạn đã bị vô hiệu hóa. Liên hệ với quản lý để có thể kích hoạt lại tài khoản.");
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//        } else if (user.getRoleID() == 2 || user.getRoleID() == 3) {
    ////            sendActivationReminder(user, request, response);
//        } else {
//            request.setAttribute("error", "Tài khoản của bạn đã bị vô hiệu hóa. Vui lòng liên hệ hỗ trợ.");
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//        }
//    }
    // Phương thức gửi email nhắc nhở kích hoạt
//    private void sendActivationReminder(User user, HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        resetService service = new resetService();
//        DAOToken daoToken = new DAOToken();
//
//        // Tạo token mới
//        String token = service.generateToken();
//        Token newToken = new Token(
//                user.getUserID(), false, token, service.expireDateTime()
//        );
//
//        // Lưu token vào bảng Token
//        boolean isTokenSaved = daoToken.insertTokenForget(newToken);
//        if (!isTokenSaved) {
//            request.setAttribute("error", "Tài khoản chưa được kích hoạt. Lỗi khi tạo link kích hoạt mới.");
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//            return;
//        }
//
//        // Gửi email nhắc nhở
//        String activationLink = "http://localhost:9999/SWP391_Group4_TutorManagement/activate?token=" + token;
//        boolean isEmailSent = service.sendActivationEmail(user.getEmail(), activationLink, user.getFullName());
//
//        if (isEmailSent) {
//            request.setAttribute("error", "Tài khoản chưa được kích hoạt. Link kích hoạt đã được gửi tới email của bạn.");
//        } else {
//            request.setAttribute("error", "Tài khoản chưa được kích hoạt. Không thể gửi email kích hoạt, vui lòng thử lại sau.");
//        }
//        request.getRequestDispatcher("login.jsp").forward(request, response);
//    }
    private void redirectBasedOnRole(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int roleId = user.getUserId();
        String contextPath = request.getContextPath();

        switch (roleId) {
            case 1:
                response.sendRedirect(contextPath + "/home.jsp");
                break;
            case 2:
                response.sendRedirect(contextPath + "/home.jsp");
                break;
            case 3:
                response.sendRedirect(contextPath + "/home.jsp");
                break;
            case 4:
                response.sendRedirect(contextPath + "/home.jsp");
                break;
            default:
                response.sendRedirect(contextPath + "/home.jsp");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }
}
