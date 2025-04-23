<%-- 
    Document   : patientAppointmentList
    Created on : 16 Apr 2025, 08:15:37
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
        <title>JSP Page</title>
    </head>
    <body>

        <%
            java.time.LocalDate today = java.time.LocalDate.now();
            request.setAttribute("currentDate", today.toString());
        %>
        <jsp:useBean id="now" class="java.util.Date" scope="page" />
        <jsp:include page="/common/patientHeaderNav.jsp" />

        <!-- Start Hero -->
        <section class="bg-dashboard">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12 mt-4 pt-2 mt-sm-0 pt-sm-0">
                        <form method="post" action="PatientAppointmentsListServlet">
                            <div class="row mb-3">
                                <div class="col-xl-6 col-lg-6 col-md-4">
                                    <h5 class="mb-0">Your List Of Appointment</h5>
                                </div><!--end col-->
                            </div>
                            <div class="row align-items-end">
                                <!-- LEFT HALF: Filters and Search -->
                                <div class="col-md-6">
                                    <input type="hidden" name="page" id="pageInput" value="${param.page != null ? param.page : 1}" />
                                    <div class="row g-3">
                                        <!-- Time Slot -->
                                        <div class="col-4">
                                            <label class="form-label">Time Slot</label>
                                            <select name="slotId" class="form-control select2input" style="min-width: 100%;">
                                                <option value="">All</option>
                                                <c:forEach var="slot" items="${timeSlots}">
                                                    <option value="${slot.slotId}" ${param.slotId == slot.slotId ? 'selected' : ''}>
                                                        ${slot.name}: ${slot.startTime} - ${slot.endTime}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <!-- Status -->
                                        <div class="col-2">
                                            <label class="form-label">Status</label>
                                            <select name="status" class="form-control select2input" style="min-width: 100%;">
                                                <option value="">All</option>
                                                <option value="Approved" ${param.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                                <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                                <option value="Cancelled" ${param.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                            </select>
                                        </div>

                                        <!-- Search + Button -->
                                        <div class="col-4">
                                            <label class="form-label">Search by Doctor</label>
                                            <input type="text" name="keyword" class="form-control" placeholder="Doctor name" value="${param.keyword}">
                                        </div>

                                        <div class="col-2 d-flex align-items-end">
                                            <button type="submit" class="btn btn-primary w-100">Search</button>
                                        </div>
                                    </div>
                                </div>

                                <!-- RIGHT HALF: Appointment Button -->
                                <div class="col-md-6 text-end mt-3 mt-md-0">
                                    <a href="/SWP_BL5_TEAM1/PatientBookAppointmentServlet" class="btn btn-success">+ Appointment</a>
                                </div>

                            </div>


                            <div class="row">
                                <div class="col-12 mt-4">
                                    <div class="table-responsive bg-white shadow rounded">
                                        <table class="table mb-0 table-center">
                                            <thead>
                                                <tr>
                                                    <th class="border-bottom p-3" style="min-width: 50px;">#</th>
                                                    <th class="border-bottom p-3" style="min-width: 180px;">Name</th>
                                                    <th class="border-bottom p-3" style="min-width: 150px;">Date of Birth</th>
                                                    <th class="border-bottom p-3" style="min-width: 150px;">Appointment Date</th>
                                                    <th class="border-bottom p-3">Time Slot</th>
                                                    <th class="border-bottom p-3" style="min-width: 220px;">Doctor</th>
                                                    <th class="border-bottom p-3" style="min-width: 130px;">Status</th>
                                                    <th class="border-bottom p-3" style="min-width: 150px;">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${empty appointments}">
                                                        <tr>
                                                            <td class="p-3 text-center" colspan="8">
                                                                No appointments found matching your search or filters.
                                                            </td>
                                                        </tr>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:forEach var="dto" items="${appointments}">
                                                            <tr>
                                                                <th class="p-3">${dto.index}</th>
                                                                <td class="p-3">
                                                                    <a href="#" class="text-dark">
                                                                        <div class="d-flex align-items-center">
                                                                            <img src="${pageContext.request.contextPath}/assets/images/client/01.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                                            <span class="ms-2">${dto.patientName}</span>
                                                                        </div>
                                                                    </a>
                                                                </td>
                                                                <td class="p-3">
                                                                    <fmt:formatDate value="${dto.patientDateOfBirth}" pattern="dd MMM yyyy" />
                                                                </td>
                                                                <td class="p-3">
                                                                    <fmt:formatDate value="${dto.appointmentDate}" pattern="dd MMM yyyy" />
                                                                </td>
                                                                <td class="p-3">${dto.timeSlotName}</td>
                                                                <td class="p-3">
                                                                    <a href="#" class="text-dark">
                                                                        <div class="d-flex align-items-center">
                                                                            <img src="${pageContext.request.contextPath}/assets/images/doctors/01.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                                            <span class="ms-2">${dto.doctorFullName}</span>
                                                                        </div>
                                                                    </a>
                                                                </td>
                                                                <td class="p-3">
                                                                    <c:choose>
                                                                        <c:when test="${dto.status == 'Pending'}">
                                                                            <span class="badge bg-warning">${dto.status}</span>
                                                                        </c:when>
                                                                        <c:when test="${dto.status == 'Approved'}">
                                                                            <span class="badge bg-success">${dto.status}</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="badge bg-secondary">${dto.status}</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td class="text-start p-3">
                                                                    <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment">
                                                                        <i class="uil uil-eye"></i>
                                                                    </a>

                                                                    <c:if test="${dto.appointmentDate.time > now.time}">
                                                                        <a href="#" 
                                                                           class="btn btn-icon btn-pills btn-soft-success"
                                                                           data-bs-toggle="modal" 
                                                                           data-bs-target="#editAppointmentModal"
                                                                           onclick="fillEditModal(this)"
                                                                           data-id="${dto.appointmentId}"
                                                                           data-fullname="${patient.fullName}"
                                                                           data-phone="${phone}"
                                                                           data-email="${user.email}"
                                                                           data-dob="${patient.dateOfBirth}"
                                                                           data-gender="${patient.gender}"
                                                                           data-doctorid="${appointment.doctorId}"
                                                                           data-date="${appointment.appointmentDate}"
                                                                           data-slotid="${appointment.slotId}">
                                                                            <i class="uil uil-edit"></i>
                                                                        </a>
                                                                    </c:if>

                                                                    <c:if test="${dto.status == 'Pending'}">
                                                                        <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment" data-appointment-id="${dto.appointmentId}">
                                                                            <i class="uil uil-times-circle"></i>
                                                                        </a>
                                                                    </c:if>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:otherwise>
                                                </c:choose>
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
                                                <option value="3" ${param.pageSize == '3' ? 'selected' : ''}>3</option>
                                                <option value="5" ${param.pageSize == '5' ? 'selected' : ''}>5</option>
                                                <option value="10" ${param.pageSize == '10' ? 'selected' : ''}>10</option>
                                                <option value="20" ${param.pageSize == '20' ? 'selected' : ''}>20</option>
                                            </select>
                                            <span class="text-muted">out of ${totalAppointments} total records</span>
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
        <!-- View Appintment Start -->
        <div class="modal fade" id="viewappointment" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header border-bottom p-3">
                        <h5 class="modal-title" id="exampleModalLabel1">Appointment Detail</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-3 pt-4">
                        <div class="d-flex align-items-center">
                            <img src="${pageContext.request.contextPath}/assets/images/client/01.jpg" class="avatar avatar-small rounded-pill" alt="">
                            <h5 class="mb-0 ms-3">Howard Tanner</h5>
                        </div>
                        <ul class="list-unstyled mb-0 d-md-flex justify-content-between mt-4">
                            <li>
                                <ul class="list-unstyled mb-0">
                                    <li class="d-flex">
                                        <h6>Age:</h6>
                                        <p class="text-muted ms-2">25 year old</p>
                                    </li>

                                    <li class="d-flex">
                                        <h6>Gender:</h6>
                                        <p class="text-muted ms-2">Male</p>
                                    </li>

                                    <li class="d-flex">
                                        <h6 class="mb-0">Department:</h6>
                                        <p class="text-muted ms-2 mb-0">Cardiology</p>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <ul class="list-unstyled mb-0">
                                    <li class="d-flex">
                                        <h6>Date:</h6>
                                        <p class="text-muted ms-2">20th Dec 2020</p>
                                    </li>

                                    <li class="d-flex">
                                        <h6>Time:</h6>
                                        <p class="text-muted ms-2">11:00 AM</p>
                                    </li>

                                    <li class="d-flex">
                                        <h6 class="mb-0">Doctor:</h6>
                                        <p class="text-muted ms-2 mb-0">Dr. Calvin Carlo</p>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- View Appintment End -->

        <!-- Edit/Update Appointment Modal Start -->
        <div class="modal fade" id="editAppointmentModal" tabindex="-1" aria-labelledby="editAppointmentLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <form method="post" action="UpdateAppointmentServlet" class="modal-content">
                    <div class="modal-body py-5">
                        <div class="text-center">
                            <div class="icon d-flex align-items-center justify-content-center bg-soft-success rounded-circle mx-auto"
                                 style="height: 95px; width:95px;">
                                <span class="mb-0"><i class="uil uil-edit h1"></i></span>
                            </div>
                            <div class="mt-4 px-4">
                                <h4 class="mb-3">Edit Appointment</h4>

                                <!-- Hidden input for Appointment ID -->
                                <input type="hidden" name="appointmentId" id="editAppointmentId">

                                <div class="form-group mb-3">
                                    <input type="text" class="form-control" name="fullName" id="editFullName"
                                           placeholder="Full Name" required pattern="^[A-Za-z\s]{3,50}$"
                                           title="Full name must be 3-50 characters long and contain only letters and spaces." readonly>
                                </div>

                                <div class="form-group mb-3">
                                    <input type="tel" class="form-control" name="phone" id="editPhone"
                                           placeholder="Phone" required pattern="^\d{10,15}$"
                                           title="Phone number must be between 10 to 15 digits." readonly>
                                </div>

                                <div class="form-group mb-3">
                                    <input type="email" class="form-control" name="email" id="editEmail"
                                           placeholder="Email" pattern="^[^@\s]+@[^@\s]+\.[^@\s]+$"
                                           title="Enter a valid email address." readonly>
                                </div>

                                <div class="row">
                                    <div class="form-group mb-3 col-md-6">
                                        <label class="form-label">Date of Birth</label>
                                        <input type="date" class="form-control" name="dateOfBirth" id="editDateOfBirth" readonly>
                                    </div>
                                    <div class="form-group mb-3 col-md-6">
                                        <label class="form-label">Gender</label>
                                        <select name="gender" id="editGender" class="form-control" disabled>
                                            <option value="">Select Gender</option>
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="form-group mb-3 col-md-6">
                                        <label class="form-label">Doctor</label>
                                        <select name="doctorId" id="editDoctorId" class="form-control" required data-selected-id="${selectedDoctorId}">
                                            <!-- Options will be loaded dynamically -->
                                        </select>

                                    </div>
                                    <div class="form-group mb-3 col-md-6">
                                        <label class="form-label">Appointment Date</label>
                                        <input type="date" class="form-control" name="appointmentDate" id="editAppointmentDate" required min="${currentDate}">
                                    </div>
                                </div>

                                <div class="form-group mb-3">
                                    <label class="form-label">Time Slot</label>
                                    <select name="slotId" id="editSlotId" class="form-control" required>
                                        <c:forEach var="slot" items="${timeSlots}">
                                            <option value="${slot.slotId}">
                                                ${slot.name} (${slot.startTime} - ${slot.endTime})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="d-grid mt-4">
                                    <button type="submit" class="btn btn-primary">Update Appointment</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- Edit/Update Appointment Modal End -->

        <!-- Cancel Appointment Modal -->
        <div class="modal fade" id="cancelappointment" tabindex="-1" aria-labelledby="cancelAppointmentLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <form method="post" action="DeleteAppointmentServlet">
                        <div class="modal-body py-5">
                            <div class="text-center">
                                <div class="icon d-flex align-items-center justify-content-center bg-soft-danger rounded-circle mx-auto" style="height: 95px; width:95px;">
                                    <span class="mb-0"><i class="uil uil-times-circle h1"></i></span>
                                </div>
                                <div class="mt-4">
                                    <h4 id="cancelAppointmentLabel">Cancel Appointment</h4>
                                    <p class="para-desc mx-auto text-muted mb-0">
                                        Are you sure you want to cancel this appointment?<br />
                                        <strong>You can only cancel appointments that are still pending.</strong>
                                    </p>

                                    <!-- Hidden input for appointment ID -->
                                    <input type="hidden" name="id" id="cancelAppointmentId">

                                    <div class="mt-4">
                                        <button type="submit" class="btn btn-soft-danger">Yes, Cancel</button>
                                        <button type="button" class="btn btn-light ms-2" data-bs-dismiss="modal">No, Keep</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- Cancel Appointment End -->

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


        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const slotSelectModal = document.getElementById('editSlotId');
                const doctorSelectModal = document.getElementById('editDoctorId');

                if (!slotSelectModal || !doctorSelectModal) {
                    console.log('Edit modal dropdowns not found!');
                    return;
                }

                function loadDoctorsForModal(slotId, selectedDoctorId = null) {
                    console.log('Loading doctors for slotId (modal):', slotId);

                    fetch(`/SWP_BL5_TEAM1/doctor-by-slot?slotId=` + slotId)
                            .then(response => response.json())
                            .then(data => {
                                doctorSelectModal.innerHTML = '';

                                if (data.length === 0) {
                                    const opt = document.createElement('option');
                                    opt.value = '';
                                    opt.text = 'No doctors on duty';
                                    doctorSelectModal.appendChild(opt);
                                } else {
                                    data.forEach(doctor => {
                                        const opt = document.createElement('option');
                                        opt.value = doctor.staffId;
                                        opt.text = doctor.fullName;
                                        if (selectedDoctorId && parseInt(selectedDoctorId) === doctor.staffId) {
                                            opt.selected = true;
                                        }
                                        doctorSelectModal.appendChild(opt);
                                    });
                                }

                                if ($(doctorSelectModal).hasClass('select2')) {
                                    $(doctorSelectModal).trigger('change.select2');
                                }
                            })
                            .catch(error => console.error('Error fetching doctors (modal):', error));
                }

                // Load doctors when slot changes in modal
                slotSelectModal.addEventListener('change', function () {
                    const slotId = this.value;
                    loadDoctorsForModal(slotId);
                });

                // Optional: When the modal opens, load doctors based on current slot & doctor
                const editModal = document.getElementById('editAppointmentModal');
                editModal.addEventListener('show.bs.modal', function () {
                    const slotId = slotSelectModal.value;
                    const selectedDoctorId = doctorSelectModal.getAttribute('data-selected-id');
                    if (slotId) {
                        loadDoctorsForModal(slotId, selectedDoctorId);
                    }
                });
            });
        </script>

        <script>
            function fillEditModal(button) {
                document.getElementById("editAppointmentId").value = button.getAttribute("data-id");
                document.getElementById("editFullName").value = button.getAttribute("data-fullname");
                document.getElementById("editPhone").value = button.getAttribute("data-phone");
                document.getElementById("editEmail").value = button.getAttribute("data-email");
                document.getElementById("editDateOfBirth").value = button.getAttribute("data-dob");
                document.getElementById("editGender").value = button.getAttribute("data-gender");
                document.getElementById("editDoctorId").value = button.getAttribute("data-doctorid");
                document.getElementById("editAppointmentDate").value = button.getAttribute("data-date");
                document.getElementById("editSlotId").value = button.getAttribute("data-slotid");
            }
        </script>

        <script>
            const cancelModal = document.getElementById('cancelappointment');
            cancelModal.addEventListener('show.bs.modal', function (event) {
                const button = event.relatedTarget;
                const appointmentId = button.getAttribute('data-appointment-id');
                const inputField = cancelModal.querySelector('#cancelAppointmentId');
                inputField.value = appointmentId;
            });
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
