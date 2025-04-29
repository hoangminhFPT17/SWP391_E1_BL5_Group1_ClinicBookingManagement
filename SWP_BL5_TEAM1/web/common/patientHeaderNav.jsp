<%-- 
    Document   : patientHeaderNav
    Created on : 15 Apr 2025, 21:52:13
    Author     : LENOVO
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <!-- User is logged in -->
                            <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <img src="${pageContext.request.contextPath}/assets/images/client/09.jpg" class="avatar avatar-ex-small rounded-circle" alt="">
                            </button>
                            <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                                <a class="dropdown-item d-flex align-items-center text-dark" href="doctor-profile.html">
                                    <img src="${pageContext.request.contextPath}/assets/images/client/09.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                    <div class="flex-1 ms-2">
                                        <span class="d-block mb-1">${sessionScope.user.fullName}</span>
                                    </div>
                                </a>
                                <a class="dropdown-item text-dark" href="patient-dashboard.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span> Dashboard</a>
                                <a class="dropdown-item text-dark" href="patient-profile.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-user align-middle h6"></i></span> Profile</a>
                                <div class="dropdown-divider border-top"></div>
                                <a class="dropdown-item text-dark" href="-logout"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- User is NOT logged in -->
                            <a href="login" class="btn btn-primary">Login</a>
                        </c:otherwise>
                    </c:choose>
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
                       
                    </ul>
                </li>
                <li><a href="PatientMedicalHistory.jsp" class="sub-menu-item">Medical History</a></li>
                <li><a href="PatientInvoiceServlet" class="sub-menu-item">Invoices</a></li>
                <li><a href="PaymentHistory.jsp" class="sub-menu-item">Payment History</a></li>                
            </ul><!--end navigation menu-->
        </div><!--end navigation-->
    </div><!--end container-->
</header><!--end header-->
<!-- Navbar End -->
