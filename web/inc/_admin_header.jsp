<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="d-flex align-items-center mb-3 admin-head">
  <a href="${ctx}/admin"
     class="btn-back me-2"
     title="Về Admin" aria-label="Về Admin">&larr;</a>

  <h4 class="mb-0 flex-grow-1">
    <c:out value="${empty param.title ? '' : param.title}"/>
  </h4>

  <c:if test="${not empty param.actions}">
    <c:out value="${param.actions}" escapeXml="false"/>
  </c:if>
</div>

