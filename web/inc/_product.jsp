<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="table-responsive">
    <table class="table table-hover align-middle admin-table">
        <thead>
            <tr>
                <th class="col-name">Tên</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th class="col-category">Danh mục</th>
                <th class="col-brand">Thương hiệu</th>
                <th class="text-end">Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td class="col-name">
                        <div class="d-flex align-items-center gap-3">
                            <img class="thumb" src="${ctx}/assets/images/${p.image}"
                                 onerror="this.src='${ctx}/assets/images/placeholder.png'">
                            <div class="name">${p.name}</div>
                        </div>
                    </td>

                <td class="price">
                    <fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/><span class="ms-1">đ</span>
                </td>

                <td class="qty">${p.quantity}</td>
                <td class="col-category">${p.idCategory}</td>
                <td class="col-brand">${p.idBrand}</td>

                <td class="text-end actions">
                    <a class="btn btn-outline-secondary me-2" href="${ctx}/admin/products/edit?id=${p.id}">Sửa</a>
                    <form action="${ctx}/admin/products/delete" method="post" class="d-inline"
                          onsubmit="return confirm('Xoá sản phẩm này?');">
                        <input type="hidden" name="id" value="${p.id}"/>
                        <button class="btn btn-outline-danger">Xoá</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </tbody>
  </table>
</div>
