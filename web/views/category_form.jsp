<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="vi_VN"/>

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>
<c:import url="/inc/_admin_header.jsp"/>

<section class="container py-4">
    <h3 class="mb-3"><c:out value="${title != null ? title: 'Danh mục'}"/></h3>
    <c:import url="/inc/_category_form.jsp"/>
</section>