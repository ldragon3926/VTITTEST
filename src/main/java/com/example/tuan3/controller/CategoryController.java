package com.example.tuan3.controller;

import com.example.tuan3.dto.category.CategorySet;
import com.example.tuan3.dto.role.RoleSet;
import com.example.tuan3.entity.Category;
import com.example.tuan3.entity.Permission;
import com.example.tuan3.entity.Role;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.CategoryService;
import com.example.tuan3.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> view(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseUltils.success(categoryService.phanTrang(page), "Lấy danh sách phân trang thành công");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseUltils.success(categoryService.getAll(), "Lấy tất cả danh sách thể loại thành công");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isEmpty()) {
            return ResponseUltils.error("error.role.not_found", "Thể loại này không tồn tại");
        }
        return ResponseUltils.success(categoryService.findById(id), "Lấy thể loại thành công");
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid CategorySet categorySet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Category category = categorySet.dto(new Category());
        return ResponseUltils.success(categoryService.add(category), "Thêm thể loại thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CategorySet categorySet, BindingResult bindingResult, @PathVariable(name = "id") Long id) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Category category = categorySet.dto(new Category());
        return ResponseUltils.success(categoryService.update(category, id), "Update thể loại thành công");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isEmpty()) {
            return ResponseUltils.error("error.category.not_found", "Category không tồn tại");
        }
        Category categoryD = categoryService.findById(id).get();
        categoryD.setStatus(false);
        categoryService.update(categoryD, id);
        return ResponseUltils.success(null, "Xóa category thành công");
    }
}
