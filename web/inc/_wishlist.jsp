<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<fmt:setLocale value="vi_VN"/>

<section class="container py-4">
  <h4 class="mb-3">Yêu thích</h4>

  <c:if test="${empty wishProducts}">
    <div class="alert alert-info">Bạn chưa yêu thích sản phẩm nào.</div>
  </c:if>

  <div class="row g-4">
    <c:forEach var="p" items="${wishProducts}">
      <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
        <div class="card h-100 product-card shadow-sm border-0">
          <a href="${ctx}/product?id=${p.id}" class="d-block">
            <img class="card-img-top"
                 src="${ctx}/assets/images/${p.image}"
                 onerror="this.src='${ctx}/assets/images/placeholder.png'">
          </a>
          <div class="card-body d-flex flex-column">
            <a class="text-dark text-decoration-none" href="${ctx}/product?id=${p.id}">
              <div class="fw-semibold text-truncate mb-2">${p.name}</div>
            </a>

            <div class="mt-auto d-flex align-items-center justify-content-between">
              <div class="text-danger fw-bold">
                <fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/> 
                <span class="ms-1">&#8363;</span> <!-- ? -->
              </div>
              <div class="d-flex gap-2">
                  <form action="${ctx}/wishlist/remove" method="post" class="m-0">
                      <input type="hidden" name="pid" value="${p.id}">
                      <button class="btn btn-outline-secondary btn-icon">&times;</button>
                  </form>
                <form action="${ctx}/cart/add" method="post" class="m-0">
                  <input type="hidden" name="pid" value="${p.id}">
                  <input type="hidden" name="qty" value="1">
                  <button class="btn btn-outline-dark btn-icon">
                    <img src="${ctx}/assets/icon/cart.png" width="16" height="16" alt="">
                  </button>
                </form>
              </div>
            </div>

          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</section>