<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Doctor Status Manager</title>
    <%@ include file="../assets/css/css-js.jsp" %>
</head>
<body>
    <div class="page-wrapper doctris-theme toggled">
        <%@ include file="../component/sideBar.jsp" %>
        <main class="page-content bg-light">
            <%@ include file="../component/header.jsp" %>
            <div class="container-fluid py-4">
                <div class="row mt-4" >
                    <div class="col text-center" style="margin-top: 50px">
                        <h3>Doctor availability</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white">
                                All Doctors Status
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover">
                                        <thead class="thead-light">
                                            <tr>
                                                <th>Doctor Name</th>
                                                <th>Department</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="doc" items="${doctorStatusList}">
                                                <tr>
                                                    <td>${doc.doctorName}</td>
                                                    <td>${doc.department}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${doc.free}">
                                                                <span class="badge bg-success">Free</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-danger">Busy</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <c:if test="${empty doctorStatusList}">
                                        <p class="text-center my-3">No doctors found.</p>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>