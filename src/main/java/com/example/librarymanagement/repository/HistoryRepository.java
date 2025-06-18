package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByStudent(Student student);

    List<History> findByBook(Book book);

    Optional<History> findByStudentAndBook(Student student, Book book);
}

