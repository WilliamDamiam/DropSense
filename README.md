# 🎓 DropSense – Hệ thống đánh giá nguy cơ bỏ học của sinh viên

**DropSense** là ứng dụng web được xây dựng bằng **Java Spring Boot**, hỗ trợ quản lý thông tin sinh viên, đánh giá nguy cơ bỏ học và đưa ra các khuyến nghị dựa trên tình hình học tập.

Hệ thống hướng đến việc hỗ trợ nhà trường và người phụ trách **phát hiện sớm những sinh viên có dấu hiệu gặp khó khăn trong học tập**, từ đó có biện pháp quan tâm và hỗ trợ phù hợp.

> **Lưu ý:** Kết quả đánh giá của DropSense chỉ mang tính tham khảo và hỗ trợ ra quyết định, không phải kết luận chắc chắn rằng một sinh viên sẽ bỏ học.

## 🌐 1. Truy cập hệ thống

**Website:** https://dropsense-production.up.railway.app/

**Mã nguồn:** https://github.com/WilliamDamiam/DropSense

Ứng dụng được triển khai trên **Railway**, sử dụng cơ sở dữ liệu MySQL và các biến môi trường để quản lý cấu hình.

## ✨ 2. Chức năng chính

### 2.1. Quản lý tài khoản

- Đăng nhập và đăng xuất.
- Đăng ký tài khoản người dùng.
- Phân quyền truy cập theo vai trò ADMIN và VIEWER.
- Khôi phục mật khẩu thông qua liên kết gửi đến email đã đăng ký.
- Đặt lại mật khẩu bằng mã xác thực có thời hạn.
- Xem và cập nhật thông tin hồ sơ cá nhân.
- Cập nhật ảnh đại diện.

### 2.2. Quản lý sinh viên

- Hiển thị danh sách sinh viên.
- Tìm kiếm sinh viên theo thông tin được hỗ trợ.
- Thêm sinh viên mới.
- Cập nhật thông tin sinh viên.
- Xóa sinh viên.
- Quản lý dữ liệu phục vụ đánh giá nguy cơ bỏ học.

Các thao tác thêm, sửa và xóa được kiểm soát theo quyền tài khoản.

### 2.3. Đánh giá nguy cơ bỏ học

Hệ thống sử dụng các tiêu chí học tập để xác định mức độ nguy cơ, bao gồm:

- Điểm trung bình học tập (GPA).
- Số môn học không đạt.
- Tỷ lệ tham gia học tập, chuyên cần.
- Tỷ lệ hoàn thành tín chỉ.
- Sự thay đổi kết quả học tập giữa các giai đoạn.

Dựa trên các tiêu chí này, DropSense thực hiện đánh giá theo những quy tắc được thiết lập trong chương trình.

### 2.4. Kết quả và lịch sử đánh giá

- Hiển thị kết quả đánh giá nguy cơ.
- Cung cấp các lý do ảnh hưởng đến kết quả.
- Đưa ra khuyến nghị hỗ trợ sinh viên.
- Xem lại lịch sử đánh giá.

**DropSense hiện sử dụng phương pháp đánh giá dựa trên quy tắc (Rule-based), không sử dụng mô hình Machine Learning hoặc AI được huấn luyện.**

## 🛠️ 3. Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ lập trình | Java |
| Backend | Spring Boot, Spring MVC |
| Xác thực và phân quyền | Spring Security |
| Truy cập dữ liệu | Spring Data JPA, Hibernate |
| Cơ sở dữ liệu | MySQL |
| Giao diện | Thymeleaf, HTML5, CSS3 |
| Thư viện giao diện | Bootstrap 5, Bootstrap Icons |
| Ngôn ngữ phía Client | JavaScript |
| Bảo mật mật khẩu | BCrypt |
| Gửi email | Gmail API qua HTTPS, Google OAuth 2.0 |
| HTTP Client | Spring RestClient |
| Quản lý thư viện | Maven |
| Quản lý phiên bản | Git, GitHub |
| Triển khai | Railway |

## 🏗️ 4. Kiến trúc hệ thống

DropSense được xây dựng theo mô hình **Spring MVC kết hợp kiến trúc phân lớp**, nhằm tách biệt các thành phần của ứng dụng.

**Các thành phần chính:**

- **Controller:** Tiếp nhận yêu cầu HTTP, xử lý điều hướng và trao đổi dữ liệu với giao diện.
- **Service:** Xử lý nghiệp vụ quản lý sinh viên, đánh giá nguy cơ và khôi phục mật khẩu.
- **Repository:** Truy vấn và thao tác với cơ sở dữ liệu thông qua Spring Data JPA.
- **Entity:** Biểu diễn các đối tượng dữ liệu được ánh xạ với MySQL.
- **Templates:** Hiển thị giao diện người dùng bằng Thymeleaf.
- **Security:** Quản lý xác thực, mã hóa mật khẩu và phân quyền truy cập.

