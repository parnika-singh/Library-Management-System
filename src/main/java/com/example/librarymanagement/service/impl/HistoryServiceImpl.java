package com.example.librarymanagement.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.dto.HistoryDTO;
import com.example.librarymanagement.exception.ResourceNotFoundException;
import com.example.librarymanagement.repository.*;
import com.example.librarymanagement.service.interfaces.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.librarymanagement.model.*;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public HistoryDTO borrowBook(Long studentId, Long bookId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Optional<History> past = historyRepository.findByStudentAndBook(student, book);
        if (past.isPresent()) {
            throw new RuntimeException("Student already borrowed this book in the past.");
        }

        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Book is not available.");
        }

        History history = new History();
        history.setStudent(student);
        history.setBook(book);
        history.setBorrowDate(LocalDate.now());
        history.setReturned(false);

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
        history = historyRepository.save(history);

        return objectMapper.convertValue(history, HistoryDTO.class);
    }

    @Override
    public HistoryDTO returnBook(Long studentId, Long bookId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        History history = historyRepository.findByStudentAndBook(student, book)
            .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found."));

        if (history.isReturned()) {
            throw new RuntimeException("Book already returned.");
        }

        history.setReturned(true);
        history.setReturnDate(LocalDate.now());

        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
        historyRepository.save(history);

        return objectMapper.convertValue(history, HistoryDTO.class);
    }

    @Override
    public List<HistoryDTO> getBorrowHistoryByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return historyRepository.findByStudent(student).stream()
            .map(h -> objectMapper.convertValue(h, HistoryDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<HistoryDTO> getCurrentBorrowedBooks() {
        return historyRepository.findAll().stream()
            .filter(h -> !h.isReturned())
            .map(h -> objectMapper.convertValue(h, HistoryDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public boolean hasStudentAlreadyBorrowedBook(Long studentId, Long bookId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return historyRepository.findByStudentAndBook(student, book).isPresent();
    }
}
