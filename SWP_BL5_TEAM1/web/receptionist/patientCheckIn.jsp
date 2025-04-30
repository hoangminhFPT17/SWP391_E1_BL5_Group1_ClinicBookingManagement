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
                        <form class="validate-form" method="post" action="${pageContext.request.contextPath}/PatientCheckin">
                            <div class="form-group">
                                <label for="phone">Phone Number</label>
                                <input type="text" id="phone" name="phone" class="form-control" required />
                            </div>
                            <button type="submit" name="action" value="checkPhone" class="btn btn-primary">Next</button>
                        </form>
                    </c:if>

                    <!-- Step 2: Patient form (new or existing) -->
                    <c:if test="${showPatientForm}">
                        <c:choose>
                            <c:when test="${not empty patient}">
                                <form class="validate-form" method="post" action="${pageContext.request.contextPath}/PatientCheckin">
                                    <input type="hidden" name="phone" value="${param.phone}"/>

                                    <div class="form-group">
                                        <label>Full Name</label>
                                        <input type="text" class="form-control"
                                               value="${patient.fullName}" readonly/>
                                    </div>
                                    <div class="form-group">
                                        <label>Date of Birth</label>
                                        <input type="date" class="form-control"
                                               value="${patient.dateOfBirth}" readonly/>
                                    </div>
                                    <div class="form-group">
                                        <label>Gender</label>
                                        <input type="text" class="form-control"
                                               value="${patient.gender}" readonly/>
                                    </div>
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="email" class="form-control"
                                               value="${patient.email}" readonly/>
                                    </div>

                                    <button type="submit" name="action" value="createPatient"
                                            class="btn btn-primary">
                                        Next: Book Appointment
                                    </button>
                                </form>
                            </c:when>

                            <c:otherwise>
                                <form class="validate-form" method="post" action="${pageContext.request.contextPath}/PatientCheckin">
                                    <input type="hidden" name="phone" value="${param.phone}"/>

                                    <div class="form-group">
                                        <label>Full Name</label>
                                        <input type="text" name="fullName" class="form-control" required/>
                                    </div>
                                    <div class="form-group">
                                        <label>Date of Birth</label>
                                        <input type="date" name="dob" class="form-control" required/>
                                    </div>
                                    <div class="form-group">
                                        <label>Gender</label>
                                        <select name="gender" class="form-control" required>
                                            <option value="">-- select --</option>
                                            <option>Male</option>
                                            <option>Female</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="email" name="email" class="form-control"/>
                                    </div>

                                    <button type="submit" name="action" value="createPatient"
                                            class="btn btn-primary">
                                        Create Patient & Next
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <!-- Step 3: Appointment form -->
                    <c:if test="${showAppointmentForm}">
                        <form class="validate-form" method="post" action="${pageContext.request.contextPath}/PatientCheckin">
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
        <script>
            document.addEventListener('DOMContentLoaded', () => {
// Attach to all forms you marked
                document.querySelectorAll('form.validate-form').forEach(form => {
                    form.addEventListener('submit', e => {
// find all required controls
                        const controls = form.querySelectorAll('input[required], textarea[required], select[required]');
                        for (const ctrl of controls) {
// if empty or just whitespace
                            if (ctrl.value.trim() === '') {
                                alert('Please fill out the "'
                                        + (ctrl.previousElementSibling?.innerText || ctrl.name)
                                        + '" field.');
                                ctrl.focus();
                                e.preventDefault();
                                return;
                            }
                        }
// all good → allow submit
                    });
                });
            });
        </script>
    </body>
</html>