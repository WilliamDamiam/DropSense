package com.dropsense.DropsenseProject.dto;

import com.dropsense.DropsenseProject.entity.RiskLevel;

// DTO chứa kết quả đánh giá nguy cơ bỏ học
public class RiskResult
{
    // Điểm nguy cơ từ 0 đến 100
    private int riskScore;

    // Mức nguy cơ: LOW, MEDIUM, HIGH
    private RiskLevel riskLevel;

    // Nguyên nhân gây ra nguy cơ
    private String reasons;

    // Khuyến nghị dành cho sinh viên
    private String recommendations;

    // Constructor không tham số
    public RiskResult()
    {
    }

    // Constructor có đầy đủ tham số
    public RiskResult(int riskScore, RiskLevel riskLevel, String reasons, String recommendations)
    {
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.reasons = reasons;
        this.recommendations = recommendations;
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
}
