<%-- 
    Document   : patientDashboard
    Created on : 15 Apr 2025, 15:21:02
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/jsp/includes/patientHead.jsp" />
        <title>Patient Dashboard</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/jsp/common/patientHeaderNav.jsp" />

        <div class="container mt-4">
            <h1 class="text-primary">Hello, Patient!</h1>
            <p class="lead">Welcome to your dashboard.</p>
        </div>

        <jsp:include page="scripts.jsp" />
    </body>
</html>

