<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Doctor Status Manager</title>
    <%@ include file="../assets/css/css-js.jsp" %>
</head>
<body>
<div class="page-wrapper doctris-theme toggled">
    <%@ include file="../component/staffSideBar.jsp" %>
    <main class="page-content bg-light">
        <%@ include file="../component/staffHeader.jsp" %>
        <div class="container-fluid py-4">
            <div class="row mt-4">
                <div class="col text-center" style="margin-top:50px">
                    <h3>Doctor Availability</h3>
                </div>
            </div>
            <div class="card shadow-sm mt-3">
                <div class="card-header bg-primary text-white">All Doctors Status</div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="thead-light">
                                <tr>
                                    <th>Doctor Name</th>
                                    <th>Department</th>
                                    <th>Status</th>
                                    <th>Current patient</th>
                                    <th>Next patient</th>
                                    <th>Assign</th>
                                    <th>Details</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="doc" items="${doctorStatusList}">
                                    <tr>
                                        <td>${doc.doctorName}</td>
                                        <td>${doc.department}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${doc.free}">
                                                    <span class="badge bg-success">Free</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger">Busy</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <!-- Current Priority: only show when doctor is busy -->
                                        <td>
                                            <c:choose>
                                                <c:when test="${!doc.free}">
                                                    ${doc.currentPatient}
                                                </c:when>
                                                <c:otherwise>
                                                    &mdash;
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <!-- Next Priority: always show if available -->
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty doc.nextPatient}">
                                                    ${doc.nextPatient}
                                                </c:when>
                                                <c:otherwise>
                                                    &mdash;
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <!-- Auto Assign -->
                                        <td>
                                            <form action="doctorStatusController" method="post" style="display:inline">
                                                <input type="hidden" name="autoAssignDoctorId" value="${doc.doctorId}" />
                                                <button type="submit"
                                                        class="btn btn-sm btn-success"
                                                        <c:if test="${!doc.free}">disabled</c:if>>
                                                    Assign
                                                </button>
                                            </form>
                                        </td>
                                        <!-- Details Button -->
                                        <td>
                                            <a href="patientQueueManager?doctorId=${doc.doctorId}"
                                               class="btn btn-sm btn-info">
                                                Details
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${empty doctorStatusList}">
                            <p class="text-center my-3">No doctors found.</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
