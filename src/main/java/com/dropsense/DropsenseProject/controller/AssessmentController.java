
package com.dropsense.DropsenseProject.controller;

import com.dropsense.DropsenseProject.dto.AssessmentForm;
import com.dropsense.DropsenseProject.dto.RiskResult;
import com.dropsense.DropsenseProject.entity.Assessment;
import com.dropsense.DropsenseProject.entity.Student;
import com.dropsense.DropsenseProject.service.AssessmentService;
import com.dropsense.DropsenseProject.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
@RequestMapping("/assessments")
public class AssessmentController
{
    private final AssessmentService assessmentService;
    private final StudentService studentService;

    // Constructor để Spring tự truyền Service
    public AssessmentController(AssessmentService assessmentService, StudentService studentService)
    {
        this.assessmentService = assessmentService;
        this.studentService = studentService;
    }

    // 1. Hiển thị trang chọn sinh viên để đánh giá

    @GetMapping
    public String showAssessmentForm(Model model, HttpServletRequest request)
    {
        // Lấy danh sách sinh viên từ MySQL
        List<Student> students = studentService.getAllStudents();

        // Truyền dữ liệu sang assessment.html
        model.addAttribute("students", students);
        model.addAttribute("assessmentForm", new AssessmentForm());

        // Kiểm tra quyền ADMIN
        model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));

        return "assessment";
    }


    // 2. Đánh giá nguy cơ và lưu kết quả vào MySQL
    @PostMapping("/evaluate")
    public String evaluateStudent(@ModelAttribute AssessmentForm assessmentForm,
                                  Model model)
    {
        try
        {
            String studentId = assessmentForm.getStudentId();

            if (studentId == null || studentId.trim().isEmpty())
            {
                throw new IllegalArgumentException("Vui lòng chọn sinh viên cần đánh giá!");
            }

            // Gọi Service để tính điểm và lưu kết quả
            Assessment assessment = assessmentService.evaluateAndSave(studentId.trim());

            // Chuyển đến trang kết quả vừa đánh giá
            return "redirect:/assessments/result/" + assessment.getAssessmentId();
        }
        catch (IllegalArgumentException e)
        {
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("assessmentForm", assessmentForm);
            model.addAttribute("error", e.getMessage());

            return "assessment";
        }
    }

    // 3. Hiển thị kết quả đánh giá
    @GetMapping("/result/{id}")
    public String showResult(@PathVariable("id") Long assessmentId,
                             Model model,
                             RedirectAttributes redirectAttributes)
    {
        try
        {
            // Lấy kết quả đánh giá đã lưu
            Assessment assessment = assessmentService.getAssessmentById(assessmentId);

            // Lấy thông tin sinh viên được đánh giá
            String studentId = assessment.getStudent().getStudentId();
            Student student = studentService.getStudentById(studentId);

            // Tạo DTO để hiển thị kết quả
            RiskResult riskResult = new RiskResult(
                    assessment.getRiskScore(),
                    assessment.getRiskLevel(),
                    assessment.getReasons(),
                    assessment.getRecommendations()
            );

            model.addAttribute("assessment", assessment);
            model.addAttribute("student", student);
            model.addAttribute("riskResult", riskResult);

            return "result";
        }
        catch (IllegalArgumentException e)
        {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/assessments/history";
        }
    }

    // 4. Hiển thị toàn bộ lịch sử đánh giá
    @GetMapping("/history")
    public String showAllHistory(Model model)
    {
        List<Assessment> assessments = assessmentService.getAllAssessments();

        model.addAttribute("assessments", assessments);

        return "risk-list";
    }

    // 5. Hiển thị lịch sử đánh giá của một sinh viên
    @GetMapping("/history/{studentId}")
    public String showStudentHistory(@PathVariable String studentId,
                                     Model model,
                                     RedirectAttributes redirectAttributes)
    {
        try
        {
            Student student = studentService.getStudentById(studentId);

            List<Assessment> assessments = assessmentService.getStudentHistory(studentId);

            model.addAttribute("student", student);
            model.addAttribute("assessments", assessments);

            return "risk-list";
        }
        catch (IllegalArgumentException e)
        {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/assessments/history";
        }
    }
}
