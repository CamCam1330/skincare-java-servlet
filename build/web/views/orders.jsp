<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>

<section class="container py-4">
  <!-- header nhỏ + back -->
  <div class="d-flex align-items-center mb-3 admin-head">
    <a href="${ctx}/admin" class="btn-back me-2" title="Về Admin" aria-label="Về Admin">&larr;</a>
    <h4 class="mb-0">Quản lý đơn hàng</h4>
  </div>

  <c:import url="/inc/_orders.jsp"/>
</section>