<%-- 
    Document   : doctorTimeSlotList
    Created on : 26 Apr 2025, 09:17:46
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
                        <div class="row mb-3">
                            <div class="col-xl-6 col-lg-6 col-md-4">
                                <h5 class="mb-0">Assign Doctor To Time Slot</h5>
                            </div><!--end col-->
                        </div>

                        <div class="row">
                            <div class="col-12 mt-4">
                                <div class="table-responsive bg-white shadow rounded">
                                    <form id="doctorTimeSlotForm">
                                        <table class="table mb-0 table-center">
                                            <thead>
                                                <tr>
                                                    <th>Time Slot</th>
                                                        <c:forEach var="day" items="${['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']}">
                                                        <th class="text-center">${day}</th>
                                                        </c:forEach>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="slot" items="${timeSlotList}">
                                                    <tr>
                                                        <td>${slot.name}: ${slot.startTime}-${slot.endTime}</td>
                                                        <c:forEach var="day" items="${['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']}">
                                                            <td class="text-center">
                                                                <input 
                                                                    type="checkbox"
                                                                    class="time-slot-checkbox"
                                                                    data-slot-id="${slot.slotId}"
                                                                    data-day="${day}"
                                                                    <c:if test="${doctorSlotMap[day] != null && doctorSlotMap[day].contains(slot.slotId)}">checked</c:if>
                                                                        />
                                                                </td>
                                                        </c:forEach>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="row mt-5 mb-3">
                            <div class="col-xl-6 col-lg-6 col-md-4">
                                <h5 class="mb-0">Date of unavailability</h5>
                            </div><!--end col-->
                        </div>
                        <div class="row">
                            <div class="col-12 mt-4">
                                <div class="table-responsive bg-white shadow rounded">
                                    <table class="table mb-0 table-center">
                                        <thead class="thead-light">
                                            <tr>
                                                <th>#</th>
                                                <th>Time Slot</th>
                                                <th>Unavailable Date</th>
                                                <th>Reason</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="unavail" items="${unavailabilities}">
                                                <tr>
                                                    <td>${unavail.index}</td>
                                                    <td>${unavail.slotNameWithTime}</td>
                                                    <td>${unavail.unavailableDate}</td>
                                                    <td>${unavail.reason}</td>
                                                    <td>
                                                        <form action="RemoveUnavailabilityServlet" method="post" style="display: inline;">
                                                            <input type="hidden" name="unavailabilityId" value="${unavail.unavailabilityId}">
                                                            <button type="submit" class="btn btn-icon btn-pills btn-soft-danger" onclick="return confirm('Are you sure you want to remove this unavailability?')">
                                                                <i class="uil uil-times-circle"></i>
                                                            </button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty unavailabilities}">
                                                <tr>
                                                    <td colspan="5" class="text-center">No unavailability records found.</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="row mt-3">
                                    <div class="col-12 text-end">
                                        <a href="#" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#addUnavailabilityModal">+ Unavailable Date</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--end row-->
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </section><!--end section-->
        <!-- End Hero -->
        <!-- Back to top -->
        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i></a>
        <!-- Back to top -->

        <!-- Add Unavailability Modal Start -->
        <div class="modal fade" id="addUnavailabilityModal" tabindex="-1" aria-labelledby="addUnavailabilityLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <form method="post" action="AddUnavailabilityServlet" class="modal-content">
                    <input type="hidden" name="staffId" value="${staffAccount.staffId}">
                    <div class="modal-body py-5">
                        <div class="text-center">
                            <div class="icon d-flex align-items-center justify-content-center bg-soft-danger rounded-circle mx-auto"
                                 style="height: 95px; width:95px;">
                                <span class="mb-0"><i class="uil uil-calendar-slash h1"></i></span>
                            </div>
                            <div class="mt-4 px-4">
                                <h4 class="mb-3">Add Unavailable Date</h4>

                                <div class="form-group mb-3">
                                    <label class="form-label">Time Slot</label>
                                    <select name="slotId" id="unavailableSlotId" class="form-control" required>
                                        <c:forEach var="slot" items="${timeSlotList}">
                                            <option value="${slot.slotId}">
                                                ${slot.name} (${slot.startTime} - ${slot.endTime})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group mb-3">
                                    <label class="form-label">Unavailable Date</label>
                                    <input type="date" class="form-control" name="unavailableDate" id="unavailableDate" required min="${currentDate}">
                                </div>

                                <div class="form-group mb-3">
                                    <label class="form-label">Reason</label>
                                    <textarea class="form-control" name="reason" id="unavailableReason" rows="3" placeholder="Enter reason" required></textarea>
                                </div>

                                <div class="d-grid mt-4">
                                    <button type="submit" class="btn btn-danger">Add Unavailability</button>
                                </div>

                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- Add Unavailability Modal End -->

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

        <c:if test="${not empty errorMessage}">
            <div aria-live="polite" aria-atomic="true" class="position-relative">
                <div class="toast-container position-fixed bottom-0 end-0 p-3">
                    <div class="toast align-items-center text-bg-danger border-0 show" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="d-flex">
                            <div class="toast-body">
                                ${errorMessage}
                            </div>
                            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>


        <script>
            document.querySelectorAll('.time-slot-checkbox').forEach(checkbox => {
                checkbox.addEventListener('change', function () {
                    const slotId = this.getAttribute('data-slot-id');
                    const day = this.getAttribute('data-day');
                    const isChecked = this.checked ? 1 : 0; // 1 = working, 0 = not working

                    console.log("Slot ID: " + slotId + ", Day: " + day + ", Checked: " + isChecked);

                    fetch('UpdateDoctorTimeSlotServlet', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: new URLSearchParams({
                            slotId: slotId,
                            dayOfWeek: day,
                            isWorking: isChecked
                        })
                    })
                            .then(response => response.json())
                            .then(data => {
                                if (data.success) {
                                    console.log('Updated successfully!');
                                } else {
                                    alert('Failed to update schedule.');
                                }
                            })
                            .catch(err => {
                                console.error('Error updating schedule:', err);
                            });
                });
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
