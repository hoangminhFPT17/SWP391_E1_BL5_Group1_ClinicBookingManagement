<%-- 
    Document   : PatientInvoice
    Created on : Apr 23, 2025, 2:06:40 PM
    Author     : JackGarland
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.ReceptionAppointmentDTO" %>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@page import="model.Patient"%>
<%@page import="dal.DAOUser"%>
<%@page import="dal.StaffAccountDAO"%>
<%@page import="dal.InvoiceDAO"%>
<%@page import="dal.AppointmentDAO"%>
<%@page import="dal.PatientDAO"%>
<%@page import="model.User"%>
<%@page import="dal.DAOPayment"%>
<%@page import="model.Payment"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%
            PatientDAO dao = new PatientDAO();
            AppointmentDAO appoint_dao = new AppointmentDAO();
            InvoiceDAO invoice_dao = new InvoiceDAO();
            User user = (User) session.getAttribute("user");
            StaffAccountDAO SA_dao = new StaffAccountDAO();
            DAOUser user_dao = new DAOUser();

            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            Patient patient = dao.getPatientByUserId(user.getUserId());
            if (patient == null) {
                response.sendRedirect("home.jsp");
                return;
            }

        %>

        <%            // Get current date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());

            // Calculate date 1 month into the future
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 1); // Add 1 month
            String futureDate = sdf.format(cal.getTime());
        %>

        <!-- Example URL with both dates -->

        <meta charset="utf-8"/>
        <title>NNNN</title>
        <!-- favicon -->
        <link rel="shortcut icon" href="<c:url value='/assets/images/favicon.ico.png'/>">
        <!-- Bootstrap -->
        <link href="<c:url value='/assets/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" />
        <!-- simplebar -->
        <link href="<c:url value='/assets/css/simplebar.css'/>" type="text/css" />
        <!-- Icons -->
        <link href="<c:url value='/assets/css/materialdesignicons.min.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/assets/css/remixicon.css'/>" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- Css -->
        <link href="<c:url value='/assets/css/style.min.css'/>" rel="stylesheet" type="text/css" id="theme-opt" />

        <!-- javascript -->
        <script src="<c:url value='${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js'/>"></script>
        <c:url var="styleUrl" value='/assets/css/style.min.css'/>
        <!-- simplebar -->
        <script src="<c:url value='${pageContext.request.contextPath}/assets/js/simplebar.min.js'/>"></script>
        <!-- Icons -->
        <script src="<c:url value='${pageContext.request.contextPath}/assets/js/feather.min.js'/>"></script>
        <!-- Main Js -->
        <script src="<c:url value='${pageContext.request.contextPath}/assets/js/app.js'/>"></script>
        <style>
            th {
                position: relative;
                cursor: pointer;
            }
            th button {
                border: none;
                background: transparent;
                padding: 0;
                margin-left: 5px;
                cursor: pointer;
            }
            .filter-input {
                width: 100%;
                box-sizing: border-box;
                margin-top: 5px;
            }
        </style>
    </head>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <jsp:include page="/common/patientHeaderNav.jsp" />
           
            <main class="page-content bg-light">
                <div class="container-fluid">
                    <div class="d-flex justify-content-between align-items-center mb-3" style="margin-top:80px;">
                        <h2 class="mb-0">Patient's Appointment lists waiting for payment</h2>
                    </div>

                    <div class="d-flex justify-content-end mb-2">
                        <button id="refreshBtn" class="btn btn-outline-primary">
                            <i class="bi-arrow-clockwise"></i> Refresh
                        </button>
                    </div>
                    <table id="apptTable" class="table table-bordered table-striped">
                        <thead class="thead-light">
                            <tr>
                                <th>
                                    # <button data-col="0">↕</button>
                                    <input class="filter-input form-control" data-col="0" placeholder="Filter #"/>
                                </th>
                                <th>
                                    Patient Phone <button data-col="1">↕</button>
                                    <input class="filter-input form-control" data-col="1" placeholder="Filter Phone"/>
                                </th>
                                <th>
                                    Patient Name <button data-col="2">↕</button>
                                    <input class="filter-input form-control" data-col="2" placeholder="Filter Name"/>
                                </th>
                                <th>
                                    Time Slot <button data-col="4">↕</button>
                                    <input class="filter-input form-control" data-col="3" placeholder="Filter Slot"/>
                                </th>
                                <th>
                                    Doctor Name<button data-col="5">↕</button>
                                    <input class="filter-input form-control" data-col="4" placeholder="Filter Doctor"/>
                                </th>
                                <th>
                                    Package <button data-col="6">↕</button>
                                    <input class="filter-input form-control" data-col="5" placeholder="Filter Package"/>
                                </th>
                                <th>
                                    Description <button data-col="6">↕</button>
                                    <input class="filter-input form-control" data-col="6" placeholder="Filter Description"/>
                                </th>
                                <th>
                                    Action
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<ReceptionAppointmentDTO> appointments = (List<ReceptionAppointmentDTO>) request.getAttribute("appointments");
                                if (appointments != null && !appointments.isEmpty()) {
                                    for (ReceptionAppointmentDTO app : appointments) {
                            %>
                            <tr>
                                <td><%= app.getAppointment_id()%></td>
                                <td><%= app.getPatient_phone()%></td>
                                <td><%= app.getPatient_fullName()%></td>
                                <td><%= app.getSlot_startTime()%> ~ <%= app.getSlot_endTime()%></td>
                                <td><%= app.getDoctor_fullName()%></td>                          
                                <td><%= app.getPackage_name() != null ? app.getPackage_name() : "N/A"%></td>
                                <td><%= app.getAppointment_description()%></td>
                                <td><a class="btn btn-primary" href="InvoiceDetailsServlet?id=<%= app.getAppointment_id()%>&packageName=<%= app.getPackage_name()%>&patientName=<%= app.getPatient_fullName()%>&currentDate=<%= currentDate%>&futureDate=<%= futureDate%>&doctorName=<%= app.getDoctor_fullName()%>&patientPhone=<%= app.getPatient_phone()%>">Invoice details</a></td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="8" class="text-center text-muted">
                                    No appointments ready for invoice.
                                </td>
                            </tr>
                            <% }%>
                        </tbody>
                    </table>

                    <!-- add right after </table> -->
                    <!-- Pagination controls -->
                    <nav aria-label="Appointment pagination" class="mt-3">
                        <ul id="apptPagination" class="pagination justify-content-center"></ul>
                    </nav>

                    <script>
                        // Wait until entire page (and the table) is loaded
                        window.onload = function () {
                            const table = document.getElementById('apptTable');
                            const tbody = table.tBodies[0];
                            const rows = Array.from(tbody.rows);
                            const rowsPerPage = 5;
                            const totalPages = Math.ceil(rows.length / rowsPerPage);
                            let currentPage = 1;

                            function renderTable(page) {
                                const start = (page - 1) * rowsPerPage;
                                const end = start + rowsPerPage;
                                rows.forEach((row, idx) => {
                                    row.style.display = (idx >= start && idx < end) ? '' : 'none';
                                });
                            }

                            function renderPagination() {
                                const ul = document.getElementById('apptPagination');
                                ul.innerHTML = '';

                                // Previous button
                                const prevLi = document.createElement('li');
                                prevLi.className = 'page-item' + (currentPage === 1 ? ' disabled' : '');
                                prevLi.innerHTML = '<a class="page-link" href="#">Previous</a>';
                                prevLi.addEventListener('click', function (e) {
                                    e.preventDefault();
                                    if (currentPage > 1) {
                                        currentPage--;
                                        update();
                                    }
                                });
                                ul.appendChild(prevLi);

                                // Page number buttons
                                for (let i = 1; i <= totalPages; i++) {
                                    const li = document.createElement('li');
                                    li.className = 'page-item' + (i === currentPage ? ' active' : '');
                                    li.innerHTML = '<a class="page-link" href="#">' + i + '</a>';
                                    li.addEventListener('click', (function (page) {
                                        return function (e) {
                                            e.preventDefault();
                                            currentPage = page;
                                            update();
                                        }
                                    })(i));
                                    ul.appendChild(li);
                                }

                                // Next button
                                const nextLi = document.createElement('li');
                                nextLi.className = 'page-item' + (currentPage === totalPages ? ' disabled' : '');
                                nextLi.innerHTML = '<a class="page-link" href="#">Next</a>';
                                nextLi.addEventListener('click', function (e) {
                                    e.preventDefault();
                                    if (currentPage < totalPages) {
                                        currentPage++;
                                        update();
                                    }
                                });
                                ul.appendChild(nextLi);
                            }

                            function update() {
                                renderTable(currentPage);
                                renderPagination();
                            }

                            // Initial call
                            update();
                        };

                        document.getElementById('refreshBtn').addEventListener('click', function (e) {
                            e.preventDefault();
                            // force a full page reload, so JSP re-queries the DB
                            window.location.reload();
                        });

                    </script>



                </div>
            </main>
        </div>
    </body>


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
</html>
