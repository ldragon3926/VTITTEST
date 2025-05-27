package com.example.tuan3.dto.role;

import com.example.tuan3.entity.Permission;
import com.example.tuan3.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RoleSet {
    private Long id;
    @NotBlank(message = "Không được để tên trống")
    private String name;
    @NotBlank(message = "Không được để mô tả trống")
    private String description;
    private Set<Long> idPermission;
    private boolean status;

    public Role dto(Role role, List<Permission> idPermissionfromDB) {
        role.setId(this.getId());
        role.setName(this.getName());
        role.setDescription(this.getDescription());
        role.setStatus(this.isStatus());
       if(idPermissionfromDB != null ){
            role.setPermissions(new HashSet<>(idPermissionfromDB));
        }
        return role;
    }
}
