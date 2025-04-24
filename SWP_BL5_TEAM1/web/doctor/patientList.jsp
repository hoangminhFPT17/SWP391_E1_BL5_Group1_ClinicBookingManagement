<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <title>Doctris - Patient List</title>
    <%@ include file="../assets/css/css-js.jsp" %>
    <style>
        .error { color: red; }
        tr.clickable-row { cursor: pointer; }
    </style>
</head>

<body>
    <div class="page-wrapper doctris-theme toggled">
        <%@ include file="../component/sideBar.jsp" %>

        <!-- Start Page Content -->
        <main class="page-content bg-light">
            <%@ include file="../component/header.jsp" %>
            <div class="container-fluid">
                <h1 class="mb-4">Patient List</h1>

                <c:if test="${not empty error}">
                    <p class="error mb-3">${error}</p>
                </c:if>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped">
                        <thead class="table-light">
                            <tr>
                                <th>Phone</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Gender</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="patient" items="${patients}">
                                <tr class="clickable-row"
                                    data-phone="${patient.phone}"
                                    data-patient-account-id="${patient.patientAccountId}"
                                    data-full-name="${patient.fullName}"
                                    data-email="${patient.email}"
                                    data-gender="${patient.gender}"
                                    data-date-of-birth='<fmt:formatDate value="${patient.dateOfBirth}" pattern="yyyy-MM-dd"/>'
                                    data-created-at='<fmt:formatDate value="${patient.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
                                    <td>${patient.phone}</td>
                                    <td>${patient.fullName}</td>
                                    <td>${patient.email}</td>
                                    <td>${patient.gender}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty patients}">
                                <tr>
                                    <td colspan="4" class="text-center">No patients found.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <!-- Bootstrap Modal for Patient Details -->
                <div class="modal fade" id="patientDetailModal" tabindex="-1" aria-labelledby="patientDetailModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="patientDetailModalLabel">Patient Details</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p><strong>Phone:</strong> <span id="modalPhone"></span></p>
                                <p><strong>Patient Account ID:</strong> <span id="modalPatientAccountId"></span></p>
                                <p><strong>Full Name:</strong> <span id="modalFullName"></span></p>
                                <p><strong>Email:</strong> <span id="modalEmail"></span></p>
                                <p><strong>Gender:</strong> <span id="modalGender"></span></p>
                                <p><strong>Date of Birth:</strong> <span id="modalDateOfBirth"></span></p>
                                <p><strong>Created At:</strong> <span id="modalCreatedAt"></span></p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
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

    <!-- JavaScript to Handle Modal -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const rows = document.querySelectorAll('.clickable-row');
            rows.forEach(row => {
                row.addEventListener('click', function () {
                    // Extract data from the row's data attributes
                    const phone = this.getAttribute('data-phone');
                    const patientAccountId = this.getAttribute('data-patient-account-id');
                    const fullName = this.getAttribute('data-full-name');
                    const email = this.getAttribute('data-email');
                    const gender = this.getAttribute('data-gender');
                    const dateOfBirth = this.getAttribute('data-date-of-birth');
                    const createdAt = this.getAttribute('data-created-at');

                    // Populate the modal with the data
                    document.getElementById('modalPhone').textContent = phone || 'Not provided';
                    document.getElementById('modalPatientAccountId').textContent = patientAccountId || 'Not provided';
                    document.getElementById('modalFullName').textContent = fullName || 'Not provided';
                    document.getElementById('modalEmail').textContent = email || 'Not provided';
                    document.getElementById('modalGender').textContent = gender || 'Not provided';
                    document.getElementById('modalDateOfBirth').textContent = dateOfBirth || 'Not provided';
                    document.getElementById('modalCreatedAt').textContent = createdAt || 'Not provided';

                    // Show the modal
                    const modal = new bootstrap.Modal(document.getElementById('patientDetailModal'));
                    modal.show();
                });
            });
        });
    </script>
</body>
</html>