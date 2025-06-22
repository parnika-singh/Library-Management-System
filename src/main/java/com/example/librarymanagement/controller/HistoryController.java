package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.HistoryDTO;
import com.example.librarymanagement.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private BookService bookService;

    //@Autowired
    //private StudentService studentService;

    // Show list of borrowed books
    @GetMapping("/list")
    public String viewBorrowedBooks(Model model) {
        List<HistoryDTO> borrowings = historyService.getCurrentBorrowedBooks();
        model.addAttribute("borrowings", borrowings);
        return "history/list";
    }

    // Show borrow form
    @GetMapping("/borrow")
    public String showBorrowForm(Model model) {
        model.addAttribute("borrowDTO", new HistoryDTO());
        model.addAttribute("availableBooks", bookService.getAllAvailableBooks());
        return "history/borrow";
    }

    // Process borrow
    @PostMapping("/borrow")
    public String borrowBook(@ModelAttribute("borrowDTO") HistoryDTO historyDTO, Model model) {
        try {
            historyService.borrowBook(historyDTO.getStudentId(), historyDTO.getBookId());
            return "redirect:/history/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("borrowDTO", new HistoryDTO());
            model.addAttribute("availableBooks", bookService.getAllAvailableBooks());
            return "history/borrow";
        }
    }

    // Show return form
    @GetMapping("/return")
    public String showReturnForm(Model model) {
        model.addAttribute("returnDTO", new HistoryDTO());
        return "history/return";
    }

    // Process return
    @PostMapping("/return")
    public String returnBook(@ModelAttribute("returnDTO") HistoryDTO historyDTO, Model model) {
        try {
            historyService.returnBook(historyDTO.getStudentId(), historyDTO.getBookId());
            return "redirect:/history/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "history/return";
        }
    }


    @GetMapping("/return/{studentId}/{bookId}")
    public String returnBookViaGet(@PathVariable Long studentId, @PathVariable Long bookId, Model model) {
        try {
            historyService.returnBook(studentId, bookId);
            return "redirect:/history/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error/general";
        }
    }

}
