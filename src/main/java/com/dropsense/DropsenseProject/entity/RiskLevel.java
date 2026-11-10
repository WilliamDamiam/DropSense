package com.dropsense.DropsenseProject.entity;

public enum RiskLevel
{
    LOW("Thấp"),
    MEDIUM("Trung bình"),
    HIGH("Cao");

    private final String displayName;

    // Constructor dùng để gán tên hiển thị cho từng mức nguy cơ
    RiskLevel(String displayName)
    {
        this.displayName = displayName;
    }

    // Lấy tên mức nguy cơ bằng tiếng Việt
    public String getDisplayName()
    {
        return displayName;
    }
}
