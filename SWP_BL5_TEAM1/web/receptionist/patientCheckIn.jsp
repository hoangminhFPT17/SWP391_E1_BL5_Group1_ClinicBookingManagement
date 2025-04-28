<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Patient Check-In</title>
        <%@ include file="../assets/css/css-js.jsp" %>
        <style>
            .form-group {
                margin-bottom: 1rem;
            }
            .btn {
                margin-right: .5rem;
            }
        </style>
    </head>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <%@ include file="../component/receptionistSideBar.jsp" %>
            <main class="page-content bg-light">
                <%@ include file="../component/header.jsp" %>
                <div class="container-fluid" style="margin-top:80px; max-width:600px;">
                    <h2>Patient Check-In</h2>

                    <!-- Step 1: Phone input -->
                    <c:if test="${empty showPatientForm and empty showAppointmentForm}">
                        <form method="post" action="${pageContext.request.contextPath}/PatientCheckin">
                            <div class="form-group">
                                <label for="phone">Phone Number</label>
                                <input type="text" id="phone" name="phone" class="form-control" required />
                            </div>
                            <button type="submit" name="action" value="checkPhone" class="btn btn-primary">Next</button>
                        </form>
                    </c:if>

                    <!-- Step 2: Patient form (new or existing) -->
                    <c:if test="${showPatientForm}">
                        <form method="post" action="${pageContext.request.contextPath}/PatientCheckin">
                            <input type="hidden" name="phone" value="${param.phone}" />
                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" id="fullName" name="fullName" class="form-control"
                                       value="${patient.fullName}" readonly required />
                            </div>
                            <div class="form-group">
                                <label for="dob">Date of Birth</label>
                                <input type="date" id="dob" name="dob" class="form-control"
                                       value="${patient.dateOfBirth}" readonly required />
                            </div>
                            <div class="form-group">
                                <label>Gender</label>
                                <input type="text" name="gender" class="form-control"
                                       value="${patient.gender}" readonly required />
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="email" class="form-control"
                                       value="${patient.email}" readonly />
                            </div>
                            <button type="submit" name="action" value="createPatient" class="btn btn-primary">
                                Next: Book Appointment
                            </button>
                        </form>
                    </c:if>

                    <!-- Step 3: Appointment form -->
                    <c:if test="${showAppointmentForm}">
                        <form method="post" action="${pageContext.request.contextPath}/PatientCheckin">
                            <input type="hidden" name="phone" value="${patient.phone}" />

                            <!-- Appointment Date -->
                            <div class="form-group">
                                <label for="appointmentDate">Appointment Date</label>
                                <input type="date"
                                       id="appointmentDate"
                                       name="appointmentDate"
                                       class="form-control"
                                       required />
                            </div>

                            <!-- Status -->
                            <div class="form-group">
                                <label for="status">Status</label>
                                <select id="status"
                                        name="status"
                                        class="form-control"
                                        required>
                                    <option value="Pending">Pending</option>
                                    <option value="Waiting">Waiting</option>
                                </select>
                            </div>

                            <!-- Examination Package selection -->
                            <div class="form-group">
                                <label for="packageId">Examination Package</label>
                                <select id="packageId" name="packageId" class="form-control" required>
                                    <option value="">-- Select Package --</option>
                                    <c:forEach var="pkg" items="${packages}">
                                        <option value="${pkg.packageId}" data-specialty="${pkg.specialtyId}">
                                            ${pkg.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Doctor selection, filtered by specialty -->
                            <div class="form-group">
                                <label for="doctorId">Doctor</label>
                                <select id="doctorId" name="doctorId" class="form-control"  required>
                                    <option value="">-- Select Doctor --</option>
                                    <c:forEach var="doc" items="${doctors}">
                                        <option value="${doc.doctorId}">
                                            ${doc.fullName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Time Slot -->
                            <div class="form-group">
                                <label for="slot">Time Slot</label>
                                <select id="slot" name="slotId" class="form-control" required>
                                    <c:forEach var="ts" items="${timeSlots}">
                                        <option value="${ts.slotId}">${ts.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Description -->
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea id="description" name="description" class="form-control" rows="3"></textarea>
                            </div>

                            <button type="submit" name="action" value="createAppointment" class="btn btn-success">
                                Confirm Appointment
                            </button>
                        </form>

                    </c:if>

                </div>
            </main>
        </div>
    </body>
</html>