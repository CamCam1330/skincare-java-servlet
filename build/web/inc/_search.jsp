<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<section class="container py-4">
    <h4 class="mb-3">Kết quả cho: <span class="text-rose"><c:out value="${q}"/></span></h4>

    <c:if test="${not empty notfound}">
        <div class="alert alert-warning"><c:out value="${notfound}"/></div>
    </c:if>

    <c:if test="${not empty products}">
        <c:import url="/inc/_category.jsp"/>
    </c:if>
</section>