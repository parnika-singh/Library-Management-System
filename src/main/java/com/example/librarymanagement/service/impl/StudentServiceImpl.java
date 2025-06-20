package com.example.librarymanagement.service.impl;

import com.example.librarymanagement.repository.HistoryRepository;
import com.example.librarymanagement.repository.StudentRepository;

import com.example.librarymanagement.dto.StudentDTO;
import com.example.librarymanagement.model.Student;
import com.example.librarymanagement.exception.ResourceNotFoundException;
import com.example.librarymanagement.service.interfaces.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public StudentDTO registerStudent(StudentDTO studentDTO) {
        Student student = objectMapper.convertValue(studentDTO, Student.class);
        return objectMapper.convertValue(studentRepository.save(student), StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        student.setName(studentDTO.getName());
        student.setContact(studentDTO.getContact());
        student.setRegistrationDate(studentDTO.getRegistrationDate());

        return objectMapper.convertValue(studentRepository.save(student), StudentDTO.class);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        boolean hasActiveBorrowings = historyRepository
            .findByStudent(student).stream()
            .anyMatch(history -> !history.isReturned());

        if (hasActiveBorrowings) {
            throw new IllegalStateException("Cannot delete student with active borrowed books.");
        }

        studentRepository.delete(student);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return objectMapper.convertValue(studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found")), StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
            .map(student -> objectMapper.convertValue(student, StudentDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name).stream()
            .map(student -> objectMapper.convertValue(student, StudentDTO.class))
            .collect(Collectors.toList());
    }
}
