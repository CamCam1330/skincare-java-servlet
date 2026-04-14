package controller;

import data.dao.Database;
import data.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Product;
import model.User;

@WebServlet(name = "AdminProductServlet",
        urlPatterns = {"/admin/products", "/admin/products/*"})
public class AdminProductServlet extends HttpServlet {

    // --- helper đơn giản giống style bạn ---
    private static String nvl(String s) { return (s == null) ? "" : s.trim(); }
    private static int i(String s) { try { return Integer.parseInt(nvl(s)); } catch (Exception e) { return 0; } }
    private static double d(String s) { try { return Double.parseDouble(nvl(s)); } catch (Exception e) { return 0d; } }
    private static boolean b(String s) { return "1".equals(s) || "on".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s); }

    private boolean ensureAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession ss = request.getSession(false);
        User u = (ss == null) ? null : (User) ss.getAttribute("user");
        if (u == null || !"admin".equalsIgnoreCase(u.getRole())) {
            // chưa đăng nhập / không phải admin -> đẩy về login
            String ctx = request.getContextPath();
            response.sendRedirect(ctx + "/login?back=" + ctx + "/admin/products");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!ensureAdmin(request, response)) return;      // chặn quyền

        String path = request.getPathInfo();
        if (path == null) path = "";

        try {
            // Navbar cần categories
            request.setAttribute("categories", Database.getCategoryDao().findAll());

            ProductDao dao = Database.getProductDao();

            switch (path) {
                case "/create": {
                    request.setAttribute("product", null);
                    request.setAttribute("title", "Thêm sản phẩm");
                    request.getRequestDispatcher("/views/product_form.jsp").forward(request, response);
                    return;
                }
                case "/edit": {
                    int id = i(request.getParameter("id"));
                    Product p = (id > 0) ? dao.find(id) : null;
                    if (p == null) {
                        response.sendRedirect(request.getContextPath() + "/admin/products");
                        return;
                    }
                    request.setAttribute("product", p);
                    request.setAttribute("title", "Sửa sản phẩm");
                    request.getRequestDispatcher("/views/product_form.jsp").forward(request, response);
                    return;
                }
                default: { // list
                    List<Product> list = Database.getProductDao().findAll();
                    request.setAttribute("products", list);
                    request.setAttribute("title", "Quản lý sản phẩm");
                    request.getRequestDispatcher("/views/products.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            throw new ServletException("Lỗi tải trang quản lý sản phẩm", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!ensureAdmin(request, response)) return;      // chặn quyền

        String path = request.getPathInfo();
        if (path == null) path = "";

        try {
            ProductDao dao = Database.getProductDao();

            switch (path) {
                case "/create": {
                    Product p = new Product();
                    p.setName(nvl(request.getParameter("name")));
                    p.setImage(nvl(request.getParameter("image")));
                    p.setPrice(d(request.getParameter("price")));
                    p.setQuantity(i(request.getParameter("quantity")));
                    p.setStatus(b(request.getParameter("status"))); 
                    p.setIdCategory(i(request.getParameter("categoryId")));
                    p.setIdBrand(i(request.getParameter("brandId")));
                    dao.insert(p);
                    response.sendRedirect(request.getContextPath() + "/admin/products");
                    return;
                }
                case "/update": {
                    int id = i(request.getParameter("id"));
                    if (id <= 0) {
                        response.sendRedirect(request.getContextPath() + "/admin/products");
                        return;
                    }
                    Product p = new Product();
                    p.setId(id);
                    p.setName(nvl(request.getParameter("name")));
                    p.setImage(nvl(request.getParameter("image")));
                    p.setPrice(d(request.getParameter("price")));
                    p.setQuantity(i(request.getParameter("quantity")));
                    p.setStatus(b(request.getParameter("status")));
                    p.setIdCategory(i(request.getParameter("categoryId")));
                    p.setIdBrand(i(request.getParameter("brandId")));
                    dao.update(p);
                    response.sendRedirect(request.getContextPath() + "/admin/products");
                    return;
                }
                case "/delete": {
                    int id = i(request.getParameter("id"));
                    if (id > 0) dao.delete(id);
                    response.sendRedirect(request.getContextPath() + "/admin/products");
                    return;
                }
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/products");
            }
        } catch (Exception e) {
            throw new ServletException("Admin products action error", e);
        }
    }
}
