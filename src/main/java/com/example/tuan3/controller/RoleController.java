package com.example.tuan3.controller;

import com.example.tuan3.dto.role.RoleSet;
import com.example.tuan3.dto.user.UserSet;
import com.example.tuan3.entity.Permission;
import com.example.tuan3.entity.Role;
import com.example.tuan3.entity.User;
import com.example.tuan3.repository.PermissionRepository;
import com.example.tuan3.repository.RoleRepository;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.RoleService;
import com.example.tuan3.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/role")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping
    public ResponseEntity<?> view(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseUltils.success(roleService.phanTrang(page), "Lấy danh sách phân trang thành công");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseUltils.success(roleService.findAll(), "Lấy tất cả danh sách user thành công");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        Optional<Role> role = roleService.findById(id);
        if (role.isEmpty()) {
            return ResponseUltils.error("error.role.not_found", "Role không tồn tại");
        }
        return ResponseUltils.success(roleService.findById(id), "Lấy role thành công");
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid RoleSet roleSet, BindingResult bindingResult) {
        Role role = new Role();
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        List<Permission> permissionList = permissionRepository.findAllById(roleSet.getIdPermission());
        Role role1 = roleSet.dto(role, permissionList);
        return ResponseUltils.success(roleService.add(role1), "Thêm role thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid RoleSet roleSet, BindingResult bindingResult, @PathVariable(name = "id") Long id) {
        Role role = new Role();
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        List<Permission> permissionList = permissionRepository.findAllById(roleSet.getIdPermission());
        Role role1 = roleSet.dto(role, permissionList);
        return ResponseUltils.success(roleService.update(role1, id), "Update role thành công");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<Role> role = roleService.findById(id);
        if (role.isEmpty()) {
            return ResponseUltils.error("error.role.not_found", "Role không tồn tại");
        }
        Role roleD = roleRepository.findById(id).get();
        roleD.setStatus(false);
        roleService.update(roleD, id);
        return ResponseUltils.success(null, "Xóa role thành công");
    }

}
