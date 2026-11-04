DropSense
Hệ thống đánh giá và cảnh báo nguy cơ bỏ học của sinh viên

DropSense là đồ án cuối kỳ của nhóm 2A, được phát triển bằng Java Spring Boot, Thymeleaf và MySQL. Hệ thống quản lý hồ sơ sinh viên cùng các chỉ số học tập, làm cơ sở để theo dõi, đánh giá mức nguy cơ bỏ học và đề xuất biện pháp hỗ trợ.
Trạng thái: Phiên bản báo cáo tiến độ 50% (tuần 1/2). Nhóm đã triển khai và kiểm thử thủ công chức năng quản lý sinh viên (CRUD). Chức năng đánh giá và cảnh báo nguy cơ đang được phát triển cho tuần 2.

1. Thông tin nhóm thực hiện
Nhóm: 2A
Họ và tên	MSSV	Công việc phụ trách
Trương Hoàng Vũ	2351010250	Backend Spring Boot, thiết kế và kết nối MySQL, xử lý CRUD, tích hợp hệ thống
Võ Nguyễn Vinh Toàn	2451012115	Thiết kế giao diện HTML, CSS, JavaScript, Thymeleaf và phối hợp kiểm thử


2. Công nghệ sử dụng
Thành phần	Công nghệ
Ngôn ngữ lập trình	Java
Backend	Spring Boot, Spring MVC
Truy cập dữ liệu	Spring Data JPA, Hibernate
Frontend	Thymeleaf, HTML, CSS thuần, JavaScript
Cơ sở dữ liệu	MySQL 8.4
Quản lý dự án và thư viện	Maven (Maven Wrapper)
Công cụ phát triển	IntelliJ IDEA, MySQL Workbench


Ghi chú: Giao diện ở giai đoạn hiện tại sử dụng CSS thuần, chưa tích hợp Bootstrap.
3. Chức năng hệ thống
3.1. Đã hoàn thành trong tuần 1
- Hiển thị danh sách và số lượng sinh viên.
- Thêm sinh viên kèm thông tin học tập.
- Chỉnh sửa thông tin sinh viên đã lưu.
- Xóa sinh viên sau khi người dùng xác nhận.
- Tìm kiếm sinh viên theo họ tên.
- Lưu, đọc và cập nhật dữ liệu trong MySQL thông qua Spring Data JPA.
Các chỉ số học tập được quản lý:
- GPA tích lũy.
- GPA học kỳ trước.
- GPA học kỳ gần nhất.
- Số học phần trượt.
- Tỷ lệ hoàn thành tín chỉ (%).
- Tỷ lệ chuyên cần (%).
3.2. Dự kiến thực hiện trong tuần 2
- Đánh giá mức nguy cơ bỏ học bằng các quy tắc lập trình bằng Java.
- Phân loại nguy cơ thành ba mức: thấp, trung bình, cao.
- Hiển thị những dấu hiệu ảnh hưởng đến kết quả đánh giá.
- Đưa ra khuyến nghị hỗ trợ học tập phù hợp với các dấu hiệu.
- Kết nối giao diện assessment.html và result.html với Backend Risk.
- Rà soát kiểm tra dữ liệu đầu vào, kiểm thử và hoàn thiện báo cáo.
Các dấu hiệu dự kiến sử dụng gồm GPA tích lũy thấp, GPA giảm giữa các học kỳ, nhiều học phần trượt, tỷ lệ hoàn thành tín chỉ thấp và chuyên cần thấp. Ngưỡng cụ thể sẽ được nhóm xác định, triển khai và kiểm thử trong tuần 2.
Lưu ý: Hai trang assessment.html và result.html hiện mới có giao diện ban đầu; chức năng đánh giá nguy cơ chưa hoạt động. Dự án không sử dụng AI, Python hay Machine Learning. Mức nguy cơ trong phiên bản hoàn thiện sẽ là kết quả đánh giá theo quy tắc, không phải xác suất dự đoán từ mô hình học máy.

4. Kiến trúc phần mềm
Dự án kết hợp Spring MVC với cách tổ chức phân lớp, giúp tách giao diện, xử lý nghiệp vụ và truy cập dữ liệu.
Trình duyệt
    |
    v
View (Thymeleaf / HTML / CSS / JavaScript)
    |
    v
Controller (Spring MVC)
    |
    v
Service (xử lý nghiệp vụ)
    |
    v
Repository (Spring Data JPA / Hibernate)
    |
    v
MySQL (bảng students)
Các thành phần Backend chính của tuần 1:
Thành phần	Vai trò
StudentController	Nhận yêu cầu HTTP, điều hướng giao diện và nhận dữ liệu từ biểu mẫu
StudentService	Xử lý nghiệp vụ thêm, sửa, xóa, tìm kiếm sinh viên
StudentRepository	Truy cập dữ liệu qua JPA
Student	Entity ánh xạ tới bảng students


Các thành phần dự kiến cho tuần 2: RiskController, RiskAssessmentService và RiskResult (DTO).
5. Cấu trúc thư mục
DropsenseProject/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── src/
    └── main/
        ├── java/com/dropsense/DropsenseProject/
        │   ├── DropsenseProjectApplication.java
        │   ├── controller/
        │   │   └── StudentController.java
        │   ├── service/
        │   │   └── StudentService.java
        │   ├── repository/
        │   │   └── StudentRepository.java
        │   ├── entity/
        │   │   └── Student.java
        │   └── dto/                  # Dự kiến dùng trong tuần 2
        └── resources/
            ├── templates/
            │   ├── fragments/
            │   │   ├── header.html
            │   │   └── navbar.html
            │   ├── students.html
            │   ├── student-form.html
            │   ├── assessment.html  # Giao diện ban đầu
            │   ├── result.html      # Giao diện ban đầu
            │   └── index.html
            ├── static/
            │   ├── css/style.css
            │   ├── js/main.js
            │   └── images/
            └── application-example.properties
