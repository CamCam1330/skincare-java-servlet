<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="u" value="${sessionScope.user}" />

<header class="border-bottom bg-white">
  <nav class="navbar navbar-expand-lg navbar-light">
    <div class="container">
      <a class="navbar-brand fw-bold text-rose" href="${ctx}/home">MyPham</a>

      <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
              data-bs-target="#navMain" aria-controls="navMain"
              aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navMain">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <!-- Home -->
          <li class="nav-item">
            <a class="nav-link" href="${ctx}/home">Home</a>
          </li>

          <!-- Category dropdown -->
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="${ctx}/category" id="catDrop"
               role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Category
            </a>
            <ul class="dropdown-menu" aria-labelledby="catDrop">
              <c:forEach var="c" items="${categories}">
                <li><a class="dropdown-item" href="${ctx}/category?id=${c.id}">${c.name}</a></li>
              </c:forEach>
            </ul>
          </li>

          <!-- Login / Hi, user -->
          <c:choose>
              <c:when test="${empty u}">
                  <li class="nav-item">
                      <a class="nav-link" href="${ctx}/login">Login</a>
                  </li>
              </c:when>

              <c:otherwise>
                  <li class="nav-item dropdown">
                      <a class="nav-link dropdown-toggle" href="#" id="userDrop" role="button"
                         data-bs-toggle="dropdown" aria-expanded="false">
                          Hi ${empty u.name ? u.email : u.name}
                      </a>
                      <ul class="dropdown-menu" aria-labelledby="userDrop">
                          <li><a class="dropdown-item" href="${ctx}/profile">Tài khoản</a></li>

                          <c:if test="${u.role eq 'admin'}">
                              <li><a class="dropdown-item" href="${ctx}/admin">Trang quản trị</a></li>
                              </c:if>

                          <li><hr class="dropdown-divider" /></li>
                          <li><a class="dropdown-item" href="${ctx}/logout">Logout</a></li>
                      </ul>
                  </li>
              </c:otherwise>
          </c:choose>

          <!-- Wishlist -->
          <li class="nav-item ms-lg-2">
            <c:choose>
              <c:when test="${empty sessionScope.user}">
                <a class="btn btn-outline-secondary position-relative"
                   href="${ctx}/login?back=${ctx}/wishlist" title="Wishlist">
                  <img src="${ctx}/assets/icon/heart.png" alt="Wishlist" width="18" height="18"
                       class="me-1 align-text-top"/>
                  Wishlist
                </a>
              </c:when>
              <c:otherwise>
                <a class="btn btn-outline-secondary position-relative"
                   href="${ctx}/wishlist" title="Wishlist">
                  <img src="${ctx}/assets/icon/heart.png" alt="Wishlist" width="18" height="18"
                       class="me-1 align-text-top"/>
                  Wishlist
                </a>
              </c:otherwise>
            </c:choose>
          </li>

          <!-- Cart -->
          <li class="nav-item ms-lg-2">
              <c:choose>
                  <c:when test="${empty sessionScope.user}">
                      <a class="btn btn-outline-dark position-relative"
                      href="${ctx}/login?back=${ctx}/cart" title="Cart">
                          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                               viewBox="0 0 16 16" fill="currentColor" class="me-1 align-text-top">
                          <path d="M0 1.5A.5.5 0 0 1 .5 1h1a.5.5 0 0 1 .485.379L2.89 6H14.5a.5.5 0 0 1 .49.598l-1.5 7A.5.5 0 0 1 13 14H4a.5.5 0 0 1-.49-.402L2.01 7H.5a.5.5 0 0 1 0-1h1.31l.84-3.36A1.5 1.5 0 0 1 3.5 1h-3z"/>
                          <path d="M4.5 16a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zm8 0a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3z"/>
                          </svg>
                          Cart
                          <c:set var="count"
                                 value="${sessionScope.cartCount != null ? sessionScope.cartCount :
                                          (sessionScope.cart != null ? sessionScope.cart.totalItems : 0)}"/>
                          <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                              <c:out value="${count}"/><span class="visually-hidden">items</span>
                          </span>
                      </a>  
                  </c:when>
                  <c:otherwise>
                      <a class="btn btn-outline-dark position-relative" href="${ctx}/cart">
                          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                               viewBox="0 0 16 16" fill="currentColor" class="me-1 align-text-top">
                          <path d="M0 1.5A.5.5 0 0 1 .5 1h1a.5.5 0 0 1 .485.379L2.89 6H14.5a.5.5 0 0 1 .49.598l-1.5 7A.5.5 0 0 1 13 14H4a.5.5 0 0 1-.49-.402L2.01 7H.5a.5.5 0 0 1 0-1h1.31l.84-3.36A1.5 1.5 0 0 1 3.5 1h-3z"/>
                          <path d="M4.5 16a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zm8 0a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3z"/>
                          </svg>
                          Cart
                          <c:set var="count"
                                 value="${sessionScope.cartCount != null ? sessionScope.cartCount :
                             (sessionScope.cart != null ? sessionScope.cart.totalItems : 0)}"/>
                          <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                              <c:out value="${count}"/><span class="visually-hidden">items</span>
                          </span>
                      </a>
                  </c:otherwise>
              </c:choose>  
          </li>
        </ul>

        <!-- Search (bên phải) -->
        <form class="d-flex ms-lg-3 search-wrap" action="${ctx}/search" method="get" role="search">
          <input class="form-control me-2" type="search" name="q" placeholder="Tìm sản phẩm..." aria-label="Search">
          <button class="btn btn-rose" type="submit">Tìm</button>
        </form>
      </div><!-- /collapse -->
    </div><!-- /container -->
  </nav>
</header>
