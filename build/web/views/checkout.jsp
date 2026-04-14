<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- Nạp header + CSS -->
<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>

<main class="checkout-wrap">
  <div class="otp-card">
    <h1 class="otp-title">Thanh toán</h1>

    <c:if test="${not empty sessionScope.flash}">
      <div class="alert alert-warning">${sessionScope.flash}</div>
      <c:remove var="flash" scope="session"/>
    </c:if>

    <!-- BƯỚC 1: Chưa gửi OTP -->
    <c:if test="${empty demoOtp}">
      <div class="card p-3 mb-3">
        <h5 class="mb-2">Chọn phương thức thanh toán</h5>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="pm" id="pm_card" checked>
          <label class="form-check-label" for="pm_card">Thẻ (demo)</label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="pm" id="pm_cod" disabled>
          <label class="form-check-label" for="pm_cod">COD (đang khóa)</label>
        </div>
      </div>

      <form method="post" action="${ctx}/checkout">
        <input type="hidden" name="step" value="send-otp">
        <button class="btn btn-rose">Gửi OTP</button>
      </form>
    </c:if>

    <!-- BƯỚC 2: Đã gửi OTP -->
    <c:if test="${not empty demoOtp}">
      <p class="otp-note">
        Mã OTP (demo): <span class="otp-code">${demoOtp}</span>
        <span> — (môi trường thật sẽ không hiển thị)</span>
      </p>

      <form method="post" action="${ctx}/checkout" class="otp-form" autocomplete="off">
        <input type="hidden" name="step" value="confirm"/>

        <label for="otp" class="otp-label">Nhập OTP</label>
        <input id="otp" name="otp"
               type="text" inputmode="numeric" maxlength="6"
               pattern="[0-9]{6}" title="Nhập đúng 6 chữ số"
               class="otp-input"
               oninput="this.value=this.value.replace(/\D/g,'').slice(0,6)" required />

        <div class="otp-actions">
          <a class="btn-light" href="${ctx}/cart">← Quay lại giỏ</a>
          <button id="payBtn" class="btn-rose">Xác nhận &amp; Thanh toán</button>
        </div>

        <p class="otp-help">
          Không nhận được OTP?
          <button class="link" type="submit" name="step" value="send-otp">Gửi lại OTP</button>
        </p>
      </form>
    </c:if>
  </div>
</main>

<c:import url="/inc/footer.jsp"/>

<script>
(function(){
  const otp = document.getElementById('otp');
  const btn = document.getElementById('payBtn');
  if (otp && btn) {
    const toggle = () => btn.disabled = (otp.value.replace(/\D/g,'').length !== 6);
    otp.addEventListener('input', toggle);
    toggle();
  }
})();
</script>
