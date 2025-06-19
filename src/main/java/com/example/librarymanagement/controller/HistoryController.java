package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.librarymanagement.dto.HistoryDTO;
import com.example.librarymanagement.service.interfaces.HistoryService;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/borrow")
    public String showBorrowForm(Model model) {
        model.addAttribute("history", new HistoryDTO());
        return "history/borrow";
    }

    @PostMapping("/borrow")
    public String borrowBook(@ModelAttribute HistoryDTO historyDTO) {
        historyService.borrowBook(historyDTO.getStudentId(), historyDTO.getBookId());
        return "redirect:/history/list";
    }

    @GetMapping("/list")
    public String listBorrowings(Model model) {
        model.addAttribute("borrowings", historyService.getCurrentBorrowedBooks());
        return "history/list";
    }
}
