<%-- 
    Document   : resetPassword
    Created on : 4 Jun, 2024, 4:48:55 AM
    Author     : Heizxje
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Doctris - Doctor Appointment Booking System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="https://shreethemes.in" />
        <meta name="Version" content="v1.2.0" />
        <!-- favicon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico.png">
        <!-- Bootstrap -->
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- Icons -->
        <link href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/assets/css/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- Css -->
        <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />

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

        <!-- Hero Start -->
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <section class="bg-home d-flex bg-light align-items-center" style="background: url('${pageContext.request.contextPath}/assets/images/bg/bg-lines-one.png') center;">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 col-md-8">
                        <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="24" class="mx-auto d-block" alt="">
                        <div class="card login-page bg-white shadow mt-4 rounded border-0">
                            <div class="card-body">
                                <h4 class="text-center">Reset Password</h4>  
                                <form class="login-form mt-4" action="resetPassword" method="POST">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="mb-3">
                                                <label class="form-label">Email<span class="text-danger">*</span></label>
                                                <input name="email" type="email" class="form-control" value="${email}" readonly required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <p class="text-muted">Please enter your new password</p>
                                                <div class="mb-3">
                                                    <label class="form-label">Password<span class="text-danger">*</span></label>
                                                    <input name="password" id="password" type="password" class="form-control" placeholder="Enter Your Password" required="">
                                                    <input type="hidden" name="hide" id="hide" />
                                                </div>                                           
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="col-lg-12">
                                                    <p class="text-muted">Please re-enter your new password</p>
                                                    <div class="mb-3">
                                                        <label class="form-label">Password<span class="text-danger">*</span></label>
                                                        <input name="confirm-password" id="confirm-password" type="password" class="form-control" placeholder="Enter Your Password" required="">
                                                    </div>
                                                    <p class="text-danger">${mess}</p>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="d-grid">
                                                        <button type="submit" onclick="return encryptForPassForgot()" class="btn btn-primary">Send</button>
                                                    </div>
                                                </div>
                                            </div>
                                            </form>
                                        </div>
                                    </div><!---->
                            </div> <!--end col-->
                        </div><!--end row-->
                    </div> <!--end container-->
                    </section><!--end section-->
                    <!-- Hero End -->

                    <!-- javascript -->
                    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
                    <!-- Icons -->
                    <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
                    <!-- Main Js -->
                    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

                    </body>

                    </html>
