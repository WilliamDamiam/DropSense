package com.dropsense.DropsenseProject.dto;

// DTO dùng để nhận dữ liệu từ form đánh giá nguy cơ
public class AssessmentForm
{
    // Mã sinh viên cần đánh giá
    private String studentId;

    // Constructor không tham số
    public AssessmentForm()
    {
    }

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }
}
