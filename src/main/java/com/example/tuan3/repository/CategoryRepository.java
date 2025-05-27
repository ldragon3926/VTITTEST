package com.example.tuan3.repository;

import com.example.tuan3.dto.category.CategoryDTO;
import com.example.tuan3.dto.role.RoleDTO;
import com.example.tuan3.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select name, description from category  where status = 1", nativeQuery = true)
     List<CategoryDTO> getAll();

    @Query(value = "select name, description from category  where status = 1", nativeQuery = true)
    Page<CategoryDTO> getPhanTrang(Pageable pageable);



}
