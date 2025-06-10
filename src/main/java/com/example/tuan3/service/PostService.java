package com.example.tuan3.service;

import com.example.tuan3.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();
    Page<Post> phanTrang(Integer page);
    Optional<Post> findById(Long id);
    Post add (Post post);
    Post update (Post post, Long id);
    void delete( Long id);
    Post getOne(Long id);
}
