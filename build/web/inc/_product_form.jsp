<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="isEdit" value="${not empty product}"/>
<c:set var="actionUrl"
       value="${isEdit ? (ctx.concat('/admin/products/update')) : (ctx.concat('/admin/products/create'))}"/>

<form method="post" action="${ctx}/admin/products/${empty product ? 'create' : 'update'}">
  <c:if test="${not empty product}">
    <input type="hidden" name="id" value="${product.id}"/>
  </c:if>

    <div class="mb-3">
      <label class="form-label">Tên sản phẩm</label>
      <input name="name" class="form-control" required
             value="${isEdit ? product.name : ''}">
    </div>

    <div class="mb-3">
      <label class="form-label">Ảnh (file name)</label>
      <input name="image" class="form-control"
             value="${isEdit ? product.image : ''}">
      <div class="form-text">Đặt file vào <code>/assets/images/</code>.</div>
    </div>

    <div class="row g-3">
      <div class="col-md-4">
        <label class="form-label">Giá (₫)</label>
        <input name="price" type="number" min="0" step="1000" class="form-control"
               value="${isEdit ? product.price : ''}">
      </div>

    <div class="col-md-4">
      <label class="form-label">Số lượng</label>
      <input type="number" min="0" class="form-control" name="quantity"
              value="<c:out value='${product != null ? product.quantity : 0}'/>">
    </div>  
    
      <div class="col-md-4">
        <label class="form-label">Danh mục (id)</label>
        <input name="categoryId" type="number" min="0" class="form-control"
               value="${isEdit ? product.idCategory : ''}">
      </div>

      <div class="col-md-4">
        <label class="form-label">Thương hiệu (id)</label>
        <input name="brandId" type="number" min="0" class="form-control"
               value="${isEdit ? product.idBrand : ''}">
      </div>
    </div>

    <div class="form-check mt-3">
      <input class="form-check-input" type="checkbox" id="status"
             name="status" <c:if test="${isEdit ? product.status : true}">checked</c:if>>
      <label class="form-check-label" for="status">Hiển thị</label>
    </div>
  </div>

  <div class="card-footer bg-transparent border-0">
    <button class="btn btn-rose">Lưu</button>
    <a class="btn btn-outline-secondary" href="${ctx}/admin/products">Hủy</a>
  </div>
</form>
