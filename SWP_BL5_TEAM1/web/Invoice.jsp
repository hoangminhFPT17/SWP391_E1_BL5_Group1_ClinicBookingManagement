<%-- 
    Document   : Invoice
    Created on : Apr 27, 2025, 11:40:04 PM
    Author     : JackGarland
--%>

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

        <!-- Navbar STart -->
        <header id="topnav" class="defaultscroll sticky d-print-none">
            <div class="container">
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

        <section class="bg-half-170 bg-light">
            <div class="container">
                <div class="row mt-5 justify-content-center">
                    <div class="col-lg-10">
                        <div class="card bg-white border-0 rounded shadow px-4 py-5">
                            <!-- Invoice Display -->
                            <div class="row mb-4">
                                <div class="col-lg-8 col-md-6">
                                    <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="22" alt="">
                                    <h6 class="mt-4 pt-2">Address :</h6>
                                    <small id="invoiceAddress" class="text-muted mb-0">1419 Riverwood Drive,<br>Redding, CA 96001</small>
                                </div>
                                <div class="col-lg-4 col-md-6 mt-4 mt-sm-0 pt-2 pt-sm-0">
                                    <ul class="list-unstyled">
                                        <li class="d-flex">
                                            <small class="mb-0 text-muted">Invoice no. : </small>
                                            <small id="invoiceNumber" class="mb-0 text-dark">&nbsp;&nbsp;${param.id}</small>
                                        </li>
                                        <li class="d-flex mt-2">
                                            <small class="mb-0 text-muted">Email : </small>
                                            <small class="mb-0">&nbsp;&nbsp;<a href="mailto:contact@example.com" class="text-dark">info@doctris.com</a></small>
                                        </li>
                                        <li class="d-flex mt-2">
                                            <small class="mb-0 text-muted">Phone : </small>
                                            <small class="mb-0">&nbsp;&nbsp;<a href="tel:+152534-468-854" class="text-dark">(+12) 1546-456-856</a></small>
                                        </li>
                                        <li class="d-flex mt-2">
                                            <small class="mb-0 text-muted">Website : </small>
                                            <small class="mb-0">&nbsp;&nbsp;<a href="javascript:void(0)" class="text-dark">www.doctris.com</a></small>
                                        </li>
                                        <li class="d-flex mt-2">
                                            <small class="mb-0 text-muted">Patient Name : </small>
                                            <small id="invoicePatient" class="mb-0">&nbsp;&nbsp;${param.patientName}</small>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="pt-4 border-top">
                                <div class="row">
                                    <div class="col-lg-8 col-md-6">
                                        <h5 class="text-muted fw-bold">Invoice</h5>
                                        <h6 id="invoicePackage">${param.packageName}</h6>
                                    </div>
                                    <div class="col-lg-4 col-md-6 mt-4 mt-sm-0 pt-2 pt-sm-0">
                                        <ul class="list-unstyled">
                                            <li class="d-flex mt-2">
                                                <small class="mb-0 text-muted">Issue Dt. : </small>
                                                <small id="invoiceIssue" class="mb-0 text-dark">&nbsp;&nbsp;${param.currentDate}</small>
                                            </li>
                                            <li class="d-flex mt-2">
                                                <small class="mb-0 text-muted">Due Dt. : </small>
                                                <small id="invoiceDue" class="mb-0 text-dark">&nbsp;&nbsp;${param.futureDate}</small>
                                            </li>
                                            <li class="d-flex mt-2">
                                                <small class="mb-0 text-muted">Dr. Name : </small>
                                                <small id="invoiceDoctor" class="mb-0 text-dark">&nbsp;&nbsp;${param.doctorName}</small>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="invoice-table pb-4">
                                    <div class="table-responsive shadow rounded mt-4">
                                        <table id="invoiceTable" class="table table-center invoice-tb mb-0">
                                            <thead>
                                                <tr>
                                                    <th>No.</th>
                                                    <th>Item</th>
                                                    <th>Rate ($)</th>
                                                    <th>Total ($)</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr data-index="1">
                                                    <td class="text-start p-3">1</td>
                                                    <td class="text-start p-3"><span class="item-name">N/A</span></td>
                                                    <td class="p-3"><span class="item-rate">0</span></td>
                                                    <td class="p-3"><span class="item-total">0</span></td>
                                                </tr>
                                                <tr data-index="2">
                                                    <td class="text-start p-3">2</td>
                                                    <td class="text-start p-3"><span class="item-name">N/A</span></td>
                                                    <td class="p-3"><span class="item-rate">0</span></td>
                                                    <td class="p-3"><span class="item-total">0</span></td>
                                                </tr>
                                                <tr data-index="3">
                                                    <td class="text-start p-3">3</td>
                                                    <td class="text-start p-3"><span class="item-name">N/A</span></td>
                                                    <td class="p-3"><span class="item-rate">0</span></td>
                                                    <td class="p-3"><span class="item-total">0</span></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-4 col-md-5 ms-auto">
                                            <ul class="list-unstyled h6 fw-normal mt-4 mb-0 ms-md-5 ms-lg-4">
                                                <li class="text-muted d-flex justify-content-between pe-3">Subtotal :<span id="invoiceSubtotal">0</span></li>
                                                <li class="text-muted d-flex justify-content-between pe-3">Taxes :<span id="invoiceTaxes">0</span></li>
                                                <li class="d-flex justify-content-between pe-3">Total :<span id="invoiceTotal">0</span></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Edit Form -->
                        <div class="p-4" id="edit-invoice-details">
                            <form id="invoiceEditForm" action="InvoiceServlet" method="POST">
                                <h5>Edit Invoice Details</h5>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="inputAddress" class="form-label">Address</label>
                                        <input id="inputAddress" name="address" type="text" class="form-control" value="1419 Riverwood Drive, Redding, CA 96001">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="inputInvoiceNo" class="form-label">Invoice No.</label>
                                        <input id="inputInvoiceNo" name="invoiceNumber" type="number" class="form-control" value="${param.id}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="inputPackage" class="form-label">Package</label>
                                        <input id="inputPackage" name="packageName" type="text" class="form-control" value="${param.packageName}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="inputDoctor" class="form-label">Doctor Name</label>
                                        <input id="inputDoctor" name="doctorName" type="text" class="form-control" value="${param.doctorName}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="inputIssueDate" class="form-label">Issue Date</label>
                                        <input id="inputIssueDate" name="issueDate" type="date" class="form-control" value="${param.currentDate}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="inputDueDate" class="form-label">Due Date</label>
                                        <input id="inputDueDate" type="date" name="dueDate" class="form-control" value="${param.futureDate}">
                                    </div>
                                </div>
                                <h6 class="mt-4">Items</h6>
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Item</th>
                                                <th>Rate ($)</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>1</td>
                                                <td><input type="text" name="item1Description" class="form-control item-input-name" value="N/A"></td>
                                                <td><input type="number" name="item1Rate" class="form-control item-input-rate" value="0"></td>
                                            </tr>
                                            <tr>
                                                <td>2</td>
                                                <td><input type="text" name="item2Description" class="form-control item-input-name" value="N/A"></td>
                                                <td><input type="number" name="item2Rate" class="form-control item-input-rate" value="0"></td>
                                            </tr>
                                            <tr>
                                                <td>3</td>
                                                <td><input type="text" name="item3Description" class="form-control item-input-name" value="N/A"></td>
                                                <td><input type="number" name="item3Rate" class="form-control item-input-rate" value="0"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <input type="hidden" name="patientPhone" class="form-control item-input-rate" value="${param.patientPhone}">
                                <input type="hidden" name="patientName" class="form-control item-input-rate" value="${param.patientName}">
                                <input type="hidden" name="appointmentId" class="form-control item-input-rate" value="${param.id}">
                                <button type="submit" id="saveInvoiceBtn" class="btn btn-primary mt-3">Send</button>
                                <button type="reset" id="resetInvoiceBtn" class="btn btn-secondary mt-3">Reset</button>
                            </form>
                        </div>

