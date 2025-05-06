<%-- 
    Document   : managerAppointmentAnalyticServlet
    Created on : 29 Apr 2025, 08:35:37
    Author     : LENOVO
--%>


<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/includes/patientHead.jsp" />
        <!-- Load Google Charts -->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

        <!-- Pass data into a JS variable -->
        <script type="text/javascript">
            var monthlyAppointmentCountsMap = {};
            <c:if test="${not empty monthlyAppointmentCountsMap}">
                <c:forEach var="entry" items="${monthlyAppointmentCountsMap}">
                    monthlyAppointmentCountsMap[${entry.key}] = ${entry.value};
                </c:forEach>
            </c:if>
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/manager_appointment_analytic.js"></script>
        <title>Manager Analytics</title>
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

        <div class="page-wrapper doctris-theme toggled">
            <jsp:include page="../component/managerSideBar.jsp" />
            <!-- sidebar-wrapper  -->

            <!-- Start Page Content -->
            <main class="page-content bg-light">
                <%@ include file="../component/header.jsp" %>
                <div class="container-fluid">
                    <div class="layout-specing">
                        <h5 class="mb-0">Dashboard</h5>

                        <div class="row">
                            <!-- LEFT HALF: Filters -->
                            <div class="col-md-6">
                                <form action="ManagerAppointmentAnalyticServlet" method="get">
                                    <div class="row g-3">

                                        <!-- Doctor Dropdown -->
                                        <div class="col-4">
                                            <label class="form-label">Doctor</label>
                                            <select name="doctorId" class="form-control select2input" style="min-width: 100%;">
                                                <option value="0">All</option>
                                                <c:forEach var="doctor" items="${allDoctors}">
                                                    <option value="${doctor.doctorId}" ${doctor.doctorId == selectedDoctorId ? 'selected' : ''}>
                                                        ${doctor.fullName} (${doctor.department})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <!-- Package Dropdown -->
                                        <div class="col-4">
                                            <label class="form-label">Package</label>
                                            <select name="packageId" class="form-control select2input" style="min-width: 100%;">
                                                <option value="0">All</option>
                                                <c:forEach var="pkg" items="${examinationPackages}">
                                                    <option value="${pkg.packageId}" ${pkg.packageId == selectedPackageId ? 'selected' : ''}>
                                                        ${pkg.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <!-- Year Input -->
                                        <div class="col-2">
                                            <label class="form-label">Year</label>
                                            <input type="number" name="year" class="form-control" value="${currentYear}" min="2000" max="2100" style="min-width: 100%;" />
                                        </div>

                                        <!-- Search Button -->
                                        <div class="col-2 d-flex align-items-end">
                                            <button type="submit" class="btn btn-primary w-100">Search</button>
                                        </div>

                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xl-11 col-lg-7 mt-4">
                                <div class="card shadow border-0 p-4">
                                    <div class="d-flex justify-content-between align-items-center mb-3"  style="height: 190px; overflow: hidden;">
                                        <h6 class="align-items-center mb-0">Doctor appointment amount</h6>
                                    </div>

                                    <!-- Chart goes inside here -->
                                    <div id="columnchart" style="height: 100%; width: 100%;"></div>
                                </div>
                            </div><!--end col-->


                        </div><!--end row-->

                        <div class="row">

                        </div><!--end row-->
                    </div>
                </div><!--end container-->

                <!-- Footer Start -->
                <footer class="bg-white shadow py-3">
                    <div class="container-fluid">
                        <div class="row align-items-center">
                            <div class="col">
                            </div><!--end col-->
                        </div><!--end row-->
                    </div><!--end container-->
                </footer><!--end footer-->
                <!-- End -->
            </main>
            <!--End page-content" -->
        </div>
        <!-- page-wrapper -->

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
                                    <li class="d-grid"><a href="javascript:void(0)" class="rtl-version t-rtl-light" onclick="setTheme('style-rtl')"><img src="${pageContext.request.contextPath}/assets/images/layouts/light-dash-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="ltr-version t-ltr-light" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/assets/images/layouts/light-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-rtl-version t-rtl-dark" onclick="setTheme('style-dark-rtl')"><img src="${pageContext.request.contextPath}/assets/images/layouts/dark-dash-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-ltr-version t-ltr-dark" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/assets/images/layouts/dark-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-version t-dark mt-4" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/assets/images/layouts/dark-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Dark Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="light-version t-light mt-4" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/assets/images/layouts/light-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Light Version</span></a></li>
                                    <li class="d-grid"><a href="${pageContext.request.contextPath}/landing/index.html" target="_blank" class="mt-4"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Landing Demos</span></a></li>
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
                    <li class="list-inline-item mb-0"><a href="${pageContext.request.contextPath}/${pageContext.request.contextPath}/${pageContext.request.contextPath}/index.html" target="_blank" class="rounded"><i class="uil uil-globe align-middle" title="website"></i></a></li>
                </ul><!--end icon-->
            </div>
        </div>
        <!-- Offcanvas End -->
        <script src="<c:url value='/assets/js/bootstrap.bundle.min.js'/>"></script>
        <!-- javascript -->
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
        <!-- simplebar -->
        <script src="${pageContext.request.contextPath}/assets/js/simplebar.min.js"></script>
        <!-- Chart -->
        <script src="${pageContext.request.contextPath}/assets/js/apexcharts.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/columnchart.init.js"></script>
        <!-- Icons -->
        <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    </body>
</html>
