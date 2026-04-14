<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="hero" value="/assets/images/hero.jpg" />

<section class="hero hero-with-img">
  <div class="hero-bg" style="--hero:url('${hero}')"></div>

  <div class="container position-relative">
    <div class="row align-items-center">
      <div class="col-12 col-lg-7 py-5">
        <h1 class="display-4 fw-bold mb-3">Mỹ phẩm chính hãng</h1>
        <p class="lead mb-4">Skincare &amp; Makeup cho làn da rạng rỡ mỗi ngày.</p>
        <a href="${pageContext.request.contextPath}/category?id=1" class="btn btn-dark btn-lg me-2">Mua ngay</a>
      </div>
    </div>
  </div>
</section>
