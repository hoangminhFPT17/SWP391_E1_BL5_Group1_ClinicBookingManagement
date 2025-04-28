<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"     prefix="c"  %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Account Manager</title>
  <%@ include file="../assets/css/css-js.jsp" %>
  <style>
    .filter-input { width:100%; box-sizing:border-box; margin-top:4px; }
    .sort-btn    { border:none;background:transparent;padding:0 4px;cursor:pointer; }
    .pagination li { display:inline-block; margin:0 2px; }
    .pagination li.active a { font-weight:bold; }
    .inline-form{display:inline;}
    .top-bar{margin-bottom:1rem;display:flex;justify-content:space-between;}
  </style>
</head>
<body>
<div class="page-wrapper doctris-theme toggled">
  <%@ include file="../component/managerSideBar.jsp" %>
  <main class="page-content bg-light">
    <%@ include file="../component/header.jsp" %>
    <div class="container-fluid" style="margin-top:80px;">

      <div class="top-bar">
        <!-- Global Search -->
        <form method="get" action="AccountManager" class="d-flex">
          <input type="text"
                 name="search"
                 class="form-control me-2"
                 placeholder="Search name or email"
                 value="${fn:escapeXml(param.search)}"/>
          <button class="btn btn-primary">Search</button>
        </form>

        <!-- Add User -->
        <button class="btn btn-success"
                data-bs-toggle="modal"
                data-bs-target="#addUserModal">
          Add User
        </button>
      </div>

      <!-- User Table -->
      <table id="userTable" class="table table-striped">
        <thead>
          <tr>
            <th>
              Email
              <button class="sort-btn" data-col="0" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="0" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="0" placeholder="Filter Email"/>
            </th>
            <th>
              Phone
              <button class="sort-btn" data-col="1" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="1" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="1" placeholder="Filter Phone"/>
            </th>
            <th>
              Full Name
              <button class="sort-btn" data-col="2" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="2" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="2" placeholder="Filter Name"/>
            </th>
            <th>
              Verified
              <button class="sort-btn" data-col="3" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="3" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="3" placeholder="true/false"/>
            </th>
            <th>
              Created At
              <button class="sort-btn" data-col="4" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="4" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="4" placeholder="YYYY-MM-DD"/>
            </th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="u" items="${userList}">
            <tr>
              <td>${u.email}</td>
              <td>${u.phone}</td>
              <td>${u.fullName}</td>
              <td>${u.isVerified}</td>
              <td><fmt:formatDate value="${u.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
              <td>
                <!-- Edit -->
                <button class="btn btn-sm btn-info edit-btn"
                        data-userid="${u.userId}"
                        data-email="${u.email}"
                        data-phone="${u.phone}"
                        data-fullname="${u.fullName}"
                        data-verified="${u.isVerified}"
                        data-bs-toggle="modal"
                        data-bs-target="#editUserModal">
                  Edit
                </button>

                <!-- Delete -->
                <form method="post" action="AccountManager" class="inline-form">
                  <input type="hidden" name="userId"  value="${u.userId}"/>
                  <input type="hidden" name="action"  value="delete"/>
                  <input type="hidden" name="search"  value="${fn:escapeXml(param.search)}"/>
                  <button type="submit"
                          class="btn btn-sm btn-danger"
                          onclick="return confirm('Delete this user?');">
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

<!-- Add User Modal -->
<div class="modal fade" id="addUserModal" tabindex="-1">
  <div class="modal-dialog">
    <form method="post" action="AccountManager">
      <input type="hidden" name="action" value="add"/>
      <input type="hidden" name="search" value="${fn:escapeXml(param.search)}"/>
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Add User</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="addEmail" class="form-label">Email</label>
            <input type="email" id="addEmail" name="email" class="form-control" required/>
          </div>
          <div class="mb-3">
            <label for="addPhone" class="form-label">Phone</label>
            <input type="text" id="addPhone" name="phone" class="form-control" />
          </div>
          <div class="mb-3">
            <label for="addFullName" class="form-label">Full Name</label>
            <input type="text" id="addFullName" name="fullName" class="form-control" />
          </div>
          <div class="form-check mb-3">
            <input type="checkbox" id="addVerified" name="isVerified" class="form-check-input"/>
            <label for="addVerified" class="form-check-label">Verified</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-success">Add User</button>
        </div>
      </div>
    </form>
  </div>
</div>

<!-- Edit User Modal -->
<div class="modal fade" id="editUserModal" tabindex="-1">
  <div class="modal-dialog">
    <form method="post" action="AccountManager">
      <input type="hidden" name="action" value="edit"/>
      <input type="hidden" id="editUserId"   name="userId"/>
      <input type="hidden" name="search" value="${fn:escapeXml(param.search)}"/>
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Edit User</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="editEmail" class="form-label">Email</label>
            <input type="email" id="editEmail" name="email" class="form-control" required/>
          </div>
          <div class="mb-3">
            <label for="editPhone" class="form-label">Phone</label>
            <input type="text" id="editPhone" name="phone" class="form-control" />
          </div>
          <div class="mb-3">
            <label for="editFullName" class="form-label">Full Name</label>
            <input type="text" id="editFullName" name="fullName" class="form-control" />
          </div>
          <div class="form-check mb-3">
            <input type="checkbox" id="editVerified" name="isVerified" class="form-check-input"/>
            <label for="editVerified" class="form-check-label">Verified</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary">Save Changes</button>
        </div>
      </div>
    </form>
  </div>
</div>

<script>
// reuse your sort/filter/pagination JS, targeting #userTable…

// pre-fill edit form
document.querySelectorAll('.edit-btn').forEach(btn=>{
  btn.addEventListener('click',()=>{
    document.getElementById('editUserId').value  = btn.dataset.userid;
    document.getElementById('editEmail').value   = btn.dataset.email;
    document.getElementById('editPhone').value   = btn.dataset.phone;
    document.getElementById('editFullName').value= btn.dataset.fullname;
    document.getElementById('editVerified').checked = (btn.dataset.verified==='true');
  });
});
</script>
</body>
</html>
