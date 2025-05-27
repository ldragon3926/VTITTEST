package com.example.tuan3.service;

import com.example.tuan3.dto.book.BookDTO;
import com.example.tuan3.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    List<BookDTO> getAll();
    Page<BookDTO> phanTrang(Integer page);
    Optional<Book> findById(Long id);
    void delete( Long id);
    Book getOne(Long id);
    Book add(Book book);
    Book update(Book book, Long id);
}
