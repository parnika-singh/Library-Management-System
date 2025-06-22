package com.example.librarymanagement.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int quantity;
    private int availableQuantity;

    public BookDTO() {}

    public Long getId() { 
        return id; }
    public void setId(Long id) { 
        this.id = id; }
    
    public String getTitle() { 
        return title; }
    public void setTitle(String title) { 
        this.title = title; }
    
    public String getAuthor() { 
        return author; }
    public void setAuthor(String author) { 
        this.author = author; }
    
    public String getIsbn() { 
        return isbn; }
    public void setIsbn(String isbn) { 
        this.isbn = isbn; }
    
    public int getQuantity() { 
        return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; }
    
    public int getAvailableQuantity() { 
        return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { 
        this.availableQuantity = availableQuantity; }
}