<%-- 
    Document   : managerTimeSlotList
    Created on : 23 Apr 2025, 11:43:00
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
        <jsp:include page="/includes/patientHead.jsp" />
        <title>Manager Time Slot Edit</title>
    </head>
    <body>

        <%
            java.time.LocalDate today = java.time.LocalDate.now();
            request.setAttribute("currentDate", today.toString());
        %>
        <jsp:useBean id="now" class="java.util.Date" scope="page" />
        <div class="page-wrapper doctris-theme toggled">
            <jsp:include page="../component/managerSideBar.jsp" />
            <main class="page-content bg-light">
                <%@ include file="../component/header.jsp" %>

                <!-- Start Hero -->
                <section class="bg-dashboard">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12 mt-4 pt-2 mt-sm-0 pt-sm-0">
                                <form method="get" action="ManagerTimeSlotListServlet">
                                    <div class="row mb-3">
                                        <div class="col-xl-6 col-lg-6 col-md-4">
                                            <h5 class="mb-0">Assign Doctor To Time Slot</h5>
                                        </div><!--end col-->
                                    </div>
                                    <div class="row align-items-end">
                                        <!-- LEFT HALF: Filters and Search -->
                                        <div class="col-md-7">
                                            <input type="hidden" name="page" id="pageInput" value="${param.page != null ? param.page : 1}" />
                                            <div class="row g-3">
                                                <!-- Status -->
                                                <div class="col-3">
                                                    <label class="form-label">Status</label>
                                                    <select name="status" class="form-control select2input" style="min-width: 100%;">
                                                        <option value="">All</option>
                                                        <option value="true" ${param.status == 'true' ? 'selected' : ''}>Active</option>
                                                        <option value="false" ${param.status == 'false' ? 'selected' : ''}>Disabled</option>
                                                    </select>
                                                </div>

                                                <!-- Search + Button -->
                                                <div class="col-4">
                                                    <label class="form-label">Search by Doctor or Time Slot</label>
                                                    <input type="text" name="keyword" class="form-control" placeholder="Doctor name" value="${param.keyword}">
                                                </div>

                                                <div class="col-2 d-flex align-items-end">
                                                    <button type="submit" class="btn btn-primary w-100">Search</button>
                                                </div>
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="row m-4 ms-5 me-5">
                                        <ul class="nav nav-pills nav-justified flex-column flex-sm-row rounded gap-2" id="pills-tab" role="tablist">
                                            <c:set var="daysOfWeek" value="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday" />
                                            <c:forEach var="dayOfTheWeek" items="${fn:split(daysOfWeek, ',')}">
                                                <li class="nav-item">
                                                    <a class="nav-link rounded ${selectedDay == dayOfTheWeek ? 'active' : ''}" 
                                                       href="ManagerTimeSlotListServlet?dayOfTheWeek=${dayOfTheWeek}">
                                                        <div class="text-center pt-1 pb-1">
                                                            <h4 class="title font-weight-normal mb-0">${dayOfTheWeek}</h4>
                                                        </div>
                                                    </a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <input type="hidden" name="dayOfTheWeek" value="${selectedDay}" />
                                    </div>
                                    <div class="row">
                                        <div class="col-12 mt-4">
                                            <div class="table-responsive bg-white shadow rounded">
                                                <table class="table mb-0 table-center">
                                                    <thead>
                                                        <tr>
                                                            <th class="border-bottom p-3" style="min-width: 50px;">#</th>
                                                            <th class="border-bottom p-3" style="min-width: 150px;">Time Slot</th>
                                                            <th class="border-bottom p-3" style="min-width: 100px;">Start Time</th>
                                                            <th class="border-bottom p-3" style="min-width: 100px;">End Time</th>
                                                            <th class="border-bottom p-3 text-center" style="min-width: 300px;">Assigned Doctors</th>
                                                            <th class="border-bottom p-3" style="min-width: 100px;">Status</th>
                                                            <th class="border-bottom p-3" style="min-width: 100px;">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="timeSlot" items="${timeSlotList}" varStatus="i">
                                                            <tr>
                                                                <th class="p-3">${i.index + 1}</th>
                                                                <td class="p-3">${timeSlot.timeSlotName}</td>
                                                                <td class="p-3">
                                                                    <fmt:formatDate value="${timeSlot.startTime}" pattern="HH:mm" type="time"/>
                                                                </td>
                                                                <td class="p-3">
                                                                    <fmt:formatDate value="${timeSlot.endTime}" pattern="HH:mm" type="time"/>
                                                                </td>
                                                                <td class="p-3 text-center">
                                                                    <c:choose>
                                                                        <c:when test="${not empty timeSlot.assignedDoctors}">
                                                                            <c:forEach var="doc" items="${timeSlot.assignedDoctors}" varStatus="status">
                                                                                ${doc.fullName} (${doc.maxAppointments})<c:if test="${!status.last}">, </c:if>
                                                                            </c:forEach>
                                                                        </c:when>
                                                                        <c:otherwise>None</c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td class="p-3">
                                                                    <c:choose>
                                                                        <c:when test="${timeSlot.isActive}">
                                                                            <span class="badge bg-success">Active</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="badge bg-secondary">Disabled</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td class="p-3">
                                                                    <c:set var="json" value="${assignedDoctorsJsonMap[timeSlot.slotId]}" />
                                                                    <a
                                                                        class="btn btn-icon btn-pills btn-soft-primary open-assign-modal"
                                                                        data-bs-toggle="modal"
                                                                        data-bs-target="#assignDoctorModal"
                                                                        data-slot-id="${timeSlot.slotId}"
                                                                        data-doctors='${fn:escapeXml(json)}'>
                                                                        <i class="uil uil-cog"></i>
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!--end row-->

                                    <!-- PAGINATION START -->
                                    <input type="hidden" name="page" id="pageInput" value="${currentPage}" />
                                    <div class="row text-center">
                                        <div class="col-12 mt-4">
                                            <div class="d-md-flex align-items-center text-center justify-content-between">
                                                <!-- Showing dropdown -->
                                                <div class="d-flex align-items-center">
                                                    <label for="pageSize" class="me-2 text-muted">Showing</label>
                                                    <select name="pageSize" id="pageSize" class="form-select form-select-sm me-2" style="width: auto;"
                                                            onchange="this.form.submit()">
                                                        <option value="5" ${param.pageSize == '5' ? 'selected' : ''}>5</option>
                                                        <option value="10" ${param.pageSize == '10' ? 'selected' : ''}>10</option>
                                                        <option value="20" ${param.pageSize == '20' ? 'selected' : ''}>20</option>
                                                    </select>
                                                    <span class="text-muted">out of ${totalRecords} total records</span>
                                                </div>


                                                <ul class="pagination justify-content-center mb-0 mt-3 mt-sm-0">

                                                    <!-- First -->
                                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                        <button type="submit" class="page-link" onclick="document.getElementById('pageInput').value = 1">First</button>
                                                    </li>

                                                    <!-- Previous -->
                                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                        <button type="submit" class="page-link" onclick="document.getElementById('pageInput').value = ${currentPage - 1}">Prev</button>
                                                    </li>

                                                    <!-- Dynamic page buttons -->
                                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                                        <c:choose>
                                                            <c:when test="${i == 1 || i == totalPages || (i >= currentPage - 1 && i <= currentPage + 1)}">
                                                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                                    <button type="submit" class="page-link" onclick="document.getElementById('pageInput').value = ${i}">${i}</button>
                                                                </li>
                                                            </c:when>

                                                            <c:when test="${i == 2 && currentPage > 3}">
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                </c:when>

                                                            <c:when test="${i == totalPages - 1 && currentPage < totalPages - 2}">
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach>

                                                    <!-- Next -->
                                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                        <button type="submit" class="page-link" onclick="document.getElementById('pageInput').value = ${currentPage + 1}">Next</button>
                                                    </li>

                                                    <!-- Last -->
                                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                        <button type="submit" class="page-link" onclick="document.getElementById('pageInput').value = ${totalPages}">Last</button>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div><!--end row-->
                                </form>
                            </div><!--end col-->
                        </div><!--end row-->
                    </div><!--end container-->
                </section><!--end section-->
                <!-- End Hero -->

                <!-- Modal start -->
                <!-- Assign Doctors Modal -->
                <div class="modal fade" id="assignDoctorModal" tabindex="-1" aria-labelledby="assignDoctorModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <!-- update this to your servlet path -->
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="assignDoctorModalLabel">Assign Doctors to Time Slot</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>

                            <div class="modal-body">
                                <p>${selectedDay}: Time Slot</p>
                                <p id="slotInfoText"></p>

                                <table class="table table-bordered text-center align-middle">
                                    <thead class="table-secondary">
                                        <tr>
                                            <th>#</th>
                                            <th>Doctor</th>
                                            <th>Patient Limit</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody id="doctorAssignTableBody">
                                        <!-- Row will be generated with js script -->
                                    </tbody>
                                </table>

                                <button type="button" id="addDoctorBtn" class="btn btn-primary">Add Doctor to Time Slot</button>

                                <input type="hidden" name="timeSlotId" value="${selectedTimeSlot.id}" />
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal end -->

                <!-- Back to top -->
                <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i></a>
                <!-- Back to top -->

                <!-- Offcanvas Start -->
                <div class="offcanvas bg-white offcanvas-top" tabindex="-1" id="offcanvasTop">
                    <div class="offcanvas-body d-flex align-items-center align-items-center">
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div class="text-center">
                                        <h4>Search now.....</h4>
                                        <div class="subcribe-form mt-4">
                                            <form>
                                                <div class="mb-0">
                                                    <input type="text" id="help" name="name" class="border bg-white rounded-pill" required="" placeholder="Search">
                                                    <button type="submit" class="btn btn-pills btn-primary">Search</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div><!--end col-->
                            </div><!--end row-->
                        </div><!--end container-->
                    </div>
                </div>
                <!-- Offcanvas End -->

                <!-- Offcanvas Start -->
                <div class="offcanvas offcanvas-end bg-white shadow" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
                    <div class="offcanvas-header p-4 border-bottom">
                        <h5 id="offcanvasRightLabel" class="mb-0">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="24" class="light-version" alt="">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-light.png" height="24" class="dark-version" alt="">
                        </h5>
                        <button type="button" class="btn-close d-flex align-items-center text-dark" data-bs-dismiss="offcanvas" aria-label="Close"><i class="uil uil-times fs-4"></i></button>
                    </div>
                    <div class="offcanvas-body p-4 px-md-5">
                        <div class="row">
                            <div class="col-12">
                                <!-- Style switcher -->
                                <div id="style-switcher">
                                    <div>
                                        <ul class="text-center list-unstyled mb-0">
                                            <li class="d-grid"><a href="javascript:void(0)" class="rtl-version t-rtl-light" onclick="setTheme('style-rtl')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-light-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                            <li class="d-grid"><a href="javascript:void(0)" class="ltr-version t-ltr-light" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                            <li class="d-grid"><a href="javascript:void(0)" class="dark-rtl-version t-rtl-dark" onclick="setTheme('style-dark-rtl')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-dark-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                            <li class="d-grid"><a href="javascript:void(0)" class="dark-ltr-version t-ltr-dark" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-dark.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                            <li class="d-grid"><a href="javascript:void(0)" class="dark-version t-dark mt-4" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-dark.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Dark Version</span></a></li>
                                            <li class="d-grid"><a href="javascript:void(0)" class="light-version t-light mt-4" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Light Version</span></a></li>
                                            <li class="d-grid"><a href="../admin/index.html" target="_blank" class="mt-4"><img src="${pageContext.request.contextPath}/assets/images/layouts/light-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Admin Dashboard</span></a></li>
                                        </ul>
                                    </div>
                                </div>
                                <!-- end Style switcher -->
                            </div><!--end col-->
                        </div><!--end row-->
                    </div>

                    <div class="offcanvas-footer p-4 border-top text-center">
                        <ul class="list-unstyled social-icon mb-0">
                            <li class="list-inline-item mb-0"><a href="https://1.envato.market/doctris-template" target="_blank" class="rounded"><i class="uil uil-shopping-cart align-middle" title="Buy Now"></i></a></li>
                            <li class="list-inline-item mb-0"><a href="https://dribbble.com/shreethemes" target="_blank" class="rounded"><i class="uil uil-dribbble align-middle" title="dribbble"></i></a></li>
                            <li class="list-inline-item mb-0"><a href="https://www.facebook.com/shreethemes" target="_blank" class="rounded"><i class="uil uil-facebook-f align-middle" title="facebook"></i></a></li>
                            <li class="list-inline-item mb-0"><a href="https://www.instagram.com/shreethemes/" target="_blank" class="rounded"><i class="uil uil-instagram align-middle" title="instagram"></i></a></li>
                            <li class="list-inline-item mb-0"><a href="https://twitter.com/shreethemes" target="_blank" class="rounded"><i class="uil uil-twitter align-middle" title="twitter"></i></a></li>
                            <li class="list-inline-item mb-0"><a href="mailto:support@shreethemes.in" class="rounded"><i class="uil uil-envelope align-middle" title="email"></i></a></li>
                            <li class="list-inline-item mb-0"><a href="../../../index.html" target="_blank" class="rounded"><i class="uil uil-globe align-middle" title="website"></i></a></li>
                        </ul><!--end icon-->
                    </div>
                </div>
                <!-- Offcanvas End -->

                <c:if test="${not empty toastMessage}">
                    <div class="toast-container position-fixed bottom-0 end-0 p-3">
                        <div class="toast align-items-center text-white bg-${toastType} border-0 show" role="alert" aria-live="assertive" aria-atomic="true">
                            <div class="d-flex">
                                <div class="toast-body">
                                    ${toastMessage}
                                </div>
                                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                            </div>
                        </div>
                    </div>
                    <script>
                        const toastEl = document.querySelector('.toast');
                        const bsToast = new bootstrap.Toast(toastEl);
                        bsToast.show();
                    </script>
                </c:if>

                <%
                    String message = request.getParameter("message");
                %>
                <div class="toast-container position-fixed bottom-0 end-0 p-3">
                    <% if (message != null) {%>
                    <div class="toast align-items-center text-white bg-success border-0"
                         id="statusToast" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="d-flex">
                            <div class="toast-body">
                                <%= message%>
                            </div>
                            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                        </div>
                    </div>
                    <% }%>
                </div>
            </main>
        </div>

        <script>
            let currentSlotId = null;
            document.querySelectorAll('[data-bs-target="#assignDoctorModal"]').forEach(button => {
                button.addEventListener('click', function () {
                    const doctorAssignDTOs = ${doctorAssignDTOsJson};
                    const doctorsData = JSON.parse(this.getAttribute('data-doctors') || '[]');
                    const slotId = this.getAttribute('data-slot-id');
                    currentSlotId = parseInt(slotId);
                    const currentDayOfWeek = document.querySelector('input[name="dayOfTheWeek"]').value;
                    const tableBody = document.getElementById('doctorAssignTableBody');
                    tableBody.innerHTML = '';

                    doctorsData.forEach((doc, index) => {
                        const row = document.createElement('tr');

                        const indexTd = document.createElement('td');
                        indexTd.textContent = index + 1;
                        row.appendChild(indexTd);

                        const nameTd = document.createElement('td');
                        nameTd.textContent = doc.fullName;

                        const limitTd = document.createElement('td');
                        limitTd.textContent = doc.maxAppointments;

                        const actionsTd = document.createElement('td');

                        const updateBtn = document.createElement('button');
                        updateBtn.className = 'btn btn-sm btn-outline-primary me-1';
                        updateBtn.textContent = 'Update';

                        const removeBtn = document.createElement('button'); // Changed to button for consistency
                        removeBtn.className = 'btn btn-sm btn-outline-danger';
                        removeBtn.textContent = 'Remove';
                        removeBtn.addEventListener('click', () => {
                            // Construct the request body
                            const requestBody = new URLSearchParams({
                                doctorId: doc.doctorId,
                                slotId: currentSlotId,
                                dayOfWeek: currentDayOfWeek
                            });

                            // Debug: log the request body
                            console.log("Sending delete request with body:", requestBody.toString());

                            // Send the delete request to the server
                            fetch('DeleteDoctorAssignmentServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: requestBody
                            })
                                    .then(res => res.json())
                                    .then(data => {
                                        if (data.success) {
                                            // Remove the row from the table if successful
                                            row.remove();
                                            // Call updateLocation to include query params in the URL
                                            redirectToManagerTimeSlotList();
                                        } else {
                                            alert("Failed to remove assignment.");
                                        }
                                    })
                                    .catch(err => console.error("Fetch error:", err));
                        });

                        // Handle update -> edit mode
                        updateBtn.addEventListener('click', () => {
                            // Create select dropdown for doctor
                            const select = document.createElement('select');
                            select.className = 'form-select';
                            doctorAssignDTOs.forEach(d => {
                                const option = document.createElement('option');
                                option.value = d.doctorId;
                                option.textContent = d.fullName;
                                if (d.doctorId === doc.doctorId) {
                                    option.selected = true;
                                }
                                select.appendChild(option);
                            });
                            nameTd.innerHTML = '';
                            nameTd.appendChild(select);

                            // Create input for patient limit
                            const input = document.createElement('input');
                            input.type = 'number';
                            input.min = 1;
                            input.className = 'form-control';
                            input.value = doc.maxAppointments;
                            limitTd.innerHTML = '';
                            limitTd.appendChild(input);

                            // Change actions to Save
                            actionsTd.innerHTML = '';
                            const saveBtn = document.createElement('button');
                            saveBtn.className = 'btn btn-sm btn-success';
                            saveBtn.textContent = 'Save';
                            actionsTd.appendChild(saveBtn);

                            saveBtn.addEventListener('click', () => {
                                const selectedDoctorId = parseInt(select.value);
                                const newLimit = parseInt(input.value);
                                const selectedDoctor = doctorAssignDTOs.find(d => d.doctorId === selectedDoctorId);

                                // Send the update to server
                                // Construct the request body
                                const requestBody = new URLSearchParams({
                                    oldDoctorId: doc.doctorId,
                                    newDoctorId: selectedDoctorId,
                                    slotId: currentSlotId,
                                    dayOfWeek: currentDayOfWeek,
                                    maxAppointments: newLimit
                                });

                                // Debug: log the full request body
                                console.log("Sending update request with body:", requestBody.toString());

                                // Send the update to the server
                                fetch('UpdateDoctorAssignmentServlet', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    },
                                    body: requestBody
                                })
                                        .then(res => res.json())
                                        .then(data => {
                                            if (data.success) {
                                                // Update local object
                                                doc.doctorId = selectedDoctorId;
                                                doc.fullName = selectedDoctor.fullName;
                                                doc.maxAppointments = newLimit;

                                                // Update UI
                                                nameTd.textContent = selectedDoctor.fullName;
                                                limitTd.textContent = newLimit;

                                                actionsTd.innerHTML = '';
                                                actionsTd.appendChild(updateBtn);
                                                actionsTd.appendChild(removeBtn);

                                                // Call updateLocation to include query params in the URL
                                                redirectToManagerTimeSlotList();
                                            } else {
                                                alert("Update failed!: Update assignment must not be clash with already existing assignment");
                                            }
                                        })
                                        .catch(err => console.error("Fetch error:", err));
                            });
                        });

                        actionsTd.appendChild(updateBtn);
                        actionsTd.appendChild(removeBtn);

                        row.appendChild(nameTd);
                        row.appendChild(limitTd);
                        row.appendChild(actionsTd);
                        tableBody.appendChild(row);
                    });
                });
            });

            document.querySelector('#addDoctorBtn').addEventListener('click', function () {
                // Get the table body element
                const tableBody = document.getElementById('doctorAssignTableBody');

                // Create a new row
                const row = document.createElement('tr');

                // Create the index column
                const indexTd = document.createElement('td');
                indexTd.textContent = tableBody.rows.length + 1;  // Index for new row
                row.appendChild(indexTd);

                // Create the doctor selection column (dropdown)
                const doctorTd = document.createElement('td');
                const select = document.createElement('select');
                const doctorAssignDTOs = ${doctorAssignDTOsJson};
                const currentDayOfWeek = document.querySelector('input[name="dayOfTheWeek"]').value;
                select.className = 'form-select';
                doctorAssignDTOs.forEach(doc => {
                    const option = document.createElement('option');
                    option.value = doc.doctorId;
                    option.textContent = doc.fullName;
                    select.appendChild(option);
                });
                doctorTd.appendChild(select);
                row.appendChild(doctorTd);

                // Create the patient limit column (input)
                const limitTd = document.createElement('td');
                const input = document.createElement('input');
                input.type = 'number';
                input.min = 1;
                input.className = 'form-control';
                input.value = 1;  // Default value for patient limit
                limitTd.appendChild(input);
                row.appendChild(limitTd);

                // Create the actions column (Save button)
                const actionsTd = document.createElement('td');
                const saveBtn = document.createElement('button');
                saveBtn.className = 'btn btn-sm btn-success';
                saveBtn.textContent = 'Save';

                // Handle the save action for the new doctor assignment
                saveBtn.addEventListener('click', () => {
                    const selectedDoctorId = parseInt(select.value);
                    const newLimit = parseInt(input.value);
                    const timeSlotId = document.querySelector('input[name="timeSlotId"]').value;

                    // Construct the request body to send to the server
                    const requestBody = new URLSearchParams({
                        doctorId: selectedDoctorId,
                        slotId: currentSlotId,
                        dayOfWeek: currentDayOfWeek,
                        maxAppointments: newLimit
                    });

                    console.log("Sending CREATE NEW request with body:", requestBody.toString());

                    // Send the request to insert the new doctor assignment
                    fetch('InsertDoctorAssignmentServlet', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: requestBody
                    })
                            .then(res => res.json())
                            .then(data => {
                                if (data.success) {
                                    // Add the new doctor assignment to the table
                                    //alert("Doctor assigned successfully!");
                                    redirectToManagerTimeSlotList();
                                } else {
                                    alert("Failed to assign doctor.");
                                }
                            })
                            .catch(err => console.error("Error:", err));
                });

                actionsTd.appendChild(saveBtn);
                row.appendChild(actionsTd);

                // Append the new row to the table body
                tableBody.appendChild(row);
            });

            // Function to get query parameters from the current URL
            function getQueryParams() {
                const params = new URLSearchParams(window.location.search);

                // Log the full query string for debugging
                console.log("Current URL search params:", window.location.search);

                // Retrieve query parameters with proper fallbacks if not found
                const page = params.get('page') || '1';  // Default to page 1 if not present
                const status = params.get('status') || '';
                const keyword = params.get('keyword') || '';
                const dayOfTheWeek = params.get('dayOfTheWeek') || '';  // Do not default to 'Wednesday'
                const pageSize = params.get('pageSize') || '5';  // Default to pageSize 5 if not present

                // Log each retrieved parameter for debugging
                console.log("Retrieved Parameters:");
                console.log("page:", page);
                console.log("status:", status);
                console.log("keyword:", keyword);
                console.log("dayOfTheWeek:", dayOfTheWeek);
                console.log("pageSize:", pageSize);

                return {page, status, keyword, dayOfTheWeek, pageSize};
            }


            function redirectToManagerTimeSlotList() {
                const queryParams = getQueryParams();
                const baseUrl = '/SWP_BL5_TEAM1/ManagerTimeSlotListServlet'; // Adjust the path if needed
                const url = new URL(baseUrl, window.location.origin); // Use URL constructor for proper encoding

                // Append parameters to the URL
                url.searchParams.append('page', queryParams.page);
                url.searchParams.append('status', queryParams.status);
                url.searchParams.append('keyword', queryParams.keyword);
                url.searchParams.append('dayOfTheWeek', queryParams.dayOfTheWeek);
                url.searchParams.append('pageSize', queryParams.pageSize);

                console.log("New URL:", url);
                // Redirect to the new URL
                window.location.href = url.toString();
            }
        </script>

        <script>
            document.querySelector('form').addEventListener('submit', function () {
                // Reset to page 1 if user changes filter/search
                const keyword = document.querySelector('input[name="keyword"]').value;
                const status = document.querySelector('select[name="status"]').value;
                const slot = document.querySelector('select[name="slotId"]').value;

                // If any filter was changed manually (not just pagination), reset page to 1
                if (keyword || status || slot) {
                    document.getElementById('pageInput').value = 1;
                }
            });

            // Still handle pageSize change separately
            document.getElementById("pageSize").addEventListener("change", function () {
                document.getElementById("pageInput").value = 1;
                this.form.submit();
            });
        </script>

        <script>
            window.addEventListener('DOMContentLoaded', () => {
                const toastEl = document.getElementById('statusToast');
                if (toastEl) {
                    const toast = new bootstrap.Toast(toastEl);
                    toast.show();

                    // Remove message param from URL after showing
                    const url = new URL(window.location);
                    url.searchParams.delete("message");
                    window.history.replaceState({}, document.title, url);
                }
            });
        </script>
        <script src="<c:url value='/assets/js/bootstrap.bundle.min.js'/>"></script>
        <!-- javascript -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
        <!-- Select2 -->
        <script src="${pageContext.request.contextPath}/assets/js/select2.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/select2.init.js"></script>
        <!-- Datepicker -->
        <script src="${pageContext.request.contextPath}/assets/js/flatpickr.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/flatpickr.init.js"></script>
        <!-- Datepicker -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.timepicker.min.js"></script> 
        <script src="${pageContext.request.contextPath}/assets/js/timepicker.init.js"></script> 
        <!-- Icons -->
        <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    </body>
</html>
