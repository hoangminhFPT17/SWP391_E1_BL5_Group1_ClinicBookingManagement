const ul = document.querySelector('.pagination ul'),
        posts = document.querySelectorAll('.post');
const generatePageNumber = (totalPages, currentPage) => {
    let pagination = [], pageNo = 1;
    while (pageNo <= totalPages) {
        let isFirstPage = pageNo <= 1,
                isLastPage = pageNo == totalPages,
                isMiddlePage = pageNo >= currentPage - 1 && currentPage + 1
        if (isFirstPage || isLastPage || isMiddlePage) {
            pagination.push(pageNo)
        } else {
            pagination.push("...");
            pageNo = pageNo < currentPage ? currentPage - 1 : totalPages
        }

        pageNo++
    }
    return pagination;
}

const updateCurrentPage = (postPerPage, currentPage) => {
    let prevRange = (currentPage-1) * postPerPage,
    currentRange = currentPage * postPerPage;
    
    posts.forEach((post, index) => {
        let isPageWithinRange = index >= prevRange && index < currentRange;
        post.classList.toggle("show", isPageWithinRange);
    })
}

const handlePagination = (postPerPage, currentPage) => {
    updateCurrentPage(postPerPage, currentPage);
    const totalPages = Math.ceil(posts.length / postPerPage);
    let pageNumbers = generatePageNumber(totalPages, currentPage);
    ul.innerHTML = "";
    let li = '';
    li += ` <li class="page ${currentPage <= 1 ? 'hidden' : ''}" onclick="handlePagination(${ostPerPage},${currentPage - 1})"><span class="icon">&lt;</li> `;
    for (let pageNumber of pageNumbers) {
        li += ` <li class="page ${currentPage == pageNumber ? 'active' : ''}" onclick="handlePagination(${ostPerPage},${pageNumber})">${pageNumber}</li> `;
    }
    li += ` <li class="page ${currentPage <= 1 ? 'hidden' : ''}" onclick="handlePagination(${ostPerPage},${currentPage + 1})"><span class="icon">&gt;</li> `;
    ul.innerHTML = li;
}



handlePagination(6, 5);