package com.example.librarymanagement.service.interface;


public interface BookService {
    BookDTO addBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    void deleteBook(Long id);
}
