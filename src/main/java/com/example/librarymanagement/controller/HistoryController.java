package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import com.example.librarymanagement.dto.HistoryDTO;
import com.example.librarymanagement.service.interfaces.HistoryService;
import com.example.librarymanagement.service.interfaces.BookService;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private BookService bookService;

    @GetMapping("/borrow")
    public String borrowForm(Model model) {
        model.addAttribute("books", bookService.getAllAvailableBooks());
        return "history/borrow";
    }


    @PostMapping("/borrow")
    public String borrow(@RequestParam Long studentId, @RequestParam Long bookId) {
        if (studentId == null || bookId == null) {
            throw new IllegalArgumentException("Student ID and Book ID must be provided");
        }

        historyService.borrowBook(studentId, bookId);
        return "redirect:/history/list";
    }

    @GetMapping("/list")
    public String listBorrowings(Model model) {
        model.addAttribute("borrowings", historyService.getCurrentBorrowedBooks());
        return "history/list";
    }
}
