package com.example.tuan3.service;

import com.example.tuan3.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAll();
    Page<Comment> phanTrang(Integer page);
    Optional<Comment> findById(Long id);
    Comment add (Comment comment);
    Comment update (Comment comment, Long id);
    void delete( Long id);
    Comment getOne(Long id);
}
