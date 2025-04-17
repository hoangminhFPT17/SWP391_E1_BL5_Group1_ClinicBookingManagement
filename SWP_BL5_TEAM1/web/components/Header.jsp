<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="home">Clinic Management System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="services">Services</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="appointments">Appointments</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <% if (session.getAttribute("user") != null) { %>
                        <li class="nav-item">
                            <a class="nav-link active" href="profile">My Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="logout">Logout</a>
                        </li>
                    <% } else { %>
                        <li class="nav-item">
                            <a class="nav-link" href="login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="register">Register</a>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>
</header>
