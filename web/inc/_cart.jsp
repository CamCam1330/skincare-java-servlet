<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<fmt:setLocale value="vi_VN"/>

<section class="container py-4">
  <h4 class="mb-4">Giỏ hàng</h4>

  <c:choose>
    <c:when test="${empty sessionScope.cart || empty sessionScope.cart.items}">
      <div class="alert alert-info">
        Giỏ hàng đang trống. <a href="${ctx}/category" class="btn btn-outline-secondary">Tiếp tục mua sắm</a>

      </div>
    </c:when>

    <c:otherwise>
      <div class="row g-4">
        <div class="col-lg-8">
          <div class="card shadow-sm border-0">
            <div class="card-body p-0">
              <div class="table-responsive">
                <table class="table align-middle mb-0">
                  <thead class="table-light">
                    <tr>
                      <th style="width:72px;"></th>
                      <th>Sản phẩm</th>
                      <th class="text-end">Đơn giá</th>
                      <th class="text-center" style="width:140px;">Số lượng</th>
                      <th class="text-end">Thành tiền</th>
                      <th class="text-center" style="width:56px;"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="it" items="${sessionScope.cart.items}">
                      <tr>
                        <td>
                          <img src="${ctx}/assets/images/${it.product.image}"
                               style="width:64px;height:64px;object-fit:cover"
                               onerror="this.onerror=null;this.src='${ctx}/assets/images/placeholder.png'">
                        </td>
                        <td>
                          <div class="fw-semibold text-truncate" style="max-width:280px">
                            <c:out value="${it.product.name}"/>
                          </div>
                          <div class="text-muted small">Mã: <c:out value="${it.product.id}"/></div>
                        </td>
                        <td class="text-end">
                          <span class="text-muted">
                            <fmt:formatNumber value="${it.product.price}" type="number" maxFractionDigits="0"/> ₫
                          </span>
                        </td>
                        <td class="text-center">
                          <form action="${ctx}/cart/update" method="post" class="d-inline-flex align-items-center gap-2">
                            <input type="hidden" name="pid" value="${it.product.id}">
                            <input name="qty" type="number" min="0" step="1"
                                   value="${it.quantity}" class="form-control form-control-sm text-center"
                                   style="width:76px">
                            <button class="btn btn-sm btn-outline-secondary">Cập nhật</button>
                          </form>
                        </td>
                        <td class="text-end">
                          <span class="fw-semibold text-danger">
                            <fmt:formatNumber value="${it.amount}" type="number" maxFractionDigits="0"/> ₫
                          </span>
                        </td>
                        <td class="text-center">
                          <form action="${ctx}/cart/remove" method="post" class="m-0">
                            <input type="hidden" name="pid" value="${it.product.id}">
                            <button class="btn btn-sm btn-outline-danger" title="Xóa">&times;</button>
                          </form>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div class="mt-3 d-flex gap-2">
            <a href="${ctx}/home" class="btn btn-outline-secondary">Tiếp tục mua sắm</a>
            <form action="${ctx}/cart/clear" method="post" class="m-0">
              <button class="btn btn-outline-danger">Xóa giỏ hàng</button>
            </form>
          </div>
        </div>

        <div class="col-lg-4">
          <div class="card shadow-sm border-0">
            <div class="card-body">
              <h5 class="mb-3">Tổng</h5>
              <div class="d-flex justify-content-between mb-2">
                <span>Tổng số lượng</span>
                <strong>${sessionScope.cart.totalItems}</strong>
              </div>
              <div class="d-flex justify-content-between mb-3">
                <span>Tạm tính</span>
                <strong class="text-danger">
                  <fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="number" maxFractionDigits="0"/> ₫
                </strong>
              </div>
              <form action="${pageContext.request.contextPath}/checkout" method="post" class="m-0">
                  <button class="btn btn-rose w-100">Thanh toán</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</section>
