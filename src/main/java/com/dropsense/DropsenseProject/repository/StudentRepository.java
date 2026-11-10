package com.dropsense.DropsenseProject.repository;

import com.dropsense.DropsenseProject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>
{
    // Tìm sinh viên theo họ tên, không phân biệt chữ hoa và chữ thường
    List<Student> findByFullNameContainingIgnoreCase(String fullName);

    // Kiểm tra mã sinh viên đã tồn tại hay chưa
    boolean existsByStudentId(String studentId);
}
