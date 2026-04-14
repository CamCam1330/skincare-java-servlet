<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>

<section class="container py-4">
  <div class="d-flex align-items-center mb-3">
    <a class="btn btn-outline-secondary btn-circle me-3" href="${ctx}/admin" aria-label="Back">←</a>
    <h4 class="mb-0">Quản lý khách hàng</h4>
  </div>

  <c:if test="${empty customers}">
    <div class="alert alert-info">Chưa có khách hàng.</div>
  </c:if>

  <c:if test="${not empty customers}">
    <div class="table-responsive">
      <table class="table align-middle">
        <thead>
          <tr>
            <th>ID</th><th>Tên</th><th>Email</th><th>Điện thoại</th><th>Role</th><th class="text-end">Thao tác</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${customers}">
          <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td>${u.phone}</td>
            <td>
              <form class="d-inline" method="post" action="${ctx}/admin/customers/set-role">
                <input type="hidden" name="id" value="${u.id}">
                <select name="role" class="form-select form-select-sm d-inline w-auto"
                        onchange="this.form.submit()">
                  <option value=""        ${empty u.role ? 'selected' : ''}>—</option>
                  <option value="customer" ${u.role=='customer' ? 'selected' : ''}>customer</option>
                  <option value="admin"    ${u.role=='admin' ? 'selected' : ''}>admin</option>
                </select>
              </form>
            </td>
            <td class="text-end">
              <a class="btn btn-outline-secondary btn-sm" href="${ctx}/admin/customers/edit?id=${u.id}">Sửa</a>
              <form class="d-inline" method="post" action="${ctx}/admin/customers/delete"
                    onsubmit="return confirm('Xóa khách hàng này?')">
                <input type="hidden" name="id" value="${u.id}">
                <button class="btn btn-outline-danger btn-sm">Xóa</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </c:if>
</section>

<c:import url="/inc/footer.jsp"/>
