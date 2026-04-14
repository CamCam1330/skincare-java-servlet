<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="d-flex align-items-center mb-3">
  <a class="btn btn-rose" href="${ctx}/admin/categories/create">+ Thêm danh mục</a>
</div>

<c:if test="${empty cats}">
  <div class="alert alert-info">Chưa có danh mục nào.</div>
</c:if>

<c:if test="${not empty cats}">
  <div class="table-responsive">
    <table class="table align-middle">
      <thead class="table-light">
      <tr>
        <th style="width:120px;">ID</th>
        <th>Tên danh mục</th>
        <th style="width:180px;" class="text-end">Thao tác</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="c" items="${cats}">
        <tr>
          <td>${c.id}</td>
          <td class="fw-medium">${c.name}</td>
          <td class="text-end">
            <a class="btn btn-outline-secondary btn-sm" href="${ctx}/admin/categories/edit?id=${c.id}">Sửa</a>
            <form action="${ctx}/admin/categories/delete" method="post" class="d-inline"
                  onsubmit="return confirm('Xóa danh mục này?');">
              <input type="hidden" name="id" value="${c.id}">
              <button class="btn btn-outline-danger btn-sm">Xóa</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</c:if>
