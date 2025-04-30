<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <title>Staff Manager</title>
        <%@ include file="../assets/css/css-js.jsp" %>
        <style>
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
            }
            .pagination li {
                display: inline-block;
                margin: 0 2px;
            }
            .pagination li.active a {
                font-weight: bold;
            }
            .inline-form {
                display: inline;
            }
            .top-bar {
                margin-bottom: 1rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            html, body {
                overflow: hidden;    /* hides page scrollbar and prevents scroll */
                height: 100%;        /* ensure full‐height so no extra space */
            }

            /* 2) (Optional) Hide scrollbars on any inner elements that might overflow */
            .some-scrollable-element {
                overflow: hidden;    /* prevents scroll within that container */
            }

            /* 3) (Optional) If you only want to hide WebKit scrollbars but still allow mouse‐wheel (not recommended) */
            ::-webkit-scrollbar {
                display: none;
            }
        </style>
    </head>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <%@ include file="../component/managerSideBar.jsp" %>
            <main class="page-content bg-light">
                <%@ include file="../component/header.jsp" %>
                <div class="container-fluid" style="margin-top:80px;">
                    <div class="d-flex justify-content-between align-items-center mb-3" style="margin-top:50px;">
                        <h2 class="mb-0">Staff manager</h2>

                    </div>

                    <div class="top-bar">
                        <!-- Global search -->
                        <form method="get" action="StaffManager" class="d-flex">
                            <input type="text"
                                   name="searchName"
                                   placeholder="Search by name"
                                   value="${fn:escapeXml(param.searchName)}"
                                   class="form-control me-2"/>
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>

                        <!-- Add Staff button -->
                        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addStaffModal">
                            Add Staff
                        </button>
                    </div>

                    <table id="staffTable" class="table table-striped">
                        <thead>
                            <tr>
                                <th>
                                    Full Name
                                    <button class="sort-btn" data-col="0" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="0" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="0" placeholder="Filter Name"/>
                                </th>
                                <th>
                                    Phone
                                    <button class="sort-btn" data-col="1" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="1" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="1" placeholder="Filter Phone"/>
                                </th>
                                <th>
                                    Email
                                    <button class="sort-btn" data-col="2" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="2" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="2" placeholder="Filter Email"/>
                                </th>
                                <th>
                                    Role
                                    <button class="sort-btn" data-col="3" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="3" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="3" placeholder="Filter Role"/>
                                </th>
                                <th>
                                    Department
                                    <button class="sort-btn" data-col="4" data-dir="asc">▲</button>
                                    <button class="sort-btn" data-col="4" data-dir="desc">▼</button>
                                    <input type="text" class="filter-input" data-col="4" placeholder="Filter Dept"/>
                                </th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${staffList}">
                                <tr>
                                    <td>${s.fullName}</td>
                                    <td>${s.phone}</td>
                                    <td>${s.email}</td>
                                    <td>${s.role}</td>
                                    <td>${s.department}</td>
                                    <td>
                                        <!-- Edit button -->
                                        <button class="btn btn-sm btn-info edit-btn"
                                                data-staffid="${s.staffId}"
                                                data-phone="${s.phone}"
                                                data-email="${s.email}"
                                                data-role="${s.role}"
                                                data-department="${s.department}"
                                                data-bs-toggle="modal"
                                                data-bs-target="#editStaffModal">
                                            Edit
                                        </button>

                                        <!-- Delete button -->
                                        <form method="post" action="StaffManager" class="inline-form">
                                            <input type="hidden" name="staffId" value="${s.staffId}"/>
                                            <input type="hidden" name="action" value="delete"/>
                                            <button type="submit"
                                                    class="btn btn-sm btn-danger"
                                                    onclick="return confirm('Delete this staff?');">
                                                Delete
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <ul id="pagination" class="pagination mb-0 list-unstyled"></ul>
                </div>
            </main>
        </div>

        <!-- Add Staff Modal -->
        <div class="modal fade" id="addStaffModal" tabindex="-1">
            <div class="modal-dialog">
                <form method="post" action="StaffManager">
                    <input type="hidden" name="action" value="addStaff"/>
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Add Staff</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="addEmail" class="form-label">User Email</label>
                                <input type="email" id="addEmail" name="email" class="form-control" required/>
                            </div>
                            <div class="mb-3">
                                <label for="addRole" class="form-label">Role</label>
                                <select id="addRole" name="role" class="form-select" required>
                                    <option value="Doctor">Doctor</option>
                                    <option value="Manager">Manager</option>
                                    <option value="Receptionist">Receptionist</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="addDept" class="form-label">Department</label>
                                <input type="text" id="addDept" name="department" class="form-control" required/>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Add</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Edit Staff Modal -->
        <div class="modal fade" id="editStaffModal" tabindex="-1">
            <div class="modal-dialog">
                <form method="post" action="StaffManager">
                    <input type="hidden" name="action" value="editStaff"/>
                    <input type="hidden" id="editStaffId" name="staffId"/>
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Staff</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="editEmail" class="form-label">User Email</label>
                                <input type="email" id="editEmail" name="email" class="form-control" required/>
                            </div>
                            <div class="mb-3">
                                <label for="editRole" class="form-label">Role</label>
                                <select id="editRole" name="role" class="form-select" required>
                                    <option value="Doctor">Doctor</option>
                                    <option value="Manager">Manager</option>
                                    <option value="Receptionist">Receptionist</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="editDept" class="form-label">Department</label>
                                <input type="text" id="editDept" name="department" class="form-control" required/>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <script>
            document.addEventListener('DOMContentLoaded', () => {
                const table = document.getElementById('staffTable');
                const tbody = table.querySelector('tbody');
                const sortButtons = table.querySelectorAll('.sort-btn');
                const filters = table.querySelectorAll('.filter-input');
                const pagination = document.getElementById('pagination');

                let rows = Array.from(tbody.querySelectorAll('tr'));
                let filtered = rows.slice();
                let pageSize = 5;
                let currentPage = 1;

                const getCell = (row, col) => row.children[col].innerText.trim().toLowerCase();

                function applyFilter() {
                    filtered = rows.filter(row =>
                        Array.from(filters).every(inp =>
                            getCell(row, +inp.dataset.col).includes(inp.value.trim().toLowerCase())
                        )
                    );
                    currentPage = 1;
                    render();
                }

                function applySort(col, dir) {
                    filtered.sort((a, b) => {
                        const v1 = getCell(a, col), v2 = getCell(b, col);
                        return dir === 'asc' ? v1.localeCompare(v2) : v2.localeCompare(v1);
                    });
                    render();
                }

                function renderTable() {
                    tbody.innerHTML = '';
                    const start = (currentPage - 1) * pageSize;
                    const end = start + pageSize;
                    filtered.slice(start, end).forEach(r => tbody.appendChild(r));
                }

                function renderPagination() {
                    pagination.innerHTML = '';
                    const totalPages = Math.max(1, Math.ceil(filtered.length / pageSize));

                    const mkBtn = (label, fn, disabled, active) => {
                        const li = document.createElement('li');
                        if (active)
                            li.classList.add('active');
                        const a = document.createElement('a');
                        a.href = '#';
                        a.innerText = label;
                        a.className = 'px-3 py-2 border' + (disabled ? ' disabled' : '');
                        a.addEventListener('click', e => {
                            e.preventDefault();
                            if (!disabled) {
                                fn();
                                render();                // <-- make sure to re-render after page change
                            }
                        });
                        li.appendChild(a);
                        return li;
                    };

                    // Prev
                    pagination.appendChild(mkBtn('Prev', () => {
                        if (currentPage > 1)
                            currentPage--;
                    }, currentPage <= 1, false));

                    // Page numbers
                    for (let i = 1; i <= totalPages; i++) {
                        pagination.appendChild(mkBtn(i, () => {
                            currentPage = i;
                        }, false, i === currentPage));
                    }

                    // Next
                    pagination.appendChild(mkBtn('Next', () => {
                        if (currentPage < totalPages)
                            currentPage++;
                    }, currentPage >= totalPages, false));
                }

                function render() {
                    renderTable();
                    renderPagination();
                }

                // wire up sorting & filtering
                sortButtons.forEach(btn =>
                    btn.addEventListener('click', () => applySort(+btn.dataset.col, btn.dataset.dir))
                );
                filters.forEach(inp => inp.addEventListener('input', applyFilter));

                // initial draw
                render();
            });
        </script>


        <script>
            // (reuse your sorting/filtering/pagination JS here, targeting #staffTable…)

            // Pre-fill Edit form when an edit-btn is clicked
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', () => {
                    document.getElementById('editStaffId').value = btn.dataset.staffid;
                    document.getElementById('editEmail').value = btn.dataset.email;
                    document.getElementById('editRole').value = btn.dataset.role;
                    document.getElementById('editDept').value = btn.dataset.department;
                });
            });
        </script>
    </body>
</html>
