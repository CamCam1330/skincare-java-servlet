package controller;

import data.dao.Database;

import data.dao.UserDao;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.*;

import java.io.IOException;

import model.User;


@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})

public class ProfileServlet extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();

        User u = (User) session.getAttribute("user");

        if (u == null) {

            session.setAttribute("Login_err", "Vui lòng đăng nhập để truy cập hồ sơ");

            resp.sendRedirect(req.getContextPath() + "/login");

            return;

        }

        try {
            req.setAttribute("categories", Database.getCategoryDao().findAll());
        } catch (Exception ignored) {
        }

        req.setAttribute("title", "Hồ sơ cá nhân");

        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);

    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();

        User current = (User) session.getAttribute("user");

        if (current == null) {

            session.setAttribute("Login_err", "Vui lòng đăng nhập để thao tác");

            resp.sendRedirect(req.getContextPath() + "/login");

            return;

        }

        String action = nvl(req.getParameter("action")); // "profile" | "password"

        try {

            if ("password".equalsIgnoreCase(action)) {

                handleChangePassword(req, resp, session, current);

            } else { // mặc định là cập nhật thông tin

                handleUpdateProfile(req, resp, session, current);

            }

        } catch (Exception e) {

            throw new ServletException("Profile action error", e);

        }

    }

    /* ========================= helpers ========================= */
    private void handleUpdateProfile(HttpServletRequest req, HttpServletResponse resp,
            HttpSession session, User current) throws Exception {

        String name = nvl(req.getParameter("name"));

        String email = nvl(req.getParameter("email"));

        String phone = nvl(req.getParameter("phone"));

        // Validate cơ bản
        if (name.isEmpty()) {

            flashErr(session, "Tên không được để trống");

            redirectProfile(req, resp);

            return;

        }

        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {

            flashErr(session, "Email không hợp lệ");

            redirectProfile(req, resp);

            return;

        }

        if (!phone.matches("^0\\d{9}$")) { // 10 số, bắt đầu bằng 0

            flashErr(session, "Số điện thoại không hợp lệ (phải là 10 số, bắt đầu bằng 0)");

            redirectProfile(req, resp);

            return;

        }

        // Check trùng email/phone (loại trừ chính mình)
        UserDao dao = Database.getUserDao();

        User byEmail = dao.findUser(email);

        if (byEmail != null && byEmail.getId() != current.getId()
                && email.equalsIgnoreCase(byEmail.getEmail())) {

            flashErr(session, "Email đã tồn tại trong hệ thống");

            redirectProfile(req, resp);

            return;

        }

        User byPhone = dao.findUser(phone);

        if (byPhone != null && byPhone.getId() != current.getId()
                && phone.equals(byPhone.getPhone())) {

            flashErr(session, "Số điện thoại đã tồn tại trong hệ thống");

            redirectProfile(req, resp);

            return;

        }

        // Cập nhật
        current.setName(name);

        current.setEmail(email);

        current.setPhone(phone);

        dao.updateProfile(current);

        // Đồng bộ session
        session.setAttribute("user", current);

        flashMsg(session, "Cập nhật thông tin thành công");

        redirectProfile(req, resp);

    }

    private void handleChangePassword(HttpServletRequest req, HttpServletResponse resp,
            HttpSession session, User current) throws Exception {

        String oldPass = nvl(req.getParameter("oldPassword"));

        String newPass = nvl(req.getParameter("newPassword"));

        String retype = nvl(req.getParameter("retypePassword"));

        // So sánh MD5
        String oldMd5 = data.utils.API.getMd5(oldPass);

        if (!oldMd5.equalsIgnoreCase(current.getPassword())) {

            flashErr(session, "Mật khẩu hiện tại không đúng");

            redirectProfile(req, resp);

            return;

        }

        if (newPass.length() < 6) {

            flashErr(session, "Mật khẩu mới phải từ 6 ký tự");

            redirectProfile(req, resp);

            return;

        }

        if (!newPass.equals(retype)) {

            flashErr(session, "Nhập lại mật khẩu không khớp");

            redirectProfile(req, resp);

            return;

        }

        String newMd5 = data.utils.API.getMd5(newPass);

        Database.getUserDao().updatePassword(current.getId(), newMd5);

        // Cập nhật session
        current.setPassword(newMd5);

        session.setAttribute("user", current);

        flashMsg(session, "Đổi mật khẩu thành công");

        redirectProfile(req, resp);

    }

    private static String nvl(String s) {
        return (s == null) ? "" : s.trim();
    }

    private static void flashErr(HttpSession s, String m) {
        s.setAttribute("profile_err", m);
    }

    private static void flashMsg(HttpSession s, String m) {
        s.setAttribute("profile_msg", m);
    }

    private void redirectProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.sendRedirect(req.getContextPath() + "/profile");

    }

}
