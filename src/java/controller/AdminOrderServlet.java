package controller;

import data.dao.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Order;
import model.User;

@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/orders", "/admin/orders/*"})
public class AdminOrderServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // Chặn user thường vào /admin/*
    User curr = (User) request.getSession().getAttribute("user");
    if (curr == null || !"admin".equalsIgnoreCase(curr.getRole())) {
      response.sendRedirect(request.getContextPath() + "/home");
      return;
    }

    request.setCharacterEncoding("UTF-8");

    // Navbar cần categories
    try { request.setAttribute("categories", Database.getCategoryDao().findAll()); }
    catch (Exception ignored) {}

    try {
      List<Order> orders = Database.getOrderDao().findPaidSummary();
      request.setAttribute("orders", orders);
      request.setAttribute("title", "Đơn hàng");
      request.getRequestDispatcher("/views/orders.jsp").forward(request, response);
    } catch (Exception e) {
      throw new ServletException("Lỗi tải danh sách đơn hàng", e);
    }
  }
}
