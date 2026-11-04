package com.dropsense.DropsenseProject.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student
{
    public Student()
    {

    }

    @Id
    @Column(length = 20)
    private String studentId;

    @Column(nullable = false, length = 100)
    private String fullName;

    // GPA tích lũy
    private Double gpa;

    // GPA học kỳ trước
    private Double previousGpa;

    // GPA học kỳ gần nhất
    private Double currentGpa;

    // Số học phần bị trượt
    private Integer failedCourses;

    // Tỷ lệ hoàn thành tín chỉ (%)
    private Double creditCompletionRate;

    // Tỷ lệ chuyên cần (%)
    private Double attendanceRate;


    public String getStudentId()
    {
        return studentId;
    }

    public String getFullName()
    {
        return fullName;
    }

    public Double getGpa()
    {
        return gpa;
    }

    public Double getPreviousGpa()
    {
        return previousGpa;
    }

    public Double getCurrentGpa()
    {
        return currentGpa;
    }

    public Integer getFailedCourses()
    {
        return failedCourses;
    }

    public Double getCreditCompletionRate()
    {
        return creditCompletionRate;
    }

    public Double getAttendanceRate()
    {
        return attendanceRate;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public void setGpa(Double gpa)
    {
        this.gpa = gpa;
    }

    public void setPreviousGpa(Double previousGpa)
    {
        this.previousGpa = previousGpa;
    }

    public void setCurrentGpa(Double currentGpa)
    {
        this.currentGpa = currentGpa;
    }

    public void setFailedCourses(Integer failedCourses)
    {
        this.failedCourses = failedCourses;
    }

    public void setCreditCompletionRate(Double creditCompletionRate)
    {
        this.creditCompletionRate = creditCompletionRate;
    }

    public void setAttendanceRate(Double attendanceRate)
    {
        this.attendanceRate = attendanceRate;
    }

}
