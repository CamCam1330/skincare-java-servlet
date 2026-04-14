
package controller;

import data.dao.Database;
import data.dao.OrderDao;
import data.driver.MySQLDriver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.User;

@WebServlet(name="CheckoutServlet", urlPatterns={"/checkout","/checkout/*"})
public class CheckoutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    // bắt buộc login
    User u = (User) req.getSession().getAttribute("user");
    if (u == null) {
      req.getSession().setAttribute("Login_err", "Vui lòng đăng nhập để thanh toán");
      resp.sendRedirect(req.getContextPath()+"/login");
      return;
    }

    // không có hàng => quay về giỏ
    Cart cart = (Cart) req.getSession().getAttribute("cart");
    if (cart == null || cart.getTotalItems() <= 0) {
      resp.sendRedirect(req.getContextPath()+"/cart");
      return;
    }

    // render trang chọn phương thức + OTP
    try { req.setAttribute("categories", Database.getCategoryDao().findAll()); } catch (Exception ignored){}
    req.setAttribute("title", "Thanh toán");
    req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    HttpSession session = req.getSession();

    User u = (User) session.getAttribute("user");
    if (u == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.getTotalItems() <= 0) {
      resp.sendRedirect(req.getContextPath()+"/cart"); return;
    }

    String step = req.getParameter("step");
    if ("send-otp".equals(step)) {
      // giả lập gửi OTP
      String otp = String.format("%06d", new Random().nextInt(1_000_000));
      session.setAttribute("OTP", otp);
      // Demo: hiển thị OTP ra trang (production thì không)
      req.setAttribute("demoOtp", otp);
      try { req.setAttribute("categories", Database.getCategoryDao().findAll()); } catch (Exception ignored){}
      req.setAttribute("title", "Xác nhận OTP");
      req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
      return;
    }

    // xác nhận + tạo đơn
    String otpClient = req.getParameter("otp");
    String otpServer = (String) session.getAttribute("OTP");
    if (otpServer == null || !otpServer.equals(otpClient)) {
      session.setAttribute("flash", "OTP không đúng. Vui lòng thử lại.");
      resp.sendRedirect(req.getContextPath()+"/checkout");
      return;
    }

    // tạo order + item theo transaction
    Connection con = MySQLDriver.getConnection();
    try {
      try {
        con.setAutoCommit(false);

        OrderDao dao = Database.getOrderDao();
        Order o = new Order();
        o.setUserId(u.getId());
        o.setStatus("PAID"); // đã thanh toán
        int orderId = dao.insert(o);

        for (CartItem ci : cart.getItems()) {
            OrderItem it = new OrderItem();
            it.setOrderId(orderId);
            it.setProductId(ci.getProduct().getId());
            it.setQuantity(ci.getQuantity());
            it.setPrice(ci.getProduct().getPrice());
            dao.insertItem(it);

          try (var st = con.prepareStatement(
              "UPDATE products SET quantity = quantity - ? WHERE id=? AND quantity >= ?")) {
            st.setInt(1, ci.getQuantity());
            st.setInt(2, ci.getProduct().getId());
            st.setInt(3, ci.getQuantity());
            int ok = st.executeUpdate();
            if (ok == 0) throw new SQLException("Không đủ tồn kho cho sản phẩm id="+ci.getProduct().getId());
          }
        }

        con.commit();
        // clear cart + clear OTP
        session.removeAttribute("OTP");
        session.removeAttribute("cart");
        session.setAttribute("flash", "Thanh toán thành công. Đơn hàng đã được tạo!");
        resp.sendRedirect(req.getContextPath()+"/orders/success");
      } catch (Exception ex) {
        con.rollback();
        throw ex;
      } finally {
        con.setAutoCommit(true);
      }
    } catch (Exception e) {
      throw new ServletException("Checkout error", e);
    }
  }
}
