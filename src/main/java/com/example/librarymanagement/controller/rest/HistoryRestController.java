package com.example.librarymanagement.controller.rest;

import com.example.librarymanagement.dto.HistoryDTO;
import com.example.librarymanagement.service.interfaces.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryRestController {

    @Autowired
    private HistoryService historyService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody HistoryDTO dto) {
        historyService.borrowBook(dto.getStudentId(), dto.getBookId());
        return ResponseEntity.ok("Book borrowed successfully");
    }
    
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody HistoryDTO dto) {
        historyService.returnBook(dto.getStudentId(), dto.getBookId());
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/borrowed")
    public List<HistoryDTO> getCurrentBorrowedBooks() {
        return historyService.getCurrentBorrowedBooks();
    }

    @GetMapping("/history/{studentId}")
    public List<HistoryDTO> getBorrowHistoryByStudent(@PathVariable Long studentId) {
        return historyService.getBorrowHistoryByStudent(studentId);
    }
}