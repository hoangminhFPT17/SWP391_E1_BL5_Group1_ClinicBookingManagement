<%-- 
    Document   : managerAnalytic
    Created on : 28 Apr 2025, 00:56:45
    Author     : LENOVO
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/includes/patientHead.jsp" />
        <!-- Load Google Charts -->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

        <!-- Pass data into a JS variable -->
        <script type="text/javascript"> //god I hate ugly java code in frontend
            var timeSlotBookedCountMap = {};
            <%
                Map<String, Integer> map = (Map<String, Integer>) request.getAttribute("timeSlotBookedCountMap");
                if (map != null) {
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
            %>
            timeSlotBookedCountMap["<%= entry.getKey()%>"] = <%= entry.getValue()%>;
            <%
                    }
                }
            %>

            var appointmentCountByDoctor = [];
            <%
                List<Map<String, Object>> appointmentList = (List<Map<String, Object>>) request.getAttribute("appointmentCountByDoctor");
                if (appointmentList != null) {
                    for (Map<String, Object> data : appointmentList) {
            %>
            appointmentCountByDoctor.push({
                doctor_name: "<%= data.get("doctor_name")%>",
                appointment_count: <%= data.get("appointment_count")%>
            });
            <%
                    }
                }
            %>
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/manager_analytic.js"></script>
        <title>Manager Analytics</title>
    </head>
    <body>
        <div class="col-xl-4 col-lg-5 mt-4">
            <div class="card shadow border-0 p-4" style="height: 500px; overflow: hidden;">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h6 class="align-items-center mb-0">Time Slot by Amount Booked</h6>
                </div>

                <!-- Chart goes inside here -->
                <div id="piechart" style="height: 100%; width: 100%;"></div>
            </div>
        </div>

        <div class="col-xl-4 col-lg-5 mt-4">
            <div class="card shadow border-0 p-4" style="height: 450px;">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h6 class="align-items-center mb-0">Appointments by Doctor</h6>
                </div>

                <!-- Chart goes inside here -->
                <div id="columnchart" style="height: 100%; width: 100%;"></div>
            </div>
        </div><!--end col-->
    </body>
</html>
