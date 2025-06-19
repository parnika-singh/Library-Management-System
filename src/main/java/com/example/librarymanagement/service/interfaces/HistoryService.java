package com.example.librarymanagement.service.interfaces;

import java.util.List;

import com.example.librarymanagement.dto.HistoryDTO;

public interface HistoryService {

    HistoryDTO borrowBook(Long studentId, Long bookId);
    HistoryDTO returnBook(Long studentId, Long bookId);
    List<HistoryDTO> getBorrowHistoryByStudent(Long studentId);
    List<HistoryDTO> getCurrentBorrowedBooks();
    boolean hasStudentAlreadyBorrowedBook(Long studentId, Long bookId);
}
