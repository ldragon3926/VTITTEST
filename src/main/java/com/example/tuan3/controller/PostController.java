package com.example.tuan3.controller;

import com.example.tuan3.dto.comment.CommentSet;
import com.example.tuan3.dto.post.PostSet;
import com.example.tuan3.entity.Comment;
import com.example.tuan3.entity.Post;
import com.example.tuan3.repository.CommentRepository;
import com.example.tuan3.repository.PostRepository;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.CommentService;
import com.example.tuan3.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;
    @GetMapping
    public ResponseEntity<?> view(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page){
        return ResponseUltils.success(postService.phanTrang(page),"Lấy danh sách phân trang thành công");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseUltils.success(postService.findAll(),"Lấy tất cả danh sách post thành công");
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id){
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            return ResponseUltils.error("error.user.not_found", "Người dùng không tồn tại");
        }
        return ResponseUltils.success(postService.getOne(id), "Lấy post thành công");
    }
    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid PostSet postSet, BindingResult bindingResult ){
        Post newPost = new Post();
        if(bindingResult.hasErrors()){
           String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
                return ResponseUltils.error("error.validation", errors);
        }
        Post post = postSet.dto(newPost);
        return ResponseUltils.success(postService.add(post),  "Thêm post thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid PostSet postSet, BindingResult bindingResult, @PathVariable(name = "id") Long id ){
        Post newPost = new Post();
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Post post = postSet.dto(newPost);
        return ResponseUltils.success(postService.update(post, id),"Update post thành công");
    }
 @PutMapping("/delete/{id}")
        public ResponseEntity<?> delete( @PathVariable(name = "id") Long id ){
     Optional<Post> post = postService.findById(id);
     if (post.isEmpty()) {
            return ResponseUltils.error("error.user.not_found", "post không tồn tại");
        }
        Post postD = postRepository.findById(id).get();
        postD.setStatus(false);
     postService.update(postD, id);
        return ResponseUltils.success(null, "Xóa post thành công");
    }

}
