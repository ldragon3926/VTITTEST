package com.example.tuan3.service.impl;

import com.example.tuan3.dto.category.CategoryDTO;
import com.example.tuan3.dto.role.RoleDTO;
import com.example.tuan3.entity.Category;
import com.example.tuan3.entity.Role;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.CategoryRepository;
import com.example.tuan3.repository.RoleRepository;
import com.example.tuan3.service.CategoryService;
import com.example.tuan3.service.RoleService;
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
public class CategoryServiceimpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public Page<CategoryDTO> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return categoryRepository.getPhanTrang(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }


    @Override
@Transactional
    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category update(Category category, Long id) {
        Category cFind = categoryRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        category.setId(cFind.getId());
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        categoryRepository.deleteById(id);
    }
}
