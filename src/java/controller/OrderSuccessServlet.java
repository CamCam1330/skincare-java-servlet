package controller;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import data.dao.Database;

@WebServlet(name="OrderSuccessServlet", urlPatterns={"/orders/success"})
public class OrderSuccessServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try { req.setAttribute("categories", Database.getCategoryDao().findAll()); } catch (Exception ignored){}
    req.setAttribute("title", "Thanh toán thành công");
    req.getRequestDispatcher("/views/order_success.jsp").forward(req, resp);
  }
}
