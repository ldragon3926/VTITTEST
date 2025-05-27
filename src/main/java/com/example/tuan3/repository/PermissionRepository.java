package com.example.tuan3.repository;

import com.example.tuan3.dto.permission.PermissionDTO;
import com.example.tuan3.dto.permission.PermissionUserDTO;
import com.example.tuan3.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "select code, name, description from permissions  where status = 1", nativeQuery = true)
    List<PermissionDTO> getAll();

    @Query(value = "select u.username, p.name from users u join user_roles ur on u.id = ur.user_id \n" +
            "join roles r on  ur.role_id  = r.id \n" +
            "join role_permissions rp on r.id   = rp.role_id\n" +
            "join permissions p on rp.permission_id  = p.id  where status = 1", nativeQuery = true)
    List<PermissionUserDTO> getPermissionsUser();

    @Query(value = "select code, name, description from permissions  where status = 1", nativeQuery = true)
    Page<PermissionDTO> getPhanTrang(Pageable pageable);

}
