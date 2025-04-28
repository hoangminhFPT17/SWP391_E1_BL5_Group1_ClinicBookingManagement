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
        tr.clickable-row { cursor: pointer; }
    </style>
</head>

<body>
    <div class="page-wrapper doctris-theme toggled">
        <%@ include file="../component/doctorSideBar.jsp" %>

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
                                <tr class="clickable-row" 
                                    data-record-id="${record.recordId}" 
                                    data-patient-phone="${record.patientPhone}" 
                                    data-diagnosis="${record.diagnosis}" 
                                    data-prescription="${record.prescription}" 
                                    data-notes="${record.notes}" 
                                    data-created-at='<fmt:formatDate value="${record.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
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

                <!-- Bootstrap Modal for Record Details -->
                <div class="modal fade" id="recordDetailModal" tabindex="-1" aria-labelledby="recordDetailModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="recordDetailModalLabel">Medical Record Details</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p><strong>Record ID:</strong> <span id="modalRecordId"></span></p>
                                <p><strong>Patient Phone:</strong> <span id="modalPatientPhone"></span></p>
                                <p><strong>Diagnosis:</strong> <span id="modalDiagnosis"></span></p>
                                <p><strong>Prescription:</strong> <span id="modalPrescription"></span></p>
                                <p><strong>Notes:</strong> <span id="modalNotes"></span></p>
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
                    const recordId = this.getAttribute('data-record-id');
                    const patientPhone = this.getAttribute('data-patient-phone');
                    const diagnosis = this.getAttribute('data-diagnosis');
                    const prescription = this.getAttribute('data-prescription');
                    const notes = this.getAttribute('data-notes');
                    const createdAt = this.getAttribute('data-created-at');

                    // Populate the modal with the data
                    document.getElementById('modalRecordId').textContent = recordId;
                    document.getElementById('modalPatientPhone').textContent = patientPhone;
                    document.getElementById('modalDiagnosis').textContent = diagnosis;
                    document.getElementById('modalPrescription').textContent = prescription;
                    document.getElementById('modalNotes').textContent = notes;
                    document.getElementById('modalCreatedAt').textContent = createdAt;

                    // Show the modal
                    const modal = new bootstrap.Modal(document.getElementById('recordDetailModal'));
                    modal.show();
                });
            });
        });
    </script>
</body>
</html>