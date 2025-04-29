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
        :root {
            --primary-color: #007bff;
            --text-color: #333;
            --border-color: #e9ecef;
            --bg-light: #f8f9fa;
            --hover-bg: #e6f0fa;
            --error-color: #dc3545;
        }

        .table-responsive {
            margin-top: 1.5rem;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        }

        .table {
            margin-bottom: 0;
            font-size: 0.95rem;
            border-collapse: separate;
            border-spacing: 0;
        }

        .table thead th {
            background-color: var(--bg-light);
            color: var(--text-color);
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            padding: 1rem;
            border-bottom: 2px solid var(--border-color);
        }

        .table tbody tr {
            transition: background-color 0.2s ease;
        }

        .table tbody tr:hover {
            background-color: var(--hover-bg);
        }

        .table td {
            padding: 1rem;
            vertical-align: middle;
            border-top: 1px solid var(--border-color);
            color: var(--text-color);
        }

        .clickable-row {
            cursor: pointer;
        }

        .clickable-row:focus {
            outline: 2px solid var(--primary-color);
            outline-offset: -2px;
        }

        .error {
            color: var(--error-color);
            font-weight: 500;
            margin-bottom: 1rem;
        }

        .modal-content {
            border-radius: 12px;
            border: none;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }

        .modal-header {
            background-color: var(--bg-light);
            border-bottom: 1px solid var(--border-color);
            padding: 1.25rem 1.5rem;
        }

        .modal-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: var(--text-color);
        }

        .modal-body {
            padding: 1.5rem;
            font-size: 0.95rem;
            line-height: 1.6;
        }

        .modal-body h6 {
            font-size: 1.1rem;
            font-weight: 600;
            color: var(--text-color);
            margin-bottom: 1rem;
            border-left: 3px solid var(--primary-color);
            padding-left: 0.75rem;
        }

        .modal-body p {
            margin-bottom: 0.75rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-body p strong {
            flex: 0 0 30%;
            color: var(--text-color);
            font-weight: 500;
        }

        .modal-body p span {
            flex: 0 0 65%;
            color: #555;
        }

        .modal-body hr {
            margin: 1.5rem 0;
            border-color: var(--border-color);
        }

        .spinner {
            width: 28px;
            height: 28px;
            border: 4px solid var(--primary-color);
            border-top-color: transparent;
            border-radius: 50%;
            animation: spin 0.8s linear infinite;
            margin: 1rem auto;
        }

        .hidden {
            display: none;
        }

        @keyframes spin {
            to {
                transform: rotate(360deg);
            }
        }

        #apptsContent .table {
            font-size: 0.9rem;
            background-color: #fff;
        }

        #apptsContent .table th,
        #apptsContent .table td {
            padding: 0.75rem;
        }

        #apptsContent .table th {
            background-color: var(--bg-light);
            font-weight: 600;
        }

        @media (max-width: 768px) {
            .table thead {
                display: none;
            }

            .table tbody tr {
                display: block;
                margin-bottom: 1rem;
                border: 1px solid var(--border-color);
                border-radius: 6px;
            }

            .table td {
                display: flex;
                justify-content: space-between;
                padding: 0.75rem;
                border: none;
                border-bottom: 1px solid var(--border-color);
            }

            .table td:before {
                content: attr(data-label);
                font-weight: 500;
                color: var(--text-color);
                flex: 0 0 40%;
            }

            .modal-dialog {
                margin: 0.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="page-wrapper doctris-theme toggled">
        <%@ include file="../component/sideBar.jsp" %>
        <main class="page-content bg-light">
            <%@ include file="../component/header.jsp" %>
            <div class="container-fluid">
                <h1 class="mb-4">Patient List</h1>
                <c:if test="${not empty error}">
                    <p class="error mb-3">${error}</p>
                </c:if>

                <!-- Search and Filter Controls -->
                <form class="mb-3" id="searchForm">
                    <div class="row">
                        <div class="col-md-4">
                            <input type="text" class="form-control" id="searchPhone" placeholder="Search by Phone" value="${param.phone}">
                        </div>
                        <div class="col-md-3">
                            <select class="form-select" id="filterGender">
                                <option value="">All Genders</option>
                                <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                                <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                                <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </form>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>Phone</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Gender</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="patient" items="${patients}">
                                <tr class="clickable-row"
                                    data-phone="${patient.phone}"
                                    data-full-name="${patient.fullName}"
                                    data-email="${patient.email}"
                                    data-gender="${patient.gender}"
                                    data-date-of-birth="<fmt:formatDate value='${patient.dateOfBirth}' pattern='yyyy-MM-dd'/>"
                                    data-created-at="<fmt:formatDate value='${patient.createdAt}' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                    <td data-label="Phone">${patient.phone}</td>
                                    <td data-label="Full Name">${patient.fullName}</td>
                                    <td data-label="Email">${patient.email}</td>
                                    <td data-label="Gender">${patient.gender}</td>
                                    <td>
                                        <button class="btn btn-sm btn-primary edit-btn" data-bs-toggle="modal" data-bs-target="#editPatientModal">Edit</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty patients}">
                                <tr>
                                    <td colspan="5" class="text-center">No patients found.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <!-- Pagination -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage - 1}&phone=${param.phone}&gender=${param.gender}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}&phone=${param.phone}&gender=${param.gender}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage + 1}&phone=${param.phone}&gender=${param.gender}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>

                <!-- Edit Modal -->
                <div class="modal fade" id="editPatientModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Edit Patient</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <form id="editPatientForm">
                                    <input type="hidden" id="editPhone">
                                    <div class="mb-3">
                                        <label for="editFullName" class="form-label">Full Name</label>
                                        <input type="text" class="form-control" id="editFullName" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="editEmail" class="form-label">Email</label>
                                        <input type="email" class="form-control" id="editEmail">
                                    </div>
                                    <div class="mb-3">
                                        <label for="editDateOfBirth" class="form-label">Date of Birth</label>
                                        <input type="date" class="form-control" id="editDateOfBirth" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Patient Detail Modal -->
                <div class="modal fade" id="patientDetailModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Patient & Record Details</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <h6>Patient Information</h6>
                                <p><strong>Phone:</strong> <span id="modalPhone"></span></p>
                                <p><strong>Full Name:</strong> <span id="modalFullName"></span></p>
                                <p><strong>Email:</strong> <span id="modalEmail"></span></p>
                                <p><strong>Gender:</strong> <span id="modalGender"></span></p>
                                <p><strong>DOB:</strong> <span id="modalDateOfBirth"></span></p>
                                <p><strong>Created At:</strong> <span id="modalCreatedAt"></span></p>
                                <hr/>
                                <h6>Medical Record</h6>
                                <div id="recordSpinner" class="spinner hidden"></div>
                                <p><strong>Record ID:</strong> <span id="modalRecordId"></span></p>
                                <p><strong>Diagnosis:</strong> <span id="modalDiagnosis"></span></p>
                                <p><strong>Prescription:</strong> <span id="modalPrescription"></span></p>
                                <p><strong>Notes:</strong> <span id="modalNotes"></span></p>
                                <p><strong>Record Created At:</strong> <span id="modalRecordCreatedAt"></span></p>
                                <hr/>
                                <h6>Appointments</h6>
                                <div id="apptsSpinner" class="spinner hidden"></div>
                                <div id="apptsContent"></div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="bg-white shadow py-3 text-center">
                <small>© <script>document.write(new Date().getFullYear())</script> Doctris</small>
            </footer>
        </main>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const baseUrl = '${pageContext.request.contextPath}';

            // Search Form Submission
            document.getElementById('searchForm').addEventListener('submit', function(e) {
                e.preventDefault();
                const phone = document.getElementById('searchPhone').value;
                const gender = document.getElementById('filterGender').value;
                window.location.href = '?phone=' + encodeURIComponent(phone) + '&gender=' + encodeURIComponent(gender);
            });

            // Edit Button Click
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', function(e) {
                    e.stopPropagation();
                    const row = this.closest('tr');
                    document.getElementById('editPhone').value = row.dataset.phone;
                    document.getElementById('editFullName').value = row.dataset.fullName;
                    document.getElementById('editEmail').value = row.dataset.email;
                    document.getElementById('editDateOfBirth').value = row.dataset.dateOfBirth;
                });
            });

            // Edit Form Submission
            document.getElementById('editPatientForm').addEventListener('submit', function(e) {
                e.preventDefault();
                const phone = document.getElementById('editPhone').value;
                const fullName = document.getElementById('editFullName').value;
                const email = document.getElementById('editEmail').value;
                const dateOfBirth = document.getElementById('editDateOfBirth').value;

                fetch(baseUrl + '/doctor/update-patient', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ phone, fullName, email, dateOfBirth })
                })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to update patient');
                    }
                })
                .then(data => {
                    alert('Patient updated successfully');
                    const row = document.querySelector(`tr[data-phone="${phone}"]`);
                    row.dataset.fullName = data.fullName;
                    row.dataset.email = data.email;
                    row.dataset.dateOfBirth = data.dateOfBirth;
                    row.querySelector('td[data-label="Full Name"]').textContent = data.fullName;
                    row.querySelector('td[data-label="Email"]').textContent = data.email;
                    bootstrap.Modal.getInstance(document.getElementById('editPatientModal')).hide();
                })
                .catch(error => {
                    alert('Error updating patient: ' + error.message);
                });
            });

            // Patient Detail Modal
            document.querySelectorAll('.clickable-row').forEach(row => {
                row.addEventListener('click', function(e) {
                    if (e.target.classList.contains('edit-btn')) return;
                    const infoMap = [
                        ['modalPhone','phone'],
                        ['modalFullName','fullName'],
                        ['modalEmail','email'],
                        ['modalGender','gender'],
                        ['modalDateOfBirth','dateOfBirth'],
                        ['modalCreatedAt','createdAt']
                    ];
                    infoMap.forEach(([id,key]) => {
                        document.getElementById(id).textContent = row.dataset[key] || '—';
                    });
                    const recSpinner = document.getElementById('recordSpinner');
                    recSpinner.classList.remove('hidden');
                    fetch(baseUrl + '/doctor/medical-records-json?patientPhone=' + encodeURIComponent(row.dataset.phone))
                        .then(res => res.json())
                        .then(data => {
                            recSpinner.classList.add('hidden');
                            const r = data[0] || {};
                            document.getElementById('modalRecordId').textContent = r.recordId || '—';
                            document.getElementById('modalDiagnosis').textContent = r.diagnosis || '—';
                            document.getElementById('modalPrescription').textContent = r.prescription || '—';
                            document.getElementById('modalNotes').textContent = r.notes || '—';
                            document.getElementById('modalRecordCreatedAt').textContent = r.createdAt || '—';
                        })
                        .catch(() => recSpinner.classList.add('hidden'));
                    const apptSpinner = document.getElementById('apptsSpinner');
                    apptSpinner.classList.remove('hidden');
                    document.getElementById('apptsContent').innerHTML = '';
                    fetch(baseUrl + '/doctor/appointments?patientPhone=' + encodeURIComponent(row.dataset.phone))
                        .then(res => res.json())
                        .then(data => {
                            apptSpinner.classList.add('hidden');
                            let table = '<table class="table table-sm"><thead><tr>' +
                                        '<th>ID</th><th>Date</th><th>Status</th><th>Created At</th></tr></thead><tbody>';
                            data.forEach(a => {
                                table += '<tr><td>' + a.appointmentId + '</td><td>' + a.appointmentDate + '</td><td>' + a.status + '</td><td>' + a.createdAt + '</td></tr>';
                            });
                            table += '</tbody></table>';
                            document.getElementById('apptsContent').innerHTML = table;
                        })
                        .catch(() => apptSpinner.classList.add('hidden'));
                    new bootstrap.Modal(document.getElementById('patientDetailModal')).show();
                });
            });
        });
    </script>
</body>
</html>