Cách tổ chức này giúp mã nguồn dễ đọc, dễ bảo trì và thuận tiện khi mở rộng chức năng.

## 💻 5. Hướng dẫn cài đặt và chạy trên máy cá nhân

### Bước 1. Chuẩn bị môi trường

Cần cài đặt:

- Java JDK tương thích với phiên bản khai báo trong `pom.xml`.
- MySQL Server.
- IntelliJ IDEA hoặc IDE hỗ trợ Maven.
- Git.

### Bước 2. Clone repository

Mở Terminal và thực hiện:

```bash
git clone https://github.com/WilliamDamiam/DropSense.git
cd DropSense
```

Mở thư mục dự án bằng IntelliJ IDEA và đợi Maven tải các thư viện cần thiết.

### Bước 3. Tạo cơ sở dữ liệu

Mở MySQL Workbench và chạy:

```sql
CREATE DATABASE IF NOT EXISTS dropsense
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Sử dụng tài khoản MySQL có quyền truy cập cơ sở dữ liệu `dropsense`.

Cấu hình JPA mặc định của dự án cho phép Hibernate cập nhật cấu trúc bảng dựa trên các Entity khi ứng dụng khởi động.

### Bước 4. Cấu hình biến môi trường

Trong IntelliJ IDEA, mở:

**Run → Edit Configurations → DropsenseProjectApplication → Environment variables**

Thiết lập các biến môi trường sau.

**Cơ sở dữ liệu MySQL**

| Biến môi trường | Giá trị ví dụ |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/dropsense` |
| `DB_USERNAME` | Tên đăng nhập MySQL |
| `DB_PASSWORD` | Mật khẩu MySQL |
| `DDL_AUTO` | `update` |
| `JPA_SHOW_SQL` | `false` |

**Gmail API và Google OAuth 2.0**

| Biến môi trường | Ý nghĩa |
|---|---|
| `GMAIL_CLIENT_ID` | OAuth Client ID được tạo trên Google Cloud |
| `GMAIL_CLIENT_SECRET` | OAuth Client Secret |
| `GMAIL_REFRESH_TOKEN` | Refresh Token được cấp thông qua Google OAuth |
| `GMAIL_FROM_EMAIL` | Địa chỉ Gmail đã cấp quyền gửi thư |
| `APP_URL` | `http://localhost:8080` |

**Tài khoản và cấu hình khác**

| Biến môi trường | Ý nghĩa |
|---|---|
| `DROPSENSE_ADMIN_USERNAME` | Tên tài khoản ADMIN khởi tạo |
| `DROPSENSE_ADMIN_PASSWORD` | Mật khẩu ADMIN khởi tạo |
| `DROPSENSE_VIEWER_USERNAME` | Tên tài khoản VIEWER khởi tạo |
| `DROPSENSE_VIEWER_PASSWORD` | Mật khẩu VIEWER khởi tạo |
| `AVATAR_DIR` | Thư mục lưu ảnh đại diện |

**Lưu ý:**

- Không ghi mật khẩu, Client Secret hoặc Refresh Token thật vào mã nguồn.
- Không commit các thông tin bí mật lên GitHub.
- Việc thay đổi biến môi trường khởi tạo tài khoản không tự động đổi mật khẩu của tài khoản đã tồn tại trong MySQL.

### Bước 5. Chạy ứng dụng

Chạy class:

`DropsenseProjectApplication.java`

Sau khi Spring Boot khởi động thành công, truy cập:

http://localhost:8080/login

## 📧 6. Chức năng khôi phục mật khẩu bằng Gmail API

DropSense sử dụng **Gmail API kết hợp Google OAuth 2.0** để gửi email khôi phục mật khẩu thông qua kết nối HTTPS.

### 6.1. Quy trình hoạt động

1. Người dùng mở trang **Quên mật khẩu**.
2. Nhập địa chỉ email đã đăng ký với DropSense.
3. Hệ thống kiểm tra tài khoản và tạo mã khôi phục có thời hạn nếu tài khoản hợp lệ.
4. `EmailService` sử dụng Google OAuth Refresh Token để yêu cầu Access Token.
5. Hệ thống gửi email chứa liên kết đặt lại mật khẩu thông qua Gmail API.
6. Người dùng mở liên kết và nhập mật khẩu mới.
7. Hệ thống xác thực mã khôi phục và cập nhật mật khẩu.

### 6.2. Cấu hình Google Cloud

Để sử dụng Gmail API, cần:

1. Tạo project trên Google Cloud Console.
2. Bật Gmail API.
3. Cấu hình Google Auth Platform.
4. Tạo OAuth Client dạng Web application.
5. Cấp quyền `https://www.googleapis.com/auth/gmail.send`.
6. Sử dụng OAuth Playground hoặc một quy trình OAuth phù hợp để nhận Refresh Token.
7. Lưu Client ID, Client Secret và Refresh Token dưới dạng biến môi trường.

