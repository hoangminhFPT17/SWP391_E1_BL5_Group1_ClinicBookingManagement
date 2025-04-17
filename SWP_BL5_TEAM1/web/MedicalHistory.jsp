<%-- 
    Document   : MedicalHistory
    Created on : Apr 17, 2025, 1:24:16 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Medical History | ClinicBooking</title>
        <!-- jQuery first (required for some Bootstrap functionality) -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap JS Bundle with Popper - placed in head for faster loading -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="assets/css/medical.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/main.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <!-- Include Header -->
        <jsp:include page="cpn/header.jsp" />
        
        <!-- Medical History Content -->
        <div class="container medical-history-container">
            <h2 class="page-title">Medical History</h2>
            
            <c:if test="${not empty message}">
                <div class="alert alert-info" role="alert">
                    ${message}
                </div>
            </c:if>
            
            <c:choose>
                <c:when test="${empty medicalRecords}">
                    <!-- Empty State -->
                    <div class="empty-state">
                        <i class="fas fa-notes-medical"></i>
                        <h3>No Medical Records Found</h3>
                        <p class="text-muted">You don't have any medical records in our system yet.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Display Medical Records -->
                    <div class="row">
                        <c:forEach var="record" items="${medicalRecords}">
                            <div class="col-md-6 col-lg-4">
                                <div class="card medical-record-card">
                                    <div class="card-header bg-primary text-white">
                                        <h5 class="card-title mb-0">Medical Record #${record.recordId}</h5>
                                    </div>
                                    <div class="card-body">
                                        <p class="record-date"><i class="far fa-calendar-alt"></i> ${record.createdAt}</p>
                                        
                                        <div class="mb-3">
                                            <div class="section-title">Diagnosis</div>
                                            <p>${record.diagnosis}</p>
                                        </div>
                                        
                                        <div class="mb-3">
                                            <div class="section-title">Prescription</div>
                                            <p>${record.prescription}</p>
                                        </div>
                                        
                                        <c:if test="${not empty record.notes}">
                                            <div>
                                                <div class="section-title">Notes</div>
                                                <p>${record.notes}</p>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        
        <!-- Include Footer -->
        <jsp:include page="cpn/footer.jsp" />
        
        <!-- Multiple approaches to fix dropdown issue -->
        <script>
            // Approach 1: Use jQuery if available
            $(document).ready(function() {
                // Initialize all dropdowns with jQuery
                $('.dropdown-toggle').dropdown();
            });
            
            // Approach 2: Direct vanilla JS approach
            document.addEventListener('DOMContentLoaded', function() {
                // Get all dropdown toggle elements
                var dropdownToggleList = [].slice.call(document.querySelectorAll('[data-bs-toggle="dropdown"]'));
                
                // Create dropdown instances manually
                dropdownToggleList.forEach(function(dropdownToggle) {
                    var dropdown = new bootstrap.Dropdown(dropdownToggle);
                });
                
                // Alternate method: Add click handlers directly if Bootstrap's JS isn't initializing properly
                document.querySelectorAll('.dropdown-toggle').forEach(function(element) {
                    element.addEventListener('click', function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        var dropdownMenu = this.nextElementSibling;
                        if (dropdownMenu.classList.contains('show')) {
                            dropdownMenu.classList.remove('show');
                        } else {
                            document.querySelectorAll('.dropdown-menu.show').forEach(function(menu) {
                                menu.classList.remove('show');
                            });
                            dropdownMenu.classList.add('show');
                        }
                    });
                });
                
                // Close dropdowns when clicking outside
                document.addEventListener('click', function(e) {
                    if (!e.target.closest('.dropdown')) {
                        document.querySelectorAll('.dropdown-menu.show').forEach(function(menu) {
                            menu.classList.remove('show');
                        });
                    }
                });
            });
        </script>
    </body>
</html>
