<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<fmt:setLocale value="vi_VN"/>

<section class="container py-4">

    <div class="d-flex align-items-center justify-content-between mb-3">
        <h4 class="mb-0">
            <c:out value="${currentCategory != null ? currentCategory.name : 'Danh mục'}"/>
        </h4>
        <a class="text-decoration-none" href="${ctx}/home">Trang chủ</a>
    </div>

    <c:if test="${empty products}">
        <div class="alert alert-info">Chưa có sản phẩm trong danh mục này.</div>
    </c:if>

    <div class="row g-4">
        <c:forEach var="p" items="${products}">
            <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
                <div class="card h-100 product-card shadow-sm border-0 position-relative">

                    <div class="media-wrap">
                        <a href="${ctx}/product?id=${p.id}" class="d-block">
                            <img class="card-img-top"
                                 src="${ctx}/assets/images/${p.image}"
                                 alt="${p.name}"
                                 style="aspect-ratio:1/1;object-fit:cover"
                                 onerror="this.onerror=null;this.src='${ctx}/assets/images/placeholder.png'">
                        </a>

                        <div class="hover-buy">
                            <c:choose>
                                <c:when test="${user==null}">
                                    <a class="btn btn-rose btn-buy px-4"
                                       href="${ctx}/login?back=${ctx}/cart">
                                        <img src="${ctx}/assets/icon/cart.png" width="16" height="16" class="me-1" alt="">
                                        Mua
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <form action="${ctx}/cart/add" method="post" class="m-0">
                                        <input type="hidden" name="pid" value="${p.id}">
                                        <input type="hidden" name="qty" value="1">
                                        <input type="hidden" name="to"  value="cart">
                                        <button class="btn btn-rose btn-buy px-4" type="submit">
                                            <img src="${ctx}/assets/icon/cart.png" width="16" height="16" class="me-1" alt="">
                                            Mua
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="card-body d-flex flex-column">
                        <a class="text-dark text-decoration-none" href="${ctx}/product?id=${p.id}">
                            <div class="fw-semibold text-truncate mb-2">${p.name}</div>
                        </a>

                        <div class="mt-auto d-flex align-items-center justify-content-between">
                            <div class="text-danger fw-bold">
                                <fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/>
                                <span class="ms-1">₫</span>
                            </div>

                            <div class="d-flex align-items-center gap-2">
                                <c:choose>
                                    <c:when test="${user == null}">
                                        <a class="btn btn-outline-secondary btn-icon"
                                           href="${ctx}/login?back=${pageContext.request.requestURI}">
                                            <img src="${ctx}/assets/icon/heart.png" width="16" height="16" alt="♥">
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${ctx}/wishlist" method="post" class="m-0">
                                            <input type="hidden" name="pid" value="${p.id}">
                                            <button class="btn btn-outline-secondary btn-icon" title="Yêu thích" type="submit">
                                                <img src="${ctx}/assets/icon/heart.png" width="16" height="16" alt="♥">
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>




                                <c:choose>
                                    <c:when test="${user == null}">
                                        <a class="btn btn-outline-dark btn-icon"
                                           href="${pageContext.request.contextPath}/login?back=${pageContext.request.requestURI}${empty pageContext.request.queryString ? '' : '?'.concat(pageContext.request.queryString)}"
                                           title="Đăng nhập để thêm vào giỏ">
                                            <img src="${pageContext.request.contextPath}/assets/icon/cart.png" width="16" height="16" alt="">
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${ctx}/cart/add" method="post" class="m-0">
                                            <input type="hidden" name="pid" value="${p.id}">
                                            <input type="hidden" name="qty" value="1">
                                            <button class="btn btn-outline-dark btn-icon" title="Thêm vào giỏ" type="submit">
                                                <img src="${ctx}/assets/icon/cart.png" width="16" height="16" alt="">
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </c:forEach>
    </div>
</section>
