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
        <jsp:include page="/WEB-INF/jsp/includes/patientHead.jsp" />
        <title>JSP Page</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/jsp/common/patientHeaderNav.jsp" />

        <!-- Start Hero -->
        <section class="bg-dashboard">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12 mt-4 pt-2 mt-sm-0 pt-sm-0">
                        <form method="get" action="PatientAppointmentsListServlet">
                            <div class="row mb-3">
                                <div class="col-xl-6 col-lg-6 col-md-4">
                                    <h5 class="mb-0">Your List Of Appointment</h5>
                                </div><!--end col-->
                            </div>
                            <div class="row align-items-end">
                                <!-- LEFT HALF: Filters and Search -->
                                <div class="col-md-6">
                                    <input type="hidden" name="phone" value="3333333333" />
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
                                    <a href="#" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#appointmentform">+ Appointment</a>
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
                                                                    <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                                                    <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-edit"></i></a>
                                                                    <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
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
        <!-- Add New Appointment Start -->
        <div class="modal fade" id="appointmentform" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header border-bottom p-3">
                        <h5 class="modal-title" id="exampleModalLabel">Book an Appointment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-3 pt-4">
                        <form>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="mb-3">
                                        <label class="form-label">Patient Name <span class="text-danger">*</span></label>
                                        <input name="name" id="name" type="text" class="form-control" placeholder="Patient Name :">
                                    </div>
                                </div><!--end col-->

                                <div class="col-lg-4 col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">Departments</label>
                                        <select class="form-control department-name select2input">
                                            <option value="EY">Eye Care</option>
                                            <option value="GY">Gynecologist</option>
                                            <option value="PS">Psychotherapist</option>
                                            <option value="OR">Orthopedic</option>
                                            <option value="DE">Dentist</option>
                                            <option value="GA">Gastrologist</option>
                                            <option value="UR">Urologist</option>
                                            <option value="NE">Neurologist</option>
                                        </select>
                                    </div>
                                </div><!--end col-->

                                <div class="col-lg-4 col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">Doctor</label>
                                        <select class="form-control doctor-name select2input">
                                            <option value="CA">Dr. Calvin Carlo</option>
                                            <option value="CR">Dr. Cristino Murphy</option>
                                            <option value="AL">Dr. Alia Reddy</option>
                                            <option value="TO">Dr. Toni Kovar</option>
                                            <option value="JE">Dr. Jessica McFarlane</option>
                                            <option value="EL">Dr. Elsie Sherman</option>
                                            <option value="BE">Dr. Bertha Magers</option>
                                            <option value="LO">Dr. Louis Batey</option>
                                        </select>
                                    </div>
                                </div><!--end col-->

                                <div class="col-lg-4 col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">Your Email <span class="text-danger">*</span></label>
                                        <input name="email" id="email" type="email" class="form-control" placeholder="Your email :">
                                    </div> 
                                </div><!--end col-->

                                <div class="col-lg-4 col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">Your Phone <span class="text-danger">*</span></label>
                                        <input name="phone" id="phone" type="tel" class="form-control" placeholder="Your Phone :">
                                    </div> 
                                </div><!--end col-->

                                <div class="col-lg-4 col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label"> Date : </label>
                                        <input name="date" type="text" class="flatpickr flatpickr-input form-control" id="checkin-date">
                                    </div>
                                </div><!--end col-->

                                <div class="col-lg-4 col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label" for="input-time">Time : </label>
                                        <input name="time" type="text" class="form-control timepicker" id="input-time" placeholder="03:30 PM">
                                    </div> 
                                </div><!--end col-->

                                <div class="col-lg-12">
                                    <div class="mb-3">
                                        <label class="form-label">Comments <span class="text-danger">*</span></label>
                                        <textarea name="comments" id="comments" rows="4" class="form-control" placeholder="Your Message :"></textarea>
                                    </div>
                                </div><!--end col-->

                                <div class="col-lg-12">
                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary">Book An Appointment</button>
                                    </div>
                                </div><!--end col-->
                            </div><!--end row-->
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Add New Appointment End -->

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

        <!-- Accept Appointment Start -->
        <div class="modal fade" id="acceptappointment" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body py-5">
                        <div class="text-center">
                            <div class="icon d-flex align-items-center justify-content-center bg-soft-success rounded-circle mx-auto" style="height: 95px; width:95px;">
                                <span class="mb-0"><i class="uil uil-edit h1"></i></span>
                            </div>
                            <div class="mt-4">
                                <h4>Accept Appointment</h4>
                                <p class="para-desc mx-auto text-muted mb-0">Great doctor if you need your family member to get immediate assistance, emergency treatment.</p>
                                <div class="mt-4">
                                    <a href="#" class="btn btn-soft-success">Accept</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Accept Appointment End -->

        <!-- Cancel Appointment Start -->
        <div class="modal fade" id="cancelappointment" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body py-5">
                        <div class="text-center">
                            <div class="icon d-flex align-items-center justify-content-center bg-soft-danger rounded-circle mx-auto" style="height: 95px; width:95px;">
                                <span class="mb-0"><i class="uil uil-times-circle h1"></i></span>
                            </div>
                            <div class="mt-4">
                                <h4>Cancel Appointment</h4>
                                <p class="para-desc mx-auto text-muted mb-0">Great doctor if you need your family member to get immediate assistance, emergency treatment.</p>
                                <div class="mt-4">
                                    <a href="#" class="btn btn-soft-danger">Cancel</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Cancel Appointment End -->
        <!-- Modal end -->

        <!-- Footer Start -->
        <footer class="bg-footer py-4">
            <div class="container-fluid">
                <div class="row align-items-center">
                    <div class="col-sm-6">
                        <div class="text-sm-start text-center">
                            <p class="mb-0"><script>document.write(new Date().getFullYear())</script> © Doctris. Design with <i class="mdi mdi-heart text-danger"></i> by <a href="../../../index.html" target="_blank" class="text-reset">Shreethemes</a>.</p>
                        </div>
                    </div><!--end col-->

                    <div class="col-sm-6 mt-4 mt-sm-0">
                        <ul class="list-unstyled footer-list text-sm-end text-center mb-0">
                            <li class="list-inline-item"><a href="terms.html" class="text-foot me-2">Terms</a></li>
                            <li class="list-inline-item"><a href="privacy.html" class="text-foot me-2">Privacy</a></li>
                            <li class="list-inline-item"><a href="aboutus.html" class="text-foot me-2">About</a></li>
                            <li class="list-inline-item"><a href="contact.html" class="text-foot me-2">Contact</a></li>
                        </ul>
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </footer><!--end footer-->
        <!-- End -->

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
