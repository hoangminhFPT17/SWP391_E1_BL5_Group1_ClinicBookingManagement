<%-- 
    Document   : Profile
    Created on : Apr 17, 2025, 1:01:02 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
        <!-- jQuery first (required for some Bootstrap functionality) -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap JS Bundle with Popper - placed in head for faster loading -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <link href="assets/css/main.css" rel="stylesheet" type="text/css" />
    </head>
    <body class="bg-light">
        <jsp:include page="cpn/header.jsp" />
        
        <div class="container">
            <div class="profile-container">
                <div class="profile-header">
                    <h1>My Profile</h1>
                    <p class="text-muted">View and update your account information</p>
                </div>
                
                <% if (request.getAttribute("successMessage") != null) { %>
                <div class="alert alert-success message-alert">
                    <%= request.getAttribute("successMessage") %>
                </div>
                <% } %>
                
                <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger message-alert">
                    <%= request.getAttribute("errorMessage") %>
                </div>
                <% } %>
                
                <form method="post" action="profile">
                    <div class="form-group">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control readonly-field" id="email" name="email" value="${user.email}" readonly>
                        <small class="text-muted">Email cannot be changed</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="fullName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" value="${patient.fullName != null ? patient.fullName : user.fullName}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="phone" class="form-label">Phone Number</label>
                        <input type="text" class="form-control readonly-field" id="phone" name="phone" value="${patient.phone != null ? patient.phone : user.phone}" readonly>
                        <small class="text-muted">Phone number cannot be changed</small>
                    </div>
                    
                    <% if (request.getAttribute("patient") != null) { %>
                    <div class="form-group">
                        <label for="dateOfBirth" class="form-label">Date of Birth</label>
                        <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" value="${patient.dateOfBirth}">
                    </div>
                    
                    <div class="form-group">
                        <label for="gender" class="form-label">Gender</label>
                        <select class="form-select" id="gender" name="gender">
                            <option value="Male" ${patient.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${patient.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${patient.gender == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </div>
                    <% } %>
                    
                    <div class="form-group">
                        <label for="role" class="form-label">Role</label>
                        <input type="text" class="form-control readonly-field" id="role" name="role" value="${user.role}" readonly>
                    </div>
                    
                    <div class="d-grid gap-2 d-md-flex justify-content-md-between mt-4">
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                    </div>
                </form>
            </div>
        </div>
        
        <jsp:include page="cpn/footer.jsp" />
        
        <!-- Initialize Bootstrap components -->
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
