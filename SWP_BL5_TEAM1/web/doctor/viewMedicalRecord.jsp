<!-- <%-- web/staff/viewMedicalRecord.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>View Medical Record | ClinicBooking</title>
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
            <h2 class="page-title">View Medical Record</h2>
            <div class="card medical-record-card">
                <div class="card-header bg-primary text-white">
                    <h5 class="card-title mb-0">Medical Record #${record.recordId}</h5>
                </div>
                <div class="card-body">
                    <p><strong>Date:</strong> ${record.createdAt}</p>
                    <p><strong>Patient Phone:</strong> ${record.patientPhone}</p>
                    <p><strong>Diagnosis:</strong> ${record.diagnosis}</p>
                    <p><strong>Prescription:</strong> ${record.prescription}</p>
                    <c:if test="${not empty record.notes}">
                        <p><strong>Notes:</strong> ${record.notes}</p>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/MedicalRecordManager?patientPhone=${record.patientPhone}" 
                       class="btn btn-secondary">Back to List</a>
                </div>
            </div>
        </div>
        <jsp:include page="../cpn/footer.jsp" />
    </body>
</html> -->