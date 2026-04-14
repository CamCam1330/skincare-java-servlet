<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>

<c:url var="assets" value="/assets"/>
<main class="container py-5">
  <div class="d-flex align-items-center mb-3">
    <h3 class="mb-0">Sản phẩm mới</h3>
    <a class="ms-auto small text-decoration-none" href="${pageContext.request.contextPath}/category?id=1">Xem tất cả →</a>
  </div>

  <div class="row g-4">
    <c:forEach var="p" items="${products}">
      <div class="col-6 col-md-4 col-lg-3">
        <div class="card h-100 product-card">
          <a href="${pageContext.request.contextPath}/product?id=${p.id}">
              <img class="card-img-top"
                   src="${assets}/images/${p.image}"
                   alt="${p.name}"
                   onerror="this.onerror=null;this.src='${assets}/images/placeholder.png';">
          </a>
          <div class="card-body">
            <h6 class="card-title text-truncate mb-1">${p.name}</h6>
            <div class="fw-bold text-rose fs-6"><fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/>
                          <span class="ms-1">₫</span>
            </div>
          </div>
          <div class="card-footer bg-transparent border-0 pt-0">
            <a class="btn btn-dark w-100" href="${pageContext.request.contextPath}/product?id=${p.id}">Xem chi tiết</a>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</main>
