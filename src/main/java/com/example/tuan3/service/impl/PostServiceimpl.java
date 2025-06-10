package com.example.tuan3.service.impl;

import com.example.tuan3.entity.Post;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.PostRepository;
import com.example.tuan3.service.PostService;
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
public class PostServiceimpl implements PostService {
    @Autowired
    PostRepository postRepository ;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return postRepository.findAll(pageable);
    }


    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post getOne(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
@Transactional
    public Post add(Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post update(Post post, Long id) {
        Post pFind = postRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        post.setId(pFind.getId());
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        postRepository.deleteById(id);
    }
}
