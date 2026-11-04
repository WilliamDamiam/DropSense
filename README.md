DropSense

Hệ thống đánh giá và cảnh báo nguy cơ bỏ học của sinh viên

DropSense là đồ án cuối kỳ của nhóm 2A, được xây dựng bằng Java Spring Boot, Thymeleaf và MySQL. Hệ thống tập hợp thông tin sinh viên và các chỉ số học tập để phục vụ việc theo dõi, đánh giá nguy cơ và đưa ra khuyến nghị hỗ trợ.

Bản hiện tại ở mốc báo cáo 50%: nhóm đã thực hiện phần quản lý sinh viên và đang tiếp tục phát triển chức năng đánh giá nguy cơ.

1. Thành viên thực hiện

Họ và tên

MSSV

Công việc phụ trách

Trương Hoàng Vũ

2351010250

Backend Spring Boot, kết nối MySQL, CRUD và tích hợp hệ thống

Võ Nguyễn Vinh Toàn

2451012115

Giao diện HTML, CSS, JavaScript và Thymeleaf

2. Công nghệ sử dụng

Thành phần

Công nghệ

Ngôn ngữ

Java

Backend

Spring Boot, Spring MVC

Truy cập dữ liệu

Spring Data JPA, Hibernate

Giao diện

Thymeleaf, HTML, CSS thuần, JavaScript

Cơ sở dữ liệu

MySQL 8.4

Quản lý dự án và thư viện

Maven

Công cụ phát triển

IntelliJ IDEA, MySQL Workbench

3. Chức năng

Đã thực hiện

Hiển thị danh sách và số lượng sinh viên.

Thêm hồ sơ sinh viên cùng các chỉ số học tập.

Chỉnh sửa thông tin sinh viên.

Xóa hồ sơ sau khi xác nhận.

Tìm kiếm sinh viên theo họ tên.

Lưu và đọc dữ liệu từ MySQL.

Thông tin học tập gồm GPA tích lũy, GPA học kỳ trước, GPA học kỳ gần nhất, số học phần trượt, tỷ lệ hoàn thành tín chỉ và tỷ lệ chuyên cần.

Dự kiến hoàn thiện ở giai đoạn tiếp theo

Đánh giá nguy cơ theo bộ quy tắc viết bằng Java.

Phân loại nguy cơ thành ba mức: thấp, trung bình và cao.

Hiển thị các dấu hiệu liên quan và khuyến nghị hỗ trợ.

Kết nối hai trang đánh giá và kết quả với phần xử lý Backend.

Rà soát kiểm tra dữ liệu đầu vào và kiểm thử toàn bộ luồng.

Hai trang assessment.html và result.html hiện mới có giao diện ban đầu. Chức năng đánh giá nguy cơ chưa hoàn thành trong bản này. Nhóm không sử dụng AI hoặc Machine Learning.

4. Kiến trúc và tổ chức mã nguồn

Dự án sử dụng Spring MVC kết hợp kiến trúc phân lớp. Controller nhận yêu cầu từ trình duyệt, gọi Service để xử lý nghiệp vụ và sử dụng Repository để truy cập MySQL thông qua JPA/Hibernate. Kết quả được chuyển sang Thymeleaf để tạo trang HTML.

Trong package gốc của ứng dụng, các thành phần được chia như sau:

Thành phần

Vai trò

controller

Nhận yêu cầu HTTP và điều hướng giao diện

service

Xử lý nghiệp vụ quản lý sinh viên

repository

Truy vấn và lưu dữ liệu

entity

Các lớp ánh xạ với bảng dữ liệu

dto

Chuẩn bị cho dữ liệu kết quả đánh giá ở giai đoạn tiếp theo

Các tài nguyên chính:

Đường dẫn tính từ thư mục gốc

Nội dung

src/main/resources/templates

Các trang Thymeleaf

src/main/resources/static

CSS, JavaScript và hình ảnh

