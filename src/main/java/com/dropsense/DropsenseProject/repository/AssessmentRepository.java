package com.dropsense.DropsenseProject.repository;

import com.dropsense.DropsenseProject.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long>
{
    // Lấy lịch sử đánh giá của một sinh viên, mới nhất trước
    List<Assessment> findByStudentStudentIdOrderByAssessedAtDesc(String studentId);

    // Lấy tất cả kết quả đánh giá, mới nhất trước
    List<Assessment> findAllByOrderByAssessedAtDesc();

    // Kiểm tra sinh viên đã có lịch sử đánh giá hay chưa
    boolean existsByStudentStudentId(String studentId);
}
