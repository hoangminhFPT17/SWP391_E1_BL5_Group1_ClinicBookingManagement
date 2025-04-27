<%-- 
    Document   : header
    Created on : 15 Apr 2025, 15:19:45
    Author     : LENOVO
--%>

<%@page import="model.User"%>
<%
    User user = (User) session.getAttribute("user");
%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
<script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/md5.js"></script>
<script>
    function encrypt()
    {
        var pass = document.getElementById('password').value;
        var hide = document.getElementById('hide').value;
        if (pass == "")
        {
            document.getElementById('err').innerHTML = 'Error:Password is missing';
            return false;
        } else
        {
            document.getElementById("hide").value = document.getElementById("password").value;
            var hash = CryptoJS.MD5(pass);
            document.getElementById('password').value = hash;
            return true;
        }
    }
</script>
<script>
    function encryptForPassChange()
    {
        var curPass = document.getElementById('currentPassword').value;
        var newPass = document.getElementById('newPassword').value;
        var confirmPass = document.getElementById('confirmPassword').value;
        var hide = document.getElementById('hide').value;
        if (curPass == "")
        {
            document.getElementById('err').innerHTML = 'Error:Password is missing';
            return false;
        } else
        {
            document.getElementById("hide").value = document.getElementById("currentPassword").value;
            var hashOld = CryptoJS.MD5(curPass);
            var hashNew = CryptoJS.MD5(newPass);
            var hashConfirm = CryptoJS.MD5(confirmPass);
            document.getElementById('currentPassword').value = hashOld;
            document.getElementById('newPassword').value = hashNew;
            document.getElementById('confirmPassword').value = hashConfirm;
            return true;
        }
    }
</script>
<script>
    function encryptForPassForgot()
    {
        var newPass = document.getElementById('password').value;
        var confirmPass = document.getElementById('confirm-password').value;
        var hide = document.getElementById('hide').value;
        if (newPass == "")
        {
            document.getElementById('err').innerHTML = 'Error:Password is missing';
            return false;
        } else
        {
            document.getElementById("hide").value = document.getElementById("password").value;
            var hashNew = CryptoJS.MD5(newPass);
            var hashConfirm = CryptoJS.MD5(confirmPass);
            document.getElementById('password').value = hashNew;
            document.getElementById('confirm-password').value = hashConfirm;
            return true;
        }
    }
</script>
<!-- Navbar STart -->
<header id="topnav" class="navigation sticky">
    <div class="container">
        <!-- Logo container-->
        <div>
            <a class="logo" href="home.jsp">
                <span class="logo-light-mode">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" class="l-dark" height="24" alt="">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" class="l-light" height="24" alt="">
                </span>
                <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="24" class="logo-dark-mode" alt="">
            </a>
        </div>
        <!-- End Logo container-->

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
                <% if (user == null) { %>
                <!--                <a href="login.jsp" class="sub-menu-item" style="color: rgba(255, 255, 255, 0.5) !important;">Login</a>
                                <a href="User?service=registerUser" class="sub-menu-item" style="color: rgba(255, 255, 255, 0.5) !important;">Register</a>-->
                <% } else {%>
                <div class="dropdown dropdown-primary">
                    <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="${pageContext.request.contextPath}/<%= user.getImgPath() != null ? user.getImgPath() : "uploads/default_avatar.jpg"%>" class="avatar avatar-ex-small rounded-circle" alt=""></button>
                    <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                        <a class="dropdown-item d-flex align-items-center text-dark" href="doctor-profile.html">
                            <img src="${pageContext.request.contextPath}/<%= user.getImgPath() != null ? user.getImgPath() : "uploads/default_avatar.jpg"%>" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                            <div class="flex-1 ms-2">
                                <span class="d-block mb-1">${sessionScope.user.getFullName()}</span>
                                <small class="text-muted">Orthopedic</small>
                            </div>
                        </a>
                        <a class="dropdown-item text-dark" href="doctor-dashboard.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span> Dashboard</a>
                        <a class="dropdown-item text-dark" href="profile_user.jsp"><span class="mb-0 d-inline-block me-1"><i class="uil uil-setting align-middle h6"></i></span> Profile Settings</a>
                        <div class="dropdown-divider border-top"></div>
                        <a class="dropdown-item text-dark" href="logout"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                    </div>
                </div>
                <% }%>

            </li>
        </ul>
        <!-- Start Dropdown -->

        <div id="navigation">
            <!-- Navigation Menu-->   
            <ul class="navigation-menu nav-left nav-light">
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
                <li><a href="login.jsp" class="sub-menu-item">Login</a></li>
            </ul><!--end navigation menu-->
        </div><!--end navigation-->
    </div><!--end container-->
</header><!--end header-->
<!-- Navbar End -->
