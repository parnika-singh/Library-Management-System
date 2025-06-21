package com.example.librarymanagement.service.impl;

import com.example.librarymanagement.dto.BookDTO;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.exception.ResourceNotFoundException;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.service.interfaces.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = objectMapper.convertValue(bookDTO, Book.class);
        book.setAvailableQuantity(book.getQuantity());
        return objectMapper.convertValue(bookRepository.save(book), BookDTO.class);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setQuantity(bookDTO.getQuantity());
        book.setAvailableQuantity(bookDTO.getAvailableQuantity());

        return objectMapper.convertValue(bookRepository.save(book), BookDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid book ID for deletion");
        }

        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        if (book.getAvailableQuantity() < book.getQuantity()) {
            throw new RuntimeException("Cannot delete book while it is currently borrowed.");
        }

        bookRepository.delete(book);
    }


    @Override
    public BookDTO getBookById(Long id) {
        return objectMapper.convertValue(bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found")), BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
            .map(book -> objectMapper.convertValue(book, BookDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findAll().stream()
            .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
            .map(b -> objectMapper.convertValue(b, BookDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooksByAuthor(String author) {
        return bookRepository.findAll().stream()
            .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
            .map(b -> objectMapper.convertValue(b, BookDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooks(String query) {
        List<Book> results = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
        return results.stream()
                  .map(book -> objectMapper.convertValue(book, BookDTO.class))
                  .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllAvailableBooks() {
        List<Book> availableBooks = bookRepository.findByAvailableQuantityGreaterThan(0);

        return availableBooks.stream()
                .map(book -> objectMapper.convertValue(book, BookDTO.class))
                .collect(Collectors.toList());
    }


}