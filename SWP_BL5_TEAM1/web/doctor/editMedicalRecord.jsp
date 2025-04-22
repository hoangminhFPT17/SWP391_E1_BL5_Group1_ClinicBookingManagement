<%-- web/staff/editMedicalRecord.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Edit Medical Record | ClinicBooking</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/medical.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <jsp:include page="../cpn/header.jsp" />
        <div class="container medical-history-container">
            <h2 class="page-title">Edit Medical Record</h2>
            <form action="${pageContext.request.contextPath}/MedicalRecordManager" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="recordId" value="${record.recordId}">
                <div class="mb-3">
                    <label for="patientPhone" class="form-label">Patient Phone</label>
                    <input type="text" class="form-control" id="patientPhone" name="patientPhone" 
                           value="${record.patientPhone}" readonly>
                </div>
                <div class="mb-3">
                    <label for="diagnosis" class="form-label">Diagnosis</label>
                    <textarea class="form-control" id="diagnosis" name="diagnosis" required>${record.diagnosis}</textarea>
                </div>
                <div class="mb-3">
                    <label for="prescription" class="form-label">Prescription</label>
                    <textarea class="form-control" id="prescription" name="prescription" required>${record.prescription}</textarea>
                </div>
                <div class="mb-3">
                    <label for="notes" class="form-label">Notes</label>
                    <textarea class="form-control" id="notes" name="notes">${record.notes}</textarea>
                </div>