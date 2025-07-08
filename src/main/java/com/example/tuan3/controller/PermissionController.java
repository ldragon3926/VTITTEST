package com.example.tuan3.controller;

import com.example.tuan3.dto.permission.PermissionSet;
import com.example.tuan3.dto.role.RoleSet;
import com.example.tuan3.entity.Permission;
import com.example.tuan3.entity.Role;
import com.example.tuan3.entity.User;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.PermissionService;
import com.example.tuan3.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping
    public ResponseEntity<?> view(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseUltils.success(permissionService.phanTrang(page), "Lấy danh sách phân trang thành công");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseUltils.success(permissionService.findAll(), "Lấy tất cả danh sách permission thành công");
    }

    @GetMapping("/find-by-user")
    public ResponseEntity<?> getPermissionUser() {
        return ResponseUltils.success(permissionService.getPermissionUser(), "Lấy tất cả danh sách quyền của user thành công");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        Optional<Permission> permission = permissionService.findById(id);
        if (permission.isEmpty()) {
            return ResponseUltils.error("error.permission.not_found", "Permission không tồn tại");
        }
        return ResponseUltils.success(permissionService.findById(id), "Lấy permission thành công");
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid PermissionSet permissionSet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Permission permission = permissionSet.dto(new Permission());
        return ResponseUltils.success(permissionService.add(permission), "Thêm permission thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid PermissionSet permissionSet, BindingResult bindingResult, @PathVariable(name = "id") Long id) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Permission permission = permissionSet.dto(new Permission());
        return ResponseUltils.success(permissionService.update(permission, id), "Update permission thành công");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<Permission> permission = permissionService.findById(id);
        if (permission.isEmpty()) {
            return ResponseUltils.error("error.user.not_found", "Người dùng không tồn tại");
        }
        Permission permissionD = permissionService.findById(id).get();
        permissionD.setStatus(false);
        permissionService.update(permissionD, id);
        return ResponseUltils.success(null, "Xóa permission thành công");
    }
}
