package com.example.tuan3.controller;

import com.example.tuan3.dto.comment.CommentSet;
import com.example.tuan3.dto.user.UserSet;
import com.example.tuan3.entity.Comment;
import com.example.tuan3.entity.Role;
import com.example.tuan3.entity.User;
import com.example.tuan3.repository.CommentRepository;
import com.example.tuan3.repository.RoleRepository;
import com.example.tuan3.repository.UserRepository;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.CommentService;
import com.example.tuan3.service.Specifications.UserSpecifications;
import com.example.tuan3.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;
    @GetMapping
    public ResponseEntity<?> view(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page){
        return ResponseUltils.success(commentService.phanTrang(page),"ROLE_VIEW_COMMENT","Lấy danh sách phân trang thành công");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseUltils.success(commentService.findAll(),"ROLE_VIEW_COMMENT", "Lấy tất cả danh sách comment thành công");
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id){
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isEmpty()) {
            return ResponseUltils.error("error.user.not_found", "Người dùng không tồn tại");
        }
        return ResponseUltils.success(commentService.getOne(id),"ROLE_VIEW_COMMENT", "Lấy comment thành công");
    }
    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid CommentSet commentSet, BindingResult bindingResult ){
        Comment newComment = new Comment();
        if(bindingResult.hasErrors()){
           String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
                return ResponseUltils.error("error.validation", errors);
        }
        Comment comment = commentSet.dto(newComment);
        return ResponseUltils.success(commentService.add(comment), "ROLE_CREATE_COMMENT", "Thêm comment thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CommentSet commentSet, BindingResult bindingResult, @PathVariable(name = "id") Long id ){
        Comment newComment = new Comment();
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Comment comment = commentSet.dto(newComment);
        return ResponseUltils.success(commentService.update(comment, id),"ROLE_UPDATE_COMMENT", "Update comment thành công");
    }
 @PutMapping("/delete/{id}")
        public ResponseEntity<?> delete( @PathVariable(name = "id") Long id ){
     Optional<Comment> comment = commentService.findById(id);
     if (comment.isEmpty()) {
            return ResponseUltils.error("error.user.not_found", "comment không tồn tại");
        }
        Comment commentD = commentRepository.findById(id).get();
        commentD.setStatus(false);
     commentService.update(commentD, id);
        return ResponseUltils.success(null,"ROLE_DELETE_COMMENT", "Xóa comment thành công");
    }

}
