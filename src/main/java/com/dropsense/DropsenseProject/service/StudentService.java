package com.dropsense.DropsenseProject.service;

import com.dropsense.DropsenseProject.entity.Student;
import com.dropsense.DropsenseProject.repository.AssessmentRepository;
import com.dropsense.DropsenseProject.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;
    private final AssessmentRepository assessmentRepository;

    // Constructor để Spring tự truyền Repository vào Service
    public StudentService(StudentRepository studentRepository, AssessmentRepository assessmentRepository)
    {
        this.studentRepository = studentRepository;
        this.assessmentRepository = assessmentRepository;
    }

    // 1. Lấy danh sách tất cả sinh viên
    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

    // 2. Tìm sinh viên theo mã sinh viên
    public Student getStudentById(String studentId)
    {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên có mã: " + studentId));
    }

    // 3. Tìm kiếm sinh viên theo họ tên
    public List<Student> searchStudents(String keyword)
    {
        if (keyword == null || keyword.trim().isEmpty())
        {
            return studentRepository.findAll();
        }

        return studentRepository.findByFullNameContainingIgnoreCase(keyword.trim());
    }

    // 4. Thêm sinh viên mới
    public Student addStudent(Student student)
    {
        validateStudent(student);

        String studentId = student.getStudentId().trim();

        if (studentRepository.existsByStudentId(studentId))
        {
            throw new IllegalArgumentException("Mã sinh viên đã tồn tại!");
        }

        student.setStudentId(studentId);
        student.setFullName(student.getFullName().trim());

        return studentRepository.save(student);
    }

    // 5. Cập nhật thông tin sinh viên
    public Student updateStudent(String studentId, Student newStudent)
    {
        // Kiểm tra sinh viên có tồn tại không
        Student student = getStudentById(studentId);

        // Kiểm tra dữ liệu mới trước khi cập nhật
        validateStudentData(newStudent);

        // Chỉ cập nhật thông tin, không thay đổi khóa chính
        student.setFullName(newStudent.getFullName().trim());
        student.setGpa(newStudent.getGpa());
        student.setPreviousGpa(newStudent.getPreviousGpa());
        student.setCurrentGpa(newStudent.getCurrentGpa());
        student.setFailedCourses(newStudent.getFailedCourses());
        student.setCreditCompletionRate(newStudent.getCreditCompletionRate());
        student.setAttendanceRate(newStudent.getAttendanceRate());

        return studentRepository.save(student);
    }

    // 6. Xóa sinh viên theo mã
    public void deleteStudent(String studentId)
    {
        // Kiểm tra sinh viên có tồn tại không
        getStudentById(studentId);

        // Không xóa nếu sinh viên đã có lịch sử đánh giá
        if (assessmentRepository.existsByStudentStudentId(studentId))
        {
            throw new IllegalStateException("Không thể xóa sinh viên vì đã có lịch sử đánh giá!");
        }

        studentRepository.deleteById(studentId);
    }

    // 7. Đếm tổng số sinh viên
    public long countStudents()
    {
        return studentRepository.count();
    }

    // 8. Kiểm tra dữ liệu khi thêm sinh viên
    private void validateStudent(Student student)
    {
        if (student == null)
        {
            throw new IllegalArgumentException("Thông tin sinh viên không được để trống!");
        }

        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty())
        {
            throw new IllegalArgumentException("Mã sinh viên không được để trống!");
        }

        if (student.getStudentId().trim().length() > 20)
        {
            throw new IllegalArgumentException("Mã sinh viên không được vượt quá 20 ký tự!");
        }

        validateStudentData(student);
    }

    // 9. Kiểm tra các chỉ số học tập
    private void validateStudentData(Student student)
    {
        if (student == null)
        {
            throw new IllegalArgumentException("Thông tin sinh viên không hợp lệ!");
        }

        if (student.getFullName() == null || student.getFullName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Họ tên sinh viên không được để trống!");
        }

        if (student.getFullName().trim().length() > 100)
        {
            throw new IllegalArgumentException("Họ tên không được vượt quá 100 ký tự!");
        }

        if (!isValidGpa(student.getGpa()))
        {
            throw new IllegalArgumentException("GPA tích lũy phải từ 0 đến 4!");
        }

        if (!isValidGpa(student.getPreviousGpa()))
        {
            throw new IllegalArgumentException("GPA học kỳ trước phải từ 0 đến 4!");
        }

        if (!isValidGpa(student.getCurrentGpa()))
        {
            throw new IllegalArgumentException("GPA học kỳ hiện tại phải từ 0 đến 4!");
        }

        if (student.getFailedCourses() < 0)
        {
            throw new IllegalArgumentException("Số môn trượt không được âm!");
        }

        if (!isValidRate(student.getCreditCompletionRate()))
        {
            throw new IllegalArgumentException("Tỷ lệ hoàn thành tín chỉ phải từ 0 đến 100%!");
        }

        if (!isValidRate(student.getAttendanceRate()))
        {
            throw new IllegalArgumentException("Tỷ lệ chuyên cần phải từ 0 đến 100%!");
        }
    }

    // 10. Kiểm tra GPA hợp lệ
    private boolean isValidGpa(double gpa)
    {
        return Double.isFinite(gpa) && gpa >= 0 && gpa <= 4;
    }

    // 11. Kiểm tra tỷ lệ phần trăm hợp lệ
    private boolean isValidRate(double rate)
    {
        return Double.isFinite(rate) && rate >= 0 && rate <= 100;
    }
}
