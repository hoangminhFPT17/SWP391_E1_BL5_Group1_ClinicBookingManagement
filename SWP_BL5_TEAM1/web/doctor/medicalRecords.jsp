<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Doctris - Medical Records</title>
    <%@ include file="../assets/css/css-js.jsp" %>
    <style>
        /* Previous CSS remains unchanged */
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f4f6f9;
        }
        .page-wrapper {
            transition: all 0.3s ease;
        }
        .container-fluid {
            padding: 20px;
        }
        .error {
            color: #dc3545;
            background-color: #ffe5e5;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-weight: 500;
        }
        .table-container {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            overflow: hidden;
            margin-bottom: 20px;
        }
        .table {
            margin-bottom: 0;
            border-collapse: separate;
            border-spacing: 0;
        }
        .table th {
            background-color: #e9ecef;
            color: #343a40;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 0.5px;
            padding: 12px 15px;
            border-bottom: 2px solid #dee2e6;
        }
        .table td {
            padding: 12px 15px;
            font-size: 0.9rem;
            color: #495057;
            border-bottom: 1px solid #e9ecef;
            vertical-align: middle;
        }
        .table tbody tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        .table tbody tr:hover {
            background-color: #e3f2fd;
            transition: background-color 0.2s ease;
        }
        .clickable-row {
            cursor: pointer;
            transition: transform 0.1s ease;
        }
        .clickable-row:hover {
            transform: scale(1.005);
        }
        .modal-content {
            border-radius: 8px;
            border: none;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }
        .modal-header {
            background-color: #007bff;
            color: white;
            border-bottom: none;
            border-radius: 8px 8px 0 0;
            padding: 15px 20px;
        }
        .modal-body {
            padding: 20px;
        }
        .modal-body p {
            margin-bottom: 12px;
            font-size: 0.95rem;
            display: flex;
        }
        .modal-body strong {
            color: #343a40;
            width: 140px;
            font-weight: 600;
        }
        .modal-body .table {
            margin-top: 15px;
            font-size: 0.85rem;
        }
        .modal-body .table th {
            background-color: #f1f3f5;
            padding: 10px;
        }
        .modal-body .table td {
            padding: 10px;
        }
        .sort-btn {
            color: #007bff;
            text-decoration: none;
            font-weight: 500;
            padding: 2px 5px;
            border-radius: 4px;
            transition: background-color 0.2s;
        }
        .sort-btn:hover {
            background-color: #e9ecef;
            color: #0056b3;
        }
        .sort-btn.asc::after {
            content: ' ↑';
            font-size: 0.8rem;
        }
        .sort-btn.desc::after {
            content: ' ↓';
            font-size: 0.8rem;
        }
        .spinner {
            width: 24px;
            height: 24px;
            border: 3px solid #007bff;
            border-top-color: transparent;
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin: 10px auto;
        }
        .spinner.hidden {
            display: none;
        }
        @keyframes spin {
            to { transform: rotate(360deg); }
        }
        @media (max-width: 768px) {
            .table th, .table td {
                font-size: 0.8rem;
                padding: 8px;
            }
            .table-container {
                overflow-x: auto;
            }
            .modal-body p {
                flex-direction: column;
            }
            .modal-body strong {
                width: auto;
                margin-bottom: 5px;
            }
        }
    </style>
