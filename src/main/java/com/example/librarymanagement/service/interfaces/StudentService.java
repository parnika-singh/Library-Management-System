package com.example.librarymanagement.service.interfaces;

import java.util.List;

import com.example.librarymanagement.dto.StudentDTO;

public interface StudentService {

    StudentDTO registerStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    List<StudentDTO> searchStudentsByName(String query);

}