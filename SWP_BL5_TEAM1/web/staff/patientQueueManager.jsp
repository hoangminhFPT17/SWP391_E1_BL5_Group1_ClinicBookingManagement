<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Patient Queue Manager</title>

    <!-- Include Bootstrap, jQuery, and theme CSS/JS -->
    <%@ include file="../assets/css/css-js.jsp" %>
    <!-- SortableJS for drag-and-drop -->
    <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.14.0/Sortable.min.js"></script>
</head>
<body>
    <div class="page-wrapper doctris-theme toggled">
        <%@ include file="../component/sideBar.jsp" %>

        <main class="page-content bg-light">
            <%@ include file="../component/header.jsp" %>

            <div class="container-fluid py-4">
                <div class="row mt-4" >
                    <div class="col text-center" style="margin-top: 50px">
                        <h3>Inbound Ticket</h3>
                    </div>
                </div>

                <div class="row mt-3">
                    <div class="col">
                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white">
                                Active Appointments (Drag rows to reorder priority)
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <!-- Form to post new priorities -->
                                    <form id="reorderForm" action="patientQueueManager" method="post">
                                        <table class="table table-bordered table-hover">
                                            <thead class="thead-light">
                                                <tr>
                                                    <th>Patient Phone</th>
                                                    <th>Patient Name</th>
                                                    <th>Doctor</th>
                                                    <th>Time Slot</th>
                                                    <th>Queue Date</th>
                                                    <th>Priority</th>
                                                    <th>Status</th>
                                                    <th>Arrival Time</th>
                                                    <th>Created By</th>
                                                </tr>
                                            </thead>
                                            <tbody id="queueTableBody">
                                                <c:forEach var="dto" items="${activeAppointments}">
                                                    <tr data-queue-id="${dto.queueId}">
                                                        <td>${dto.patientPhone}</td>
                                                        <td>${dto.patientName}</td>
                                                        <td>${dto.doctorName}</td>
                                                        <td>${dto.startTime} - ${dto.endTime}</td>
                                                        <td>${dto.queueDate}</td>
                                                        <td class="priority-cell">${dto.priorityNumber}</td>
                                                        <td>${dto.status}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty dto.arrivalTime}">
                                                                    ${dto.arrivalTime}
                                                                </c:when>
                                                                <c:otherwise>N/A</c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${dto.createdByName}</td>
                                                        <!-- Hidden inputs for form submit -->
                                                        <input type="hidden" name="id" value="${dto.queueId}" />
                                                        <input type="hidden" name="priority" class="priority-input" value="${dto.priorityNumber}" />
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                        <c:if test="${empty activeAppointments}">
                                            <p class="text-center my-3">No active appointments at this time.</p>
                                        </c:if>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var tbody = document.getElementById('queueTableBody');
            var form = document.getElementById('reorderForm');

            Sortable.create(tbody, {
                animation: 150,
                onEnd: function(evt) {
                    // Recalculate priorities and update hidden inputs
                    var rows = tbody.querySelectorAll('tr');
                    rows.forEach(function(row, index) {
                        var newPriority = index + 1; // Starting at 1
                        row.querySelector('.priority-cell').textContent = newPriority;
                        row.querySelector('.priority-input').value = newPriority;
                    });

                    // Submit the form to post updated priorities
                    form.submit();
                }
            });
        });
    </script>
</body>
</html>
