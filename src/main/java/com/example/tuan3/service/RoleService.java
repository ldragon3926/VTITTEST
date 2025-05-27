package com.example.tuan3.service;

import com.example.tuan3.dto.role.RoleDTO;
import com.example.tuan3.entity.Role;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    List<RoleDTO> getAll();
    Page<RoleDTO> phanTrang(Integer page);
    Optional<Role> findById(Long id);
    Role add (Role role);
    Role update (Role role, Long id);
    void delete( Long id);
}
