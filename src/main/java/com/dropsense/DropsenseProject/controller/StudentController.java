package com.dropsense.DropsenseProject.controller;

import com.dropsense.DropsenseProject.entity.Student;
import com.dropsense.DropsenseProject.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController
{
    private final StudentService studentService;

    // Constructor để Spring tự truyền StudentService
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    // 1. Hiển thị danh sách và tìm kiếm sinh viên
    @GetMapping
    public String showStudents(@RequestParam(required = false) String keyword, Model model, HttpServletRequest request)
    {
        List<Student> students = studentService.searchStudents(keyword);

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("totalStudents", studentService.countStudents());

        // Kiểm tra người đăng nhập có quyền ADMIN hay không
        model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));

        return "students";
    }

    // 2. Hiển thị form thêm sinh viên
    @GetMapping("/new")
    public String showAddForm(Model model)
    {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        model.addAttribute("formAction", "/students/add");

        return "student-form";
    }

    // 3. Xử lý thêm sinh viên
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student, Model model, RedirectAttributes redirectAttributes)
    {
        try
        {
            studentService.addStudent(student);

            redirectAttributes.addFlashAttribute("success", "Thêm sinh viên thành công!");

            return "redirect:/students";
        }
        catch (IllegalArgumentException e)
        {
            model.addAttribute("student", student);
            model.addAttribute("isEdit", false);
            model.addAttribute("formAction", "/students/add");
            model.addAttribute("error", e.getMessage());

            return "student-form";
        }
    }

    // 4. Hiển thị form sửa sinh viên
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String studentId, Model model, RedirectAttributes redirectAttributes)
    {
        try
        {
            Student student = studentService.getStudentById(studentId);

            model.addAttribute("student", student);
            model.addAttribute("isEdit", true);
            model.addAttribute("formAction", "/students/update/" + studentId);

            return "student-form";
        }
        catch (IllegalArgumentException e)
        {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/students";
        }
    }

    // 5. Xử lý cập nhật thông tin sinh viên
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") String studentId,
                                @ModelAttribute Student student,
                                Model model,
                                RedirectAttributes redirectAttributes)
    {
        try
        {
            studentService.updateStudent(studentId, student);

            redirectAttributes.addFlashAttribute("success", "Cập nhật sinh viên thành công!");

            return "redirect:/students";
        }
        catch (IllegalArgumentException e)
        {
            student.setStudentId(studentId);

            model.addAttribute("student", student);
            model.addAttribute("isEdit", true);
            model.addAttribute("formAction", "/students/update/" + studentId);
            model.addAttribute("error", e.getMessage());

            return "student-form";
        }
    }

    // 6. Xử lý xóa sinh viên
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") String studentId, RedirectAttributes redirectAttributes)
    {
        try
        {
            studentService.deleteStudent(studentId);

            redirectAttributes.addFlashAttribute("success", "Xóa sinh viên thành công!");
        }
        catch (IllegalArgumentException | IllegalStateException e)
        {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/students";
    }
}
