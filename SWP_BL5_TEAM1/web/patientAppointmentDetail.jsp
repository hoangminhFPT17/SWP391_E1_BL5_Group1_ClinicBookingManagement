<%-- 
    Document   : patientAppointmentDetail
    Created on : 17 Apr 2025, 11:35:03
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/WEB-INF/jsp/includes/patientHead.jsp" />
        <title>JSP Page</title>
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
        
        <jsp:include page="/WEB-INF/jsp/common/patientHeaderNav.jsp" />
        
        <!-- Start Hero -->
        <section class="bg-half-170 d-table w-100 bg-light">
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

                                <li class="nav-item">
                                    <a class="nav-link rounded-0" id="online-booking" data-bs-toggle="pill" href="#pills-online" role="tab" aria-controls="pills-online" aria-selected="false">
                                        <div class="text-center pt-1 pb-1">
                                            <h4 class="title fw-normal mb-0">Online Appointment</h4>
                                        </div>
                                    </a><!--end nav link-->
                                </li><!--end nav item-->
                            </ul>

                            <div class="tab-content p-4" id="pills-tabContent">
                                <div class="tab-pane fade show active" id="pills-clinic" role="tabpanel" aria-labelledby="clinic-booking">
                                    <form method="post" action="PatientBookAppointmentServlet">
                                        <div class="row">

                                            <!-- Full Name -->
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Full Name <span class="text-danger">*</span></label>
                                                    <input name="fullName" id="fullName" type="text" class="form-control" placeholder="Patient Full Name :" required>
                                                </div>
                                            </div>

                                            <!-- Phone -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Phone <span class="text-danger">*</span></label>
                                                    <input name="phone" id="phone" type="tel" class="form-control" placeholder="Your Phone :" required>
                                                </div>
                                            </div>

                                            <!-- Email -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Email</label>
                                                    <input name="email" id="email" type="email" class="form-control" placeholder="Your Email :">
                                                </div>
                                            </div>

                                            <!-- Date of Birth -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Date of Birth</label>
                                                    <input name="dateOfBirth" id="dateOfBirth" type="date" class="form-control">
                                                </div>
                                            </div>

                                            <!-- Gender -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Gender</label>
                                                    <select name="gender" class="form-control">
                                                        <option value="">Select Gender</option>
                                                        <option value="Male">Male</option>
                                                        <option value="Female">Female</option>
                                                        <option value="Other">Other</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- Doctor -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Doctor <span class="text-danger">*</span></label>
                                                    <select name="doctorId" id="doctorSelect" class="form-control select2" required>
                                                        <option value="">Select a time slot first</option>
                                                    </select>
                                                </div>
                                            </div>


                                            <!-- Appointment Date -->
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Appointment Date <span class="text-danger">*</span></label>
                                                    <input name="appointmentDate" id="appointmentDate" type="date" class="form-control" required>
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

                                            <!-- Submit Button -->
                                            <div class="col-lg-12">
                                                <div class="d-grid">
                                                    <button type="submit" class="btn btn-primary">Book Appointment</button>
                                                </div>
                                            </div>

                                        </div>
                                    </form>

                                </div>

                                <div class="tab-pane fade" id="pills-online" role="tabpanel" aria-labelledby="online-booking">
                                    <form>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Patient Name <span class="text-danger">*</span></label>
                                                    <input name="name" id="name2" type="text" class="form-control" placeholder="Patient Name :">
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-6">
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

                                            <div class="col-md-6">
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

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Your Email <span class="text-danger">*</span></label>
                                                    <input name="email" id="email2" type="email" class="form-control" placeholder="Your email :">
                                                </div> 
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Your Phone <span class="text-danger">*</span></label>
                                                    <input name="phone" id="phone2" type="tel" class="form-control" placeholder="Your Phone :">
                                                </div> 
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label"> Date : </label>
                                                    <input name="date" type="text" class="flatpickr flatpickr-input form-control" id="checkin-date">
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label" for="input-time">Time : </label>
                                                    <input name="time" type="text" class="form-control timepicker" id="input-time" placeholder="03:30 PM">
                                                </div> 
                                            </div><!--end col-->

                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Comments <span class="text-danger">*</span></label>
                                                    <textarea name="comments" id="comments2" rows="4" class="form-control" placeholder="Your Message :"></textarea>
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
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </section><!--end section-->
        <!-- End -->

        <!-- Start -->
        <footer class="bg-footer">
            <div class="container">
                <div class="row">
                    <div class="col-xl-5 col-lg-4 mb-0 mb-md-4 pb-0 pb-md-2">
                        <a href="#" class="logo-footer">
                            <img src="../assets/images/logo-light.png" height="22" alt="">
                        </a>
                        <p class="mt-4 me-xl-5">Great doctor if you need your family member to get effective immediate assistance, emergency treatment or a simple consultation.</p>
                    </div><!--end col-->

                    <div class="col-xl-7 col-lg-8 col-md-12">
                        <div class="row">
                            <div class="col-md-4 col-12 mt-4 mt-sm-0 pt-2 pt-sm-0">
                                <h5 class="text-light title-dark footer-head">Company</h5>
                                <ul class="list-unstyled footer-list mt-4">
                                    <li><a href="aboutus.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> About us</a></li>
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Services</a></li>
                                    <li><a href="doctor-team-two.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Team</a></li>
                                    <li><a href="blog-detail.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Project</a></li>
                                    <li><a href="blogs.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Blog</a></li>
                                    <li><a href="login.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Login</a></li>
                                </ul>
                            </div><!--end col-->

                            <div class="col-md-4 col-12 mt-4 mt-sm-0 pt-2 pt-sm-0">
                                <h5 class="text-light title-dark footer-head">Departments</h5>
                                <ul class="list-unstyled footer-list mt-4">
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Eye Care</a></li>
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Psychotherapy</a></li>
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Dental Care</a></li>
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Orthopedic</a></li>
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Cardiology</a></li>
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Gynecology</a></li>
                                    <li><a href="departments.html" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Neurology</a></li>
                                </ul>
                            </div><!--end col-->

                            <div class="col-md-4 col-12 mt-4 mt-sm-0 pt-2 pt-sm-0">
                                <h5 class="text-light title-dark footer-head">Contact us</h5>
                                <ul class="list-unstyled footer-list mt-4">
                                    <li class="d-flex align-items-center">
                                        <i data-feather="mail" class="fea icon-sm text-foot align-middle"></i>
                                        <a href="mailto:contact@example.com" class="text-foot ms-2">contact@example.com</a>
                                    </li>

                                    <li class="d-flex align-items-center">
                                        <i data-feather="phone" class="fea icon-sm text-foot align-middle"></i>
                                        <a href="tel:+152534-468-854" class="text-foot ms-2">+152 534-468-854</a>
                                    </li>

                                    <li class="d-flex align-items-center">
                                        <i data-feather="map-pin" class="fea icon-sm text-foot align-middle"></i>
                                        <a href="javascript:void(0)" class="video-play-icon text-foot ms-2">View on Google map</a>
                                    </li>
                                </ul>

                                <ul class="list-unstyled social-icon footer-social mb-0 mt-4">
                                    <li class="list-inline-item"><a href="#" class="rounded-pill"><i data-feather="facebook" class="fea icon-sm fea-social"></i></a></li>
                                    <li class="list-inline-item"><a href="#" class="rounded-pill"><i data-feather="instagram" class="fea icon-sm fea-social"></i></a></li>
                                    <li class="list-inline-item"><a href="#" class="rounded-pill"><i data-feather="twitter" class="fea icon-sm fea-social"></i></a></li>
                                    <li class="list-inline-item"><a href="#" class="rounded-pill"><i data-feather="linkedin" class="fea icon-sm fea-social"></i></a></li>
                                </ul><!--end icon-->
                            </div><!--end col-->
                        </div><!--end row-->
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->

            <div class="container mt-5">
                <div class="pt-4 footer-bar">
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
                </div>
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

        <c:if test="${not empty appointmentStatus}">
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    const status = '${appointmentStatus}';
                    if (status === 'success') {
                        showToast("Appointment booked successfully!", "success");
                    } else {
                        showToast("Failed to book appointment. Please try again.", "error");
                    }
                });

                function showToast(message, type) {
                    const toast = document.createElement('div');
                    toast.className = 'toast ' + type;
                    toast.textContent = message;
                    document.body.appendChild(toast);

                    setTimeout(() => {
                        toast.classList.add('show');
                        setTimeout(() => {
                            toast.classList.remove('show');
                            setTimeout(() => toast.remove(), 500);
                        }, 3000);
                    }, 100);
                }
            </script>

            <style>
                .toast {
                    position: fixed;
                    bottom: 30px;
                    right: 30px;
                    padding: 15px 25px;
                    color: #fff;
                    background-color: #333;
                    border-radius: 8px;
                    opacity: 0;
                    transition: all 0.5s ease;
                    z-index: 9999;
                }

                .toast.success {
                    background-color: #28a745;
                }

                .toast.error {
                    background-color: #dc3545;
                }

                .toast.show {
                    opacity: 1;
                    transform: translateY(-10px);
                }
            </style>
        </c:if>
        
        <script>
        document.addEventListener("DOMContentLoaded", function () {
            const departmentSelect = document.querySelector('.department-name');
            const doctorSelect = document.querySelector('#doctorSelect');
            const allDoctors = Array.from(doctorSelect.options);

            departmentSelect.addEventListener('change', function () {
                const selectedDept = departmentSelect.value;

                // Clear current options
                doctorSelect.innerHTML = '';

                // Add matching options back
                allDoctors.forEach(doctor => {
                    if (doctor.dataset.department === selectedDept) {
                        doctorSelect.appendChild(doctor);
                    }
                });

                // Optional: reset selected value
                doctorSelect.selectedIndex = 0;
            });
        });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const slotSelect = document.querySelector('select[name="slotId"]');
                if (!slotSelect) {
                    console.log('Dropdown not found!');
                    return;
                }

                slotSelect.addEventListener('change', function () {
                    console.log('Slot dropdown changed!');
                    const slotId = this.value;
                    const doctorSelect = document.getElementById('doctorSelect');
                    console.log('SLOT ID RIGHT NOW: ' + slotId);

                    fetch(`/SWP_BL5_TEAM1/doctor-by-slot?slotId=` + slotId)
                            .then(response => response.json())
                            .then(data => {
                                console.log('Doctors fetched:', data);

                                doctorSelect.innerHTML = '';
                                if (data.length === 0) {
                                    const opt = document.createElement('option');
                                    opt.value = '';
                                    opt.text = 'No doctors on duty';
                                    doctorSelect.appendChild(opt);
                                } else {
                                    data.forEach(doctor => {
                                        const opt = document.createElement('option');
                                        opt.value = doctor.staffId;
                                        opt.text = doctor.fullName; // <-- SHOW NAME INSTEAD
                                        doctorSelect.appendChild(opt);
                                    });
                                }

                                if ($(doctorSelect).hasClass('select2')) {
                                    $(doctorSelect).trigger('change.select2');
                                }
                            })
                            .catch(error => console.error('Error fetching doctors:', error));
                });
            });
        </script>
        
    </body>
</html>