<!--                        <div class="text-end mt-4 pt-2">
                            <a href="javascript:window.print()" class="btn btn-soft-primary d-print-none"><i class="uil uil-print"></i> Print</a>
                        </div>-->
                    </div>
                </div>
            </div>

            <!-- Script to sync edits -->
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    const saveBtn = document.getElementById('saveInvoiceBtn');
                    // Display elements
                    const invoiceAddress = document.getElementById('invoiceAddress');
                    const invoiceNumber = document.getElementById('invoiceNumber');
                    const invoicePackage = document.getElementById('invoicePackage');
                    const invoiceDoctor = document.getElementById('invoiceDoctor');
                    const invoiceIssue = document.getElementById('invoiceIssue');
                    const invoiceDue = document.getElementById('invoiceDue');
                    const invoiceSubtotal = document.getElementById('invoiceSubtotal');
                    const invoiceTotal = document.getElementById('invoiceTotal');
                    const tableBody = document.querySelector('#invoiceTable tbody');

                    saveBtn.addEventListener('click', function () {
                        // Header
                        invoiceAddress.innerHTML = document.getElementById('inputAddress').value.replace(', ', '<br>');
                        invoiceNumber.textContent = '  ' + document.getElementById('inputInvoiceNo').value;
                        invoicePackage.textContent = document.getElementById('inputPackage').value;
                        invoiceDoctor.textContent = '  ' + document.getElementById('inputDoctor').value;
                        invoiceIssue.textContent = '  ' + document.getElementById('inputIssueDate').value;
                        invoiceDue.textContent = '  ' + document.getElementById('inputDueDate').value;

                        // Items and totals
                        const names = document.querySelectorAll('.item-input-name');
                        const rates = document.querySelectorAll('.item-input-rate');
                        let subtotal = 0;
                        names.forEach((nameEl, idx) => {
                            const rateEl = rates[idx];
                            const row = tableBody.rows[idx];
                            const rate = parseFloat(rateEl.value) || 0;
                            subtotal += rate;
                            row.querySelector('.item-name').textContent = nameEl.value;
                            row.querySelector('.item-rate').textContent = rate.toFixed(2);
                            row.querySelector('.item-total').textContent = rate.toFixed(2);
                        });
                        invoiceSubtotal.textContent = subtotal.toFixed(2);
                        invoiceTotal.textContent = subtotal.toFixed(2);
                    });

                    // Live preview on input
                    document.getElementById('invoiceEditForm').addEventListener('input', function (e) {
                        if (e.target.matches('#inputAddress')) {
                            invoiceAddress.innerHTML = e.target.value.replace(', ', '<br>');
                        }
                        if (e.target.matches('#inputInvoiceNo')) {
                            invoiceNumber.textContent = '  ' + e.target.value;
                        }
                        if (e.target.matches('#inputPackage')) {
                            invoicePackage.textContent = e.target.value;
                        }
                        if (e.target.matches('#inputDoctor')) {
                            invoiceDoctor.textContent = '  ' + e.target.value;
                        }
                        if (e.target.matches('#inputIssueDate')) {
                            invoiceIssue.textContent = '  ' + e.target.value;
                        }
                        if (e.target.matches('#inputDueDate')) {
                            invoiceDue.textContent = '  ' + e.target.value;
                        }
                        if (e.target.matches('.item-input-name') || e.target.matches('.item-input-rate')) {
                            let subtotal = 0;
                            document.querySelectorAll('.item-input-rate').forEach((rateEl, idx) => {
                                const rate = parseFloat(rateEl.value) || 0;
                                subtotal += rate;
                                const row = tableBody.rows[idx];
                                row.querySelector('.item-name').textContent = document.querySelectorAll('.item-input-name')[idx].value;
                                row.querySelector('.item-rate').textContent = rate.toFixed(2);
                                row.querySelector('.item-total').textContent = rate.toFixed(2);
                            });
                            invoiceSubtotal.textContent = subtotal.toFixed(2);
                            invoiceTotal.textContent = subtotal.toFixed(2);
                        }
                    });
                });
            </script>
        </section>




        <!-- Start -->
        <footer class="bg-footer d-print-none">
            <div class="container">
                <div class="row">
                    <div class="col-xl-5 col-lg-4 mb-0 mb-md-4 pb-0 pb-md-2">
                        <a href="#" class="logo-footer">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-light.png" height="22" alt="">
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
                                <p class="mb-0"><script>document.write(new Date().getFullYear())</script> © Doctris. Design with <i class="mdi mdi-heart text-danger"></i> by <a href="${pageContext.request.contextPath}/${pageContext.request.contextPath}/${pageContext.request.contextPath}/index.html" target="_blank" class="text-reset">Shreethemes</a>.</p>
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
                                <h4>Search now${pageContext.request.contextPath}${pageContext.request.contextPath}.</h4>
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
                                    <li class="d-grid"><a href="javascript:void(0)" class="rtl-version t-rtl-light" onclick="setTheme('style-rtl')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-light-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="ltr-version t-ltr-light" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-rtl-version t-rtl-dark" onclick="setTheme('style-dark-rtl')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-dark-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-ltr-version t-ltr-dark" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-dark.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="dark-version t-dark mt-4" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-dark.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Dark Version</span></a></li>
                                    <li class="d-grid"><a href="javascript:void(0)" class="light-version t-light mt-4" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/assets/images/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Light Version</span></a></li>
                                    <li class="d-grid"><a href="${pageContext.request.contextPath}/admin/index.html" target="_blank" class="mt-4"><img src="${pageContext.request.contextPath}/assets/images/layouts/light-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Admin Dashboard</span></a></li>
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
                    <li class="list-inline-item mb-0"><a href="${pageContext.request.contextPath}/index.html" target="_blank" class="rounded"><i class="uil uil-globe align-middle" title="website"></i></a></li>
                </ul><!--end icon-->
            </div>
        </div>
        <!-- Offcanvas End -->

        <!-- javascript -->
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
        <!-- Icons -->
        <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    </body>

</html>