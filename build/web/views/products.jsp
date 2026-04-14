<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="vi_VN"/>

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>
<c:import url="/inc/_admin_header.jsp"/>

<section class="container py-4 admin-products">
    <div class="d-flex align-items-center mb-3">
        <h4 class="page-title mb-0">Quản lý sản phẩm</h4>
        <a href="${ctx}/admin/products/create" class="btn btn-rose ms-auto">+ Thêm sản phẩm</a>
    </div>

    <c:if test="${empty products}">
        <div class="alert alert-info">Chưa có sản phẩm nào.</div>
    </c:if>


    <c:if test="${not empty products}">
        <c:import url="/inc/_product.jsp"/>
    </c:if>


