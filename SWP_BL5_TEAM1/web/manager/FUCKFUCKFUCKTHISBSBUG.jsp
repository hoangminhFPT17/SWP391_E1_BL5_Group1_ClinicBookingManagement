<%-- 
    Document   : FUCKFUCKFUCKTHISBSBUG
    Created on : 23 Apr 2025, 23:57:14
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/patientHead.jsp" />
    </head>
    <body>
        <table class="table mb-0 table-center">
            <thead>
                <tr>
                    <th style="min-width: 50px;">#</th>
                    <th style="min-width: 150px;">Time Slot</th>
                    <th style="min-width: 100px;">Start Time</th>
                    <th style="min-width: 100px;">End Time</th>
                    <th style="min-width: 300px;">Assigned Doctors</th>
                    <th style="min-width: 100px;">Status</th>
                    <th style="min-width: 100px;">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="timeSlot" items="${timeSlotList}" varStatus="i">
                    <tr>
                        <th>${i.index + 1}</th>
                        <td>${timeSlot.timeSlotName}</td>
                        <td>
                            <fmt:formatDate value="${timeSlot.startTime}" pattern="HH:mm" type="time"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${timeSlot.endTime}" pattern="HH:mm" type="time"/>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty timeSlot.assignedDoctors}">
                                    <c:forEach var="doc" items="${timeSlot.assignedDoctors}" varStatus="status">
                                        ${doc.fullName} (${doc.maxAppointments})<c:if test="${!status.last}">, </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>None</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${timeSlot.isActive}">
                                    <span class="badge bg-success">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Disabled</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:set var="json" value="${assignedDoctorsJsonMap[timeSlot.slotId]}" />
                            <a
                                data-bs-toggle="modal"
                                data-bs-target="#assignDoctorModal"
                                data-slot-id="${timeSlot.slotId}"
                                data-doctors='${fn:escapeXml(json)}'>
                                <i>OPEN THE FUCKING MODAL</i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Assign Doctors Modal -->
        <div id="assignDoctorModal" >
            <div>
                <form method="post" action="AssignDoctorServlet">
                    <div>
                        <table>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Doctor</th>
                                    <th>Patient Limit</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody id="doctorAssignTableBody">

                                <!-- Add new assignment row -->

                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>

        <script>
            document.querySelectorAll('[data-bs-target="#assignDoctorModal"]').forEach(button => {
                button.addEventListener('click', function () {
                    const doctorsData = JSON.parse(this.getAttribute('data-doctors') || '[]');
                    const tableBody = document.getElementById('doctorAssignTableBody');
                    console.log("Parsed Doctors:", doctorsData);

                    // Clear previous rows
                    tableBody.innerHTML = '';

                    doctorsData.forEach((doc, index) => {
                        const row = document.createElement('tr');

                        // Index
                        const indexTd = document.createElement('td');
                        indexTd.textContent = index + 1;
                        row.appendChild(indexTd);

                        // Doctor name
                        const nameTd = document.createElement('td');
                        nameTd.textContent = doc.fullName;
                        row.appendChild(nameTd);

                        // Patient limit
                        const limitTd = document.createElement('td');
                        limitTd.textContent = doc.maxAppointments;
                        row.appendChild(limitTd);

                        // Actions
                        const actionsTd = document.createElement('td');

                        const updateBtn = document.createElement('a');
                        updateBtn.href = `UpdateAssignmentServlet?id=${doc.doctorId}`;
                        updateBtn.className = 'btn btn-sm btn-outline-primary me-1';
                        updateBtn.textContent = 'Update';

                        const removeBtn = document.createElement('a');
                        removeBtn.href = `RemoveAssignmentServlet?id=${doc.doctorId}`;
                        removeBtn.className = 'btn btn-sm btn-outline-danger';
                        removeBtn.textContent = 'Remove';

                        actionsTd.appendChild(updateBtn);
                        actionsTd.appendChild(removeBtn);
                        row.appendChild(actionsTd);

                        tableBody.appendChild(row);
                    });
                });
            });
        </script>
    </body>
</html>
