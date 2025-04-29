<%-- 
    Document   : patientBookingAppointment
    Created on : 15 Apr 2025, 23:20:26
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/includes/patientHead.jsp" />
        <title>Book an appointment</title>
    </head>
    <body>
        <!-- Loader -->
        <div id="preloader">
            <div id="status">
                <div class="spinner">
                    <div class="double-bounce1"></div>
                    <div class="double-bounce2"></div>
                </div>
            </div>
        </div>
        <!-- Loader -->
        <%
            java.time.LocalDate today = java.time.LocalDate.now();
            request.setAttribute("currentDate", today.toString());
        %>
        <c:set var="isLoggedIn" value="${not empty sessionScope.user}" />
        <jsp:include page="/common/patientHeaderNav.jsp" />

        <!-- Start Hero -->
        <section class="bg-half-70 d-table w-100 bg-light">
            <div class="container">
                <div class="row mt-5 justify-content-center">
                    <div class="col-12">
                        <div class="section-title text-center">
                            <h3 class="sub-title mb-4">Book an appointment</h3>
                            <p class="para-desc mx-auto text-muted">Great doctor if you need your family member to get effective immediate assistance, emergency treatment or a simple consultation.</p>

                            <nav aria-label="breadcrumb" class="d-inline-block mt-3">
                                <ul class="breadcrumb bg-transparent mb-0 py-1">
                                    <li class="breadcrumb-item"><a href="index.html">Doctris</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Appointment</li>
                                </ul>
                            </nav>
                        </div>
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </section><!--end section-->
        <div class="position-relative">
            <div class="shape overflow-hidden text-white">
                <svg viewBox="0 0 2880 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M0 48H1437.5H2880V0H2160C1442.5 52 720 0 720 0H0V48Z" fill="currentColor"></path>
                </svg>
            </div>
        </div>
        <!-- End Hero -->

        <!-- Start -->
        <section class="section">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8">
                        <div class="card border-0 shadow rounded overflow-hidden">
                            <ul class="nav nav-pills nav-justified flex-column flex-sm-row rounded-0 shadow overflow-hidden bg-light mb-0" id="pills-tab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link rounded-0 active" id="clinic-booking" data-bs-toggle="pill" href="#pills-clinic" role="tab" aria-controls="pills-clinic" aria-selected="false">
                                        <div class="text-center pt-1 pb-1">
                                            <h4 class="title fw-normal mb-0">Clinic Appointment</h4>
                                        </div>
                                    </a><!--end nav link-->
                                </li><!--end nav item-->
                            </ul>

                            <div class="tab-content p-4" id="pills-tabContent">
                                <div class="tab-pane fade show active" id="pills-clinic" role="tabpanel" aria-labelledby="clinic-booking">
                                    <form method="post" action="PatientBookAppointmentServlet">
                                        <div class="row">
                                            <!-- Examination Package -->
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Examination Package</label>
                                                    <input type="text" class="form-control" 
                                                           value="${examinationPackage.name}" 
                                                           readonly />
                                                    <input type="hidden" name="examPackageId" value="${examinationPackage.packageId}" />
                                                </div>
                                            </div>

                                            <!-- Full Name -->
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Full Name <span class="text-danger">*</span></label>
                                                    <input name="fullName" id="fullName" type="text" class="form-control"
                                                           placeholder="Patient Full Name :" required 
                                                           pattern="^[A-Za-z]+(?:\s[A-Za-z]+)*$"
                                                           title="Full name must be 1-100 characters long and contain only letters and spaces."
                                                           minlength="1" maxlength="100"
                                                           value="${fullName}" <c:if test="${isLoggedIn}"></c:if> />
                                                    </div>
                                                </div>

                                                <!-- Phone -->
                                                <div class="col-md-6">
                                                    <div class="mb-3">
                                                        <label class="form-label">Phone <span class="text-danger">*</span></label>
                                                        <input name="phone" id="phone" type="tel" class="form-control"
                                                               placeholder="Your Phone :" required pattern="^\d{10,15}$"
                                                               title="Phone number must be between 10 to 15 digits."
                                                               value="${phone}" <c:if test="${isLoggedIn}"></c:if> />
                                                    </div>
                                                </div>

                                                <!-- Email -->
                                                <div class="col-md-6">
                                                    <div class="mb-3">
                                                        <label class="form-label">Email</label>
                                                        <input name="email" id="email" type="email" class="form-control"
                                                               placeholder="Your Email :" pattern="^[^@\s]+@[^@\s]+\.[^@\s]+$"
                                                               title="Enter a valid email address."
                                                               value="${email}" <c:if test="${isLoggedIn}"></c:if> />
                                                    </div>
                                                </div>

                                                <!-- Date of Birth -->
                                                <div class="col-md-6">
                                                    <div class="mb-3">
                                                        <label class="form-label">Date of Birth</label>
                                                        <input name="dateOfBirth" id="dateOfBirth" type="date" class="form-control"
                                                               value="${dateOfBirth}" 
                                                        max="${currentDate}" 
                                                        <c:if test="${isLoggedIn}"></c:if> />
                                                    </div>
                                                </div>

                                                <!-- Gender -->
                                                <div class="col-md-6">
                                                    <div class="mb-3">
                                                        <label class="form-label">Gender</label>
                                                        <select name="gender" class="form-control" <c:if test="${isLoggedIn}"></c:if>>
                                                            <option value="">Select Gender</option>
                                                            <option value="Male" ${gender == 'Male' ? 'selected' : ''}>Male</option>
                                                        <option value="Female" ${gender == 'Female' ? 'selected' : ''}>Female</option>
                                                    </select>

                                                </div>
                                            </div>

                                            <!-- Doctor -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Doctor <span class="text-danger">*</span></label>
                                                    <select name="doctorId" id="doctorSelect" class="form-control select2" required>
                                                        <option value="">Select a time slot and date first</option>
                                                    </select>
                                                </div>
                                            </div>


                                            <!-- Appointment Date -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Appointment Date <span class="text-danger">*</span></label>
                                                    <input name="appointmentDate" id="appointmentDate" type="date" class="form-control" required min="${currentDate}">
                                                </div>
                                            </div>

                                            <!-- Time Slot -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Time Slot <span class="text-danger">*</span></label>
                                                    <select name="slotId" class="form-control select2input" required>
                                                        <c:forEach var="slot" items="${timeSlots}">
                                                            <option value="${slot.slotId}">
                                                                ${slot.name} (${slot.startTime} - ${slot.endTime})
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- Description -->
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Description</label>
                                                    <textarea name="description" id="description" rows="4" class="form-control" 
                                                              placeholder="Write a description for your appointment (optional)">${description}</textarea>
                                                </div>
                                            </div><!--end col-->

                                            <!-- Submit Button -->
                                            <div class="col-lg-12">
                                                <div class="d-grid">
                                                    <button type="submit" class="btn btn-primary">Book Appointment</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </section><!--end section-->
        <!-- End -->
        
        <jsp:include page="/component/footer.jsp" />

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
                    <img src="../assets/images/logo-dark.png" height="24" class="light-version" alt="">
                    <img src="../assets/images/logo-light.png" height="24" class="dark-version" alt="">
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
                                    <li class="d-grid"><a href="javascript:void(0)" class="rtl-version t-rtl-light" onclick="setTheme('style-rtl')"><img src="../assets/images/layouts/landing-light-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="ltr-version t-ltr-light" onclick="setTheme('style')"><img src="../assets/images/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-rtl-version t-rtl-dark" onclick="setTheme('style-dark-rtl')"><img src="../assets/images/layouts/landing-dark-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-ltr-version t-ltr-dark" onclick="setTheme('style-dark')"><img src="../assets/images/layouts/landing-dark.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-version t-dark mt-4" onclick="setTheme('style-dark')"><img src="../assets/images/layouts/landing-dark.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Dark Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="light-version t-light mt-4" onclick="setTheme('style')"><img src="../assets/images/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Light Version</span></a></li>
                                    <li class="d-grid"><a href="../admin/index.html" target="_blank" class="mt-4"><img src="../assets/images/layouts/light-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Admin Dashboard</span></a></li>
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

        <!<!-- Toast notification -->
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div class="toast align-items-center text-white bg-success border-0 ${appointmentStatus == 'fail' ? 'd-none' : ''}" 
                 id="successToast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        Appointment booked successfully!
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>

            <div class="toast align-items-center text-white bg-danger border-0 ${appointmentStatus == 'success' ? 'd-none' : ''}" 
                 id="failToast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        Failed to book appointment. Please try again.
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </div>
        <%
            String status = request.getParameter("status");
        %>

        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <% if ("success".equals(status)) { %>
            <div class="toast align-items-center text-white bg-success border-0" 
                 id="statusToast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        Appointment booked successfully!
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
            <% } else if ("fail".equals(status)) { %>
            <div class="toast align-items-center text-white bg-danger border-0" 
                 id="statusToast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        Failed to book appointment. Please try again.
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
            <% }%>
        </div>
        
        

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
        <script src=".${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

        <script>
                                        document.addEventListener('DOMContentLoaded', function () {
                                            const toastEl = document.getElementById('statusToast');
                                            if (toastEl) {
                                                new bootstrap.Toast(toastEl).show();
                                            }
                                        });
        </script>

        <script>
            document.querySelector('form').addEventListener('submit', function (e) {
                const phone = document.getElementById('phone').value;
                const email = document.getElementById('email').value;

                const phoneRegex = /^\d{10,15}$/;
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                if (!phoneRegex.test(phone)) {
                    alert("Please enter a valid phone number (10-15 digits).");
                    e.preventDefault();
                }

                if (email && !emailRegex.test(email)) {
                    alert("Please enter a valid email address.");
                    e.preventDefault();
                }
            });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const slotSelect = document.querySelector('select[name="slotId"]');
                const doctorSelect = document.getElementById('doctorSelect');
                const appointmentDateInput = document.getElementById('appointmentDate');

                if (!slotSelect || !doctorSelect || !appointmentDateInput) {
                    console.log('Dropdowns or date input not found!');
                    return;
                }

                function loadDoctors(slotId, appointmentDate) {
                    console.log('Loading doctors for slotId:', slotId, 'on date:', appointmentDate);

                    if (!slotId || !appointmentDate) {
                        console.log('Missing slot or date');
                        doctorSelect.innerHTML = '<option value="">Select a time slot and date</option>';
                        return;
                    }

                    fetch("/SWP_BL5_TEAM1/doctor-by-slot-and-date?slotId=" + slotId + "&appointmentDate=" + appointmentDate)
                            .then(response => response.json())
                            .then(data => {
                                console.log('Doctors fetched:', data);
                                doctorSelect.innerHTML = '';

                                if (data.length === 0) {
                                    const opt = document.createElement('option');
                                    opt.value = '';
                                    opt.text = 'No doctors available';
                                    doctorSelect.appendChild(opt);
                                } else {
                                    data.forEach(doctor => {
                                        const opt = document.createElement('option');
                                        opt.value = doctor.staffId;
                                        opt.text = doctor.fullName;
                                        doctorSelect.appendChild(opt);
                                    });
                                }

                                if ($(doctorSelect).hasClass('select2')) {
                                    $(doctorSelect).trigger('change.select2');
                                }
                            })
                            .catch(error => console.error('Error fetching doctors:', error));
                }

                function triggerLoad() {
                    const slotId = slotSelect.value;
                    const appointmentDate = appointmentDateInput.value;
                    loadDoctors(slotId, appointmentDate);
                }

                // Run when slot changes
                slotSelect.addEventListener('change', triggerLoad);

                // Run when date changes
                appointmentDateInput.addEventListener('change', triggerLoad);

                // Initial load
                if (slotSelect.value && appointmentDateInput.value) {
                    triggerLoad();
                }
            });
        </script>

        <!--        <script>
                    $(document).ready(function() {
                        $('.select2input').select2();
        
                        $('.select2input').on('change', function () {
                            const selectedValue = $(this).val();
                            console.log("Selected Slot ID (Select2):", selectedValue);
                        });
                    });
                </script>-->


    </body>
</html>
