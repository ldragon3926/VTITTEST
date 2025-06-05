package com.example.tuan3.repository;

import com.example.tuan3.dto.category.CategoryDTO;
import com.example.tuan3.dto.comment.CommentDTO;
import com.example.tuan3.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> , JpaSpecificationExecutor<Comment> {

    @Query(value = "select id,content from comments  where status = 1", nativeQuery = true)
    List<CommentDTO> getAll();

    @Query(value = "select id,content from comments  where status = 1", nativeQuery = true)
    Page<CommentDTO> getPhanTrang(Pageable pageable);

    
}
