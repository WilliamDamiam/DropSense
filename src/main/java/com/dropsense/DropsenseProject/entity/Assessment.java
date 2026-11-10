
package com.dropsense.DropsenseProject.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessments")
public class Assessment
{
    // Mã đánh giá, tự động tăng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Long assessmentId;

    // Sinh viên được đánh giá
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Điểm nguy cơ từ 0 đến 100
    @Column(name = "risk_score", nullable = false)
    private int riskScore;

    // Mức nguy cơ: LOW, MEDIUM hoặc HIGH
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 20)
    private RiskLevel riskLevel;

    // Nguyên nhân dẫn đến mức nguy cơ
    @Column(name = "reasons", columnDefinition = "TEXT")
    private String reasons;

    // Khuyến nghị hỗ trợ sinh viên
    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;

    // Thời điểm thực hiện đánh giá
    @Column(name = "assessed_at", nullable = false, updatable = false)
    private LocalDateTime assessedAt;

    // Constructor không tham số
    public Assessment()
    {
    }

    // Tự động ghi thời gian khi thêm đánh giá mới
    @PrePersist
    public void beforeSave()
    {
        if (assessedAt == null)
        {
            assessedAt = LocalDateTime.now();
        }
    }

    public Long getAssessmentId()
    {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId)
    {
        this.assessmentId = assessmentId;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public int getRiskScore()
    {
        return riskScore;
    }

    public void setRiskScore(int riskScore)
    {
        this.riskScore = riskScore;
    }

    public RiskLevel getRiskLevel()
    {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel)
    {
        this.riskLevel = riskLevel;
    }

    public String getReasons()
    {
        return reasons;
    }

    public void setReasons(String reasons)
    {
        this.reasons = reasons;
    }

    public String getRecommendations()
    {
        return recommendations;
    }

    public void setRecommendations(String recommendations)
    {
        this.recommendations = recommendations;
    }

    public LocalDateTime getAssessedAt()
    {
        return assessedAt;
    }

    public void setAssessedAt(LocalDateTime assessedAt)
    {
        this.assessedAt = assessedAt;
    }
}
