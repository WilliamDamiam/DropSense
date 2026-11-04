package com.dropsense.DropsenseProject.service;

import com.dropsense.DropsenseProject.entity.Student;
import com.dropsense.DropsenseProject.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    // Lấy danh sách sinh viên
    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

    // Tìm sinh viên theo MSSV
    public Student getStudentById(String studentId)
    {
        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Không tìm thấy sinh viên: " + studentId));
    }

    // Tìm kiếm sinh viên theo họ tên
    public List<Student> searchStudents(String keyword)
    {
        if (keyword == null || keyword.isBlank())
        {
            return studentRepository.findAll();
        }

        return studentRepository.findByFullNameContainingIgnoreCase(keyword.trim());
    }

    // Thêm sinh viên
    public Student addStudent(Student student)
    {
        if (studentRepository.existsById(student.getStudentId()))
        {
            throw new IllegalArgumentException("MSSV đã tồn tại!");
        }

        return studentRepository.save(student);
    }

    // Cập nhật thông tin sinh viên
    public Student updateStudent(String studentId, Student updatedStudent)
    {
        Student existing = getStudentById(studentId);
        existing.setFullName(updatedStudent.getFullName());
        existing.setGpa(updatedStudent.getGpa());
        existing.setPreviousGpa(updatedStudent.getPreviousGpa());
        existing.setCurrentGpa(updatedStudent.getCurrentGpa());
        existing.setFailedCourses(updatedStudent.getFailedCourses());
        existing.setCreditCompletionRate(updatedStudent.getCreditCompletionRate());
        existing.setAttendanceRate(updatedStudent.getAttendanceRate());

        return studentRepository.save(existing);
    }

    // Xóa sinh viên-
    public void deleteStudent(String studentId)
    {
        Student student = getStudentById(studentId);
        studentRepository.delete(student);
    }


}
