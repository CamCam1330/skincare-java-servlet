# 🌿 Skincare & Cosmetics E-Commerce Web App

> Một ứng dụng web thương mại điện tử chuyên cung cấp các sản phẩm chăm sóc da và mỹ phẩm chính hãng (Sữa rửa mặt, Nước tẩy trang, Kem chống nắng...). Dự án được phát triển dựa trên nền tảng **Java Web (Servlet/JSP)**, mang lại trải nghiệm mua sắm trực tuyến trực quan cho người dùng và cung cấp công cụ quản lý đắc lực cho quản trị viên.

## 🚀 Công nghệ sử dụng (Tech Stack)

* **Backend:** Java, Servlet, JSP (JavaServer Pages)
* **Frontend:** HTML, CSS (`app.css`), Bootstrap (thiết kế UI/UX và Jumbotron)
* **Database/DAO:** Triển khai qua `ProductImpl` (thực thi `ProductDao`)
* **Server/Deployment:** Apache Tomcat (cấu hình qua `context.xml` với context path `/skincare`, đóng gói dưới dạng `skincare.war`)

## ✨ Tính năng nổi bật (Key Features)

### 🛍️ Dành cho Khách hàng (User Web)
* **Khám phá sản phẩm:** Hiển thị và phân loại các mặt hàng mỹ phẩm đa dạng từ các thương hiệu lớn (La Roche-Posay, Anessa, Bioderma, Cetaphil...).
* **Danh mục (`_category.jsp`):** Duyệt sản phẩm theo từng nhóm riêng biệt (Cleanser, Micellar Water, Sunscreen...).
* **Tài khoản cá nhân:** Đăng ký tài khoản (`_register.jsp`) và quản lý hồ sơ cá nhân (`profile.jsp`).
* **Tính năng mua sắm:** Lưu sản phẩm yêu thích (`_wishlist.jsp`) và thông báo đặt hàng thành công (`order_success.jsp`).

### ⚙️ Dành cho Quản trị viên (Admin Portal)
* **Bảng điều khiển (`_admindashboard.jsp`):** Giao diện tổng quan dành riêng cho Admin.
* **Quản lý sản phẩm (`products.jsp`, `AdminProductServlet`):** Thêm, sửa, xóa thông tin và hình ảnh của các mặt hàng mỹ phẩm.
* **Quản lý danh mục (`CategoryServlet`):** Kiểm soát và phân loại các nhóm sản phẩm hiển thị trên trang chủ.
