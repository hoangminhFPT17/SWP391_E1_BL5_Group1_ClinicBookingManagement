<%-- 
    Document   : signup
    Created on : Apr 20, 2025, 3:07:16 PM
    Author     : JackGarland
--%>
<%@page import="model.GoogleAccount"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <%
            GoogleAccount googleAccount = (GoogleAccount) session.getAttribute("googleAccount");
        %>
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
        <jsp:include page="/common/header.jsp" />
        <section class="bg-half-150 d-table w-100 bg-light" style="background: url('${pageContext.request.contextPath}/assets/images/bg/bg-lines-one.png') center;">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 col-md-8">
                        <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="24" class="mx-auto d-block" alt="">
                        <div class="card login-page bg-white shadow mt-4 rounded border-0">
                            <div class="card-body">
                                <h4 class="text-center">Sign Up</h4>  
                                <form action="User" method="POST" class="login-form mt-4" enctype="multipart/form-data">
                                    <input type="hidden" name="service" value="registerUser">
                                    <input type="hidden" name="hide" id="hide" />
                                    <div class="row">
                                        <% if (googleAccount == null) {%>
                                        <div class="col-md-6">
                                            <div class="mb-3">                                               
                                                <label class="form-label">Your Email <span class="text-danger">*</span></label>
                                                <input type="email" class="form-control" placeholder="User1@gmail.com" name="Email" required="">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="mb-3">                                                
                                                <label class="form-label">Your Name <span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" placeholder="JM DCB" name="FullName" required="">
                                            </div>
                                        </div>
                                        <% } else { %>
                                        <div class="col-md-6">
                                            <div class="mb-3">                                               
                                                <label class="form-label">Your Email <span class="text-danger">*</span></label>
                                                <input type="email" class="form-control" value="${sessionScope.googleAccount.getEmail()}" name="Email" required="" readonly="" >
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="mb-3">                                                
                                                <label class="form-label">Your Name <span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" value="${sessionScope.googleAccount.getName()}" name="FullName" required="" readonly="">
                                            </div>
                                        </div>
                                        <% }%>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Your Phone <span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" placeholder="099211822" name="Phone" required="">
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Password <span class="text-danger">*</span></label>
                                                <input name="Password" id="password" type="text" class="form-control" placeholder="Password" required="">
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <div class="form-check">
                                                    <input class="form-check-input align-middle" type="checkbox" value="" id="accept-tnc-check" required>
                                                    <label class="form-check-label" for="accept-tnc-check">I Accept <a href="#" class="text-primary">Terms And Condition</a></label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="d-grid">
                                                <button class="btn btn-primary" type="submit" onclick="return encrypt()" name="submit" value="registerUser">Register</button>
                                            </div>
                                        </div>

                                        <div class="mx-auto">
                                            <p class="mb-0 mt-3"><small class="text-dark me-2">Already have an account ?</small> <a href="login.html" class="text-dark fw-bold">Sign in</a></p>
                                        </div>

                                        <c:if test="${not empty requestScope.error}">
                                            <div class="col-lg-12"><p class="text-danger">${requestScope.error}</p></div>
                                        </c:if>
                                        <c:if test="${not empty sessionScope.success}">
                                            <div class="col-lg-12"><p class="text-success">${sessionScope.success}</p></div>
                                            <c:remove var="success" scope="session"/>
                                        </c:if>
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