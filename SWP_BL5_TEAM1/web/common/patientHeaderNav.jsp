<%-- 
    Document   : patientHeaderNav
    Created on : 15 Apr 2025, 21:52:13
    Author     : LENOVO
--%>

<!-- Navbar STart -->
<header id="topnav" class="defaultscroll sticky">
    <div class="container-fluid">
        <!-- Logo container-->
        <a class="logo" href="index.html">
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
            <li class="list-inline-item mb-0">
                <a href="javascript:void(0)" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
                    <div class="btn btn-icon btn-pills btn-primary"><i data-feather="settings" class="fea icon-sm"></i></div>
                </a>
            </li>

            <li class="list-inline-item mb-0 ms-1">
                <a href="javascript:void(0)" class="btn btn-icon btn-pills btn-primary" data-bs-toggle="offcanvas" data-bs-target="#offcanvasTop" aria-controls="offcanvasTop">
                    <i class="uil uil-search"></i>
                </a>
            </li>

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
                <li class="has-submenu parent-menu-item">
                    <a href="javascript:void(0)">Home</a><span class="menu-arrow"></span>
                    <ul class="submenu">
                        <li><a href="index.html" class="sub-menu-item">Index One</a></li>
                        <li><a href="index-two.html" class="sub-menu-item">Index Two</a></li>
                        <li><a href="index-three.html" class="sub-menu-item">Index Three</a></li>
                    </ul>
                </li>

                <li class="has-submenu parent-parent-menu-item">
                    <a href="javascript:void(0)">Doctors</a><span class="menu-arrow"></span>
                    <ul class="submenu">
                        <li class="has-submenu parent-menu-item">
                            <a href="javascript:void(0)" class="menu-item"> Dashboard </a><span class="submenu-arrow"></span>
                            <ul class="submenu">
                                <li><a href="doctor-dashboard.html" class="sub-menu-item">Dashboard</a></li>
                                <li><a href="doctor-appointment.html" class="sub-menu-item">Appointment</a></li>
                                <li><a href="patient-list.html" class="sub-menu-item">Patients</a></li>
                                <li><a href="doctor-schedule.html" class="sub-menu-item">Schedule Timing</a></li>
                                <li><a href="invoices.html" class="sub-menu-item">Invoices</a></li>
                                <li><a href="patient-review.html" class="sub-menu-item">Reviews</a></li>
                                <li><a href="doctor-messages.html" class="sub-menu-item">Messages</a></li>
                                <li><a href="doctor-profile.html" class="sub-menu-item">Profile</a></li>
                                <li><a href="doctor-profile-setting.html" class="sub-menu-item">Profile Settings</a></li>
                                <li><a href="doctor-chat.html" class="sub-menu-item">Chat</a></li>
                                <li><a href="login.html" class="sub-menu-item">Login</a></li>
                                <li><a href="signup.html" class="sub-menu-item">Sign Up</a></li>
                                <li><a href="forgot-password.html" class="sub-menu-item">Forgot Password</a></li>
                            </ul>
                        </li>
                        <li><a href="doctor-team-one.html" class="sub-menu-item">Doctors One</a></li>
                        <li><a href="doctor-team-two.html" class="sub-menu-item">Doctors Two</a></li>
                        <li><a href="doctor-team-three.html" class="sub-menu-item">Doctors Three</a></li>
                    </ul>
                </li>

                <li class="has-submenu parent-menu-item">
                    <a href="javascript:void(0)">Patients</a><span class="menu-arrow"></span>
                    <ul class="submenu">
                        <li><a href="patient-dashboard.html" class="sub-menu-item">Dashboard</a></li>
                        <li><a href="patient-profile.html" class="sub-menu-item">Profile</a></li>
                        <li><a href="booking-appointment.html" class="sub-menu-item">Book Appointment</a></li>
                        <li><a href="patient-invoice.html" class="sub-menu-item">Invoice</a></li>
                    </ul>
                </li>

                <li class="has-submenu parent-menu-item">
                    <a href="javascript:void(0)">Pharmacy</a><span class="menu-arrow"></span>
                    <ul class="submenu">
                        <li><a href="pharmacy.html" class="sub-menu-item">Pharmacy</a></li>
                        <li><a href="pharmacy-shop.html" class="sub-menu-item">Shop</a></li>
                        <li><a href="pharmacy-product-detail.html" class="sub-menu-item">Medicine Detail</a></li>
                        <li><a href="pharmacy-shop-cart.html" class="sub-menu-item">Shop Cart</a></li>
                        <li><a href="pharmacy-checkout.html" class="sub-menu-item">Checkout</a></li>
                        <li><a href="pharmacy-account.html" class="sub-menu-item">Account</a></li>
                    </ul>
                </li>

                <li class="has-submenu parent-parent-menu-item"><a href="javascript:void(0)">Pages</a><span class="menu-arrow"></span>
                    <ul class="submenu">
                        <li><a href="aboutus.html" class="sub-menu-item"> About Us</a></li>
                        <li><a href="departments.html" class="sub-menu-item">Departments</a></li>
                        <li><a href="faqs.html" class="sub-menu-item">FAQs</a></li>
                        <li class="has-submenu parent-menu-item">
                            <a href="javascript:void(0)" class="menu-item"> Blogs </a><span class="submenu-arrow"></span>
                            <ul class="submenu">
                                <li><a href="blogs.html" class="sub-menu-item">Blogs</a></li>
                                <li><a href="blog-detail.html" class="sub-menu-item">Blog Details</a></li>
                            </ul>
                        </li>
                        <li><a href="terms.html" class="sub-menu-item">Terms & Policy</a></li>
                        <li><a href="privacy.html" class="sub-menu-item">Privacy Policy</a></li>
                        <li><a href="error.html" class="sub-menu-item">404 !</a></li>
                        <li><a href="contact.html" class="sub-menu-item">Contact</a></li>
                    </ul>
                </li>
                <li><a href="${pageContext.request.contextPath}/admin/index.html" class="sub-menu-item" target="_blank">Admin</a></li>
            </ul><!--end navigation menu-->
        </div><!--end navigation-->
    </div><!--end container-->
</header><!--end header-->
<!-- Navbar End -->
