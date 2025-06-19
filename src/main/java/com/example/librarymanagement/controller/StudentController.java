package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.librarymanagement.dto.StudentDTO;
import com.example.librarymanagement.service.interfaces.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new StudentDTO());
        return "students/register";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute StudentDTO studentDTO) {
        studentService.registerStudent(studentDTO);
        return "redirect:/students";
    }
}
