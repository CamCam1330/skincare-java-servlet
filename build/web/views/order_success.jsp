<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>

<section class="container py-5 text-center">
  <h3 class="mb-3">Thanh toán thành công 🎉</h3>
  <p>Đơn hàng của bạn đã được tạo. Bạn có thể xem lịch sử đơn hàng trong tài khoản.</p>
  <a class="btn btn-rose mt-2" href="${ctx}/home">Về trang chủ</a>
  <a class="btn btn-outline-dark mt-2 ms-2" href="${ctx}/admin/orders">Xem danh sách đơn (Admin)</a>
</section>

<c:import url="/inc/footer.jsp"/>
