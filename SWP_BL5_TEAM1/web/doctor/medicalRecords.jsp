<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <title>Doctris - Medical Records</title>
    <%@ include file="../assets/css/css-js.jsp" %>
    <style>
        .error { color: red; }
    </style>
</head>

<body>
    <div class="page-wrapper doctris-theme toggled">
        <%@ include file="../component/sideBar.jsp" %>

        <!-- Start Page Content -->
        <main class="page-content bg-light">
            <%@ include file="../component/header.jsp" %>
            <div class="container-fluid">
                <h1 class="mb-4">All Medical Records</h1>

                <c:if test="${not empty error}">
                    <p class="error mb-3">${error}</p>
                </c:if>

                <form method="get" action="${pageContext.request.contextPath}/doctor/medical-records" class="mb-4">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-3">
                            <label for="patientPhone" class="form-label">Filter by Patient Phone:</label>
                            <input type="text" id="patientPhone" name="patientPhone" value="${patientPhone}" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label for="startDate" class="form-label">Start Date:</label>
                            <input type="date" id="startDate" name="startDate" value="${startDate}" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label for="endDate" class="form-label">End Date:</label>
                            <input type="date" id="endDate" name="endDate" value="${endDate}" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-primary w-100">Filter</button>
                        </div>
                    </div>
                </form>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped">
                        <thead class="table-light">
                            <tr>
                                <th>Patient Phone</th>
                                <th>Record ID</th>
                                <th>Diagnosis</th>
                                <th>Prescription</th>
                                <th>Notes</th>
                                <th>Created At</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="record" items="${records}">
                                <tr>
                                    <td>${record.patientPhone}</td>
                                    <td>${record.recordId}</td>
                                    <td>${record.diagnosis}</td>
                                    <td>${record.prescription}</td>
                                    <td>${record.notes}</td>
                                    <td><fmt:formatDate value="${record.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty records}">
                                <tr>
                                    <td colspan="6" class="text-center">No records found.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div><!--end container-->

            <!-- Footer Start -->
            <footer class="bg-white shadow py-3">
                <div class="container-fluid">
                    <div class="row align-items-center">
                        <div class="col">
                            <div class="text-sm-start text-center">
                                <p class="mb-0 text-muted"><script>document.write(new Date().getFullYear())</script> © Doctris. Design with <i class="mdi mdi-heart text-danger"></i> by <a href="../../../index.html" target="_blank" class="text-reset">Shreethemes</a>.</p>
                            </div>
                        </div><!--end col-->
                    </div><!--end row-->
                </div><!--end container-->
            </footer><!--end footer-->
            <!-- End -->
        </main>
        <!--End page-content-->
    </div>
</body>
</html>