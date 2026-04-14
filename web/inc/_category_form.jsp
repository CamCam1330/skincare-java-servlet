<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:set var="isEdit" value="${not empty category}"/>

<form method="post" action="${ctx}/admin/categories/${isEdit ? 'update' : 'create'}" class="card border-0 shadow-sm">
  <div class="card-body">
    <c:if test="${isEdit}">
      <input type="hidden" name="id" value="${category.id}" />
    </c:if>

    <div class="mb-3">
      <label class="form-label">Tên danh mục</label>
      <input name="name" class="form-control" required
             value="${isEdit ? category.name : ''}">
    </div>
  </div>

  <div class="card-footer bg-transparent border-0">
    <button class="btn btn-rose">Lưu</button>
    <a class="btn btn-outline-secondary" href="${ctx}/admin/categories">Hủy</a>
  </div>
</form>
