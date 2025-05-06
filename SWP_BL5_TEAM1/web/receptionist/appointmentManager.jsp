<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Appointment Manager</title>
        <%@ include file="../assets/css/css-js.jsp" %>
        <style>
            .detail-table th {
                width: 200px;
                position: relative;
            }
            .filter-input {
                width: 100%;
                box-sizing: border-box;
                margin-top: 4px;
            }
            .sort-btn {
                border: none;
                background: transparent;
                padding: 0 4px;
                cursor: pointer;
                font-size: 0.9em;
            }
            .pagination li {
                display: inline-block;
                margin: 0 2px;
            }
            .pagination li.active a {
                font-weight: bold;
            }
            .filter-buttons {
                margin-bottom: 1rem;
            }
            .filter-buttons button {
                margin-right: 0.5rem;
            }
        </style>
    </head>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <%@ include file="../component/receptionistSideBar.jsp" %>
            <main class="page-content bg-light">
                <%@ include file="../component/header.jsp" %>
                <div class="container-fluid" style="margin-top:80px;">
                    <h2>Appointment Manager</h2>

                    <!-- Global search -->
                    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
                    <form id="searchForm" method="get" action="AppointmentManager" class="mb-3">
                        <input type="text" name="searchName" class="form-control" placeholder="Search by patient name" value="${fn:escapeXml(param.searchName)}"/>
                        <button type="submit" class="btn btn-primary mt-2">Search</button>
                    </form>

                    <!-- Page size selector -->
                    <label for="pageSizeSelect">Rows per page:</label>
                    <select id="pageSizeSelect">
                        <option value="5">5</option>
                        <option value="10" selected>10</option>
                        <option value="20">20</option>
                    </select>

                    <!-- Quick filter buttons -->
                    <div class="filter-buttons">
                        <button id="currentDateBtn" class="btn btn-sm btn-outline-secondary">Current Date</button>
                        <button id="currentSlotBtn" class="btn btn-sm btn-outline-secondary">Current Time Slot</button>
                        <button id="pendingBtn" class="btn btn-sm btn-outline-secondary">Pending</button>
                        <button id="resetBtn" class="btn btn-sm btn-outline-secondary">Reset</button>
                    </div>

                    <table id="apptTable" class="table table-striped detail-table">
                        <thead>
                            <tr>
                                <th>
                                    #
                                    <button class="sort-btn" data-col="0" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="0" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="0" placeholder="#" />
                                </th>
                                <th>
                                    Patient Name
                                    <button class="sort-btn" data-col="1" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="1" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="1" placeholder="Search Name" />
                                </th>
                                <th>
                                    DOB
                                    <button class="sort-btn" data-col="2" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="2" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="2" placeholder="YYYY-MM-DD" />
                                </th>
                                <th>
                                    Appt Date
                                    <button class="sort-btn" data-col="3" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="3" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="3" placeholder="YYYY-MM-DD" />
                                </th>
                                <th>
                                    Time Slot
                                    <button class="sort-btn" data-col="4" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="4" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="4" placeholder="Search Slot" />
                                </th>
                                <th>
                                    Doctor
                                    <button class="sort-btn" data-col="5" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="5" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="5" placeholder="Search Doctor" />
                                </th>
                                <th>
                                    Status
                                    <button class="sort-btn" data-col="6" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="6" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="6" placeholder="Search Status" />
                                </th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appt" items="${appointments}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${appt.patientName}</td>
                                    <td><fmt:formatDate value="${appt.patientDateOfBirth}" pattern="yyyy-MM-dd"/></td>
                                    <td><fmt:formatDate value="${appt.appointmentDate}" pattern="yyyy-MM-dd"/></td>
                                    <td>${appt.timeSlotName}</td>
                                    <td>${appt.doctorFullName}</td>
                                    <td>${appt.status}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${appt.status == 'Pending'}">
                                                <form class="inline"
                                                      method="post"
                                                      action="${pageContext.request.contextPath}/AppointmentManager">
                                                    <input type="hidden" name="appointmentId" value="${appt.appointmentId}" />
                                                    <input type="hidden" name="action" value="toWaiting" />
                                                    <button type="submit" class="btn btn-sm btn-warning">
                                                        Waiting
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:when test="${appt.status == 'Waiting-Payment'}">
                                                <form class="inline"
                                                      method="post"
                                                      action="${pageContext.request.contextPath}/AppointmentManager">
                                                    <input type="hidden" name="appointmentId" value="${appt.appointmentId}" />
                                                    <input type="hidden" name="action" value="complete" />
                                                    <button type="submit" class="btn btn-sm btn-success">
                                                        Payment-Complete
                                                    </button>
                                                </form>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination controls -->
                    <nav aria-label="Page navigation">
                        <ul id="pagination" class="pagination mb-0 list-unstyled"></ul>
                    </nav>
                </div>
            </main>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', () => {
                const table = document.getElementById('apptTable');
                let rows = Array.from(table.querySelectorAll('tbody tr'));
                const pageSizeSelect = document.getElementById('pageSizeSelect');
                const pagination = document.getElementById('pagination');
                const sortButtons = table.querySelectorAll('.sort-btn');
                const filterInputs = table.querySelectorAll('.filter-input');
                const currentDateBtn = document.getElementById('currentDateBtn');
                const currentSlotBtn = document.getElementById('currentSlotBtn');
                const pendingBtn = document.getElementById('pendingBtn');
                const resetBtn = document.getElementById('resetBtn');

                let pageSize = parseInt(pageSizeSelect.value);
                let currentPage = 1;
                let filtered = rows.slice();

                const getCellValue = (row, col) => row.children[col].innerText.trim();

                function applyFilter() {
                    filtered = rows.filter(row => {
                        return Array.from(filterInputs).every(input => {
                            const col = parseInt(input.dataset.col);
                            return getCellValue(row, col).toLowerCase().includes(input.value.toLowerCase());
                        });
                    });
                    currentPage = 1;
                    render();
                }

                function applySort(col, dir) {
                    filtered.sort((a, b) => {
                        const v1 = getCellValue(a, col);
                        const v2 = getCellValue(b, col);
                        return dir === 'asc' ? v1.localeCompare(v2) : v2.localeCompare(v1);
                    });
                    render();
                }

                function renderTable() {
                    const start = (currentPage - 1) * pageSize;
                    const end = start + pageSize;
                    table.querySelectorAll('tbody tr').forEach(r => r.remove());
                    filtered.slice(start, end).forEach(r => table.querySelector('tbody').appendChild(r));
                }

                function renderPagination() {
                    pagination.innerHTML = '';
                    const totalPages = Math.ceil(filtered.length / pageSize) || 1;
                    const createLi = (text, fn, active) => {
                        const li = document.createElement('li');
                        if (active)
                            li.classList.add('active');
                        const a = document.createElement('a');
                        a.href = '#';
                        a.className = 'px-3 py-2 border';
                        a.innerText = text;
                        a.addEventListener('click', e => {
                            e.preventDefault();
                            fn();
                        });
                        li.appendChild(a);
                        return li;
                    };
                    pagination.appendChild(createLi('Prev', () => {
                        if (currentPage > 1)
                            currentPage--, render();
                    }, false));
                    for (let i = 1; i <= totalPages; i++) {
                        pagination.appendChild(createLi(i, () => {
                            currentPage = i;
                            render();
                        }, i === currentPage));
                    }
                    pagination.appendChild(createLi('Next', () => {
                        if (currentPage < totalPages)
                            currentPage++, render();
                    }, false));
                }

                function render() {
                    renderTable();
                    renderPagination();
                }

                // Event bindings
                pageSizeSelect.addEventListener('change', () => {
                    pageSize = parseInt(pageSizeSelect.value);
                    currentPage = 1;
                    render();
                });
                sortButtons.forEach(btn => btn.addEventListener('click', () => applySort(parseInt(btn.dataset.col), btn.dataset.dir)));
                filterInputs.forEach(input => input.addEventListener('input', applyFilter));

                // Quick filter button handlers
                currentDateBtn.addEventListener('click', () => {
                    const today = new Date().toISOString().slice(0, 10);
                    document.querySelector('.filter-input[data-col="3"]').value = today;
                    applyFilter();
                });
                currentSlotBtn.addEventListener('click', () => {
                    const now = new Date();
                    const h = now.getHours();
                    let slot = '';
                    if (h >= 7 && h < 9)
                        slot = 'Morning1';
                    else if (h >= 9 && h < 11)
                        slot = 'Morning2';
                    else if (h >= 12 && h < 14)
                        slot = 'Afternoon1';
                    else if (h >= 14 && h < 16)
                        slot = 'Afternoon2';
                    else
                        slot = 'Night';
                    document.querySelector('.filter-input[data-col="4"]').value = slot;
                    applyFilter();
                });
                pendingBtn.addEventListener('click', () => {
                    document.querySelector('.filter-input[data-col="6"]').value = 'Pending';
                    applyFilter();
                });
                resetBtn.addEventListener('click', () => {
                    filterInputs.forEach(i => i.value = '');
                    filtered = rows.slice();
                    currentPage = 1;
                    render();
                });

                // Initial setup
                render();
            });
        </script>
    </body>
</html>
