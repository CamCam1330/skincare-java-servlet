/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import data.dao.Database;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Product;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart/*"})
public class CartServlet extends HttpServlet {

    private Cart getCart(HttpSession session) {
        Cart c = (Cart) session.getAttribute("cart");
        if (c == null) {
            c = new Cart();
            session.setAttribute("cart", c);
        }
        return c;
    }
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
            out.println("<title>Servlet CartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath() + "</h1>");
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
            String path = request.getPathInfo();
            if(path == null || "/".equals(path) || path.isEmpty()){
                try {
                    request.setAttribute("categories", Database.getCategoryDao().findAll());
                }catch (Exception ignored) {}
                request.setAttribute("cart", getCart(request.getSession()));
                request.setAttribute("title", "Giỏ hàng");
                request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
                return;
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
        if (path == null) {
            path = "";
        }

        switch (path) {
            case "/add": {
                int pid = Integer.parseInt(request.getParameter("pid"));
                int qty = 1;
                try {
                    qty = Integer.parseInt(request.getParameter("qty"));
                } catch (Exception ignored) {
                }

                try {
                    Product p = Database.getProductDao().find(pid);
                    if (p != null) {
                        Cart cart = getCart(request.getSession());
                        cart.add(p, qty);
                        request.getSession().setAttribute("cartCount", cart.getTotalItems());
                    }
                } catch (Exception e) {
                    log("cart add error", e);
                }
                
                String to = request.getParameter("to");
                if ("cart".equalsIgnoreCase(to)) {
                    response.sendRedirect(request.getContextPath() + "/cart");
                } else {
                    String ref = request.getHeader("Referer");
                    response.sendRedirect(ref != null ? ref : request.getContextPath() + "/category");
                }
                return;
            }

            case "/remove": {
                int pid = Integer.parseInt(request.getParameter("pid"));
                Cart cart = getCart(request.getSession());
                cart.remove(pid);
                request.getSession().setAttribute("cartCount", cart.getTotalItems());
                response.sendRedirect(request.getHeader("Referer"));
                return;
            }

            case "/update": {
                int pid = Integer.parseInt(request.getParameter("pid"));
                int qty = Integer.parseInt(request.getParameter("qty"));
                Cart cart = getCart(request.getSession());
                if (qty <= 0) {
                    cart.remove(pid);
                } else {
                    try {
                        Product p = Database.getProductDao().find(pid);
                        if (p != null) {
                            cart.remove(pid);
                            cart.add(p, qty);
                        }
                    } catch (Exception e) {
                        log("cart update error", e);
                    }
                }
                request.getSession().setAttribute("cartCount", cart.getTotalItems());
                response.sendRedirect(request.getContextPath() + "/cart"); //
                return;
            }
            
            case "/clear": {
                request.getSession().removeAttribute("cart");
                request.getSession().setAttribute("cartCount", 0);
                response.sendRedirect(request.getHeader("Referer"));
                return;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/home");
        }
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
