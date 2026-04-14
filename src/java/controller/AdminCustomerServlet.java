package controller;

import data.dao.Database;
import data.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.User;

@WebServlet(name="AdminCustomerServlet",
    urlPatterns = {"/admin/customers", "/admin/customers/*"})
public class AdminCustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        if (path == null) path = "";

        try {
            // Navbar category
            req.setAttribute("categories", Database.getCategoryDao().findAll());

            switch (path) {
                case "/edit" -> {
                    int id = parseInt(req.getParameter("id"));
                    User u = Database.getUserDao().findById(id);
                    req.setAttribute("customer", u);
                    req.setAttribute("title", "Sửa khách hàng");
                    req.getRequestDispatcher("/views/customer_form.jsp").forward(req, resp);
                }
                default -> { // list
                    List<User> list = Database.getUserDao().findCustomers();
                    req.setAttribute("customers", list); // <-- tên attribute KHỚP JSP
                    req.setAttribute("title", "Quản lý khách hàng");
                    req.getRequestDispatcher("/views/customers.jsp").forward(req, resp);
                }
            }
        } catch (Exception e) {
            throw new ServletException("Load customers error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        if (path == null) path = "";

        try {
            UserDao dao = Database.getUserDao();

            switch (path) {
                case "/update" -> {
                    int id = parseInt(req.getParameter("id"));
                    User u = new User();
                    u.setId(id);
                    u.setName(nvl(req.getParameter("name")));
                    u.setEmail(nvl(req.getParameter("email")));
                    u.setPhone(nvl(req.getParameter("phone")));
                    dao.updateBasic(u);
                }
                case "/set-role" -> {
                    int id = parseInt(req.getParameter("id"));
                    String role = nvl(req.getParameter("role")); // "customer" | "admin" | ""
                    dao.updateRole(id, role);
                }
                case "/delete" -> {
                    int id = parseInt(req.getParameter("id"));
                    if (id > 0) dao.delete(id);
                }
            }
        } catch (Exception e) {
            throw new ServletException("Admin customers action error", e);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/customers");
    }

    private static int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
    private static String nvl(String s) { return s == null ? "" : s.trim(); }
}
