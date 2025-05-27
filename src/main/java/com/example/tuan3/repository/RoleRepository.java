package com.example.tuan3.repository;

import com.example.tuan3.dto.role.RoleDTO;
import com.example.tuan3.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select name, description from roles where status = 1", nativeQuery = true)
     List<RoleDTO> getAll();

    @Query(value = "select name, description from roles  where status = 1", nativeQuery = true)
    Page<RoleDTO> getPhanTrang(Pageable pageable);



}
