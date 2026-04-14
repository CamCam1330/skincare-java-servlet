<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="isEdit" value="${not empty customer}"/>

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>

<section class="container py-4">
  <div class="d-flex align-items-center mb-3">
    <a class="btn btn-outline-secondary btn-circle me-3" href="${ctx}/admin">←</a>
    <h4 class="mb-0"><c:out value="${isEdit ? 'Sửa khách hàng' : 'Thêm khách hàng'}"/></h4>
  </div>

  <form method="post" action="${ctx}/admin/customers/${isEdit ? 'update' : 'create'}" class="card p-4 border-0 shadow-sm">
    <c:if test="${isEdit}">
      <input type="hidden" name="id" value="${customer.id}"/>
    </c:if>

    <div class="row g-3">
      <div class="col-md-6">
        <label class="form-label">Tên</label>
        <input name="name" class="form-control" required
               value="${isEdit ? customer.name : ''}">
      </div>
      <div class="col-md-6">
        <label class="form-label">Email</label>
        <input name="email" type="email" class="form-control" required
               value="${isEdit ? customer.email : ''}">
      </div>

      <div class="col-md-6">
        <label class="form-label">Điện thoại</label>
        <input name="phone" class="form-control"
               value="${isEdit ? customer.phone : ''}">
      </div>
      <div class="col-md-6">
        <label class="form-label">Mật khẩu</label>
        <input name="password" type="password" class="form-control"
               placeholder="${isEdit ? 'Để trống nếu không đổi' : ''}">
      </div>

      <div class="col-md-6">
        <label class="form-label">Role</label>
        <select name="role" class="form-select">
          <option value=""         ${isEdit && empty customer.role ? 'selected' : ''}>user</option>
          <option value="customer" ${isEdit && customer.role=='customer' ? 'selected' : ''}>customer</option>
          <option value="admin"    ${isEdit && customer.role=='admin' ? 'selected' : ''}>admin</option>
        </select>
      </div>
    </div>

    <div class="mt-4">
      <button class="btn btn-rose">Lưu</button>
      <a class="btn btn-outline-secondary" href="${ctx}/admin/customers">Hủy</a>
    </div>
  </form>
</section>

<c:import url="/inc/footer.jsp"/>
