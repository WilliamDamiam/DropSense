package com.dropsense.DropsenseProject.service;

import com.dropsense.DropsenseProject.dto.RiskResult;
import com.dropsense.DropsenseProject.entity.Assessment;
import com.dropsense.DropsenseProject.entity.Student;
import com.dropsense.DropsenseProject.repository.AssessmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService
{
    private final AssessmentRepository assessmentRepository;
    private final StudentService studentService;
    private final RiskService riskService;

    // Constructor để Spring tự truyền các thành phần cần thiết
    public AssessmentService(AssessmentRepository assessmentRepository, StudentService studentService, RiskService riskService)
    {
        this.assessmentRepository = assessmentRepository;
        this.studentService = studentService;
        this.riskService = riskService;
    }

    // 1. Đánh giá nguy cơ và lưu kết quả vào MySQL
    @Transactional
    public Assessment evaluateAndSave(String studentId)
    {
        // Tìm thông tin sinh viên
        Student student = studentService.getStudentById(studentId);

        // Gọi RiskService để tính điểm nguy cơ
        RiskResult result = riskService.evaluateRisk(student);

        // Tạo một bản ghi đánh giá mới
        Assessment assessment = new Assessment();

        assessment.setStudent(student);
        assessment.setRiskScore(result.getRiskScore());
        assessment.setRiskLevel(result.getRiskLevel());
        assessment.setReasons(result.getReasons());
        assessment.setRecommendations(result.getRecommendations());

        // Lưu kết quả vào bảng assessments
        return assessmentRepository.save(assessment);
    }

    // 2. Lấy lịch sử đánh giá của một sinh viên
    public List<Assessment> getStudentHistory(String studentId)
    {
        // Kiểm tra sinh viên có tồn tại không
        studentService.getStudentById(studentId);

        return assessmentRepository.findByStudentStudentIdOrderByAssessedAtDesc(studentId);
    }

    // 3. Lấy toàn bộ lịch sử đánh giá
    public List<Assessment> getAllAssessments()
    {
        return assessmentRepository.findAllByOrderByAssessedAtDesc();
    }

    // 4. Tìm một kết quả đánh giá theo ID
    public Assessment getAssessmentById(Long assessmentId)
    {
        Optional<Assessment> result = assessmentRepository.findById(assessmentId);

        if (result.isEmpty())
        {
            throw new IllegalArgumentException("Không tìm thấy kết quả đánh giá!");
        }

        return result.get();
    }
}
