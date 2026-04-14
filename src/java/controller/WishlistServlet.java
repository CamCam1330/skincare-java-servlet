/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import data.dao.Database;
import data.dao.WishlistDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "WishlistServlet", urlPatterns = {"/wishlist", "/wishlist/*"})
public class WishlistServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WishlistServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WishlistServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getPathInfo();
        if (path == null) path = "";
        try {
            if ("/remove".equals(path)) {
                int pid = 0;
                try { pid = Integer.parseInt(request.getParameter("pid")); } catch (Exception ignored) {}
                if (pid > 0) {
                    User u = (User) request.getSession().getAttribute("user");
                    WishlistDao dao = Database.getWishlistDao();
                    dao.remove(u.getId(), pid);
                }
                String ref = request.getHeader("Referer");
                response.sendRedirect(ref != null ? ref : request.getContextPath() + "/wishlist");
                return;
            }
            request.setAttribute("categories", Database.getCategoryDao().findAll());
            User u = (User) request.getSession().getAttribute("user");
            List<Product> products = Database.getWishlistDao().listByUser(u.getId());

            request.setAttribute("wishProducts", products);
            request.setAttribute("title", "Yêu thích");
            request.getRequestDispatcher("/views/wishlist.jsp").forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(WishlistServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Lỗi tải wishlist", ex);
        }
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = request.getPathInfo();
        if (path == null) path = "";

         int pid = 0;
        try { pid = Integer.parseInt(request.getParameter("pid")); } catch (Exception ignored) {}

        try {
            User u = (User) request.getSession().getAttribute("user");
            WishlistDao dao = Database.getWishlistDao();

            switch (path) {
                case "/add"    -> { if (pid > 0) dao.add(u.getId(), pid); }
                case "/remove" -> { if (pid > 0) dao.remove(u.getId(), pid); }
                case "/toggle", "" -> {
                    if (pid > 0) {
                        if (dao.exists(u.getId(), pid)) dao.remove(u.getId(), pid);
                        else dao.add(u.getId(), pid);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Wishlist action error", e);
        }

        String ref = request.getHeader("Referer");
        response.sendRedirect(ref != null ? ref : request.getContextPath() + "/wishlist");
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