Lưu ý: application.properties là file cấu hình riêng trên từng máy, không nên đưa lên GitHub nếu chứa tài khoản hoặc mật khẩu. File application-example.properties là mẫu cần được lưu trong repository để người khác cấu hình theo môi trường của họ.
6. Hướng dẫn cài đặt và chạy dự án
Bước 1. Chuẩn bị môi trường
- Cài JDK phù hợp với phiên bản Java được khai báo trong pom.xml.
- Cài và khởi động MySQL Server.
- Có IntelliJ IDEA (hoặc IDE hỗ trợ Maven) và kết nối Internet để tải thư viện trong lần chạy đầu.
Bước 2. Tải mã nguồn
Tải ZIP từ GitHub rồi giải nén, hoặc clone repository:
git clone https://github.com/WilliamDamiam/DropSense.git
cd DropSense
Mở thư mục chứa pom.xml bằng IntelliJ IDEA và để Maven tải các dependencies.
Bước 3. Tạo cơ sở dữ liệu
Trong MySQL Workbench, kết nối với MySQL Server và chạy:
CREATE DATABASE IF NOT EXISTS dropsense
CHARACTER SET utf8mb4;
Tài khoản MySQL dùng cho ứng dụng cần có quyền truy cập cơ sở dữ liệu dropsense. Khi dùng spring.jpa.hibernate.ddl-auto=update, tài khoản cũng cần quyền tạo hoặc cập nhật cấu trúc bảng trong môi trường phát triển.
Bước 4. Cấu hình ứng dụng
Tại src/main/resources/, sao chép application-example.properties thành application.properties (nếu chưa có), sau đó chỉnh các thông số cho máy của bạn:
spring.application.name=DropsenseProject
spring.datasource.url=jdbc:mysql://localhost:3306/dropsense
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
server.port=8080
Thay YOUR_MYSQL_USERNAME và YOUR_MYSQL_PASSWORD bằng thông tin MySQL của bạn. Nếu server dùng cổng khác 3306, cần sửa lại URL kết nối.

Không commit file cấu hình chứa mật khẩu thật. Kiểm tra .gitignore đã bỏ qua src/main/resources/application.properties và file mẫu không có thông tin bí mật.
Bước 5. Khởi động Spring Boot
Trong IntelliJ IDEA, mở DropsenseProjectApplication.java rồi nhấn Run.

Hoặc chạy tại thư mục chứa pom.xml:

Windows (PowerShell):
.\mvnw.cmd spring-boot:run
macOS / Linux:
./mvnw spring-boot:run
Nếu đã cài Maven, có thể dùng:
mvn spring-boot:run
Sau khi ứng dụng khởi động thành công, truy cập:

http://localhost:8080/students

Cơ sở dữ liệu mới có thể chưa có bản ghi nào. Hãy sử dụng biểu mẫu Thêm sinh viên để nhập dữ liệu mẫu. Với cấu hình ddl-auto=update, Hibernate có thể tạo hoặc cập nhật bảng dựa trên Entity ở môi trường phát triển.
7. Kiểm thử chức năng tuần 1
Nhóm đã kiểm thử thủ công các chức năng CRUD trên máy cục bộ với dữ liệu mẫu.
STT	Trường hợp kiểm thử	Kết quả mong đợi
1	Mở trang /students	Danh sách sinh viên hiển thị từ MySQL
2	Thêm sinh viên tại /students/new	Bản ghi mới xuất hiện trong danh sách
3	Chỉnh sửa hồ sơ sinh viên	Thông tin được cập nhật, MSSV giữ nguyên
4	Tìm kiếm theo họ tên	Hiển thị các sinh viên có tên phù hợp
5	Xóa sinh viên và xác nhận	Bản ghi được xóa khỏi danh sách và MySQL
6	Kiểm tra bảng students trong Workbench	Dữ liệu phù hợp với các thao tác trên giao diện


Có thể đối chiếu dữ liệu bằng câu lệnh:
USE dropsense;
SELECT * FROM students;
Hiện tại ứng dụng chạy trên localhost để phục vụ đồ án; chưa triển khai đăng nhập, phân quyền hay website công khai. Chỉ sử dụng dữ liệu giả lập khi kiểm thử và trình diễn.
8. Kế hoạch hoàn thiện tuần 2
Công việc	Phụ trách
Xây dựng DTO kết quả đánh giá (RiskResult)	Trương Hoàng Vũ
Lập trình quy tắc đánh giá (RiskAssessmentService)	Trương Hoàng Vũ
Xây dựng điều hướng và xử lý yêu cầu Risk (RiskController)	Trương Hoàng Vũ
Kết nối và hoàn thiện giao diện đánh giá, kết quả	Võ Nguyễn Vinh Toàn
Kiểm thử tích hợp, sửa lỗi, hoàn thiện báo cáo và demo	Cả nhóm


9. Tài liệu tham khảo
- Spring Boot Documentation
- Spring Data JPA Reference
- Thymeleaf Documentation
- MySQL Reference Manual
- Apache Maven Documentation
Nhóm 2A – DropSense
Đồ án môn học, giai đoạn báo cáo tiến độ 50%.
