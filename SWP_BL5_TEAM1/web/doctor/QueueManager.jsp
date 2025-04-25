<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>Queue manager</title>
  <%@ include file="../assets/css/css-js.jsp" %>
  <style>
    th { position: relative; cursor: pointer; }
    th button { border: none; background: transparent; padding: 0; margin-left: 5px; cursor: pointer; }
    .filter-input { width: 100%; box-sizing: border-box; margin-top: 5px; }
  </style>
</head>
<body>
  <div class="page-wrapper doctris-theme toggled">
    <%@ include file="../component/doctorSideBar.jsp" %>
    <main class="page-content bg-light">
      <%@ include file="../component/doctorHeader.jsp" %>
      <div class="container-fluid">
        <div class="d-flex justify-content-between align-items-center mb-3" style="margin-top:80px;">
          <h2 class="mb-0">Patient queue in this time slot</h2>
        </div>


        <table id="apptTable" class="table table-bordered table-striped">
          <thead class="thead-light">
            <tr>
              <th>
                # <button data-col="0">↕</button>
                <input class="filter-input form-control" data-col="0" placeholder="Filter #"/>
              </th>
              <th>
                Patient Name <button data-col="1">↕</button>
                <input class="filter-input form-control" data-col="1" placeholder="Filter Name"/>
              </th>
              <th>
                DOB <button data-col="2">↕</button>
                <input class="filter-input form-control" data-col="2" placeholder="Filter DOB"/>
              </th>
              <th>
                Time Slot <button data-col="4">↕</button>
                <input class="filter-input form-control" data-col="4" placeholder="Filter Slot"/>
              </th>
              <th>
                Doctor <button data-col="5">↕</button>
                <input class="filter-input form-control" data-col="5" placeholder="Filter Doctor"/>
              </th>
              <th>
                Status <button data-col="6">↕</button>
                <input class="filter-input form-control" data-col="6" placeholder="Filter Status"/>
              </th>
              <th>
                Action
              </th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="app" items="${appointments}">
              <tr>
                <td>${app.index}</td>
                <td>${app.patientName}</td>
                <td><fmt:formatDate value="${app.patientDateOfBirth}" pattern="yyyy-MM-dd"/></td>
                <td>${app.timeSlotName}</td>
                <td>${app.doctorFullName}</td>
                <td>${app.status}</td>
                <td><a class="btn btn-primary" href="DoctorAppointmentDetail?id=${app.index}">Detail</a></td>
              </tr>
            </c:forEach>
            <c:if test="${empty appointments}">
              <tr>
                <td colspan="7" class="text-center text-muted">
                  No appointments in this time slot.
                </td>
              </tr>
            </c:if>
          </tbody>
        </table>
      </div>
    </main>
  </div>


  <script>
    const table = document.getElementById('apptTable');
    const tbody = table.tBodies[0];
    let sortOrder = {};

    // Sorting
    table.querySelectorAll('th button').forEach(btn => {
      btn.addEventListener('click', () => {
        const col = +btn.getAttribute('data-col');
        const asc = !sortOrder[col];
        sortOrder[col] = asc;
        const rows = Array.from(tbody.rows);
        rows.sort((a, b) => {
          const aText = a.cells[col].textContent.trim();
          const bText = b.cells[col].textContent.trim();
          let cmp;
          // columns 0 (#), 2 (DOB), 3 (ApptDate) can be treated as dates/numbers
          if (col === 0) {
            cmp = parseFloat(aText) - parseFloat(bText);
          } else if (col === 2 || col === 3) {
            // parse as ISO dates
            cmp = new Date(aText) - new Date(bText);
          } else {
            cmp = aText.localeCompare(bText, undefined, {numeric: true, sensitivity: 'base'});
          }
          return asc ? cmp : -cmp;
        });
        rows.forEach(r => tbody.appendChild(r));
      });
    });

    // Filtering
    table.querySelectorAll('.filter-input').forEach(input => {
      input.addEventListener('input', () => {
        const col = +input.getAttribute('data-col');
        const val = input.value.trim().toLowerCase();
        Array.from(tbody.rows).forEach(row => {
          const cell = row.cells[col].textContent.trim().toLowerCase();
          row.style.display = cell.includes(val) ? '' : 'none';
        });
      });
    });
  </script>
</body>
</html>
