package controller;

import data.dao.Database;
import data.dao.CategoryDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Category;

@WebServlet(name="AdminCategoryServlet",
        urlPatterns = {"/admin/categories", "/admin/categories/*"})
public class AdminCategoryServlet extends HttpServlet {

  private static String nvl(String s){ return s == null ? "" : s.trim(); }
  private static int i(String s){
    try { return Integer.parseInt(nvl(s)); } catch (Exception e){ return 0; }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    // Navbar cần categories
    try { request.setAttribute("categories", Database.getCategoryDao().findAll()); } catch (Exception ignored) {}

    String path = request.getPathInfo(); // null / "", "/create", "/edit"
    if (path == null) path = "";

    try {
      CategoryDao dao = Database.getCategoryDao();

      switch (path) {
        case "/create" -> {
          request.setAttribute("category", null);
          request.setAttribute("title", "Thêm danh mục");
          request.getRequestDispatcher("/views/category_form.jsp").forward(request, response);
        }
        case "/edit" -> {
          int id = i(request.getParameter("id"));
          Category c = dao.findById(id);
          if (c == null){
            response.sendRedirect(request.getContextPath()+"/admin/categories");
            return;
          }
          request.setAttribute("category", c);
          request.setAttribute("title", "Sửa danh mục");
          request.getRequestDispatcher("/views/category_form.jsp").forward(request, response);
        }
        default -> {
          List<Category> list = dao.findAll();
          request.setAttribute("cats", list);
          request.setAttribute("title", "Quản lý danh mục");
          request.getRequestDispatcher("/views/admincategories.jsp").forward(request, response);
        }
      }
    } catch (Exception e){
      throw new ServletException("Lỗi tải trang quản lý danh mục", e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    String path = request.getPathInfo();
    if (path == null) path = "";

    try {
      CategoryDao dao = Database.getCategoryDao();

      switch (path) {
        case "/create" -> {
          String name = nvl(request.getParameter("name"));
          if (!name.isEmpty()){
            Category c = new Category();
            c.setName(name);
            dao.insert(c);
          }
          response.sendRedirect(request.getContextPath()+"/admin/categories");
          return;
        }
        case "/update" -> {
          int id = i(request.getParameter("id"));
          String name = nvl(request.getParameter("name"));
          if (id > 0 && !name.isEmpty()){
            Category c = new Category();
            c.setId(id);
            c.setName(name);
            dao.update(c);
          }
          response.sendRedirect(request.getContextPath()+"/admin/categories");
          return;
        }
        case "/delete" -> {
          int id = i(request.getParameter("id"));
          if (id > 0) dao.delete(id);
          response.sendRedirect(request.getContextPath()+"/admin/categories");
          return;
        }
        default -> {
          response.sendRedirect(request.getContextPath()+"/admin/categories");
        }
      }
    } catch (Exception e){
      throw new ServletException("Admin categories action error", e);
    }
  }
}
