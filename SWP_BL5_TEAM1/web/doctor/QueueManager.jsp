<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Queue manager</title>
        <%@ include file="../assets/css/css-js.jsp" %>

    </head>

    <body>


        <div class="page-wrapper doctris-theme toggled">
            <%@ include file="../component/doctorSideBar.jsp" %>

            <!-- Start Page Content -->
            <main class="page-content bg-light">
                <%@ include file="../component/doctorHeader.jsp" %>
                <div class="container-fluid">
                    <div class="d-flex justify-content-between align-items-center mb-3" style="margin-top:50px;">
                        <h2 class="mb-0">Queue manager in thhis time slot</h2>

                        
                    </div>
                    
                    <table class="table table-bordered table-striped">
                            <thead class="thead-light">
                                <tr>
                                    <th>#</th>
                                    <th>Patient Name</th>
                                    <th>DOB</th>
                                    <th>Appointment Date</th>
                                    <th>Time Slot</th>
                                    <th>Doctor</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="app" items="${appointments}">
                                    <tr>
                                        <td>${app.index}</td>
                                        <td>${app.patientName}</td>
                                        <td><fmt:formatDate value="${app.patientDateOfBirth}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${app.appointmentDate}" pattern="yyyy-MM-dd"/></td>
                                <td>${app.timeSlotName}</td>
                                <td>${app.doctorFullName}</td>
                                <td>${app.status}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty appointments}">
                                <tr>
                                    <td colspan="7" class="text-center text-muted">
                                        No appointments in this time slot.
                                    </td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>

                </div><!--end container-->


                <!-- End -->
            </main>
            <!--End page-content" -->
        </div>
    </body>
</html>