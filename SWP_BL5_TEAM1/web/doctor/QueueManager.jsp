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
            .btn-toolbar {
                margin-bottom: 1rem;
            }
            .detail-table th {
                width: 200px;
            }
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
                            <form id="actionForm" method="post" action="${pageContext.request.contextPath}/QueueManager">
                                <input type="hidden" name="appointmentId" value="${appointmentDetail.appointmentId}"/>
                                <input type="hidden" name="patientPhone"  value="${appointmentDetail.patientPhone}"/>

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

                                        <!-- Add Record: only once In Progress -->
                                        <button type="button"
                                                class="btn btn-info"
                                                data-bs-toggle="modal"
                                                data-bs-target="#addRecordModal"
                                        <c:if test="${appointmentDetail.status ne 'In progress' && appointmentDetail.status ne 'Back-from-hand-off'}">disabled</c:if>>
                                            Add Record
                                        </button>

                                        <!-- Finish: only show if at least one record exists -->
                                <c:if test="${not empty medicalRecords}">
                                    <button type="submit" name="action" value="finish"
                                            class="btn btn-success">
                                        Finish
                                    </button>
                                </c:if>
                            </form>

                            <!-- Add Record Modal -->
                            <div class="modal fade" id="addRecordModal" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog">
                                    <form method="post" action="${pageContext.request.contextPath}/QueueManager">
                                        <input type="hidden" name="action"       value="addRecord"/>
                                        <input type="hidden" name="patientPhone" value="${appointmentDetail.patientPhone}"/>

                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Add Medical Record</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label for="diagnosis" class="form-label">Diagnosis</label>
                                                    <textarea id="diagnosis" name="diagnosis"
                                                              class="form-control" rows="2" required></textarea>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="prescription" class="form-label">Prescription</label>
                                                    <textarea id="prescription" name="prescription"
                                                              class="form-control" rows="2" required></textarea>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="notes" class="form-label">Notes</label>
                                                    <textarea id="notes" name="notes"
                                                              class="form-control" rows="3"></textarea>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button"
                                                        class="btn btn-secondary"
                                                        data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit"
                                                        class="btn btn-primary">Save Record</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

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
                            <!-- Medical Records Table -->
                            <c:if test="${not empty medicalRecords}">
                                <h3 class="mt-4">Patient Medical Records</h3>
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Record ID</th>
                                            <th>Diagnosis</th>
                                            <th>Prescription</th>
                                            <th>Notes</th>
                                            <th>Created At</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="rec" items="${medicalRecords}">
                                            <tr>
                                                <td>${rec.recordId}</td>
                                                <td>${rec.diagnosis}</td>
                                                <td>${rec.prescription}</td>
                                                <td>${rec.notes}</td>
                                                <td>
                                                    <fmt:formatDate value="${rec.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${empty medicalRecords}">
                                <p class="mt-4 alert alert-info">No medical records found for this patient.</p>
                            </c:if>

                        </c:when>
                        <c:otherwise>
                            <p class="alert alert-info">No more appointments in the current time slot.</p>
                        </c:otherwise>
                    </c:choose>
                    </body>
                    </html>
