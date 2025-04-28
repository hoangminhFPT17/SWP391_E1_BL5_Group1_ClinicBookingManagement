<%-- 
    Document   : paymentResult
    Created on : Apr 29, 2025, 1:18:01 AM
    Author     : JackGarland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>D@</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" 
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }
            .container {
                text-align: center;
                background: white;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                max-width: 500px;
                width: 100%;
            }
            .container img {
                width: 120px;
                height: 120px;
                margin-bottom: 20px;
            }
            .container h3 {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 15px;
            }
            .success h3 {
                color: #28a745;
            }
            .success i {
                color: #28a745;
            }
            .failed h3 {
                color: #dc3545;
            }
            .failed i {
                color: #dc3545;
            }
            .processing h3 {
                color: #ffc107;
            }
            .processing i {
                color: #ffc107;
            }
            .btn {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-size: 16px;
                transition: background-color 0.3s;
            }
            .btn:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div>
                <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:150:0/q:90/plain/https://cellphones.com.vn/media/wysiwyg/Review-empty.png" 
                     alt="Transaction Status" 
                     style="width: 120px; height: 120px; margin-bottom: 20px;">
            </div>

            <!-- Giao dịch thành công -->
            <c:if test="${transResult}">
                <div class="success">
                    <h3>
                        Bạn đã giao dịch thành công! 
                        <i class="fas fa-check-circle"></i>
                    </h3>
                </div>
            </c:if>

            <!-- Giao dịch thất bại -->
            <c:if test="${transResult == false}">
                <div class="failed">
                    <h3>
                        Đơn hàng giao dịch thất bại!
                        <i class="fas fa-times-circle"></i>
                    </h3>
                </div>
            </c:if>

            <!-- Đang xử lý giao dịch -->
            <c:if test="${transResult == null}">
                <div class="processing">
                    <h3>
                        Đang xử lý giao dịch, vui lòng chờ!
                        <i class="fas fa-spinner fa-spin"></i>
                    </h3>
                </div>
            </c:if>

            <!-- Nút Quay lại -->
            <a href="PatientInvoiceServlet" class="btn">Return</a>
        </div>
    </body>
</html>