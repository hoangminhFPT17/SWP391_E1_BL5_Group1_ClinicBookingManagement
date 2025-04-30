<%-- 
    Document   : patientQueueViewer
    Created on : 16 Apr 2025, 08:15:37
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/includes/patientHead.jsp" />
        <title>Queue viewer</title>
    </head>
    <body>

        <%
            java.time.LocalDate today = java.time.LocalDate.now();
            request.setAttribute("currentDate", today.toString());
        %>
        <jsp:useBean id="now" class="java.util.Date" scope="page" />
        <jsp:include page="/common/patientHeaderNav.jsp" />


        <!-- Start Hero -->
        <section class="bg-dashboard">
            <div class="container-fluid">
                <c:choose>
                    <c:when test="${not empty queueInfo}">
                        <h3>Your Appointment Today</h3>
                        <table cellpadding="8">
                            <tr><th>Queue #</th><td>${queueInfo.queueNumber}</td></tr>
                            <tr><th>Doctor</th><td>${queueInfo.doctorName}</td></tr>
                            <tr><th>Slot</th><td>${queueInfo.slot}</td></tr>
                            <tr><th>Date</th><td><fmt:formatDate value="${queueInfo.appointmentDate}" pattern="yyyy-MM-dd"/></td></tr>
                            <tr><th>Checked In At</th><td><fmt:formatDate value="${queueInfo.createdAt}" pattern="HH:mm:ss"/></td></tr>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p class="alert alert-info">
                            You currently have <strong>no “Waiting”</strong> appointment today,
                            or you have not checked in yet.
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
        <!-- Back to top -->
        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i></a>
        <!-- Back to top -->

        <jsp:include page="/component/footer.jsp" />
        <!-- javascript -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
        <!-- Select2 -->
        <script src="${pageContext.request.contextPath}/assets/js/select2.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/select2.init.js"></script>
        <!-- Datepicker -->
        <script src="${pageContext.request.contextPath}/assets/js/flatpickr.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/flatpickr.init.js"></script>
        <!-- Datepicker -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.timepicker.min.js"></script> 
        <script src="${pageContext.request.contextPath}/assets/js/timepicker.init.js"></script> 
        <!-- Icons -->
        <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    </body>
</html>
