/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.user;

/**
 *
 * @author JackGarland
 */
import dal.DAOToken;
import dal.DAOUser;
import model.Token;
import model.User;
import model.Patient;
import dal.PatientDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ActivateServlet", urlPatterns = {"/activate"})
public class ActivateServlet extends HttpServlet {

    private final DAOToken daoToken = new DAOToken();
    private final DAOUser daoUser = new DAOUser();
    private final PatientDAO patientdao = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            request.setAttribute("mess", "Link kích hoạt không hợp lệ.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin token từ bảng Token
        Token activationToken = daoToken.getTokenPassword(token); // Sử dụng Token
        ResetService service = new ResetService();

        if (activationToken == null) {
            request.setAttribute("mess", "Token không hợp lệ.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (activationToken.isUsed()) {
            request.setAttribute("mess", "Token đã được sử dụng.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (service.isExpireTime(activationToken.getExpiryTime())) {
            request.setAttribute("mess", "Token đã hết hạn.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            // Kích hoạt tài khoản
            boolean isActivated = daoUser.activateUser(activationToken.getUserId());
            if (isActivated) {
                // Đánh dấu token đã sử dụng
                activationToken.setUsed(true);
                daoToken.updateStatus(activationToken);

                // Lấy thông tin user để gửi email
                User user = daoUser.getUserById(activationToken.getUserId());
                if (user != null) {
                    // Gửi email thông báo tài khoản đã được kích hoạt
                    service.sendAccountActivatedEmail(user.getEmail(), user.getFullName());
                }

                request.setAttribute("mess", "Tài khoản đã được kích hoạt thành công. Vui lòng đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("mess", "Không thể kích hoạt tài khoản. Vui lòng thử lại.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mess", "Lỗi hệ thống khi kích hoạt tài khoản.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for account activation";
    }
}