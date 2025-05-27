package com.example.tuan3.service;

import com.example.tuan3.dto.permission.PermissionDTO;
import com.example.tuan3.dto.permission.PermissionUserDTO;
import com.example.tuan3.entity.Permission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    List<Permission> findAll();
    List<PermissionDTO> getAll();
    List<PermissionUserDTO> getPermissionUser();
    Page<PermissionDTO> phanTrang(Integer page);
    Optional<Permission> findById(Long id);
    Permission add (Permission permission);
    Permission update (Permission permission, Long id);
    void delete( Long id);
}
