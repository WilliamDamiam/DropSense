package com.dropsense.DropsenseProject.controller;

import com.dropsense.DropsenseProject.entity.Student;
import com.dropsense.DropsenseProject.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController
{
    private final StudentService studentService;

    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    // Trang chủ
    @GetMapping("/")
    public String home()
    {
        return "redirect:/students";
    }

    // Hiển thị danh sách và tìm kiếm sinh viên
    @GetMapping("/students")
    public String listStudents(@RequestParam(required = false) String keyword, Model model)
    {
        model.addAttribute("students", studentService.searchStudents(keyword));
        model.addAttribute("keyword", keyword);
        return "students";
    }

    // Hiển thị form thêm sinh viên
    @GetMapping("/students/new")
    public String showAddForm(Model model)
    {
        model.addAttribute("student", new Student());
        model.addAttribute("formAction", "/students/add");
        return "student-form";
    }

    // Thêm sinh viên
    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student)
    {
        studentService.addStudent(student);
        return "redirect:/students";
    }

    // Hiển thị form sửa sinh viên
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model)
    {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("formAction", "/students/update/" + id);
        return "student-form";
    }

    // Cập nhật sinh viên
    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable String id, @ModelAttribute Student student)
    {
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    // Xóa sinh viên
    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable String id)
    {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

}
