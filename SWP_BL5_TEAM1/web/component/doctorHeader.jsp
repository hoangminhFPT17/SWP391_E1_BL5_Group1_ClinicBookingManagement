<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="top-header">
    <div class="header-bar d-flex justify-content-between border-bottom">
        <div class="d-flex align-items-center">
            <a href="#" class="logo-icon">
                <img src="<c:url value='/assets/images/logo-icon.png'/>" height="30" class="small" alt="logo-icon">
                <span class="big">
                    <img src="<c:url value='/assets/images/logo-dark.png'/>" height="24" class="logo-light-mode" alt="logo-dark">
                    <img src="<c:url value='/assets/images/logo-light.png'/>" height="24" class="logo-dark-mode" alt="logo-light">
                </span>
            </a>
            <a id="close-sidebar" class="btn btn-icon btn-pills btn-soft-primary ms-2" href="#">
                <i class="uil uil-bars"></i>
            </a>
            <div class="search-bar p-0 d-none d-lg-block ms-2">
                <div id="search" class="menu-search mb-0">
                    <form role="search" method="get" id="searchform" class="searchform">
                        <div>
                            <input type="text" class="form-control border rounded-pill" name="s" id="s" placeholder="Search Keywords...">
                            <input type="submit" id="searchsubmit" value="Search">
                        </div>
                    </form>
                </div>
            </div>
        </div>

            

            <!-- User Avatar Dropdown -->
            <li class="list-inline-item mb-0 ms-1">
                <div class="dropdown dropdown-primary">
                    <button id="userAvatarToggle" type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="<c:url value='/assets/images/doctors/01.jpg'/>" class="avatar avatar-ex-small rounded-circle" alt="doctor">
                    </button>
                    <div id="userAvatarMenu" class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                        <a class="dropdown-item d-flex align-items-center text-dark" href="#">
                            <img src="<c:url value='/assets/images/doctors/01.jpg'/>" class="avatar avatar-md-sm rounded-circle border shadow" alt="doctor">
                            <div class="flex-1 ms-2">
                                <span class="d-block mb-1">Calvin Carlo</span>
                                <small class="text-muted">Orthopedic</small>
                            </div>
                        </a>
                        <a class="dropdown-item text-dark" href="#"><i class="uil uil-dashboard align-middle me-1"></i> Profile</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item text-dark" href="#"><i class="uil uil-sign-out-alt align-middle me-1"></i> Logout</a>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>


<script src="<c:url value='/assets/js/bootstrap.bundle.min.js'/>"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Sidebar toggle
        const sidebarBtn = document.getElementById('close-sidebar');
        sidebarBtn.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector('.page-wrapper')?.classList.toggle('toggled');
        });
    });
</script>
