<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:url var="hero" value="/assets/images/hero.jpg" />

<section class="register-section">
  <div class="login-decor"></div>

  <div class="container-xl py-5">
    <div class="register-frame mx-auto">
      <div class="card register-card border-0 rounded-4 overflow-hidden shadow-lg">
        <div class="row g-0">

            <div class="col-lg-5 col-xl-6 d-none d-lg-block">
                <div class="register-side"
                     style="background-image:
                   linear-gradient(120deg, rgba(255,228,234,.92), rgba(255,255,255,.35)),
                   url('${hero}');">
                    <div class="register-side__content">
                        <span class="badge text-bg-light mb-3">MyPham</span>
                        <h4 class="fw-bold mb-3">Ưu đãi độc quyền cho thành viên</h4>
                        <ul class="list-unstyled text-muted small mb-0">
                            <li class="mb-2">• Tích điểm &amp; nhận quà sinh nhật</li>
                            <li class="mb-2">• Deal hời mỗi tuần</li>
                            <li>• Theo dõi đơn &amp; lịch sử mua hàng</li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-lg-7 col-xl-6 bg-white">
                <div class="p-4 p-lg-5">
                <h3 class="fw-bold mb-1">Đăng ký</h3>
                <p class="text-muted mb-4">Tạo tài khoản để mua sắm dễ dàng hơn ✨</p>

                <!-- Email/Phone đã tồn tại -->
                <c:if test="${not empty sessionScope.exist_user}">
                  <div class="alert alert-danger rounded-3 py-2">
                    ${sessionScope.exist_user}
                  </div>
                  <c:remove var="exist_user" scope="session"/>
                </c:if>

                <form action="${ctx}/register" method="post" autocomplete="on">
                  <!-- Họ và tên -->
                  <div class="mb-3">
                    <label for="name" class="form-label">Họ và tên</label>
                    <div class="input-group input-group-lg login-input field">
                      <span class="input-group-text">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                          <path d="M12 12a5 5 0 1 0-5-5 5 5 0 0 0 5 5Zm0 2c-4.42 0-8 2.24-8 5v1h16v-1c0-2.76-3.58-5-8-5Z"/>
                        </svg>
                      </span>
                      <input
                        type="text"
                        class="form-control"
                        id="name"
                        name="name"
                        maxlength="60"
                        value="${not empty sessionScope.reg_name ? sessionScope.reg_name : param.name}"
                        required
                      />
                    </div>
                  </div>

                  <div class="row g-3">
                    <!-- Email -->
                    <div class="col-md-6">
                      <label for="email" class="form-label">Email</label>
                      <div class="input-group input-group-lg login-input field">
                        <span class="input-group-text">
                          <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                            <path d="M20 4H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V6a2 2 0 0 0-2-2Zm0 2v.01L12 13 4 6.01V6h16ZM4 18V8.24l7.4 6.29a1 1 0 0 0 1.2 0L20 8.24V18H4Z"/>
                          </svg>
                        </span>
                        <input
                          type="email"
                          class="form-control ${not empty sessionScope.err_email ? 'is-invalid' : ''}"
                          id="email"
                          name="email"
                          maxlength="100"
                          value="${not empty sessionScope.reg_email ? sessionScope.reg_email : param.email}"
                          required
                        />
                      </div>
                      <c:if test="${not empty sessionScope.err_email}">
                        <div class="invalid-feedback d-block">${sessionScope.err_email}</div>
                        <c:remove var="err_email" scope="session"/>
                      </c:if>
                    </div>

                    <!-- Phone -->
                    <div class="col-md-6">
                      <label for="phone" class="form-label">Số điện thoại</label>
                      <div class="input-group input-group-lg login-input field">
                        <span class="input-group-text">
                          <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                            <path d="M6.62 10.79a15.1 15.1 0 0 0 6.59 6.59l2.2-2.2a1 1 0 0 1 1.01-.24 11.36 11.36 0 0 0 3.56.57 1 1 0 0 1 1 1V20a1 1 0 0 1-1 1A17 17 0 0 1 3 7a1 1 0 0 1 1-1h3.49a1 1 0 0 1 1 1 11.36 11.36 0 0 0 .57 3.56 1 1 0 0 1-.24 1.01l-2.2 2.2Z"/>
                          </svg>
                        </span>
                        <input
                          type="tel"
                          class="form-control ${not empty sessionScope.err_phone ? 'is-invalid' : ''}"
                          id="phone"
                          name="phone"
                          inputmode="tel"
                          pattern="0\d{9}" 
                          maxlength="10"
                          value="${not empty sessionScope.reg_phone ? sessionScope.reg_phone : param.phone}"
                          required
                        />
                      </div>
                      <c:if test="${not empty sessionScope.err_phone}">
                        <div class="invalid-feedback d-block">${sessionScope.err_phone}</div>
                        <c:remove var="err_phone" scope="session"/>
                      </c:if>
                    </div>
                  </div>

                  <div class="row g-3 mt-1">
                    <!-- Password -->
                    <div class="col-md-6">
                      <label for="password" class="form-label">Mật khẩu</label>
                      <div class="input-group input-group-lg login-input field">
                        <span class="input-group-text">
                          <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                            <path d="M17 8h-1V6a4 4 0 1 0-8 0v2H7a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2v-8a2 2 0 0 0-2-2Zm-6 0V6a3 3 0 1 1 6 0v2h-6Z"/>
                          </svg>
                        </span>
                        <input
                          type="password"
                          class="form-control"
                          id="password"
                          name="password"
                          maxlength="64"
                          required
                        />
                      </div>
                    </div>

                    <!-- Repassword -->
                    <div class="col-md-6">
                      <label for="repassword" class="form-label">Nhập lại mật khẩu</label>
                      <div class="input-group input-group-lg login-input field">
                        <span class="input-group-text">
                          <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                            <path d="M17 8h-1V6a4 4 0 1 0-8 0v2H7a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2v-8a2 2 0 0 0-2-2Zm-6 0V6a3 3 0 1 1 6 0v2h-6Z"/>
                          </svg>
                        </span>
                        <input
                          type="password"
                          class="form-control ${not empty sessionScope.err_repassword ? 'is-invalid' : ''}"
                          id="repassword"
                          name="repassword"
                          maxlength="64"
                          required
                        />
                      </div>
                      <c:if test="${not empty sessionScope.err_repassword}">
                        <div class="invalid-feedback d-block">${sessionScope.err_repassword}</div>
                        <c:remove var="err_repassword" scope="session"/>
                      </c:if>
                    </div>
                  </div>

                  <button type="submit" class="btn btn-rose btn-lg w-100 shadow-sm mt-4">
                    Tạo tài khoản
                  </button>

                  <p class="text-center text-muted mt-4 mb-0">
                    Đã có tài khoản?
                    <a class="text-decoration-none" href="${ctx}/login">Đăng nhập</a>
                  </p>
                </form>
              </div>
            </div>
            <!-- /Form column -->

          </div>
        </div>
      </div>
    </div>
  

</section>
