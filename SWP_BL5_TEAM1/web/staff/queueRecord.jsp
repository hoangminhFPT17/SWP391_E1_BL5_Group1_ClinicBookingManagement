<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Check up record</title>
        <%@ include file="../assets/css/css-js.jsp" %>
    </head>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <%@ include file="../component/staffSideBar.jsp" %>

            <!-- Start Page Content -->
            <main class="page-content bg-light">
                <%@ include file="../component/staffHeader.jsp" %>

                <div class="container-fluid">
                    <div class="row mt-4">

                        <div class="col">

                            <div class="row mt-4">
                                <div class="col text-center" style="margin-top:50px">
                                    <h3>Check up record</h3>
                                </div>
                            </div>

                            <div class="table-responsive">
                                <table class="table table-striped table-bordered">
                                    <thead class="thead-light">
                                        <tr>
                                            <th>Queue ID</th>
                                            <th>Patient Phone</th>
                                            <th>Patient Name</th>
                                            <th>Doctor</th>
                                            <th>Queue Date</th>
                                            <th>Status</th>
                                            <th>Arrival</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="dto" items="${patientQueueList}">
                                            <tr>
                                                <td>${dto.queueId}</td>
                                                <td>${dto.patientPhone}</td>
                                                <td>${dto.patientName}</td>
                                                <td>${dto.doctorName}</td>
                                                <td>${dto.queueDate}</td>
                                                <td>${dto.status}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty dto.arrivalTime}">
                                                            ${dto.arrivalTime}
                                                        </c:when>
                                                        <c:otherwise>
                                                            N/A
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <c:if test="${empty patientQueueList}">
                                    <p class="text-center text-muted">No queue records available.</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div><!--end container-->

            </main>
            <!--End page-content" -->
        </div>
        <!-- page-wrapper -->
    </body>
</html>
