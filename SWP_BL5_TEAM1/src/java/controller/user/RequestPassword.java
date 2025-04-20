/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.user;

/**
 *
 * @author JackGarland
 */
import dal.DAOToken; // Đổi từ DAOTokenForget thành DAOToken
import dal.DAOUser;
import model.Token; // Sử dụng entity.Token
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@WebServlet(name="requestPassword", urlPatterns={"/requestPassword"})
public class RequestPassword extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet requestPassword</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet requestPassword at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAOUser daoUser = new DAOUser();
        String email = request.getParameter("email");
        // Email có tồn tại trong db
        User user = daoUser.getUserByEmail(email);
        if(user == null) {
            request.setAttribute("mess", "Email không tồn tại");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }
        ResetService service = new ResetService();
        String token = service.generateToken();
        
        String linkReset = "http://localhost:8080/SWP_BL5_TEAM1/resetPassword?token="+token;
        
        Token newToken = new Token( // Sử dụng Token
                user.getUserId(), false, token, service.expireDateTime()
        );
        
        // Gửi link đến email này
        DAOToken daoToken = new DAOToken();
        boolean isInsert = daoToken.insertTokenForget(newToken);
        if(!isInsert) {
            request.setAttribute("mess", "Có lỗi trong server");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }
        boolean isSend = service.sendEmail(email, linkReset, user.getFullName());
        if(!isSend) {
            request.setAttribute("mess", "Không thể gửi yêu cầu");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }
        request.setAttribute("mess", "Gửi yêu cầu thành công");
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public static void main(String[] args) {
        ResetService service = new ResetService();
        String token = service.generateToken();
        
        Token newToken = new Token( // Sử dụng Token
                1, false, token, service.expireDateTime()
        );
        
        // Gửi link đến email này
        DAOToken daoToken = new DAOToken();
        System.out.println(daoToken.insertTokenForget(newToken));
    }
}