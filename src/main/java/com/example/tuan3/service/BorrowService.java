package com.example.tuan3.service;

import com.example.tuan3.dto.book.BookDTO;
import com.example.tuan3.dto.borrow.BorrowDTO;
import com.example.tuan3.entity.Book;
import com.example.tuan3.entity.Borrows;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BorrowService {
    List<Borrows> findAll();
    List<BorrowDTO> getAll();
    Page<BorrowDTO> phanTrang(Integer page);
    Optional<Borrows> findById(Long id);
    void delete( Long id);
    Borrows getOne(Long id);
    Borrows add(Borrows borrows);
    Borrows update(Borrows borrows, Long id);
}
