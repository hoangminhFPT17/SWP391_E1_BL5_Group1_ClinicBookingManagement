<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <title>Doctris - Examination Package Manager</title>
        <%@ include file="../assets/css/css-js.jsp" %>
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
            <%@ include file="../component/managerSideBar.jsp" %>
            <main class="page-content bg-light">
                <%@ include file="../component/managerHeader.jsp" %>
                <div class="container-fluid">

                    <!-- Header -->
                    <div class="d-flex justify-content-between align-items-center mb-3" style="margin-top:50px;">
                        <h2 class="mb-0">Examination Packages</h2>

                    </div>

                    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal" style="margin: 10px">
                        Add New Package
                    </button>

                    <!-- Table -->
                    <table id="pkgTable" class="table table-bordered table-striped">
                        <thead class="thead-light">
                            <tr>
                                <th>
                                    ID <button data-col="0">↕</button>
                                    <input class="filter-input form-control" data-col="0" placeholder="Filter ID" />
                                </th>
                                <th>
                                    Name <button data-col="1">↕</button>
                                    <input class="filter-input form-control" data-col="1" placeholder="Filter Name" />
                                </th>
                                <th>
                                    Description <button data-col="2">↕</button>
                                    <input class="filter-input form-control" data-col="2" placeholder="Filter Description" />
                                </th>
                                <th>
                                    Price <button data-col="3">↕</button>
                                    <input class="filter-input form-control" data-col="3" placeholder="Filter Price" />
                                </th>
                                <th>
                                    Specialty <button data-col="4">↕</button>
                                    <input class="filter-input form-control" data-col="4" placeholder="Filter Specialty" />
                                </th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pkg" items="${packages}">
                                <tr data-id="${pkg.packageId}">
                                    <td>${pkg.packageId}</td>
                                    <td>${pkg.name}</td>
                                    <td>${pkg.description}</td>
                                    <td>${pkg.price}</td>
                                    <td>${pkg.specialty}</td>
                                    <td>
                                        <button 
                                            class="btn btn-sm btn-primary edit-btn"
                                            data-bs-toggle="modal"
                                            data-bs-target="#editModal"
                                            data-id="${pkg.packageId}"
                                            data-name="${pkg.name}"
                                            data-description="${pkg.description}"
                                            data-price="${pkg.price}"
                                            data-specialty="${pkg.specialty}">
                                            >

                                            Update
                                        </button>
                                        <form
                                            action="<c:url value='ExaminationPackageManager'/>"
                                            method="post"
                                            style="display:inline;"
                                            onsubmit="return confirm('Are you sure you want to delete this package?');">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="packageId" value="${pkg.packageId}"/>
                                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty packages}">
                                <tr>
                                    <td colspan="6" class="text-center text-muted">
                                        No examination packages found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>

        <!-- Add Modal (unchanged) -->
        <div class="modal fade" id="addModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <form 
                    action="<c:url value='ExaminationPackageManager'/>" 
                    method="post" 
                    class="modal-content"
                    onsubmit="return validateAddForm();">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Package</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Inline alert, hidden by default -->
                        <div id="add-alert" class="alert alert-primary d-none" role="alert"></div>

                        <input type="hidden" name="action" value="create" />
                        <div class="mb-3">
                            <label class="form-label">Name</label>
                            <input type="text" name="name" id="add-name" class="form-control" required />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="description" id="add-description" class="form-control" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Price</label>
                            <input type="number" step="0.01" name="price" class="form-control" required />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Specialty</label>
                            <select name="specialtyId" class="form-select" required>
                                <c:forEach var="s" items="${specialties}">
                                    <option value="${s.specialtyId}">${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button 
                            type="button" 
                            class="btn btn-secondary" 
                            data-bs-dismiss="modal">
                            Close
                        </button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Edit Modal -->
        <div class="modal fade" id="editModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <form action="<c:url value='ExaminationPackageManager'/>"
                      method="post"
                      class="modal-content"
                      id="editForm"
                      onsubmit="return validateEditForm();">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Package</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div id="edit-alert" class="alert alert-primary d-none" role="alert"></div>
                        <input type="hidden" name="action" value="edit" />
                        <input type="hidden" name="packageId" id="edit-id" />
                        <div class="mb-3">
                            <label class="form-label">Name</label>
                            <input type="text" name="name" id="edit-name" class="form-control" required/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="description" id="edit-description" class="form-control" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Price</label>
                            <input type="number" step="0.01" name="price" id="edit-price" class="form-control" required/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Specialty</label>
                            <select name="specialtyId" id="edit-specialty" class="form-select" required>
                                <c:forEach var="s" items="${specialties}">
                                    <option value="${s.specialtyId}">${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            // Client-side sorting & filtering
            const table = document.getElementById('pkgTable');
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
                        let aText = a.cells[col].textContent.trim();
                        let bText = b.cells[col].textContent.trim();
                        let cmp;
                        if (col === 0 || col === 3) { // numeric
                            cmp = parseFloat(aText) - parseFloat(bText);
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

        <script>
            // ... sorting & filtering code omitted for brevity ...

            // When you click an Edit button, pull its data-* attributes into the modal
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', () => {
                    const id = btn.getAttribute('data-id');
                    const name = btn.getAttribute('data-name');
                    const desc = btn.getAttribute('data-description');
                    const price = btn.getAttribute('data-price');
                    const spec = btn.getAttribute('data-specialty');

                    // fill fields
                    document.getElementById('edit-id').value = id;
                    document.getElementById('edit-name').value = name;
                    document.getElementById('edit-description').value = desc;
                    document.getElementById('edit-price').value = price;

                    // select the matching specialty
                    const select = document.getElementById('edit-specialty');
                    Array.from(select.options).forEach(opt => {
                        opt.selected = (opt.text === spec);
                    });

                    // clear any previous alert
                    document.getElementById('edit-alert').classList.add('d-none');
                });
            });

            // validation for Edit (ensure no all-spaces)
            function validateEditForm() {
                const name = document.getElementById('edit-name').value.trim();
                const desc = document.getElementById('edit-description').value.trim();
                const alertBox = document.getElementById('edit-alert');
                alertBox.classList.add('d-none');

                if (!name) {
                    alertBox.textContent = 'Name cannot be blank or spaces only.';
                    alertBox.classList.remove('d-none');
                    return false;
                }
                if (!desc) {
                    alertBox.textContent = 'Description cannot be blank or spaces only.';
                    alertBox.classList.remove('d-none');
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
