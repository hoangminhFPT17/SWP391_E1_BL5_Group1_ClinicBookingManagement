<%-- 
    Document   : patientHeaderNav
    Created on : 15 Apr 2025, 21:52:13
    Author     : LENOVO
--%>

<!-- Navbar STart -->
<header id="topnav" class="defaultscroll sticky">
    <div class="container-fluid">
        <!-- Logo container-->
        <a class="logo" href="home.jsp">
            <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="24" class="logo-light-mode" alt="">
            <img src="${pageContext.request.contextPath}/assets/images/logo-light.png" height="24" class="logo-dark-mode" alt="">
        </a>                
        <!-- Logo End -->

        <!-- Start Mobile Toggle -->
        <div class="menu-extras">
            <div class="menu-item">
                <!-- Mobile menu toggle-->
                <a class="navbar-toggle" id="isToggle" onclick="toggleMenu()">
                    <div class="lines">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </a>
                <!-- End mobile menu toggle-->
            </div>
        </div>
        <!-- End Mobile Toggle -->

        <!-- Start Dropdown -->
        <ul class="dropdowns list-inline mb-0">



            <li class="list-inline-item mb-0 ms-1">
                <div class="dropdown dropdown-primary">
                    <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="${pageContext.request.contextPath}/assets/images/client/09.jpg" class="avatar avatar-ex-small rounded-circle" alt=""></button>
                    <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                        <a class="dropdown-item d-flex align-items-center text-dark" href="doctor-profile.html">
                            <img src="${pageContext.request.contextPath}/assets/images/client/09.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                            <div class="flex-1 ms-2">
                                <span class="d-block mb-1">Mrs. Christopher</span>
                                <small class="text-muted">25 Year old</small>
                            </div>
                        </a>
                        <a class="dropdown-item text-dark" href="patient-dashboard.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span> Dashboard</a>
                        <a class="dropdown-item text-dark" href="patient-profile.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-user align-middle h6"></i></span> Profile</a>
                        <div class="dropdown-divider border-top"></div>
                        <a class="dropdown-item text-dark" href="javascript:void(0)"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                    </div>
                </div>
            </li>
        </ul>
        <!-- Start Dropdown -->

        <div id="navigation">
            <!-- Navigation Menu-->   
            <ul class="navigation-menu nav-left">
               <li><a href="home.jsp" class="sub-menu-item">Home</a></li>
               
                <li class="has-submenu parent-menu-item">
                    <a href="javascript:void(0)">Patients</a><span class="menu-arrow"></span>
                    <ul class="submenu">
                        <li><a href="PatientAppointmentsListServlet" class="sub-menu-item">Your Appointment List</a></li>
                        <li><a href="PatientExaminationPackageSelectServlet" class="sub-menu-item">Book an appointment</a></li>
                        <li><a href="patient-invoice.html" class="sub-menu-item">Invoice</a></li>
                    </ul>
                </li>
            </ul><!--end navigation menu-->
        </div><!--end navigation-->
    </div><!--end container-->
</header><!--end header-->
<!-- Navbar End -->
