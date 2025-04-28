<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>Transfer Patient</title>
  <%@ include file="../assets/css/css-js.jsp" %>
  <style>
    .filter-input { width:100%; box-sizing:border-box; margin-top:4px; }
    .sort-btn    { border:none; background:transparent; padding:0 4px; cursor:pointer; }
    .pagination li { display:inline-block; margin:0 2px; }
    .pagination li.active a { font-weight:bold; }
  </style>
</head>
<body>
<div class="page-wrapper doctris-theme toggled">
  <%@ include file="../component/doctorSideBar.jsp" %>
  <main class="page-content bg-light">
    <%@ include file="../component/header.jsp" %>
    <div class="container-fluid" style="margin-top:80px;">
      <h2>Transfers To Me</h2>
      <table id="handoffTable" class="table table-striped">
        <thead>
          <tr>
            <th>
              Handoff ID
              <button class="sort-btn" data-col="0" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="0" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="0" placeholder="Filter ID"/>
            </th>
            <th>
              From Doctor
              <button class="sort-btn" data-col="1" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="1" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="1" placeholder="Filter From"/>
            </th>
            <th>
              To Doctor
              <button class="sort-btn" data-col="2" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="2" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="2" placeholder="Filter To"/>
            </th>
            <th>
              Appointment
              <button class="sort-btn" data-col="3" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="3" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="3" placeholder="Filter AppID"/>
            </th>
            <th>
              Reason
              <button class="sort-btn" data-col="4" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="4" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="4" placeholder="Filter Reason"/>
            </th>
            <th>
              Status
              <button class="sort-btn" data-col="5" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="5" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="5" placeholder="Filter Status"/>
            </th>
            <th>
              Created At
              <button class="sort-btn" data-col="6" data-dir="asc">▲</button>
              <button class="sort-btn" data-col="6" data-dir="desc">▼</button>
              <input type="text" class="filter-input" data-col="6" placeholder="YYYY-MM-DD"/>
            </th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="h" items="${handoffs}">
            <tr>
              <td>${h.handoffId}</td>
              <td>${h.fromDoctorName}</td>
              <td>${h.toDoctorName}</td>
              <td>${h.appointmentId}</td>
              <td>${h.reason}</td>
              <td>${h.status}</td>
              <td><fmt:formatDate value="${h.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <!-- Pagination -->
      <ul id="pagination" class="pagination mb-0 list-unstyled"></ul>
    </div>
  </main>
</div>

<script>
document.addEventListener('DOMContentLoaded', () => {
  const table       = document.getElementById('handoffTable');
  const tbody       = table.querySelector('tbody');
  let rows          = Array.from(tbody.querySelectorAll('tr'));
  const sortButtons = table.querySelectorAll('.sort-btn');
  const filters     = table.querySelectorAll('.filter-input');
  const pagination  = document.getElementById('pagination');

  let pageSize    = 10;
  let currentPage = 1;
  let filtered    = rows.slice();

  const getCell = (r, c) => r.children[c].innerText.trim().toLowerCase();

  function applyFilter() {
    filtered = rows.filter(r =>
      Array.from(filters).every(inp => getCell(r, +inp.dataset.col).includes(inp.value.toLowerCase()))
    );
    currentPage = 1; render();
  }

  function applySort(col, dir) {
    filtered.sort((a,b) => {
      const v1 = getCell(a,col), v2 = getCell(b,col);
      return dir==='asc' ? v1.localeCompare(v2) : v2.localeCompare(v1);
    });
    render();
  }

  function renderTable() {
    tbody.innerHTML = '';
    const start = (currentPage-1)*pageSize, end = start+pageSize;
    filtered.slice(start,end).forEach(r=>tbody.appendChild(r));
  }

  function renderPagination() {
    pagination.innerHTML = '';
    const total = Math.max(1, Math.ceil(filtered.length/pageSize));
    const mk = (lbl,fn,dis,act) => {
      const li=document.createElement('li'); if(act)li.classList.add('active');
      const a=document.createElement('a'); a.href='#'; a.innerText=lbl;
      a.className='px-3 py-2 border'+(dis?' disabled':'');
      a.onclick=e=>{e.preventDefault(); if(!dis)fn();};
      li.appendChild(a); return li;
    };
    pagination.appendChild(mk('Prev', ()=>currentPage>1&&(--currentPage,render()),currentPage<=1,false));
    for(let i=1;i<=total;i++) pagination.appendChild(mk(i,()=>{currentPage=i;render()},false,i===currentPage));
    pagination.appendChild(mk('Next',()=>currentPage<total&&(currentPage++,render()),currentPage>=total,false));
  }

  function render() { renderTable(); renderPagination(); }

  sortButtons.forEach(btn=>btn.addEventListener('click',()=>applySort(+btn.dataset.col,btn.dataset.dir)));
  filters.forEach(inp=>inp.addEventListener('input', applyFilter));

  render();
});
</script>
</body>
</html>
