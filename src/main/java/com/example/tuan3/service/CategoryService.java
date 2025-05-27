package com.example.tuan3.service;

import com.example.tuan3.dto.category.CategoryDTO;
import com.example.tuan3.entity.Category;
import com.example.tuan3.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    List<CategoryDTO> getAll();
    Page<CategoryDTO> phanTrang(Integer page);
    Optional<Category> findById(Long id);
    Category add (Category category);
    Category update (Category category, Long id);
    void delete( Long id);
}
