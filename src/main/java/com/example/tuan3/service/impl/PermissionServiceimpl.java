package com.example.tuan3.service.impl;

import com.example.tuan3.dto.permission.PermissionDTO;
import com.example.tuan3.dto.permission.PermissionUserDTO;
import com.example.tuan3.dto.role.RoleDTO;
import com.example.tuan3.entity.Permission;
import com.example.tuan3.entity.Role;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.PermissionRepository;
import com.example.tuan3.repository.RoleRepository;
import com.example.tuan3.service.PermissionService;
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
public class PermissionServiceimpl implements PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
    @Override
    public List<PermissionDTO> getAll() {
        return permissionRepository.getAll();
    }



@Override
    public List<PermissionUserDTO> getPermissionUser() {
        return permissionRepository.getPermissionsUser();
    }

    @Override
    public Page<PermissionDTO> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return permissionRepository.getPhanTrang(pageable);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }


    @Override
@Transactional
    public Permission add(Permission   permission) {
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public Permission update(Permission permission, Long id) {
        Permission pFind = permissionRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        permission.setId(pFind.getId());
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        permissionRepository.deleteById(id);
    }
}
