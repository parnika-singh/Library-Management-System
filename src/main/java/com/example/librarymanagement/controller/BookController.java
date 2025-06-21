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

import com.example.librarymanagement.dto.BookDTO;
import com.example.librarymanagement.service.interfaces.BookService;


@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new BookDTO());
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO) {
        bookService.addBook(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<BookDTO> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(required = true) Long id, Model model) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        BookDTO book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute BookDTO bookDTO) {
        if (id == null || bookDTO == null) {
            throw new IllegalArgumentException("Invalid update request");
        }
        bookService.updateBook(id, bookDTO);
        return "redirect:/books";
    }


}