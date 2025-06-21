package com.example.librarymanagement.service.interfaces;

import java.util.List;
import com.example.librarymanagement.dto.BookDTO;

public interface BookService {

    BookDTO addBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);
    BookDTO getBookById(Long id);
    List<BookDTO> getAllBooks();
    List<BookDTO> searchBooksByTitle(String title);
    List<BookDTO> searchBooksByAuthor(String author);
    List<BookDTO> searchBooks(String query);
    List<BookDTO> getAllAvailableBooks();


}
