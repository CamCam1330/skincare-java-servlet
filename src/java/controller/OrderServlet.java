package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

import data.dao.Database;
import data.dao.OrderDao;        
import model.User;
import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderItem;

@WebServlet(name="OrderServlet", urlPatterns={"/order/*","/orders/*"})
public class OrderServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String path = req.getPathInfo();  // -> "/checkout"
    if (path == null) path = "";
    switch (path) {
      default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private void handleCheckout(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {

  HttpSession session = req.getSession(false);

  // 1) Bắt buộc đăng nhập
  model.User u = (session != null) ? (model.User) session.getAttribute("user") : null;
  if (u == null) {
    resp.sendRedirect(req.getContextPath() + "/login");
    return;
  }

  // 2) Lấy giỏ hàng & kiểm tra rỗng
  model.Cart cart = (session != null) ? (model.Cart) session.getAttribute("cart") : null;
  if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
    resp.sendRedirect(req.getContextPath() + "/cart");
    return;
  }

  // 3) Tính tổng
  int totalItems = 0;
  double total = 0.0;

  // Nếu getItems() là List/Collection<CartItem>:
  for (model.CartItem ci : cart.getItems()) {
    totalItems += ci.getQuantity();
    // nếu CartItem đã có getAmount(): total += ci.getAmount();
    total += ci.getQuantity() * ci.getProduct().getPrice();
  }

  try {
    data.dao.OrderDao dao = data.dao.Database.getOrderDao();

    // 4) Tạo order
    model.Order o = new model.Order();
    o.setUserId(u.getId());
    o.setStatus("PAID");          // demo: coi như đã thanh toán
    o.setItemCount(totalItems);
    o.setTotal(total);

    int orderId = dao.insert(o);

    // 5) Lưu từng item
    for (model.CartItem ci : cart.getItems()) {
      model.OrderItem it = new model.OrderItem();
      it.setOrderId(orderId);
      it.setProductId(ci.getProduct().getId());
      it.setQuantity(ci.getQuantity());
      it.setPrice(ci.getProduct().getPrice()); // đơn giá tại thời điểm đặt
      dao.insertItem(it);
    }

    // 6) Clear cart
    session.removeAttribute("cart");
    session.setAttribute("cartCount", 0);

    // 7) Điều hướng tới trang thành công
    resp.sendRedirect(req.getContextPath() + "/orders/success?oid=" + orderId);

  } catch (Exception ex) {
    throw new ServletException("Checkout error", ex);
  }
}
}