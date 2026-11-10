# 🎓 DropSense

**DropSense** là website hỗ trợ **đánh giá nguy cơ bỏ học của sinh viên** dựa trên một số thông tin học tập, giúp nhà trường hoặc người phụ trách nhận diện sớm các trường hợp cần được quan tâm.

> **Lưu ý:** Kết quả của DropSense mang tính **tham khảo và hỗ trợ ra quyết định**, không phải kết luận chắc chắn rằng một sinh viên sẽ bỏ học.

## 1. Chức năng chính

- **Đăng nhập và đăng xuất**; phân quyền người dùng thành **ADMIN** và **VIEWER**.
- **Đăng ký tài khoản**, **quên mật khẩu** và đặt lại mật khẩu bằng liên kết gửi qua email.
- **Quản lý sinh viên:** xem danh sách, tìm kiếm, thêm, sửa và xóa thông tin (tùy quyền tài khoản).
- **Đánh giá nguy cơ bỏ học** dựa trên điểm trung bình, số môn không đạt, tỷ lệ chuyên cần, tiến độ tín chỉ và biến động kết quả học tập.
- **Xem kết quả, lý do, khuyến nghị** và lịch sử đánh giá.
- **Trang hồ sơ cá nhân** và cập nhật ảnh đại diện.

## 2. Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ | Java |
| Backend | Spring Boot, Spring MVC, Spring Security |
| Truy cập dữ liệu | Spring Data JPA / Hibernate |
| Cơ sở dữ liệu | MySQL |
| Giao diện | Thymeleaf, HTML, CSS, Bootstrap 5, JavaScript |
| Gửi email | Gmail SMTP / Spring Mail |
| Quản lý dự án | Maven, Git, GitHub |

## 3. Cách chạy DropSense trên máy cá nhân

**Bước 1. Chuẩn bị**

Cài đặt **Java JDK** phù hợp với phiên bản khai báo trong `pom.xml`, **MySQL Server** và **IntelliJ IDEA** (hoặc IDE hỗ trợ Maven).

**Bước 2. Tạo cơ sở dữ liệu MySQL**

Mở MySQL Workbench, chạy lệnh:

```sql
CREATE DATABASE IF NOT EXISTS dropsense
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Sử dụng tài khoản MySQL có quyền truy cập cơ sở dữ liệu `dropsense`.

**Bước 3. Mở dự án**

Clone repository và mở thư mục dự án trong IntelliJ:

```bash
git clone https://github.com/WilliamDamiam/DropSense.git
cd DropSense
```

Đợi Maven tải các thư viện trong `pom.xml`.

**Bước 4. Cấu hình biến môi trường**

Vào **Run → Edit Configurations → DropsenseProjectApplication → Environment variables** và khai báo:

| Tên biến | Ý nghĩa / giá trị ví dụ |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/dropsense` |
| `DB_USERNAME` | Tài khoản MySQL của bạn |
| `DB_PASSWORD` | Mật khẩu tài khoản MySQL |
| `MAIL_USERNAME` | Gmail dùng để gửi email |
| `MAIL_APP_PASSWORD` | Gmail App Password |
| `APP_URL` | `http://localhost:8080` |
| `DROPSENSE_ADMIN_USERNAME` | Tên đăng nhập ADMIN do bạn đặt |
| `DROPSENSE_ADMIN_PASSWORD` | Mật khẩu khởi tạo ADMIN |
| `DROPSENSE_VIEWER_USERNAME` | Tên đăng nhập VIEWER do bạn đặt |
| `DROPSENSE_VIEWER_PASSWORD` | Mật khẩu khởi tạo VIEWER |

> Các biến email cần thiết khi sử dụng tính năng khôi phục mật khẩu. Không ghi mật khẩu thật vào `application.properties` hoặc commit lên GitHub. Tài khoản mặc định chỉ được khởi tạo nếu chưa tồn tại; đổi biến môi trường không tự đổi mật khẩu của tài khoản đã lưu trong MySQL.

**Bước 5. Chạy ứng dụng**

Chạy class `DropsenseProjectApplication.java`, sau đó mở trình duyệt:

**http://localhost:8080/login**

## 4. Phân quyền sử dụng

- **ADMIN:** quản lý dữ liệu sinh viên và thực hiện đánh giá nguy cơ.
- **VIEWER:** xem thông tin và kết quả được phép truy cập, không được thực hiện các thao tác quản trị.

## 5. Lưu ý về bảo mật và dữ liệu

- Mật khẩu tài khoản được mã hóa bằng **BCrypt**.
- Thông tin kết nối MySQL, Gmail và tài khoản khởi tạo được lấy từ **biến môi trường**.
- Thư mục `uploads/` chứa ảnh tải lên không được đẩy lên GitHub.
- Khi triển khai lên Railway, cần cấu hình biến môi trường, MySQL và nơi lưu ảnh đại diện bền vững.
- Quy tắc đánh giá hiện tại dựa trên **các tiêu chí được thiết lập trong chương trình**, không phải mô hình AI/ML đã được huấn luyện và kiểm định.

## 6. Mục đích dự án

Đây là **đồ án học tập** nhằm thực hành xây dựng một ứng dụng web Java với Spring Boot, cơ sở dữ liệu MySQL, xác thực và phân quyền người dùng, cùng quy trình phát triển và triển khai phần mềm.
