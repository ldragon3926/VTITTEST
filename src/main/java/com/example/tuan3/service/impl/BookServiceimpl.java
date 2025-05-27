package com.example.tuan3.service.impl;

import com.example.tuan3.dto.book.BookDTO;
import com.example.tuan3.entity.Book;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.BookRepository;
import com.example.tuan3.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class BookServiceimpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookDTO> getAll() {
        return bookRepository.getAll();
    }
    @Override
    public Page<BookDTO> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return bookRepository.getPhanTrang(pageable);
    }


    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book update(Book book, Long id) {
        Book bFind = bookRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        book.setId(bFind.getId());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        bookRepository.deleteById(id);
    }

}