</head>
<body>
    <div class="page-wrapper doctris-theme toggled">
        <%@ include file="../component/managerSideBar.jsp" %>

        <!-- Start Page Content -->
        <main class="page-content bg-light">
            <%@ include file="../component/header.jsp" %>
            <div class="container-fluid">
                <h1 class="mb-4">All Medical Records</h1>
                <c:if test="${not empty error}">
                    <p class="error">${error}</p>
                </c:if>
                <form method="get" action="${pageContext.request.contextPath}/doctor/medical-records" class="mb-4">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <input type="text" name="patientPhone" class="form-control" placeholder="Search by Patient Phone" value="${param.patientPhone}">
                        </div>
                        <div class="col-md-4">
                            <input type="text" name="diagnosis" class="form-control" placeholder="Search by Diagnosis" value="${param.diagnosis}">
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary w-100">Filter</button>
                        </div>
                    </div>
                </form>
                <div class="table-container">
                    <div class="table-responsive">
                        <table class="table table-hover table-striped">
                            <thead>
                                <tr>
                                    <th>Record ID</th>
                                    <th>Patient Phone</th>
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
                                        <td>${record.recordId}</td>
                                        <td>${record.patientPhone}</td>
                                        <td>${record.diagnosis}</td>
                                        <td>${record.prescription}</td>
                                        <td>${record.notes}</td>
                                        <td><fmt:formatDate value="${record.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty records}">
                                    <tr>
                                        <td colspan="6" class="text-center py-3">No records found.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- Modal for Record Details -->
                <div class="modal fade" id="recordDetailModal" tabindex="-1" aria-labelledby="recordDetailModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
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
                                <div id="modalAppointments" class="mt-3">
                                    <div class="spinner hidden" id="appointmentsSpinner"></div>
                                    <div id="appointmentsContent"></div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
    <!-- JavaScript for Modal, AJAX, and Sorting -->
    <script>
        (function() {
            const baseUrl = '${pageContext.request.contextPath}';
            let appointmentsData = [];
            let sortKey = 'appointmentDate';
            let sortOrder = 'asc';

            function renderAppointments() {
                const container = document.getElementById('appointmentsContent');
                if (!container) {
                    console.error('Appointments content container not found');
                    return;
                }
                if (!appointmentsData.length) {
                    container.innerHTML = '<p class="text-muted">No appointments found for this patient.</p>';
                    return;
                }
                appointmentsData.sort((a, b) => {
                    let va = a[sortKey];
                    let vb = b[sortKey];
                    if (sortKey === 'createdAt') {
                        va = va.split('.')[0];
                        vb = vb.split('.')[0];
                    }
                    if (va < vb) return sortOrder === 'asc' ? -1 : 1;
                    if (va > vb) return sortOrder === 'asc' ? 1 : -1;
                    return 0;
                });
                let html = '<h6 class="mb-3">Appointments</h6>';
                html += '<div class="table-responsive">';
                html += '<table class="table table-sm table-hover">';
                html += '<thead><tr>' +
                        '<th>#</th>' +
                        '<th><button class="sort-btn" data-key="appointmentDate">Appointment Date</button></th>' +
                        '<th><button class="sort-btn" data-key="status">Status</button></th>' +
                        '<th><button class="sort-btn" data-key="createdAt">Created At</button></th>' +
                    '</tr></thead><tbody>';
                appointmentsData.forEach((a, idx) => {
                    const created = a.createdAt.split('.')[0];
                    html += '<tr>' +
                        '<td>' + (idx + 1) + '</td>' +
                        '<td>' + a.appointmentDate + '</td>' +
                        '<td>' + a.status + '</td>' +
                        '<td>' + created + '</td>' +
                    '</tr>';
                });
                html += '</tbody></table></div>';
                container.innerHTML = html;
                container.querySelectorAll('.sort-btn').forEach(btn => {
                    btn.classList.remove('asc', 'desc');
                    if (btn.dataset.key === sortKey) {
                        btn.classList.add(sortOrder);
                    }
                    btn.addEventListener('click', () => {
                        const key = btn.dataset.key;
                        if (sortKey === key) {
                            sortOrder = sortOrder === 'asc' ? 'desc' : 'asc';
                        } else {
                            sortKey = key;
                            sortOrder = 'asc';
                        }
                        renderAppointments();
                    });
                });
            }

            document.addEventListener('DOMContentLoaded', function() {
                document.querySelectorAll('.clickable-row').forEach(row => {
                    row.addEventListener('click', function() {
                        document.getElementById('modalRecordId').textContent = this.dataset.recordId;
                        document.getElementById('modalPatientPhone').textContent = this.dataset.patientPhone;
                        document.getElementById('modalDiagnosis').textContent = this.dataset.diagnosis;
                        document.getElementById('modalPrescription').textContent = this.dataset.prescription;
                        document.getElementById('modalNotes').textContent = this.dataset.notes;
                        document.getElementById('modalCreatedAt').textContent = this.dataset.createdAt;
                        const modalEl = document.getElementById('recordDetailModal');
                        const spinner = document.getElementById('appointmentsSpinner');
                        const content = document.getElementById('appointmentsContent');
                        if (spinner && content) {
                            spinner.classList.remove('hidden');
                            content.innerHTML = '';
                        }
                        const modal = new bootstrap.Modal(modalEl);
                        modal.show();
                        fetch(baseUrl + '/doctor/appointments?patientPhone=' + encodeURIComponent(this.dataset.patientPhone))
                            .then(res => res.json())
                            .then(data => {
                                if (spinner) spinner.classList.add('hidden');
                                appointmentsData = Array.isArray(data) ? data : [];
                                renderAppointments();
                            })
                            .catch(err => {
                                console.error(err);
                                if (spinner) spinner.classList.add('hidden');
                                if (content) content.innerHTML = '<p class="text-danger">Error loading appointments.</p>';
                            });
                    });
                });
            });
        })();
    </script>
</body>
</html>