**Tài liệu tham khảo:**

- Google Cloud Console: https://console.cloud.google.com/
- Gmail API: https://developers.google.com/workspace/gmail/api
- OAuth Playground: https://developers.google.com/oauthplayground/

### 6.3. Lưu ý về OAuth

Khi ứng dụng External ở chế độ Testing, Refresh Token cấp với quyền Gmail thường hết hạn sau khoảng **7 ngày**.

Khi Refresh Token hết hạn, chức năng gửi email sẽ không tiếp tục hoạt động cho đến khi ứng dụng được cấp quyền lại và cập nhật Refresh Token mới.

Việc gửi email phải tuân thủ các chính sách và hạn mức của Google.

## 🔐 7. Phân quyền người dùng

DropSense hỗ trợ hai vai trò:

| Chức năng | ADMIN | VIEWER |
|---|:---:|:---:|
| Đăng nhập hệ thống | ✅ | ✅ |
| Xem danh sách sinh viên | ✅ | ✅ |
| Tìm kiếm sinh viên | ✅ | ✅ |
| Thêm sinh viên | ✅ | ❌ |
| Chỉnh sửa sinh viên | ✅ | ❌ |
| Xóa sinh viên | ✅ | ❌ |
| Thực hiện đánh giá nguy cơ | ✅ | ❌ |
| Xem thông tin, kết quả được phân quyền | ✅ | ✅ |
| Cập nhật ảnh đại diện cá nhân | ✅ | ✅ |

Hệ thống sử dụng **Spring Security** để kiểm soát quyền truy cập đến các chức năng và yêu cầu HTTP.

## 🚀 8. Triển khai trên Railway

Ứng dụng được triển khai trên Railway và kết nối với mã nguồn GitHub.

### Quy trình triển khai

1. Đưa mã nguồn DropSense lên GitHub.
2. Tạo project và service trên Railway.
3. Kết nối repository DropSense.
4. Cấu hình kết nối MySQL.
5. Khai báo các biến môi trường cần thiết.
6. Build và Deploy ứng dụng.
7. Kiểm tra trạng thái hoạt động và nhật ký triển khai.

**Các biến quan trọng trên Railway:**

```text
DB_URL
DB_USERNAME
DB_PASSWORD

GMAIL_CLIENT_ID
GMAIL_CLIENT_SECRET
GMAIL_REFRESH_TOKEN
GMAIL_FROM_EMAIL

APP_URL
```

Giá trị `APP_URL` khi triển khai:

```text
https://dropsense-production.up.railway.app
```

Railway cung cấp biến `PORT` cho ứng dụng sử dụng khi chạy trên môi trường triển khai.

**Lưu ý:** Nếu sử dụng thư mục lưu ảnh trên hệ thống tệp, cần cấu hình nơi lưu trữ bền vững để tránh mất ảnh khi triển khai lại hoặc thay thế container.

## 🛡️ 9. Bảo mật và quản lý dữ liệu

DropSense áp dụng các biện pháp bảo mật cơ bản:

- Mã hóa mật khẩu người dùng bằng BCrypt.
- Xác thực và phân quyền thông qua Spring Security.
- Giới hạn quyền truy cập đối với các thao tác quản trị.
- Sử dụng token có thời hạn cho chức năng đặt lại mật khẩu.
- Lưu thông tin nhạy cảm thông qua biến môi trường.
- Không đưa thư mục ảnh tải lên hoặc thông tin bí mật vào repository.
- Không tiết lộ trực tiếp việc một địa chỉ email có tồn tại trong hệ thống thông qua thông báo quên mật khẩu.

Dữ liệu sinh viên được sử dụng cho mục đích quản lý và hỗ trợ đánh giá học tập. Kết quả đánh giá không nên được sử dụng như căn cứ duy nhất để đưa ra quyết định ảnh hưởng đến sinh viên.

## 📚 10. Mục đích dự án

DropSense được thực hiện với mục đích học tập và thực hành:

- Xây dựng ứng dụng web bằng Java Spring Boot.
- Tổ chức mã nguồn theo mô hình MVC và kiến trúc phân lớp.
- Làm việc với MySQL thông qua Spring Data JPA và Hibernate.
- Xây dựng chức năng đăng nhập và phân quyền.
- Tích hợp dịch vụ bên ngoài thông qua REST API và OAuth 2.0.
- Sử dụng Git và GitHub trong quá trình phát triển nhóm.
- Triển khai ứng dụng lên nền tảng đám mây.

Dự án tập trung vào việc vận dụng kiến thức lập trình web, cơ sở dữ liệu, bảo mật và phát triển phần mềm vào một bài toán hỗ trợ quản lý giáo dục.

---

**DropSense © 2026 – Nhóm 2A**

*Dự án phục vụ mục đích học tập và nghiên cứu.*