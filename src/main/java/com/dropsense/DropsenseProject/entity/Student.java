package com.dropsense.DropsenseProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student
{
    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "gpa")
    private double gpa;

    @Column(name = "previous_gpa")
    private double previousGpa;

    @Column(name = "current_gpa")
    private double currentGpa;

    @Column(name = "failed_courses")
    private int failedCourses;

    @Column(name = "credit_completion_rate")
    private double creditCompletionRate;

    @Column(name = "attendance_rate")
    private double attendanceRate;

    // Constructor không tham số
    public Student()
    {
    }

    // Lấy mã sinh viên
    public String getStudentId()
    {
        return studentId;
    }

    // Cập nhật mã sinh viên
    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    // Lấy họ và tên
    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    // Lấy GPA tích lũy
    public double getGpa()
    {
        return gpa;
    }

    public void setGpa(double gpa)
    {
        this.gpa = gpa;
    }

    // Lấy GPA học kỳ trước
    public double getPreviousGpa()
    {
        return previousGpa;
    }

    public void setPreviousGpa(double previousGpa)
    {
        this.previousGpa = previousGpa;
    }

    // Lấy GPA học kỳ hiện tại
    public double getCurrentGpa()
    {
        return currentGpa;
    }

    public void setCurrentGpa(double currentGpa)
    {
        this.currentGpa = currentGpa;
    }

    // Lấy số học phần trượt
    public int getFailedCourses()
    {
        return failedCourses;
    }

    public void setFailedCourses(int failedCourses)
    {
        this.failedCourses = failedCourses;
    }

    // Lấy tỷ lệ hoàn thành tín chỉ
    public double getCreditCompletionRate()
    {
        return creditCompletionRate;
    }

    public void setCreditCompletionRate(double creditCompletionRate)
    {
        this.creditCompletionRate = creditCompletionRate;
    }

    // Lấy tỷ lệ chuyên cần
    public double getAttendanceRate()
    {
        return attendanceRate;
    }

    public void setAttendanceRate(double attendanceRate)
    {
        this.attendanceRate = attendanceRate;
    }
}
