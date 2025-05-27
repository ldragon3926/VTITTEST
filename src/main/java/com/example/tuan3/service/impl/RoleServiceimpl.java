package com.example.tuan3.service.impl;

import com.example.tuan3.dto.role.RoleDTO;
import com.example.tuan3.entity.Role;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.RoleRepository;
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
public class RoleServiceimpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.getAll();
    }

    @Override
    public Page<RoleDTO> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return roleRepository.getPhanTrang(pageable);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }


    @Override
@Transactional
    public Role add(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role update(Role role, Long id) {
        Role rFind = roleRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        role.setId(rFind.getId());
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        roleRepository.deleteById(id);
    }
}
