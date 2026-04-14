<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="adminProductsUrl"   value="/admin/products"/>
<c:url var="adminCategoriesUrl" value="/admin/categories"/>
<c:url var="adminOrdersUrl" value="/admin/orders"/>
<c:url var="adminCustomersUrl" value="/admin/customers"/>

<section class="container py-4">
  <h4 class="mb-3">Admin Dashboard</h4>
  <div class="row g-3">
    <div class="col-md-4">
      <div class="card border-0 shadow-sm">
        <div class="card-body">
          <h6 class="fw-bold">Sản phẩm & Danh mục</h6>
          <a class="btn btn-rose me-2" href="${adminProductsUrl}">Quản lý sản phẩm</a>
          <a class="btn btn-outline-secondary" href="${adminCategoriesUrl}">Quản lý danh mục</a>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card border-0 shadow-sm">
        <div class="card-body">
          <h6 class="fw-bold">Đơn hàng</h6>
          <a class="btn btn-outline-dark" href="${adminOrdersUrl}">Xem danh sách đơn hàng</a>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card border-0 shadow-sm">
        <div class="card-body">
          <h6 class="fw-bold">Khách hàng</h6>
          <a class="btn btn-outline-dark" href="${adminCustomersUrl}">Xem danh sách khách hàng</a>
        </div>
      </div>
    </div>
  </div>
</section>