src/main/resources/application-example.properties

Cấu hình mẫu, không chứa tài khoản và mật khẩu thật

pom.xml

Thông tin dự án và các thư viện Maven

5. Hướng dẫn chạy trên máy

Bước 1. Chuẩn bị môi trường

Cài JDK phù hợp với phiên bản Java khai báo trong pom.xml.

Cài MySQL và bảo đảm MySQL Server đang chạy.

Sử dụng IntelliJ IDEA để mở dự án.

Có kết nối Internet trong lần đầu Maven tải các thư viện.

Tải mã nguồn từ kho GitHub này bằng Code → Download ZIP rồi giải nén, hoặc clone bằng Git. Mở thư mục chứa pom.xml trong IntelliJ và chờ Maven tải xong thư viện.

Bước 2. Tạo cơ sở dữ liệu

Đăng nhập MySQL bằng tài khoản có quyền tạo cơ sở dữ liệu và chạy:

CREATE DATABASE IF NOT EXISTS dropsense
CHARACTER SET utf8mb4;

Tài khoản được cấu hình cho ứng dụng cần có quyền đọc, ghi và tạo/cập nhật bảng trong cơ sở dữ liệu này khi chạy với thiết lập ddl-auto=update.

Bước 3. Cấu hình kết nối

Trong src/main/resources, sao chép application-example.properties thành application.properties nếu chưa có file cấu hình riêng. Nếu file đã tồn tại, chỉnh sửa trực tiếp file đó.

Thay thông tin kết nối theo MySQL trên máy:

spring.datasource.url=jdbc:mysql://localhost:3306/dropsense
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

Thay YOUR_MYSQL_USERNAME và YOUR_MYSQL_PASSWORD bằng tài khoản và mật khẩu MySQL của người chạy. Nếu MySQL dùng cổng khác 3306, sửa lại URL tương ứng.

Giữ các thiết lập còn lại theo file cấu hình mẫu, trong đó có:

spring.jpa.hibernate.ddl-auto=update
server.port=8080

application.properties là cấu hình riêng trên từng máy và được bỏ qua trong .gitignore. Kho mã nguồn chỉ lưu application-example.properties để người khác có thể tự cấu hình.

Bước 4. Khởi động ứng dụng

Trong IntelliJ IDEA, mở lớp DropsenseProjectApplication và chọn Run.

Hoặc mở Terminal tại thư mục chứa pom.xml và chạy trên Windows:

.\mvnw.cmd spring-boot:run

Nếu đã cài Maven riêng, có thể dùng:

mvn spring-boot:run

Sau khi ứng dụng khởi động thành công, truy cập:

http://localhost:8080/students

Với cấu hình ddl-auto=update, Hibernate tạo hoặc cập nhật các bảng theo Entity khi ứng dụng khởi động. Cơ sở dữ liệu mới có thể chưa có hồ sơ; sử dụng chức năng thêm sinh viên để tạo dữ liệu thử nghiệm. Các hồ sơ trong MySQL trên máy của nhóm không tự được tải lên GitHub cùng mã nguồn.

6. Kiểm tra sau khi chạy

Mở trang danh sách sinh viên.

Thêm một hồ sơ mẫu và kiểm tra hồ sơ xuất hiện trong danh sách.

Sửa thông tin học tập rồi lưu lại.

Tìm kiếm theo họ tên.

Xóa hồ sơ mẫu và xác nhận dữ liệu đã được xóa.

Đối chiếu dữ liệu trong bảng students của MySQL.

Dữ liệu sử dụng để thử nghiệm là dữ liệu mẫu. Ở giai đoạn hiện tại, nhóm kiểm thử thủ công trên máy cục bộ; chưa triển khai đăng nhập, phân quyền hoặc website trên Internet.

7. Tài liệu kỹ thuật

Spring Boot và cấu hình cơ sở dữ liệu SQL

Chạy ứng dụng Spring Boot bằng Maven
