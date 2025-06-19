package com.example.librarymanagement.dto;

public class HistoryDTO {
    private Long id;
    private Long studentId;
    private Long bookId;
    private String borrowDate;
    private String returnDate;
    private boolean returned;

    public HistoryDTO() {}

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public boolean isReturned() { 
        return returned; 
    }
    public void setReturned(boolean returned) { 
        this.returned = returned; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
