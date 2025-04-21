<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Patient Queue Manager</title>
        <%@ include file="../assets/css/css-js.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.14.0/Sortable.min.js"></script>
    </head>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <%@ include file="../component/staffSideBar.jsp" %>

            <main class="page-content bg-light">
                <%@ include file="../component/staffHeader.jsp" %>

                <div class="container-fluid py-4">
                    <div class="row mt-4">
                        <div class="col text-center" style="margin-top:50px">
                            <h3>Patient Priority Queue</h3>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col">
                            <div class="card shadow-sm">
                                <div class="card-header bg-primary text-white">
                                    Active Appointments (Drag rows or use “Move to”)
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <!-- single form for both drag-and-drop and manual moves -->
                                        <form id="reorderForm" action="patientQueueManager" method="post">
                                            <input type="hidden" name="doctorId" value="${doctorId}" />
                                            <div class="row mb-3">
                                                <div class="col-md-4">
                                                    <input type="text"
                                                           id="queueSearch"
                                                           class="form-control"
                                                           placeholder="Search by patient name or phone…">
                                                </div>
                                            </div>
                                            <table class="table table-bordered table-hover">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th>Patient Phone</th>
                                                        <th>Patient Name</th>
                                                        <th>Doctor</th>
                                                        <th>Time Slot</th>
                                                        <th>Queue Date</th>
                                                        <th>Status</th>
                                                        <th>Type</th>
                                                        <th>Arrival Time</th>
                                                        <th>Created By</th>
                                                        <th>Move to</th>
                                                        <th>Action</th>
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
                                                            <td>${dto.status}</td>
                                                            <td>${dto.patientType}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty dto.arrivalTime}">
                                                                        ${dto.arrivalTime}
                                                                    </c:when>
                                                                    <c:otherwise>N/A</c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>${dto.createdByName}</td>
                                                            <!-- manual Move-to inputs -->
                                                            <td>
                                                                <div class="d-flex">
                                                                    <input type="number"
                                                                           min="1"
                                                                           class="form-control form-control-sm move-input"
                                                                           placeholder="#${dto.priorityNumber}"
                                                                           style="width:70px;"/>
                                                                    <button type="button"
                                                                            class="btn btn-sm btn-primary move-btn ms-1">
                                                                        Move
                                                                    </button>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <form action="patientQueueManager" method="post" style="display:inline">
                                                                    <input type="hidden" name="doctorId"      value="${doctorId}" />
                                                                    <input type="hidden" name="cancelQueueId"  value="${dto.queueId}"  />
                                                                    <button type="submit"
                                                                            class="btn btn-sm btn-danger">
                                                                        Cancel
                                                                    </button>
                                                                </form>
                                                            </td>
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


                </div> <!-- /container-fluid -->
            </main>
        </div>
        <script>
            // When the user types, filter queue rows by patient name or phone
            document.getElementById('queueSearch')
                    .addEventListener('input', function () {
                        const filter = this.value.trim().toLowerCase();
                        const rows = document.querySelectorAll('#queueTableBody tr');
                        rows.forEach(row => {
                            const phone = row.cells[0].textContent.trim().toLowerCase();
                            const name = row.cells[1].textContent.trim().toLowerCase();
                            const match = phone.includes(filter) || name.includes(filter);
                            row.style.display = match ? '' : 'none';
                        });
                    });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const tbody = document.getElementById('queueTableBody');
                const form = document.getElementById('reorderForm');

                // helper to clear old hidden inputs
                function clearHidden() {
                    form.querySelectorAll('input[name="id"], input[name="priority"]').forEach(i => i.remove());
                }

                // build hidden inputs & submit
                function submitReorder(pairs) {
                    clearHidden();
                    // pairs: array of {id, priority}
                    pairs.forEach(p => {
                        let i1 = document.createElement('input');
                        i1.type = 'hidden';
                        i1.name = 'id';
                        i1.value = p.id;
                        let i2 = document.createElement('input');
                        i2.type = 'hidden';
                        i2.name = 'priority';
                        i2.value = p.priority;
                        form.appendChild(i1);
                        form.appendChild(i2);
                    });
                    form.submit();
                }

                // Sortable for drag-and-drop
                Sortable.create(tbody, {
                    animation: 150,
                    onEnd: function () {
                        const rows = Array.from(tbody.querySelectorAll('tr'));
                        const pairs = rows.map((row, idx) => ({
                                id: row.dataset.queueId,
                                priority: idx + 1
                            }));
                        submitReorder(pairs);
                    }
                });

                // Manual “Move” buttons
                tbody.querySelectorAll('.move-btn').forEach(btn => {
                    btn.addEventListener('click', function () {
                        const row = btn.closest('tr');
                        const id = row.dataset.queueId;
                        const inp = row.querySelector('.move-input');
                        const newPrio = parseInt(inp.value, 10);
                        if (!newPrio || newPrio < 1) {
                            alert('Please enter a valid priority (>= 1).');
                            return;
                        }
                        submitReorder([{id: id, priority: newPrio}]);
                    });
                });
            });
        </script>
    </body>
</html>
