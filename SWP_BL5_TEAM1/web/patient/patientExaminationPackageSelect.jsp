<%-- 
    Document   : patientExaminationPackageSelect
    Created on : 26 Apr 2025, 16:31:18
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
        <title>Select a package</title>
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
        <jsp:include page="/common/patientHeaderNav.jsp" />

        <!-- Start Hero -->
        <section class="bg-half-70 d-table w-100" style="background: url('${pageContext.request.contextPath}/assets/images/bg/department.jpg') center center;">
            <div class="bg-overlay bg-overlay-dark"></div>
            <div class="container">
                <div class="row mt-5 justify-content-center">
                    <div class="col-12">
                        <div class="section-title text-center">
                            <h3 class="sub-title mb-4 text-white title-dark">Examination Package</h3>
                            <p class="para-desc mx-auto text-white-50">Select your examination package to start booking an appointment</p>
                        </div>
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </section><!--end section-->

        <!-- End Hero -->

        <!-- Start -->
        <form method="get" action="PatientExaminationPackageSelectServlet">
            <section class="section">
                <div class="container">
                    <div class="row mb-4">
                        <!-- Tier Filter -->
                        <div class="col-md-2">
                            <label class="form-label">Tier</label>
                            <select name="tier" class="form-control">
                                <option value="">All</option>
                                <option value="Normal" ${param.tier == 'Normal' ? 'selected' : ''}>Normal</option>
                                <option value="VIP" ${param.tier == 'VIP' ? 'selected' : ''}>VIP</option>
                            </select>
                        </div>

                        <!-- Specialty Filter -->
                        <div class="col-md-3">
                            <label class="form-label">Specialty</label>
                            <select name="specialtyId" class="form-control">
                                <option value="">All</option>
                                <c:forEach var="s" items="${specialties}">
                                    <option value="${s.specialtyId}" ${param.specialtyId == s.specialtyId ? 'selected' : ''}>${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Search -->
                        <div class="col-md-4">
                            <label class="form-label">Search by Name</label>
                            <input type="text" name="search" value="${param.search}" class="form-control" placeholder="Package name" />
                        </div>

                        <!-- Search Button -->
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100">Search</button>
                        </div>
                    </div>
                    <div class="row">
                        <c:forEach var="examPackage" items="${examinationPackages}">
                            <div class="col-xl-3 col-lg-4 col-md-6 col-12 mt-4 pt-2">
                                <div class="card features feature-primary border-0">
                                    <div class="icon text-center rounded-md">
                                        <i class="ri-hospital-line h3 mb-0"></i>
                                    </div>
                                    <div class="card-body p-0 mt-3">
                                        <a href="PatientBookAppointmentServlet?examPackageId=${examPackage.packageId}" class="title text-dark h5">${examPackage.name}</a>
                                        <p class="text-muted mt-3">${examPackage.description}</p>
                                        <a href="PatientBookAppointmentServlet?examPackageId=${examPackage.packageId}" class="link">Book now <i class="ri-arrow-right-line align-middle"></i></a>
                                    </div>
                                </div>
                            </div><!--end col-->
                        </c:forEach>
                    </div><!--end row-->
                    <div class="row">
                        <div class="col-12 text-center mt-4">
                            <ul class="pagination justify-content-center mb-0">
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}&tier=${param.tier}&specialtyId=${param.specialtyId}&search=${param.search}">${i}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div><!--end container-->
            </section><!--end section-->
        </form>
        <!-- End -->

        <!-- Back to top -->
        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i></a>
        <!-- Back to top -->

        <jsp:include page="/component/footer.jsp" />

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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</html>
