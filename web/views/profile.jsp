<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:import url="/inc/header.jsp"/>
<c:import url="/inc/navbar.jsp"/>

<main class="profile-wrap">
    <!-- back -->
    <a href="${ctx}/home" class="back-btn-sm" aria-label="Về trang chủ">
        <span aria-hidden="true">←</span>
    </a>

    <h1 class="profile-title">Hồ sơ cá nhân</h1>

    <!-- flash messages -->
    <c:if test="${not empty sessionScope.profile_err}">
        <div class="alert alert-warning shadow-sm mb-3">
            ${sessionScope.profile_err}
        </div>
        <c:remove var="profile_err" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.profile_msg}">
        <div class="alert alert-success shadow-sm mb-3">
            ${sessionScope.profile_msg}
        </div>
        <c:remove var="profile_msg" scope="session"/>
    </c:if>

    <div class="profile-grid">
        <!-- CẬP NHẬT THÔNG TIN -->
        <section class="profile-card">
            <h2 class="card-title">Thông tin cơ bản</h2>
            <form method="post" action="${ctx}/profile" novalidate>
                <input type="hidden" name="action" value="profile"/>

                <div class="mb-3">
                    <label class="form-label">Họ và tên</label>
                    <input name="name" class="form-control" required
                           minlength="2" maxlength="60"
                           value="${sessionScope.user.name}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input name="email" type="email" class="form-control" required
                           value="${sessionScope.user.email}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Số điện thoại</label>
                    <input name="phone" class="form-control"
                           inputmode="numeric" pattern="^0\\d{9}$"
                           title="Số điện thoại phải có 10 số và bắt đầu bằng 0"
                           required value="${sessionScope.user.phone}">
                </div>

                <div class="d-flex gap-2">
                    <button class="btn btn-rose">Lưu thay đổi</button>
                    <a class="btn btn-outline-secondary" href="${ctx}/home">Hủy</a>
                </div>
            </form>
        </section>

        <!-- ĐỔI MẬT KHẨU -->
        <section class="profile-card">
            <h2 class="card-title">Đổi mật khẩu</h2>
            <form method="post" action="${ctx}/profile" autocomplete="off" id="pwdForm">
                <input type="hidden" name="action" value="password"/>

                <div class="mb-3">
                    <label class="form-label">Mật khẩu hiện tại</label>
                    <div class="password-field">
                        <input id="oldPassword" name="oldPassword" type="password" class="form-control" required>
                        <button class="toggle" type="button" data-target="oldPassword">👁</button>
                    </div>
                </div>

                <div class="mb-3">

                    <label class="form-label">Mật khẩu mới</label>
                    <div class="password-field">
                        <input id="newPassword" name="newPassword" type="password"
                               class="form-control" minlength="6" required>
                        <button class="toggle" type="button" data-target="newPassword">👁</button>
                    </div>
                    <div class="form-text">Ít nhất 6 ký tự.</div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Nhập lại mật khẩu mới</label>
                    <div class="password-field">
                        <input id="retypePassword" name="retypePassword" type="password"
                               class="form-control" minlength="6" required>
                        <button class="toggle" type="button" data-target="retypePassword">👁</button>
                    </div>
                </div>

                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-rose" id="btnPwd">Đổi mật khẩu</button>
                    <a class="btn btn-outline-secondary" href="${ctx}/profile">Hủy</a>
                </div>
            </form>
        </section>
    </div>
</main>

<c:import url="/inc/footer.jsp"/>

<!-- JS nhỏ: toggle mắt & check khớp mật khẩu -->
<script>
    // bật/tắt hiện mật khẩu
    document.querySelectorAll('.password-field .toggle').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = btn.getAttribute('data-target');
            const input = document.getElementById(id);
            input.type = input.type === 'password' ? 'text' : 'password';
        });
    });

    // kiểm tra 2 mật khẩu khớp nhau trước khi submit
    const pwdForm = document.getElementById('pwdForm');
    const newPwd = document.getElementById('newPassword');
    const retype = document.getElementById('retypePassword');
    const btnPwd = document.getElementById('btnPwd');

    const validatePwd = () => {
        const ok = newPwd.value.length >= 6 && newPwd.value === retype.value;
        btnPwd.disabled = !ok;
    };
    newPwd.addEventListener('input', validatePwd);
    retype.addEventListener('input', validatePwd);
    validatePwd();
</script>