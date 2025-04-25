<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Appointment Detail</title>
  <%@ include file="../assets/css/css-js.jsp" %>
</head>
<body>
  <div class="page-wrapper doctris-theme toggled">
    <%@ include file="../component/sideBar.jsp" %>
    <main class="page-content bg-light">
      <%@ include file="../component/header.jsp" %>
      <div class="container-fluid" style="margin-top:50px;">
        <h2 class="mb-4">Appointment Detail</h2>

        <c:if test="${detail != null}">
          <div class="card">
            <div class="card-body">
              <dl class="row">
                <dt class="col-sm-3">Appointment ID</dt>
                <dd class="col-sm-9">${detail.appointmentId}</dd>

                <dt class="col-sm-3">Patient Phone</dt>
                <dd class="col-sm-9">${detail.patientPhone}</dd>

                <dt class="col-sm-3">Doctor</dt>
                <dd class="col-sm-9">${detail.doctorId}</dd>

                <dt class="col-sm-3">Time Slot</dt>
                <dd class="col-sm-9">${detail.slot}</dd>

                <dt class="col-sm-3">Appointment Date</dt>
                <dd class="col-sm-9">
                  <fmt:formatDate value="${detail.appointmentDate}" pattern="yyyy-MM-dd"/>
                </dd>

                <dt class="col-sm-3">Status</dt>
                <dd class="col-sm-9">${detail.status}</dd>

                <dt class="col-sm-3">Created At</dt>
                <dd class="col-sm-9">
                  <fmt:formatDate value="${detail.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </dd>

                <dt class="col-sm-3">Description</dt>
                <dd class="col-sm-9">${detail.description}</dd>

                <dt class="col-sm-3">Exam Package</dt>
                <dd class="col-sm-9">${detail.examinationPackage}</dd>
              </dl>
            </div>
          </div>
        </c:if>

        <c:if test="${detail == null}">
          <div class="alert alert-warning">
            Appointment not found.
          </div>
        </c:if>

      </div>
      <footer class="bg-white shadow py-3 mt-5">
        <div class="container-fluid text-center text-muted">
          &copy; <script>document.write(new Date().getFullYear())</script>
          Doctris.
        </div>
      </footer>
    </main>
  </div>
</body>
</html>
