<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="vi_VN"/>

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>
<c:import url="/inc/_admin_header.jsp"/>
<section class="container py-4">
  <h4 class="mb-3">
    <c:choose>
      <c:when test="${empty product}">Thêm sản phẩm</c:when>
      <c:otherwise>Sửa sản phẩm</c:otherwise>
    </c:choose>
  </h4>

  <c:import url="/inc/_product_form.jsp"/>
</section>

<c:import url="/inc/footer.jsp"/>
