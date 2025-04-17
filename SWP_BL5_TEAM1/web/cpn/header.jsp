<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Header Start -->
<header class="main-header">
    <!-- Top Bar -->
    <div class="header-top">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <div class="contact-info">
                        <div><i class="fas fa-phone-alt"></i> +1 (800) 123-4567</div>
                        <div><i class="far fa-clock"></i> Mon-Fri: 8:00AM - 8:00PM</div>
                    </div>
                </div>
                <div class="col-lg-6 text-end">
                    <div class="social-links">
                        <a href="#"><i class="fab fa-facebook-f"></i></a>
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="#"><i class="fab fa-instagram"></i></a>
                        <a href="#"><i class="fab fa-linkedin-in"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Main Navigation -->
    <div class="main-nav-container">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-3 col-6">
                    <div class="logo-container">
                        <!-- Image Logo -->
                        <img src="assets/images/logo.png" alt="ClinicBooking" class="logo-img">
                        <!-- Text Logo (as fallback if image doesn't load) -->
                        <div class="clinic-name">Clinic<span>Booking</span></div>
                    </div>
                </div>
                <div class="col-lg-6 d-none d-lg-block">
                    <ul class="nav-menu">
                        <li class="active"><a href="home.jsp">Home</a></li>
                        <li><a href="services.jsp">Services</a></li>
                        <li><a href="doctors.jsp">Doctors</a></li>
                        <li><a href="about.jsp">About Us</a></li>
                        <li><a href="blog.jsp">Blog</a></li>
                        <li><a href="contact.jsp">Contact</a></li>
                    </ul>
                </div>
                <div class="col-lg-3 col-6 text-end">
                    <div class="nav-right">
                        <a href="appointment.jsp" class="appointment-btn">
                            <i class="far fa-calendar-check"></i>
                            <span>Book Appointment</span>
                        </a>
                        <div class="user-actions d-none d-lg-flex">
                            <% if (session.getAttribute("user") != null) { %>
                                <!-- User is logged in - show dropdown -->
                                <div class="dropdown">
                                    <button class="user-btn dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="far fa-user-circle"></i>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                        <li><a class="dropdown-item" href="profile">My Profile</a></li>
                                        <li><a class="dropdown-item" href="medicalhistory">View Medical History</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item" href="logout">Logout</a></li>
                                    </ul>
                                </div>
                            <% } else { %>
                                <!-- User is not logged in - show login button -->
                                <a href="login" class="login-btn">
                                    <i class="fas fa-sign-in-alt"></i>
                                    <span>Login</span>
                                </a>
                            <% } %>
                        </div>
                        <button class="mobile-toggle d-lg-none">
                            <span></span>
                            <span></span>
                            <span></span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- Header End -->
