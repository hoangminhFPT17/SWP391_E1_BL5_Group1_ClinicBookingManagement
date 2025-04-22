
<%-- 
    Document   : MedicalRecordManagement
    Created on : Apr 21, 2025
    Author     : [Your Name]
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Manage Medical Records | ClinicBooking</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/medical.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <jsp:include page="../cpn/header.jsp" />
        <div class="container medical-history-container">
            <h2 class="page-title">Manage Medical Records</h2>

            <!-- Display Messages -->
            <c:if test="${not empty message}">
                <div class="alert alert-info" role="alert">
                    ${message}
                </div>
            </c:if>

            <!-- CRUD Operations -->
            <c:choose>
                <c:when test="${action == 'list' || empty action}">
                    <!-- List Records -->
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4>Patient Phone: ${patientPhone}</h4>
                        <a href="${pageContext.request.contextPath}/MedicalRecordManager?action=create&patientPhone=${patientPhone}" 
                           class="btn btn-primary">
                            <i class="fas fa-plus"></i> Add New Record
                        </a>
                    </div>
                    <c:choose>
                        <c:when test="${empty records}">
                            <div class="empty-state">
                                <i class="fas fa-notes-medical"></i>
                                <h3>No Medical Records Found</h3>
                                <p class="text-muted">No medical records exist for this patient. Click "Add New Record" to create one.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <c:forEach var="record" items="${records}">
                                    <div class="col-md-6 col-lg-4">
                                        <div class="card medical-record-card">
                                            <div class="card-header bg-primary text-white">
                                                <h5 class="card-title mb-0">Medical Record #${record.recordId}</h5>
                                            </div>
                                            <div class="card-body">
                                                <p class="record-date"><i class="far fa-calendar-alt"></i> ${record.createdAt}</p>
                                                <div class="mb-3">
                                                    <div class="section-title">Diagnosis</div>
                                                    <p>${record.diagnosis}</p>
                                                </div>
                                                <div class="mb-3">
                                                    <div class="section-title">Prescription</div>
                                                    <p>${record.prescription}</p>
                                                </div>
                                                <c:if test="${not empty record.notes}">
                                                    <div>
                                                        <div class="section-title">Notes</div>
                                                        <p>${record.notes}</p>
                                                    </div>
                                                </c:if>
                                                <div class="d-flex justify-content-between mt-3">
                                                    <a href="${pageContext.request.contextPath}/MedicalRecordManager?action=view&recordId=${record.recordId}" 
                                                       class="btn btn-sm btn-info">
                                                        <i class="fas fa-eye"></i> View
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/MedicalRecordManager?action=edit&recordId=${record.recordId}" 
                                                       class="btn btn-sm btn-warning">
                                                        <i class="fas fa-edit"></i> Edit
                                                    </a>
                                                    <form action="${pageContext.request.contextPath}/MedicalRecordManager" method="post" 
                                                          onsubmit="return confirm('Are you sure you want to delete this record?');">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="recordId" value="${record.recordId}">
                                                        <input type="hidden" name="patientPhone" value="${patientPhone}">
                                                        <button type="submit" class="btn btn-sm btn-danger">
                                                            <i class="fas fa-trash"></i> Delete
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:when test="${action == 'view'}">
                    <!-- View Record -->
                    <div class="card medical-record-card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="card-title mb-0">Medical Record #${record.recordId}</h5>
                        </div>
                        <div class="card-body">
                            <p><strong>Date:</strong> ${record.createdAt}</p>
                            <p><strong>Patient Phone:</strong> ${record.patientPhone}</p>
                            <p><strong>Diagnosis:</strong> ${record.diagnosis}</p>
                            <p><strong>Prescription:</strong> ${record.prescription}</p>
                            <c:if test="${not empty record.notes}">
                                <p><strong>Notes:</strong> ${record.notes}</p>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/MedicalRecordManager?patientPhone=${record.patientPhone}" 
                               class="btn btn-secondary">Back to List</a>
                        </div>
                    </div>
                </c:when>

                <c:when test="${action == 'create'}">
                    <!-- Create Record -->
                    <form action="${pageContext.request.contextPath}/MedicalRecordManager" method="post">
                        <input type="hidden" name="action" value="create">
                        <div class="mb-3">
                            <label for="patientPhone" class="form-label">Patient Phone</label>
                            <input type="text" class="form-control" id="patientPhone" name="patientPhone" 
                                   value="${patientPhone}" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="diagnosis" class="form-label">Diagnosis</label>
                            <textarea class="form-control" id="diagnosis" name="diagnosis" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="prescription" class="form-label">Prescription</label>
                            <textarea class="form-control" id="prescription" name="prescription" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="notes" class="form-label">Notes</label>
                            <textarea class="form-control" id="notes" name="notes"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Create Record</button>
                        <a href="${pageContext.request.contextPath}/MedicalRecordManager?patientPhone=${patientPhone}" 
                           class="btn btn-secondary">Cancel</a>
                    </form>
                </c:when>

                <c:when test="${action == 'edit'}">
                    <!-- Edit Record -->
                    <form action="${pageContext.request.contextPath}/MedicalRecordManager" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="recordId" value="${record.recordId}">
                        <div class="mb-3">
                            <label for="patientPhone" class="form-label">Patient Phone</label>
                            <input type="text" class="form-control" id="patientPhone" name="patientPhone" 
                                   value="${record.patientPhone}" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="diagnosis" class="form-label">Diagnosis</label>
                            <textarea class="form-control" id="diagnosis" name="diagnosis" required>${record.diagnosis}</textarea>
                        </div>
                        <div class="mb-3">
                            <label for="prescription" class="form-label">Prescription</label>
                            <textarea class="form-control" id="prescription" name="prescription" required>${record.prescription}</textarea>
                        </div>
                        <div class="mb-3">
                            <label for="notes" class="form-label">Notes</label>
                            <textarea class="form-control" id="notes" name="notes">${record.notes}</textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Update Record</button>
                        <a href="${pageContext.request.contextPath}/MedicalRecordManager?patientPhone=${record.patientPhone}" 
                           class="btn btn-secondary">Cancel</a>
                    </form>
                </c:when>
            </c:choose>
        </div>
        <jsp:include page="../cpn/footer.jsp" />
        <script>
            $(document).ready(function() {
                $('.dropdown-toggle').dropdown();
            });
            document.addEventListener('DOMContentLoaded', function() {
                var dropdownToggleList = [].slice.call(document.querySelectorAll('[data-bs-toggle="dropdown"]'));
                dropdownToggleList.forEach(function(dropdownToggle) {
                    var dropdown = new bootstrap.Dropdown(dropdownToggle);
                });
                document.querySelectorAll('.dropdown-toggle').forEach(function(element) {
                    element.addEventListener('click', function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        var dropdownMenu = this.nextElementSibling;
                        if (dropdownMenu.classList.contains('show')) {
                            dropdownMenu.classList.remove('show');
                        } else {
                            document.querySelectorAll('.dropdown-menu.show').forEach(function(menu) {
                                menu.classList.remove('show');
                            });
                            dropdownMenu.classList.add('show');
                        }
                    });
                });
                document.addEventListener('click', function(e) {
                    if (!e.target.closest('.dropdown')) {
                        document.querySelectorAll('.dropdown-menu.show').forEach(function(menu) {
                            menu.classList.remove('show');
                        });
                    }
                });
            });
        </script>
    </body>
</html>
