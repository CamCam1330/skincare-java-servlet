<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>

<c:choose>
  <c:when test="${empty orders}">
    <div class="alert alert-info">Chưa có đơn hàng nào.</div>
  </c:when>
  <c:otherwise>
    <div class="table-responsive">
      <table class="table align-middle">
        <thead class="table-light">
          <tr>
            <th>#</th>
            <th>Khách hàng</th>
            <th>SL SP</th>
            <th>Tổng tiền</th>
            <th>Ngày</th>
            <th>Trạng thái</th>
            <th class="text-end">Thao tác</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="o" items="${orders}">
          <tr>
            <td class="fw-semibold">#${o.id}</td>
            <td><c:out value="${o.userName}"/></td>
            <td>${o.itemCount}</td>
            <td class="text-rose fw-semibold">
              <span>
               <fmt:formatNumber value="${o.total}" type="number" maxFractionDigits="0"/> ₫ 
              </span>
            </td>
            <td>
              <fmt:formatDate value="${o.createdAtDate}" pattern="dd/MM/yyyy HH:mm" type="both"/>
            </td>
            <td>
              <span class="badge bg-success">PAID</span>
            </td>
            <td class="text-end">
              <!-- ?? sau làm chi ti?t -->
              <a class="btn btn-outline-dark btn-sm disabled" href="#">Xem - TODO</a>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </c:otherwise>
</c:choose>
