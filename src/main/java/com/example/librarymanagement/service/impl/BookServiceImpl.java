package com.example.librarymanagement.service.impl;

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
        Book saved = bookRepository.save(book);
        return objectMapper.convertValue(saved, BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> objectMapper.convertValue(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return objectMapper.convertValue(book, BookDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
