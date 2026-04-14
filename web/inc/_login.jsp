<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<section class="login-section">
  <div class="login-decor"></div>

  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-5">
        <div class="card login-card border-0 rounded-4 shadow-lg">
          <div class="card-body p-4 p-lg-5">
            <h3 class="fw-bold text-center mb-1">Đăng nhập</h3>
            <p class="text-center text-muted mb-4">Chào mừng bạn trở lại ✨</p>

            <c:if test="${not empty sessionScope.Login_err}">
              <div class="alert alert-danger rounded-3 py-2">
                ${sessionScope.Login_err}
              </div>
              <c:remove var="Login_err" scope="session"/>
            </c:if>

            <form action="${ctx}/login" method="post" autocomplete="on">
              <div class="mb-3">
                <label for="emailphone" class="form-label">Email hoặc số điện thoại</label>
                <div class="input-group input-group-lg login-input">
                  <span class="input-group-text">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                      <path d="M20 4H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V6a2 2 0 0 0-2-2Zm0 2v.01L12 13 4 6.01V6h16ZM4 18V8.24l7.4 6.29a1 1 0 0 0 1.2 0L20 8.24V18H4Z"/>
                    </svg>
                  </span>
                  <input type="text" class="form-control"
                         id="emailphone" name="emailphone" autofocus required
                         value="${not empty param.emailphone ? param.emailphone : sessionScope.lastLoginId}"/>
                </div>
              </div>

              <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu</label>
                <div class="input-group input-group-lg login-input">
                  <span class="input-group-text">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                      <path d="M17 8h-1V6a4 4 0 1 0-8 0v2H7a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2v-8a2 2 0 0 0-2-2Zm-6 0V6a3 3 0 1 1 6 0v2h-6Z"/>
                    </svg>
                  </span>
                  <input type="password" class="form-control" id="password" name="password" required/>
                </div>
              </div>

              <div class="d-flex justify-content-between align-items-center mb-4">
                <div class="form-check">
                  <input class="form-check-input" type="checkbox" id="remember" name="remember">
                  <label class="form-check-label" for="remember">Ghi nhớ</label>
                </div>
                <a class="small text-decoration-none" href="${ctx}/forgot">Quên mật khẩu?</a>
              </div>

              <button type="submit" class="btn btn-rose btn-lg w-100 shadow-sm">Đăng nhập</button>

              <p class="text-center text-muted mt-4 mb-0">
                Chưa có tài khoản? <a class="text-decoration-none" href="${ctx}/register">Đăng ký ngay</a>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
