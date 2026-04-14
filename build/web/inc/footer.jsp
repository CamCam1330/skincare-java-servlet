<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<footer class="bg-light mt-5 border-top">
  <div class="container py-4">
    <div class="row g-3">
      <div class="col-md-6">
        <div class="fw-semibold mb-2">MyPham</div>
        <div class="text-muted small">© 2025 MyPham. All rights reserved.</div>
      </div>
      <div class="col-md-6">
        <form class="d-flex justify-content-md-end" method="post" action="#">
          <input class="form-control w-auto me-2" type="email" name="email" placeholder="Nhận tin khuyến mãi" required>
          <button type="submit" class="btn btn-dark">Đăng ký</button>
        </form>
      </div>
    </div>
  </div>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>
