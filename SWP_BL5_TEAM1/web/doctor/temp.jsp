<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Medical Records</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .error { color: red; }
    </style>
</head>
<body>
    <h1>All Medical Records</h1>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form method="get" action="${pageContext.request.contextPath}/staff/medical-records">
        <label for="patientPhone">Filter by Patient Phone:</label>
        <input type="text" id="patientPhone" name="patientPhone" value="${patientPhone}">
        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate" value="${startDate}">
        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate" value="${endDate}">
        <input type="submit" value="Filter">
    </form>

    <table>
        <thead>
            <tr>
                <th>Patient Phone</th>
                <th>Record ID</th>
                <th>Diagnosis</th>
                <th>Prescription</th>
                <th>Notes</th>
                <th>Created At</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="record" items="${records}">
                <tr>
                    <td>${record.patientPhone}</td>
                    <td>${record.recordId}</td>
                    <td>${record.diagnosis}</td>
                    <td>${record.prescription}</td>
                    <td>${record.notes}</td>
                    <td><fmt:formatDate value="${record.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </c:forEach>
            <c:if test="${empty records}">
                <tr>
                    <td colspan="6">No records found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>