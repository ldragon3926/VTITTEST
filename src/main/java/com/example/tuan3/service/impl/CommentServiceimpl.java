package com.example.tuan3.service.impl;

import com.example.tuan3.entity.Comment;
import com.example.tuan3.entity.User;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.CategoryRepository;
import com.example.tuan3.repository.CommentRepository;
import com.example.tuan3.repository.UserRepository;
import com.example.tuan3.service.CommentService;
import com.example.tuan3.service.UserService;
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
public class CommentServiceimpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Page<Comment> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return commentRepository.findAll(pageable);
    }


    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment getOne(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
@Transactional
    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment comment, Long id) {
        Comment cFind = commentRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        comment.setId(cFind.getId());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        commentRepository.deleteById(id);
    }
}
