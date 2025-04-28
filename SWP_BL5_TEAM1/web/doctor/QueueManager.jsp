<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
     <title>Queue Manager</title>
    <%@ include file="../assets/css/css-js.jsp" %>
    <style>
        .btn-toolbar { margin-bottom: 1rem; }
        .detail-table th { width: 200px; }
    </style>
</head>
<body>
<div class="page-wrapper doctris-theme toggled">
    <%@ include file="../component/doctorSideBar.jsp" %>
    <main class="page-content bg-light">
        <%@ include file="../component/header.jsp" %>
        <div class="container-fluid" style="margin-top:80px;">
            <h2>Next Patient in Queue</h2>

            <c:choose>
                <c:when test="${not empty appointmentDetail}">
                    <!-- Action buttons and modal form -->
                    <form id="actionForm" method="post" action="${pageContext.request.contextPath}/QueueManager" style="margin-bottom: 10px">
                        <input type="hidden" name="appointmentId" value="${appointmentDetail.appointmentId}"/>

                        <!-- Start: only if Pending -->
                        <button type="submit" name="action" value="start"
                                class="btn btn-primary"
                                <c:if test="${appointmentDetail.status ne 'Pending'}">disabled</c:if>>
                            Start
                        </button>

                        <!-- Send to Service: opens modal, only if In progress -->
                        <button type="button"
                                class="btn btn-secondary"
                                data-bs-toggle="modal"
                                data-bs-target="#handoffModal"
                                <c:if test="${appointmentDetail.status ne 'In progress' and appointmentDetail.status ne 'Back-from-hand-off'}">disabled</c:if>>
                            Send to Service
                        </button>

                        <!-- Medical Record: only if In progress -->
                        <button type="button" class="btn btn-info"
                                onclick="location.href='${pageContext.request.contextPath}/MedicalRecord?appointmentId=${appointmentDetail.appointmentId}'"
                                <c:if test="${appointmentDetail.status ne 'In progress'}">disabled</c:if>>
                            Medical Record
                        </button>

                        <!-- Finish and Next (modifiable as needed) -->
                        <button type="submit" name="action" value="finish"
                                class="btn btn-success"
                                <c:if test="${appointmentDetail.status ne 'In progress' and appointmentDetail.status ne 'Back-from-hand-off'}">disabled</c:if>>
                            Finish
                        </button>

                    </form>

                    <!-- Handoff Modal -->
                    <div class="modal fade" id="handoffModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <form method="post" action="${pageContext.request.contextPath}/QueueManager">
                                <input type="hidden" name="appointmentId" value="${appointmentDetail.appointmentId}"/>
                                <input type="hidden" name="action" value="sendService"/>
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Send to Service</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="toDoctorId" class="form-label">Assign to Doctor</label>
                                            <select id="toDoctorId" name="toDoctorId" class="form-select" required>
                                                <c:forEach var="doc" items="${doctorList}">
                                                    <c:if test="${doc.fullName ne appointmentDetail.doctorId}">
                                                        <option value="${doc.doctorId}">${doc.fullName}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="reason" class="form-label">Reason</label>
                                            <textarea id="reason" name="reason" class="form-control" rows="3" required></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-primary">Confirm</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Appointment details table -->
                    <table class="table table-bordered detail-table">
                        <tr><th>Appointment ID</th><td>${appointmentDetail.appointmentId}</td></tr>
                        <tr><th>Patient Name</th><td>${appointmentDetail.patientName}</td></tr>
                        <tr><th>Patient Phone</th><td>${appointmentDetail.patientPhone}</td></tr>
                        <tr><th>Doctor</th><td>${appointmentDetail.doctorId}</td></tr>
                        <tr><th>Slot</th><td>${appointmentDetail.slot}</td></tr>
                        <tr><th>Date</th><td><fmt:formatDate value="${appointmentDetail.appointmentDate}" pattern="yyyy-MM-dd"/></td></tr>
                        <tr><th>Status</th><td>${appointmentDetail.status}</td></tr>
                        <tr><th>Booked At</th><td><fmt:formatDate value="${appointmentDetail.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td></tr>
                        <tr><th>Description</th><td>${appointmentDetail.description}</td></tr>
                        <tr><th>Package</th><td>${appointmentDetail.examinationPackage}</td></tr>
                    </table>

                </c:when>
                <c:otherwise>
                    <p class="alert alert-info">No more appointments in the current time slot.</p>
                </c:otherwise>
            </c:choose>
</body>
</html>
