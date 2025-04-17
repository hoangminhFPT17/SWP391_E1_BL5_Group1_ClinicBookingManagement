<%-- 
    Document   : patient_appointments
    Created on : 17 Apr 2025, 09:45:27
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Appointment" %>
<%
    List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Patient Appointments</h2>

        <% if (appointments == null || appointments.isEmpty()) { %>
        <p>No appointments found for this patient.</p>
        <% } else { %>
        <table border="1" cellpadding="10">
            <tr>
                <th>ID</th>
                <th>Doctor ID</th>
                <th>Slot ID</th>
                <th>Date</th>
                <th>Status</th>
            </tr>
            <% for (Appointment appt : appointments) { %>
            <tr>
                <td><%= appt.getAppointmentId() %></td>
                <td><%= appt.getDoctorId() %></td>
                <td><%= appt.getSlotId() %></td>
                <td><%= appt.getAppointmentDate() %></td>
                <td><%= appt.getStatus() %></td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </body>
</html>





