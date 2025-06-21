package com.example.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/search")
    public String searchStudents(@RequestParam("query") String query, Model model) {
        List<StudentDTO> students = studentService.searchStudentsByName(query);
        model.addAttribute("students", students);
        return "students/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }


}
